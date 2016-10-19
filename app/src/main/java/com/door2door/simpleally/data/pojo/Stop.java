
package com.door2door.simpleally.data.pojo;


public class Stop {

    private Float lat;
    private Float lng;
    private String datetime;
    private String name;
    private Object properties;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

}
