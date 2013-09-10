package edu.sjsu.sixdegreesofseparation.data;

import edu.sjsu.sixdegreesofseparation.constants.DataType;
import edu.sjsu.sixdegreesofseparation.dao.DatabaseAcessObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jake Karnes
 */
public class DataInserter implements Runnable {

    private final DataType type;
    private final int max_id;
    private final int numThreads;

    /**
     * A DataInsert that will initiate the pulling from the server, collect the
     * results, and initiate the data insertion into the DB.
     *
     * @param type The type of data that is being retrieved.
     * @param max_id The highest ID number to be pulled.
     * @param numThreads The number of threads that will be used to pull
     * information from the server.
     */
    public DataInserter(DataType type, int max_id, int numThreads) {
        this.type = type;
        this.max_id = max_id;
        this.numThreads = numThreads;

    }

    @Override
    public void run() {
        ExecutorService pullers = Executors.newCachedThreadPool();
        Collection<DataPuller> tasks = new ArrayList<>(max_id);
        int limit = (max_id / numThreads);
        //Add all but the last thread
        for (int i = 0; i < (numThreads - 1); i++) {
            tasks.add(new DataPuller(type, limit, (limit * i)));
        }
        //Add last thread that handles the remainder
        int remainder = (max_id % numThreads);
        tasks.add(new DataPuller(type, (limit + remainder), (limit * (numThreads - 1))));
        //This Set will hold all of the results from all threads. Using a set to avoid duplicates.
        Set<RowResult> masterResultSet = null;
        try {
            //Begin all DataPuller threads
            List<Future<List<RowResult>>> results = pullers.invokeAll(tasks);
            //Create an empty list to hold all of the row results.
            masterResultSet = new HashSet<>(max_id);
            for (Future<List<RowResult>> f : results) {
                //Gather the results from each DataPuller
                List<RowResult> smallResultList = f.get();
                for (RowResult row : smallResultList) {
                    //If the row is valid, add it to the complete "Master" list
                    if (row != null) {
                        masterResultSet.add(row);
                    }
                }

            }
            pullers.shutdownNow();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (masterResultSet != null && !masterResultSet.isEmpty()) {
            //Create list from Set
            List<RowResult> masterResultList = new ArrayList<>(masterResultSet);
            //Sort the results by IDs
            Collections.sort(masterResultList);

            for (RowResult result1 : masterResultList) {
                for (RowResult result2 : masterResultList) {
                    if (result1.getId() == result2.getId() && !result1.equals(result2)) {
                        Logger.getLogger(DataInserter.class.getName()).log(Level.SEVERE, "FOUND TWO ROWS WITH THE SAME IDS BUT DIFFERENT VALUES. "
                                + "ID = {0} Result1 values = {1} Result2 values = {2}", new Object[]{result1.getId(), result1.getProperties(), result2.getProperties()});
                    }
                }
            }
            Logger.getLogger(DataInserter.class.getName()).log(Level.INFO, "Total Results returned = {0}", masterResultList.size());
            //Insert all of the results into the database
            DatabaseAcessObject.batchInsert(type, masterResultList);
        }
    }
}
