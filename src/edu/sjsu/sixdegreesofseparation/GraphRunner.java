package edu.sjsu.sixdegreesofseparation;

import edu.sjsu.sixdegreesofseparation.constants.Constants;
import edu.sjsu.sixdegreesofseparation.constants.DataType;
import edu.sjsu.sixdegreesofseparation.dao.DatabaseAcessObject;
import edu.sjsu.sixdegreesofseparation.data.RowResult;
import edu.sjsu.sixdegreesofseparation.graph.LabelledEdge;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;


/**
 *
 * @author Jake Karnes
 */
public class GraphRunner {

    public static void main(String[] args) {
        //Get Rows from Database
        RowResult[] actorArray = DatabaseAcessObject.getAllRows(DataType.ACTOR);
        RowResult[] filmArray = DatabaseAcessObject.getAllRows(DataType.FILM);
        RowResult[] performanceArray = DatabaseAcessObject.getAllRows(DataType.PERFORMANCE);

        //Create Graph.
        SimpleGraph<RowResult, LabelledEdge> graph = new SimpleGraph<>(LabelledEdge.class);
        List<RowResult> vertexList = new LinkedList<>();
        //Add vertices
        for (int i = 0; i < actorArray.length; i++) {
            if (actorArray[i] != null) {
                graph.addVertex(actorArray[i]);
                vertexList.add(actorArray[i]);
            }
        }
        for (int i = 0; i < filmArray.length; i++) {
            if (filmArray[i] != null) {
                graph.addVertex(filmArray[i]);
                vertexList.add(filmArray[i]);
            }
        }
        Logger.getLogger(GraphRunner.class.getName()).log(Level.INFO, "Number of vertices in graph before pruning = {0}", vertexList.size());

        //Add edges
        List<LabelledEdge> edgeList = new LinkedList<>();
        for (int i = 0; i < performanceArray.length; i++) {
            if (performanceArray[i] != null) {
                RowResult performance = performanceArray[i];
                int actorid = Integer.parseInt(performance.getProperties().get(Constants.PERFORMANCE_ACTOR_ID));
                int filmid = Integer.parseInt(performance.getProperties().get(Constants.PERFORMANCE_FILM_ID));
                String charName = performance.getProperties().get(Constants.PERFORMANCE_CHAR_NAME);
                LabelledEdge edge = new LabelledEdge(actorArray[actorid], filmArray[filmid], charName);
                graph.addEdge(actorArray[actorid], filmArray[filmid], edge);
                edgeList.add(edge);
            }
        }
        Logger.getLogger(GraphRunner.class.getName()).log(Level.INFO, "Number of edges in graph = {0}", edgeList.size());

        //Prune the graph by remove vertices with no edges. These won't help us.
        for (Iterator<RowResult> it = vertexList.iterator(); it.hasNext();) {
            RowResult row = it.next();
            if (graph.degreeOf(row) == 0) {
                graph.removeVertex(row);
                it.remove();
            }
        }
        Logger.getLogger(GraphRunner.class.getName()).log(Level.INFO, "Number of vertices in graph after pruning vertices with no edges = {0}", vertexList.size());

        //get paths
        //List<GraphPath<RowResult,LabelledEdge>> paths =  getAllPaths(graph,vertexList);
        
        
        //Actors: "The Rock";  "Jack Black"; "Lil J"; "Rosie O'Donnell"; "Sting"; "Hank Azaria";
        //"Marilyn Monroe"; "Olivia Wilde"; "Kevin Bacon"; "Samuel L. Jackson"; "Audrey Hepburn";
        //"Will Smith"; "Tom Hanks";

        String actor1Name = "Anna Kendrick";
        RowResult actor1 = getActorFromName(vertexList, actor1Name);
        if (actor1 == null) {
            Logger.getLogger(GraphRunner.class.getName()).log(Level.SEVERE, "Couldn''t find an actor with the name {0}", actor1Name);
        }
        String actor2Name = "Kevin Bacon";
        RowResult actor2 = getActorFromName(vertexList, actor2Name);
        if (actor2 == null) {
            Logger.getLogger(GraphRunner.class.getName()).log(Level.SEVERE, "Couldn''t find an actor with the name {0}", actor2Name);
        }
        if (actor1 != null && actor2 != null) {
            DijkstraShortestPath<RowResult, LabelledEdge> shortestPath = new DijkstraShortestPath(graph, actor1, actor2);
            GraphPath<RowResult, LabelledEdge> path = shortestPath.getPath();
            if (path
                    == null) {
                Logger.getLogger(GraphRunner.class.getName()).log(Level.WARNING, "No connection between {0} and {1}", new Object[]{actor1.getProperties().get(Constants.ACTOR_NAME), actor2.getProperties().get(Constants.ACTOR_NAME)});
            } else {
                String formatted = getPrettyPath(path);
                Logger.getLogger(GraphRunner.class.getName()).log(Level.INFO, formatted);
            }
        }
    }

    private static List<GraphPath<RowResult, LabelledEdge>> getAllPaths(SimpleGraph<RowResult, LabelledEdge> graph, List<RowResult> vertexList) {

        List<GraphPath<RowResult, LabelledEdge>> paths = new LinkedList<>();
        for (RowResult vertex1 : vertexList) {
            for (RowResult vertex2 : vertexList) {
                if (vertex1.getType() == DataType.ACTOR && vertex2.getType() == DataType.ACTOR
                        && !vertex1.equals(vertex2)) {
                    DijkstraShortestPath<RowResult, LabelledEdge> shortestPath = new DijkstraShortestPath(graph, vertex1, vertex2);
                    GraphPath<RowResult, LabelledEdge> path = shortestPath.getPath();


                    if (path != null) {
                        Logger.getLogger(GraphRunner.class
                                .getName()).log(Level.INFO, "Added path from {0} to {1}", new Object[]{vertex1.getProperties()
                            .get(Constants.ACTOR_NAME), vertex2.getProperties().get(Constants.ACTOR_NAME)});
                        paths.add(path);


                    }
                }
            }
        }
        Logger.getLogger(GraphRunner.class
                .getName()).log(Level.INFO, "Number of computed paths before pruning = {0}", paths.size());
        //remove duplicate paths. 
        for (GraphPath path1 : paths) {
            for (Iterator<GraphPath<RowResult, LabelledEdge>> it = paths.iterator(); it.hasNext();) {
                GraphPath<RowResult, LabelledEdge> path2 = it.next();
                if (path1.getStartVertex().equals(path2.getEndVertex()) && path1.getEndVertex().equals(path2.getStartVertex())) {
                    it.remove();
                }
            }
        }

        Logger.getLogger(GraphRunner.class
                .getName()).log(Level.INFO, "Number of computed paths after pruning = {0}", paths.size());
        return paths;
    }

    private static RowResult getActorFromName(List<RowResult> vertexList, String actorName) {
        for (RowResult row : vertexList) {
            if (row.getType() == DataType.ACTOR && row.getProperties().get(Constants.ACTOR_NAME).equalsIgnoreCase(actorName)) {
                return row;
            }
        }
        return null;
    }

    private static String getPrettyPath(GraphPath<RowResult, LabelledEdge> path) {
        String formatted = "";
        for (int i = 0; i < path.getEdgeList().size(); i++) {
            LabelledEdge edge = path.getEdgeList().get(i);
            if (i == 0) {
                RowResult actor = (RowResult) edge.getActor();
                String actorName = actor.getProperties().get(Constants.ACTOR_NAME);
                RowResult film = (RowResult) edge.getFilm();
                String filmName = film.getProperties().get(Constants.FILM_NAME);
                if (edge.getCharacterName().isEmpty()) {
                    formatted += actorName + " who was in \"" + filmName + "\" with ";
                } else {
                    formatted += actorName + " who played \"" + edge.getCharacterName() + "\" in \"" + filmName + "\" with ";
                }
            } else if (i == path.getEdgeList().size() - 1) {
                RowResult actor = (RowResult) edge.getActor();
                String actorName = actor.getProperties().get(Constants.ACTOR_NAME);
                if (edge.getCharacterName().isEmpty()) {
                    formatted += actorName + ".";
                } else {
                    formatted += actorName + " who played \"" + edge.getCharacterName() + ".\"";
                }
            } else if (i % 2 == 1) {
                RowResult actor = (RowResult) edge.getActor();
                String actorName = actor.getProperties().get(Constants.ACTOR_NAME);
                if (edge.getCharacterName().isEmpty()) {
                    formatted += actorName + " who was in ";
                } else {
                    formatted += actorName + " who played \"" + edge.getCharacterName() + "\" in ";
                }
            } else if (i % 2 == 0) {
                RowResult film = (RowResult) edge.getFilm();
                String filmName = film.getProperties().get(Constants.FILM_NAME);
                formatted += "\"" + filmName + "\" with ";
            }
        }
        return formatted;
    }
}

