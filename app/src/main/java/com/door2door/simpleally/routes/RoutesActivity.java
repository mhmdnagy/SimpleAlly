package com.door2door.simpleally.routes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.door2door.simpleally.R;
import com.door2door.simpleally.utils.schedulers.SchedulerProvider;

public class RoutesActivity extends AppCompatActivity {

    RoutesPresenter mRoutesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        setTitle(getString(R.string.title_routes));

        RoutesFragment fragment = (RoutesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = RoutesFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .addToBackStack("routes_fragment")
                    .commit();
        }

        mRoutesPresenter = new RoutesPresenter(fragment, SchedulerProvider.getInstance());

    }
}
