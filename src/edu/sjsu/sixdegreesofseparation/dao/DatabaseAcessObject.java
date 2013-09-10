package edu.sjsu.sixdegreesofseparation.dao;

import edu.sjsu.sixdegreesofseparation.constants.Constants;
import edu.sjsu.sixdegreesofseparation.constants.DataType;
import edu.sjsu.sixdegreesofseparation.data.DataHandler;
import edu.sjsu.sixdegreesofseparation.data.RowResult;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jake Karnes
 */
public class DatabaseAcessObject {

    public static void batchInsert(DataType type, List<RowResult> masterResultList) {
        if (masterResultList == null || masterResultList.isEmpty()) {
            return;
        }
        switch (type) {
            case ACTOR:
                batchActorInsert(masterResultList);
                break;
            case FILM:
                batchFilmInsert(masterResultList);
                break;
            case PERFORMANCE:
                batchPerfomanceInsert(masterResultList);
                break;
        }
    }

    private static void batchActorInsert(List<RowResult> masterResultList) {
        String url = Constants.DB_URL;
        String dbName = Constants.DB_NAME;
        String driver = Constants.DB_DRIVER;
        String userName = Constants.DB_USER;
        String password = Constants.DB_PASS;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "INSERT into actor (actorid, name) values (?, ?)";
            ps = conn.prepareStatement(sql);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO CONNECT TO DATBASE!", ex);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        final int batchSize = 1000;
        int count = 0;

        for (RowResult result : masterResultList) {
            try {
                ps.setInt(1, result.getId());
                HashMap<String, String> properties = result.getProperties();
                String actorName = properties.get(Constants.ACTOR_NAME);
                ps.setString(2, actorName);

                ps.addBatch();
                count++;
                if (count == batchSize) {
                    ps.executeBatch();
                    count = 0;
                }

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
            }
        }
        try {
            ps.executeBatch(); // insert remaining records
            ps.close();
            conn.close();
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.INFO, "{0} rows were inserted into the ACTOR table.", masterResultList.size());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void batchFilmInsert(List<RowResult> masterResultList) {
        String url = Constants.DB_URL;
        String dbName = Constants.DB_NAME;
        String driver = Constants.DB_DRIVER;
        String userName = Constants.DB_USER;
        String password = Constants.DB_PASS;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "INSERT into film (filmid, name) values (?, ?)";
            ps = conn.prepareStatement(sql);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO CONNECT TO DATBASE!", ex);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        final int batchSize = 1000;
        int count = 0;

        for (RowResult result : masterResultList) {
            try {
                ps.setInt(1, result.getId());
                HashMap<String, String> properties = result.getProperties();
                String filmName = properties.get(Constants.FILM_NAME);
                ps.setString(2, filmName);

                ps.addBatch();
                count++;
                if (count == batchSize) {
                    ps.executeBatch();
                    count = 0;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
            }
        }
        try {
            ps.executeBatch(); // insert remaining records
            ps.close();
            conn.close();
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.INFO, "{0} rows were inserted into the FILM table.", masterResultList.size());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void batchPerfomanceInsert(List<RowResult> masterResultList) {

        String url = Constants.DB_URL;
        String dbName = Constants.DB_NAME;
        String driver = Constants.DB_DRIVER;
        String userName = Constants.DB_USER;
        String password = Constants.DB_PASS;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            String sql = "INSERT into performance (performanceid, actorid, filmid,char_name) values (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO CONNECT TO DATBASE!", ex);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        final int batchSize = 1000;
        int count = 0;

        for (RowResult result : masterResultList) {
            try {
                ps.setInt(1, result.getId());
                HashMap<String, String> properties = result.getProperties();
                String actorid = properties.get(Constants.PERFORMANCE_ACTOR_ID);
                ps.setString(2, actorid);
                String filmid = properties.get(Constants.PERFORMANCE_FILM_ID);
                ps.setString(3, filmid);
                String char_name = properties.get(Constants.PERFORMANCE_CHAR_NAME);
                ps.setString(4, char_name == null ? "" : char_name);

                ps.addBatch();
                count++;
                if (count == batchSize) {
                    ps.executeBatch();
                    count = 0;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
            }
        }
        try {
            ps.executeBatch(); // insert remaining records
            ps.close();
            conn.close();
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.INFO, "{0} rows were inserted into the PERFORMANCE table.", masterResultList.size());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO INSERT ROW(S) INTO DB!", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static RowResult[] getAllRows(DataType dataType) {

        String url = Constants.DB_URL;
        String dbName = Constants.DB_NAME;
        String driver = Constants.DB_DRIVER;
        String userName = Constants.DB_USER;
        String password = Constants.DB_PASS;
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO CONNECT TO DATBASE!", ex);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex2) {
                    Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            if (conn == null) {
                return null;
            }
            String sql = "SELECT * FROM ";
            switch (dataType) {
                case ACTOR:
                    sql += "actor ;";
                    break;
                case FILM:
                    sql += "film ;";
                    break;
                case PERFORMANCE:
                    sql += "performance ;";
                    break;
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();
            RowResult[] resultList = DataHandler.getResultList(dataType, results);
            ps.close();
            conn.close();
            return resultList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAcessObject.class.getName()).log(Level.SEVERE, "FAILED TO EXECUTE SELECT QUERY!", ex);
            return null;
        }

    }
}
