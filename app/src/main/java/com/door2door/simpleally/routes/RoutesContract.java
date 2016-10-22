package com.door2door.simpleally.routes;

import com.door2door.simpleally.BasePresenter;
import com.door2door.simpleally.BaseView;
import com.door2door.simpleally.data.pojo.Route;

import java.util.List;

/**
 * Created by vezikon on 10/19/16.
 */

public interface RoutesContract {

    interface View extends BaseView<Presenter> {
        void showRoutes(List<Route> routes);
    }


    interface Presenter extends BasePresenter {

    }
}
