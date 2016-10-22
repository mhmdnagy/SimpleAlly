package com.door2door.simpleally.data;

import com.door2door.simpleally.data.pojo.Routes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Created by vezikon on 10/19/16.
 */

public class RoutesDataSource {


    public Routes readRoutes(){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(RoutesJson.ROUTES_JSON, Routes.class);
    }
}
