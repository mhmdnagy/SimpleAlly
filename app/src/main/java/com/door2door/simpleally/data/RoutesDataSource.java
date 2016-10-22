package com.door2door.simpleally.data;

import com.door2door.simpleally.data.pojo.Routes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import rx.Observable;


/**
 * Created by vezikon on 10/19/16.
 */

public class RoutesDataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    public Routes readRoutes(){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(RoutesJson.ROUTES_JSON, Routes.class);
    }

    public Observable<Routes> getRoutes(){
        Gson gson = new GsonBuilder().create();
        Routes routes =  gson.fromJson(RoutesJson.ROUTES_JSON, Routes.class);

        return Observable.just(routes).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS);

    }
}
