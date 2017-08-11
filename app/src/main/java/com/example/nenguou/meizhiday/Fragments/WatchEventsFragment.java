package com.example.nenguou.meizhiday.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nenguou.meizhiday.R;

public class WatchEventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout watch_events_swiperefreshlayout;
    private RecyclerView watch_events_recyclerview;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        initViews();

    }

    private void initViews() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        watch_events_recyclerview.setLayoutManager(linearLayoutManager);
        watch_events_recyclerview.setHasFixedSize(true);
        watch_events_swiperefreshlayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);

    }

    private void initId() {
        watch_events_swiperefreshlayout = (SwipeRefreshLayout) getView().findViewById(R.id.watch_events_swiperefreshlayout);
        watch_events_recyclerview = (RecyclerView) getView().findViewById(R.id.watch_events_recyclerview);

    }

    public WatchEventsFragment() {

    }


    public static WatchEventsFragment newInstance() {
        WatchEventsFragment fragment = new WatchEventsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch_events, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
