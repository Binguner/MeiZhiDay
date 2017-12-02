package com.example.nenguou.meizhiday.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nenguou.meizhiday.Entity.Gank;
import com.example.nenguou.meizhiday.GetDatas.GankOkhttp;
import com.example.nenguou.meizhiday.UI.others.GithubPageActivity;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.adapter.Android_iOS_Adapter;
import com.example.nenguou.meizhiday.network.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IOSFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IOSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IOSFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout ios_swipe_refresh_layout;
    private RecyclerView ios_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private Android_iOS_Adapter android_iOS_adapter;
    private ArrayList<Gank> main_ganks = new ArrayList<>();
    private int count = 1;
    private int lastVisibleItem;
    private GetiOSDates getiOSDates = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        inidViews();
        if (!Utils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            //firstLoad();
            FirstLoadDatas();
            setRefreshListener();
        }
        //wFirstLoadDatas();
        //setRefreshListener();
    }

    private void setRefreshListener() {
        ios_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getiOSDates =
                new GetiOSDates("http://gank.io/api/data/iOS/10/1", 1);
                getiOSDates.execute();
            }
        });
        ios_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 3 >= linearLayoutManager.getItemCount()) {
                    getiOSDates =
                    new GetiOSDates("http://gank.io/api/data/iOS/20/" + (++count), 0);
                    getiOSDates.execute();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void inidViews() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        ios_recyclerview.setLayoutManager(linearLayoutManager);
        ios_recyclerview.hasFixedSize();
        ios_swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);


        android_iOS_adapter = new Android_iOS_Adapter(R.layout.card_layout_android_ios, main_ganks, getContext());


        ios_recyclerview.setAdapter(android_iOS_adapter);
        android_iOS_adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        android_iOS_adapter.isFirstOnly(true);
    }

    private void initId() {
        ios_swipe_refresh_layout = (SwipeRefreshLayout) getView().findViewById(R.id.ios_swipe_refresh_layout);
        ios_recyclerview = (RecyclerView) getView().findViewById(R.id.ios_recyclerview);
    }

    private void FirstLoadDatas() {
        getiOSDates =
        new GetiOSDates("http://gank.io/api/data/iOS/10/1", 0);
        getiOSDates.execute();
    }


    public class GetiOSDates extends AsyncTask<String, Void, String> {

        private List<Gank> ganks;
        private String url;
        private String datas;
        private String JSONDatas;
        private int falg;
        private boolean isLoading = true;

        public GetiOSDates(String url, int falg) {
            this.url = url;
            this.falg = falg;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ios_swipe_refresh_layout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            if (isLoading) {
                try {
                    datas = GankOkhttp.getDatas(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isLoading = false;


            }
            return datas;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (falg == 0) {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(datas);
                    JSONDatas = jsonObject.getString("results");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Type type = new TypeToken<ArrayList<Gank>>() {
                }.getType();
                ganks = gson.fromJson(JSONDatas, type);
                //int i =
                //Log.i("shishishis",ganks.get(0).desc);
                try{main_ganks.addAll(ganks);}catch (Exception e){}
                android_iOS_adapter.notifyItemInserted(main_ganks.size());
                //Log.d("dndnndndndnd",main_ganks.size()+"");
                ios_swipe_refresh_layout.setRefreshing(false);
                isLoading = true;
            } else if (falg == 1) {
                Toast.makeText(getContext(), "No more datas", Toast.LENGTH_SHORT).show();
                ios_swipe_refresh_layout.setRefreshing(false);
            }

            android_iOS_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    //Toast.makeText(getContext(), "第" + i + "个", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), GithubPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", main_ganks.get(i).url);
                    bundle.putString("title", main_ganks.get(i).desc);
                    intent.putExtras(bundle);
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getWidth() / 2, (int) view.getHeight() / 2, 0, 0);
                    ActivityCompat.startActivity(getContext(), intent, compat.toBundle());
                }
            });
        }
    }

    public IOSFragment() {
        // Required empty public constructor
    }

    public static IOSFragment newInstance() {
        IOSFragment fragment = new IOSFragment();
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        getiOSDates.cancel(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ios, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
