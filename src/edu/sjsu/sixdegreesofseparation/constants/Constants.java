package edu.sjsu.sixdegreesofseparation.constants;

/**
 *
 * @author Jake Karnes
 */
public class Constants {

    public static String LINKEDMDB_SPARQL_ENTRY = "http://data.linkedmdb.org/sparql?query=";
    
    public static String PERFORMANCE_QUERY_PT_1 = "PREFIX+owl%3A+<http%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23>%0D%0APREFIX+xsd%3A+<http%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23>%0D%0A"
            + "PREFIX+rdfs%3A+<http%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23>%0D%0APREFIX+rdf%3A+<http%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23>%0D%0A"
            + "PREFIX+foaf%3A+<http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F>%0D%0APREFIX+oddlinker%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Foddlinker%2F>%0D%0A"
            + "PREFIX+map%3A+<file%3A%2FC%3A%2Fd2r-server-0.4%2Fmapping.n3%23>%0D%0APREFIX+db%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2F>%0D%0A"
            + "PREFIX+dbpedia%3A+<http%3A%2F%2Fdbpedia.org%2Fproperty%2F>%0D%0APREFIX+skos%3A+<http%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23>%0D%0A"
            + "PREFIX+dc%3A+<http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F>%0D%0APREFIX+movie%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fmovie%2F>%0D%0A"
            + "SELECT++%3Fperformance_id+%3Factor_id+%3Ffilm_id+%3Fchar_name%0D%0AWHERE+%7B%0D%0A+++<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fperformance%2F";
    public static String PERFORMANCE_QUERY_PT_2 = ">+movie%3Aperformance_performanceid+%3Fperformance_id+.%0D%0A+++<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fperformance%2F";
    public static String PERFORMANCE_QUERY_PT_3 = ">+movie%3Aperformance_character+%3Fchar_name+.%0D%0A+++%3Factor+movie%3Aperformance+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fperformance%2F";
    public static String PERFORMANCE_QUERY_PT_4 = ">+.%0D%0A+++%3Factor+movie%3Aactor_actorid+%3Factor_id+.%0D%0A+++%3Ffilm+movie%3Aperformance+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fperformance%2F";
    public static String PERFORMANCE_QUERY_PT_5 = ">+.%0D%0A+++%3Ffilm+movie%3Afilmid+%3Ffilm_id+.%0D%0A%7D&output=json";
    public static String PERFORMANCE_ID = "performanceid";
    public static String PERFORMANCE_ACTOR_ID = "actorid";
    public static String PERFORMANCE_FILM_ID = "filmid";
    public static String PERFORMANCE_CHAR_NAME = "char_name";
   
    public static String ACTOR_QUERY_PT_1 = "PREFIX+owl%3A+<http%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23>%0D%0A"
            + "PREFIX+xsd%3A+<http%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23>%0D%0A"
            + "PREFIX+rdfs%3A+<http%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23>%0D%0A"
            + "PREFIX+rdf%3A+<http%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23>%0D%0A"
            + "PREFIX+foaf%3A+<http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F>%0D%0A"
            + "PREFIX+oddlinker%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Foddlinker%2F>%0D%0A"
            + "PREFIX+map%3A+<file%3A%2FC%3A%2Fd2r-server-0.4%2Fmapping.n3%23>%0D%0A"
            + "PREFIX+db%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2F>%0D%0A"
            + "PREFIX+dbpedia%3A+<http%3A%2F%2Fdbpedia.org%2Fproperty%2F>%0D%0A"
            + "PREFIX+skos%3A+<http%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23>%0D%0A"
            + "PREFIX+dc%3A+<http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F>%0D%0A"
            + "PREFIX+movie%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fmovie%2F>%0D%0A"
            + "SELECT+%3Factor_id+%3Factor_name+WHERE+%7B%0D%0A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Factor%2F";
    public static String ACTOR_QUERY_PT_2 = ">+movie%3Aactor_actorid+%3Factor_id+.%0D%0A+++<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Factor%2F";
    public static String ACTOR_QUERY_PT_3 = ">+movie%3Aactor_name+%3Factor_name+.%0D%0A%7D&output=json";
    public static String ACTOR_ID = "actorid";
    public static String ACTOR_NAME = "name";
    
    public static String FILM_QUERY_PT_1 = "PREFIX+owl%3A+<http%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23>%0D%0A"
            + "PREFIX+xsd%3A+<http%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23>%0D%0A"
            + "PREFIX+rdfs%3A+<http%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23>%0D%0A"
            + "PREFIX+rdf%3A+<http%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23>%0D%0A"
            + "PREFIX+foaf%3A+<http%3A%2F%2Fxmlns.com%2Ffoaf%2F0.1%2F>%0D%0A"
            + "PREFIX+oddlinker%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Foddlinker%2F>%0D%0A"
            + "PREFIX+map%3A+<file%3A%2FC%3A%2Fd2r-server-0.4%2Fmapping.n3%23>%0D%0A"
            + "PREFIX+db%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2F>%0D%0A"
            + "PREFIX+dbpedia%3A+<http%3A%2F%2Fdbpedia.org%2Fproperty%2F>%0D%0A"
            + "PREFIX+skos%3A+<http%3A%2F%2Fwww.w3.org%2F2004%2F02%2Fskos%2Fcore%23>%0D%0A"
            + "PREFIX+dc%3A+<http%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F>%0D%0A"
            + "PREFIX+movie%3A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Fmovie%2F>%0D%0A"
            + "SELECT+%3Ffilm_id+%3Ffilm_name+WHERE+%7B%0D%0A+<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Ffilm%2F";
    public static String FILM_QUERY_PT_2 = ">+movie%3Afilmid+%3Ffilm_id+.%0D%0A+++<http%3A%2F%2Fdata.linkedmdb.org%2Fresource%2Ffilm%2F";
    public static String FILM_QUERY_PT_3 = ">+dc%3Atitle+%3Ffilm_name+.%0D%0A%7D&output=json";
    public static String FILM_ID = "filmid";
    public static String FILM_NAME = "name";
    
    public static String DB_PASS = "root";
    public static String DB_USER = "root";
    public static String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static String DB_NAME = "test";
    public static String DB_URL = "jdbc:mysql://localhost:3306/";
    
    //rounded up
    public static int ACTOR_MAX_ID = 70000;
    public static int FILM_MAX_ID = 100000;
    public static int PERF_MAX_ID= 200000;
    
}
