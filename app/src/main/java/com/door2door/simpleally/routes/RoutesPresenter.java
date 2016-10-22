package com.door2door.simpleally.routes;

import android.support.annotation.NonNull;

import com.door2door.simpleally.data.RoutesDataSource;
import com.door2door.simpleally.data.pojo.Routes;

/**
 * Created by vezikon on 10/19/16.
 */

public class RoutesPresenter implements RoutesContract.Presenter {

    private Routes routes;

    @NonNull
    private final RoutesContract.View mRoutesView;

    public RoutesPresenter(@NonNull RoutesContract.View routesView) {
        mRoutesView = routesView;
        mRoutesView.setPresenter(this);
    }

    @Override
    public void bind() {
        loadRoutes();
    }

    @Override
    public void unbind() {

    }

    private void loadRoutes(){
        RoutesDataSource dataSource = new RoutesDataSource();
        mRoutesView.showRoutes(dataSource.readRoutes().routes);
    }
}
