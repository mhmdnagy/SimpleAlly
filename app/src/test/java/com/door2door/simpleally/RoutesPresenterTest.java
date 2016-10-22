package com.door2door.simpleally;

/**
 * Created by vezikon on 10/23/16.
 */

import com.door2door.simpleally.data.RoutesDataSource;
import com.door2door.simpleally.data.pojo.Routes;
import com.door2door.simpleally.routes.RoutesContract;
import com.door2door.simpleally.routes.RoutesPresenter;
import com.door2door.simpleally.utils.schedulers.BaseSchedulerProvider;
import com.door2door.simpleally.utils.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.Mockito.when;

public class RoutesPresenterTest {

    private static Routes ROUTES;

    @Mock
    private RoutesDataSource mRoutesDataSource;

    @Mock
    private RoutesContract.View mRoutesView;

    private BaseSchedulerProvider mSchedulerProvider;

    private RoutesPresenter mRoutesPresenter;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();

        mRoutesPresenter = new RoutesPresenter(mRoutesView, mSchedulerProvider);

        ROUTES = mRoutesDataSource.readRoutes();
    }

    @Test
    public void loadAllTasksFromRepositoryAndLoadIntoView() {
        when(mRoutesDataSource.getRoutes()).thenReturn(Observable.just(ROUTES));

    }
}
