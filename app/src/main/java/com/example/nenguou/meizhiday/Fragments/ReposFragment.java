package com.example.nenguou.meizhiday.Fragments;

import android.content.Intent;
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
import com.example.nenguou.meizhiday.Entity.RepoBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.UI.GithubUI.RepoActivity;
import com.example.nenguou.meizhiday.adapter.RepoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReposFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReposFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReposFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout repos_swiperefreshlayout;
    private RecyclerView repos_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private RepoAdapter repoAdapter;
    public List<RepoBean> repoBeans = new ArrayList<>();
    private GetGitInfoUtils getGitInfoUtils;
    private String name;
    private int lastViewItem;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        ininViews();
    }


    private void ininViews() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        repos_recyclerview.setLayoutManager(linearLayoutManager);
        repos_recyclerview.setHasFixedSize(true);

        repos_swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorYello), getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorTablayout));
        repoAdapter = new RepoAdapter(R.layout.card_layout_repos,repoBeans);
        repos_recyclerview.setAdapter(repoAdapter);
        repoAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        repoAdapter.isFirstOnly(true);

        //第一次加载数据
        getGitInfoUtils = new GetGitInfoUtils(ReposFragment.this,repoAdapter,repos_swiperefreshlayout);
        getGitInfoUtils.GetGitRepos("Nenguou");

        //下拉事件
        repos_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                repos_swiperefreshlayout.setRefreshing(false);
            }
        });

        //上拉事件


        //item 点击事件
        repoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), RepoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initId() {
        repos_swiperefreshlayout = (SwipeRefreshLayout) getView().findViewById(R.id.repos_swiperefreshlayout);
        repos_recyclerview = (RecyclerView) getView().findViewById(R.id.repos_recyclerview);
    }

    public ReposFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ReposFragment newInstance() {
        ReposFragment fragment = new ReposFragment();
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
        return inflater.inflate(R.layout.fragment_repos, container, false);
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
        void onFragmentInteraction(Uri uri);
    }
}
