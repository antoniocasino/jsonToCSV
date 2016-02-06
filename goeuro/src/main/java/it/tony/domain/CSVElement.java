package it.tony.domain;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by casinan on 05/02/2016.
 */
public class CSVElement implements Serializable{
    private long id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString(){
        return id+","+name+","+type+","+latitude+","+longitude;
    }
}
