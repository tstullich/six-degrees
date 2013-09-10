package edu.sjsu.sixdegreesofseparation.data;

import edu.sjsu.sixdegreesofseparation.constants.Constants;
import edu.sjsu.sixdegreesofseparation.constants.DataType;
import static edu.sjsu.sixdegreesofseparation.constants.DataType.ACTOR;
import static edu.sjsu.sixdegreesofseparation.constants.DataType.FILM;
import static edu.sjsu.sixdegreesofseparation.constants.DataType.PERFORMANCE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Jake Karnes
 */
public class DataHandler {

    /**
     * Create and return the SPARQL Query for the given DataType and ID.
     *
     * @param type The DataType to get a SPARQL Query for.
     * @param id The ID to get a SPARQL Query for.
     * @return A String containing the SPARQL Query for the given Type and ID.
     */
    public static String getSPARQLQUERY(DataType type, int id) {
        switch (type) {
            case ACTOR:
                return getActorSPARQLQUERY(id);
            case FILM:
                return getFilmSPARQLQUERY(id);
            case PERFORMANCE:
                return getPerformanceSPARQLQUERY(id);
            default:
                return "";
        }
    }

    /**
     *
     * @param type The DataType of the Re
     * @param resultJSONString
     * @param id
     * @return
     */
    public static RowResult getResult(DataType type, String resultJSONString, int id) {
        if (resultJSONString == null || resultJSONString.isEmpty()) {
            return null;
        }
        switch (type) {
            case ACTOR:
                return getActorResult(resultJSONString, id);
            case FILM:
                return getFilmResult(resultJSONString, id);
            case PERFORMANCE:
                return getPerformanceResult(resultJSONString, id);
            default:
                return null;
        }
    }

    private static String getActorSPARQLQUERY(int id) {
        return Constants.ACTOR_QUERY_PT_1 + (id)
                + Constants.ACTOR_QUERY_PT_2 + (id)
                + Constants.ACTOR_QUERY_PT_3;
    }

    private static String getFilmSPARQLQUERY(int id) {
        return Constants.FILM_QUERY_PT_1 + (id)
                + Constants.FILM_QUERY_PT_2 + (id)
                + Constants.FILM_QUERY_PT_3;
    }

    private static String getPerformanceSPARQLQUERY(int id) {
        return Constants.PERFORMANCE_QUERY_PT_1 + (id)
                + Constants.PERFORMANCE_QUERY_PT_2 + (id)
                + Constants.PERFORMANCE_QUERY_PT_3 + (id)
                + Constants.PERFORMANCE_QUERY_PT_4 + (id)
                + Constants.PERFORMANCE_QUERY_PT_5;
    }

    private static RowResult getActorResult(String resultJSONString, int id) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(resultJSONString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resultsObject = (JSONObject) jsonObject.get("results");
            JSONArray bindingsArray = (JSONArray) resultsObject.get("bindings");
            if (bindingsArray.size() == 0) {
                //There were no results returned for this id. 
                return new RowResult(DataType.ACTOR, id, null);
            }
            JSONObject actorObject = (JSONObject) bindingsArray.get(0);

            //get actor actor_id
            JSONObject actorIDObject = (JSONObject) actorObject.get("actor_id");
            int actor_id = Integer.parseInt((String) actorIDObject.get("value"));

            //create map for other properties.
            HashMap<String, String> properties = new HashMap<>();

            //get actor actor_name
            JSONObject actorNameObject = (JSONObject) actorObject.get("actor_name");
            String actorNameValue = (String) actorNameObject.get("value");
            properties.put(Constants.ACTOR_NAME, actorNameValue);

            return new RowResult(DataType.ACTOR, actor_id, properties);
        } catch (Exception ex) {
            //If this is reached, we'll repeat.
            Logger.getLogger(DataHandler.class.getName()).log(Level.WARNING, "JSON threw an exception! This row will repeated. ID = " + id + " JSON = \"" + resultJSONString + "\"", ex);
            return null;
        }

    }

    private static RowResult getFilmResult(String resultJSONString, int id) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(resultJSONString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resultsObject = (JSONObject) jsonObject.get("results");
            JSONArray bindingsArray = (JSONArray) resultsObject.get("bindings");
            if (bindingsArray.size() == 0) {
                //There were no results returned for this id. 
                return new RowResult(DataType.FILM, id, null);
            }
            JSONObject filmObject = (JSONObject) bindingsArray.get(0);

            //get film film_id
            JSONObject filmIDObject = (JSONObject) filmObject.get("film_id");
            int film_id = Integer.parseInt((String) filmIDObject.get("value"));

            //create map for other properties.
            HashMap<String, String> properties = new HashMap<>();

            //get film film_name
            JSONObject filmNameObject = (JSONObject) filmObject.get("film_name");
            String filmNameValue = (String) filmNameObject.get("value");
            properties.put(Constants.FILM_NAME, filmNameValue);

            return new RowResult(DataType.FILM, film_id, properties);
        } catch (Exception ex) {
            //If this is reached, we'll repeat.
            Logger.getLogger(DataHandler.class.getName()).log(Level.WARNING, "JSON threw an exception! This row will repeated. ID = " + id + " JSON = \"" + resultJSONString + "\"", ex);
            return null;
        }
    }

    private static RowResult getPerformanceResult(String resultJSONString, int id) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(resultJSONString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resultsObject = (JSONObject) jsonObject.get("results");
            JSONArray bindingsArray = (JSONArray) resultsObject.get("bindings");
            if (bindingsArray.size() == 0) {
                //There were no results returned for this id. 
                return new RowResult(DataType.PERFORMANCE, id, null);
            }
            JSONObject propertiesObject = (JSONObject) bindingsArray.get(0);

            //get performance performance_id
            JSONObject performanceIDObject = (JSONObject) propertiesObject.get("performance_id");
            int performance_id = Integer.parseInt((String) performanceIDObject.get("value"));

            //create map for other properties.
            HashMap<String, String> properties = new HashMap<>();

            //get actor performance_id
            JSONObject actorIDObject = (JSONObject) propertiesObject.get("actor_id");
            String actorIDValue = (String) actorIDObject.get("value");
            properties.put(Constants.PERFORMANCE_ACTOR_ID, actorIDValue);

            //get film performance_id
            JSONObject filmIDObject = (JSONObject) propertiesObject.get("film_id");
            String filmIDValue = (String) filmIDObject.get("value");
            properties.put(Constants.PERFORMANCE_FILM_ID, filmIDValue);

            //get char name
            JSONObject charNameObject = (JSONObject) propertiesObject.get("char_name");
            String charNameValue = (String) charNameObject.get("value");
            properties.put(Constants.PERFORMANCE_CHAR_NAME, charNameValue);

            return new RowResult(DataType.PERFORMANCE, performance_id, properties);
        } catch (Exception ex) {
            //If this is reached, we'll repeat.
            Logger.getLogger(DataHandler.class.getName()).log(Level.WARNING, "JSON threw an exception! This row will repeated. ID = " + id + " JSON = \"" + resultJSONString + "\"", ex);
            return null;
        }

    }

    /**
     * Determines if the JSONString is valid by attempting to parse it.
     *
     * @param JSONString
     * @return True, if JSONString is valid JSON. False, otherwise.
     */
    public static boolean isJSONStringValid(DataType type, String JSONString) {
        if (JSONString.isEmpty() || JSONString.length() < 5) {
            return false;
        }
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(JSONString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject resultsObject = (JSONObject) jsonObject.get("results");
            JSONArray bindingsArray = (JSONArray) resultsObject.get("bindings");
            //If this statement is reached, JSON was parsed and is valid.
            return true;
        } catch (Exception ex) {
            //JSON value is invalid. So return false.
            return false;
        }
    }

    public static RowResult[] getResultList(DataType dataType, ResultSet results) throws SQLException {
        switch (dataType) {
            case ACTOR:
                return getActorResultList(results);
            case FILM:
                return getFilmResultList(results);
            case PERFORMANCE:
                return getPerformanceResultList(results);
            default: return null;
        }
    }

    private static RowResult[] getActorResultList(ResultSet results) throws SQLException {
       RowResult[] actorArray = new RowResult[Constants.ACTOR_MAX_ID];
       //for every returned result.
       while(results.next()){
           int id = results.getInt(Constants.ACTOR_ID);
           String name = results.getString(Constants.ACTOR_NAME);
           HashMap<String,String> properties = new HashMap<>();
           properties.put(Constants.ACTOR_NAME, name);
           RowResult row = new RowResult(DataType.ACTOR, id, properties);
           actorArray[id] = row;
       }
       return actorArray;
    }

    private static RowResult[] getFilmResultList(ResultSet results) throws SQLException {
        RowResult[] filmArray = new RowResult[Constants.FILM_MAX_ID];
       //for every returned result.
       while(results.next()){
           int id = results.getInt(Constants.FILM_ID);
           String name = results.getString(Constants.FILM_NAME);
           HashMap<String,String> properties = new HashMap<>();
           properties.put(Constants.FILM_NAME, name);
           RowResult row = new RowResult(DataType.FILM, id, properties);
           filmArray[id] = row;
       }
       return filmArray;
    }

    private static RowResult[] getPerformanceResultList(ResultSet results) throws SQLException {
        RowResult[] performanceArray = new RowResult[Constants.PERF_MAX_ID];
       //for every returned result.
       while(results.next()){
           int id = results.getInt(Constants.PERFORMANCE_ID);
           int actorid = results.getInt(Constants.PERFORMANCE_ACTOR_ID);
           int filmid = results.getInt(Constants.PERFORMANCE_FILM_ID);
           String characterName = results.getString(Constants.PERFORMANCE_CHAR_NAME);
           HashMap<String,String> properties = new HashMap<>();
           properties.put(Constants.PERFORMANCE_ACTOR_ID, Integer.toString(actorid));
           properties.put(Constants.PERFORMANCE_FILM_ID, Integer.toString(filmid));
           properties.put(Constants.PERFORMANCE_CHAR_NAME, characterName);
           RowResult row = new RowResult(DataType.PERFORMANCE, id, properties);
           performanceArray[id] = row;
       }
       return performanceArray;
    }
}
