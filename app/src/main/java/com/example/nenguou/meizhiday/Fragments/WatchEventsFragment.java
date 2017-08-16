package com.example.nenguou.meizhiday.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nenguou.meizhiday.Bean.WatchEventBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.adapter.WatchEventAdapter;

import java.util.ArrayList;
import java.util.List;

public class WatchEventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout watch_events_swiperefreshlayout;
    private RecyclerView watch_events_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private WatchEventAdapter watchEventAdapter;
    public List<WatchEventBean> watchEventBeans = new ArrayList<>();
    private GetGitInfoUtils getGitInfoUtils = null;
    private String userName;
    private int lastViewItem;
    private int page = 1;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        getSharedPreferences();
        initViews();

    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UseerBean",Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("login_name",null);
    }

    @SuppressLint("ResourceAsColor")
    private void initViews() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        watch_events_recyclerview.setLayoutManager(linearLayoutManager);
        watch_events_recyclerview.setHasFixedSize(true);
        watch_events_swiperefreshlayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);

        watchEventAdapter = new WatchEventAdapter(R.layout.card_layout_watch_events,watchEventBeans,getContext());
        watch_events_recyclerview.setAdapter(watchEventAdapter);
        watchEventAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        watchEventAdapter.isFirstOnly(true);

        getGitInfoUtils = new GetGitInfoUtils(WatchEventsFragment.this,watchEventAdapter,watchEventBeans,watch_events_swiperefreshlayout);

        //第一次加载数据
        getGitInfoUtils.getGitWatchEvent(userName,page);

        //item 点击事件
        watchEventAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("clickck",position+"个");
                Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_LONG).show();
            }
        });

        //下拉事件
        watch_events_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                watch_events_swiperefreshlayout.setRefreshing(false);
            }
        });
        //上拉事件
        watch_events_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastViewItem+2 >= linearLayoutManager.getItemCount()){
                    watch_events_swiperefreshlayout.setRefreshing(true);
                    getGitInfoUtils.getGitWatchEvent(userName,++page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastViewItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });

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
