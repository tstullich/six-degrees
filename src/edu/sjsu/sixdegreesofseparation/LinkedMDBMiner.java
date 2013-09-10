package edu.sjsu.sixdegreesofseparation;

import edu.sjsu.sixdegreesofseparation.constants.Constants;
import edu.sjsu.sixdegreesofseparation.constants.DataType;
import edu.sjsu.sixdegreesofseparation.data.DataInserter;

/**
 * Hello world!
 *
 */
public class LinkedMDBMiner {

    public static void main(String[] args) {
        DataInserter actorInserter = new DataInserter(DataType.ACTOR, Constants.ACTOR_MAX_ID, 8);
        actorInserter.run();
        DataInserter filmInserter = new DataInserter(DataType.FILM, Constants.FILM_MAX_ID, 8);
        filmInserter.run();
        DataInserter performanceInserter = new DataInserter(DataType.PERFORMANCE, Constants.PERF_MAX_ID, 8);
        performanceInserter.run();
    }
}
