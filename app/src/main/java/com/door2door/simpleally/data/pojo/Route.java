
package com.door2door.simpleally.data.pojo;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public String type;
    public String provider;
    public List<Segment> segments = new ArrayList<>();
    public Properties properties;
    public Price price;


}
