package com.door2door.simpleally.routes;

import android.support.annotation.NonNull;

import com.door2door.simpleally.data.RoutesDataSource;
import com.door2door.simpleally.data.pojo.Routes;
import com.door2door.simpleally.utils.schedulers.BaseSchedulerProvider;
import com.door2door.simpleally.utils.schedulers.SchedulerProvider;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by vezikon on 10/19/16.
 */

public class RoutesPresenter implements RoutesContract.Presenter {

    private CompositeSubscription mSubscriptions;

    @NonNull
    private final RoutesContract.View mRoutesView;

    private final BaseSchedulerProvider mSchedulerProvider;


    public RoutesPresenter(@NonNull RoutesContract.View routesView,
                           @NonNull BaseSchedulerProvider schedulerProvider) {
        mRoutesView = routesView;
        mSchedulerProvider = schedulerProvider;

        mRoutesView.setPresenter(this);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void bind() {
        loadRoutes();
    }

    @Override
    public void unbind() {
        mSubscriptions.clear();
    }

    public void loadRoutes() {
        RoutesDataSource dataSource = new RoutesDataSource();
        mRoutesView.showRoutes(dataSource.readRoutes().routes);

        mSubscriptions.clear();
        Subscription subscription = dataSource.getRoutes()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<Routes>() {
                    @Override
                    public void onCompleted() {

                        //TODO: add loading indicator
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO: add error message
                    }

                    @Override
                    public void onNext(Routes routes) {
                        mRoutesView.showRoutes(routes.getRoutes());
                    }
                });
        mSubscriptions.add(subscription);
    }
}
