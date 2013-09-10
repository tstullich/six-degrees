package edu.sjsu.sixdegreesofseparation.data;

import edu.sjsu.sixdegreesofseparation.constants.Constants;
import edu.sjsu.sixdegreesofseparation.constants.DataType;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jake Karnes
 */
public class DataPuller implements Callable<List<RowResult>> {

    private final DataType type;
    private final int limit;
    private final int offset;

    public DataPuller(DataType type, int limit, int offset) {
        this.type = type;
        this.limit = limit;
        this.offset = offset;

    }

    @Override
    public List<RowResult> call() throws Exception {
        //Create empty result list
        List<RowResult> resultList = new ArrayList<>();
        for (int id = offset; id < offset + limit; id++) {

            //Get the SPARQL Query for this Type with this ID
            String SPARQLQuery = DataHandler.getSPARQLQUERY(type, id);

            String resultJSONString = null;
            RowResult result = null;
            //Repeat until we have a valid result. Empty or not. 
            while (result == null) {
                //pull JSON from linked MDB through SPARQL for "type" with particular ID
                resultJSONString = getJSON(type, Constants.LINKEDMDB_SPARQL_ENTRY + SPARQLQuery);

                //create Result object from the pulled JSON
                result = DataHandler.getResult(type, resultJSONString, id);
            }
            //if the properties aren't empty, add the result
            if (result != null && result.getProperties() != null && !result.getProperties().isEmpty()) {
                resultList.add(result);
            } else {
                Logger.getLogger(DataHandler.class.getName()).log(Level.INFO, "ID {0} had no results returned.", id);
            }

        }
        //return result list
        Logger.getLogger(DataHandler.class.getName()).log(Level.INFO, "Call with offset {0} has completed!", offset);
        return resultList;
    }

    /**
     * Connect to the given URL and return the server's response in a valid JSON
     * format as a String, blocking if necessary. This method will continually
     * attempt to connect to the server until valid (parsable) JSON. If the
     * server returns malformed JSON, responses with an error, or is down, this
     * method will continue attempting to connect until the expected result is
     * received.
     *
     * @param urlString The URL that is excepted to return JSON.
     * @return The server's response, in a valid JSON format.
     * @throws MalformedURLException Throw an exception if the URL is invalid.
     */
    private String getJSON(DataType type, String urlString) {
        String JSONString = null;
        //Keep attempting to connect until JSON is successfully and fully returned.
        while (JSONString == null) {
            try {
                URL url = new URL(urlString);
                URLConnection connection = url.openConnection();
                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                JSONString = getStringFromInputStream(in);
                //Check if we recieved fully, parsable JSON
                if (!DataHandler.isJSONStringValid(type, JSONString)) {
                    //If JSON isn't valid, set it to null so we'll try again.
                    JSONString = null;
                }
                in.close();
            } catch (IOException ex) {
                //Leave JSON as null, causing loop to repeat.
                JSONString = null;
            }

        }
        return JSONString;
    }

    /**
     * Dump the entire contents of the BufferedInputStream into a String.
     *
     * @param is The BufferedInputStream to be read.
     * @return A String holding the contents of the BufferedInputStream.
     * @throws IOException
     */
    private String getStringFromInputStream(BufferedInputStream is) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return sb.toString();
    }
}
