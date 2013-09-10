package edu.sjsu.sixdegreesofseparation.data;

import edu.sjsu.sixdegreesofseparation.constants.DataType;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Jake Karnes
 */
public class RowResult implements Comparable<RowResult> {

    private final DataType type;
    private final int id;
    private final HashMap<String, String> properties;

    public RowResult(DataType type, int id, HashMap<String, String> properties) {
        this.type = type;
        this.id = id;
        this.properties = properties;
    }

    @Override
    public int compareTo(RowResult other) {
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(other.id));
    }

    public int getId() {
        return id;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public DataType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ID = " + Integer.toString(id) + " Values = "+properties.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.properties);
        return hash;
    }
    
    
}
