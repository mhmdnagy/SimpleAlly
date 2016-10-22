package com.door2door.simpleally.routes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.door2door.simpleally.R;
import com.door2door.simpleally.data.pojo.Route;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RoutesFragment extends Fragment implements RoutesContract.View {

    private OnListFragmentInteractionListener mListener;

    //UI
    RecyclerView recyclerView;

    private RoutesContract.Presenter mPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RoutesFragment() {
    }

    @SuppressWarnings("unused")
    public static RoutesFragment newInstance() {
        RoutesFragment fragment = new RoutesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unbind();
    }

    @Override
    public void showRoutes(List<Route> routes) {
        recyclerView.setAdapter(new RoutesRecyclerViewAdapter(routes, mListener, getActivity()));
    }

    @Override
    public void setPresenter(RoutesContract.Presenter presenter) {
        mPresenter = presenter;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Route item);
    }
}
