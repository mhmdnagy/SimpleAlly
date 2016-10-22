
package com.door2door.simpleally.data.pojo;

import java.util.ArrayList;
import java.util.List;


public class Segment {


    private String name;
    private Integer num_stops;
    private List<Stop> stops = new ArrayList<Stop>();
    private String travel_mode;
    private Object description;
    private String color;
    private String icon_url;
    private String polyline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumStops() {
        return num_stops;
    }

    public void setNumStops(Integer numStops) {
        this.num_stops = numStops;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }
}
