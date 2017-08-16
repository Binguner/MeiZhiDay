package com.example.nenguou.meizhiday.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nenguou.meizhiday.Bean.MyEventsBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.adapter.MyEventAdapter;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEventsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout my_event_swiperefreshlayout;
    private RecyclerView my_event_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private MyEventAdapter myEventAdapter;
    public List<MyEventsBean> myEventsBeans = new ArrayList<>();
    private GetGitInfoUtils getGitInfoUtils;
    private String userName;
    private int lastViewItem;
    private int page = 1;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        getSharedPreferences();
        initViesws();
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UseerBean",Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("login_name",null);
    }

    @SuppressLint("ResourceAsColor")
    private void initViesws() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        my_event_recyclerview.setLayoutManager(linearLayoutManager);
        my_event_recyclerview.setHasFixedSize(true);
        my_event_swiperefreshlayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);

        myEventAdapter = new MyEventAdapter(R.layout.card_layout_my_events,myEventsBeans,getContext());
        my_event_recyclerview.setAdapter(myEventAdapter);
        myEventAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        myEventAdapter.isFirstOnly(true);

        getGitInfoUtils = new GetGitInfoUtils(MyEventsFragment.this,myEventAdapter,my_event_swiperefreshlayout);
        //第一次加载数据
        getGitInfoUtils.getGitMyEvent(userName,page);

        //item 点击事件
        myEventAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });

        //下拉事件
        my_event_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                my_event_swiperefreshlayout.setRefreshing(false);
            }
        });

        //上拉刷新事件
        my_event_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastViewItem+2 >= linearLayoutManager.getItemCount()){
                    my_event_swiperefreshlayout.setRefreshing(true);
                    getGitInfoUtils.getGitMyEvent(userName,++page);
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
        my_event_swiperefreshlayout = (SwipeRefreshLayout) getView().findViewById(R.id.my_event_swiperefreshlayout);
        my_event_recyclerview = (RecyclerView) getView().findViewById(R.id.my_event_recyclerview);
    }

    public MyEventsFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static MyEventsFragment newInstance() {
        MyEventsFragment fragment = new MyEventsFragment();
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
        return inflater.inflate(R.layout.fragment_my_events, container, false);
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
