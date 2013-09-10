package edu.sjsu.sixdegreesofseparation.graph;

import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author Jake Karnes
 */
public class LabelledEdge<RowResult> extends DefaultEdge {

    private RowResult actor;
    private RowResult film;
    private String characterName;

    public LabelledEdge(RowResult actor, RowResult film, String characterName) {
        this.actor = actor;
        this.film = film;
        this.characterName = characterName;
    }

    public RowResult getActor() {
        return actor;
    }

    public RowResult getFilm() {
        return film;
    }

    public String getCharacterName() {
        if (characterName == null) {
            return "";
        }
        return characterName;
    }
}