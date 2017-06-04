package com.example.nenguou.meizhiday;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nenguou.meizhiday.Bean.MeiZHI;
import com.example.nenguou.meizhiday.adapter.MeiZhiAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private SwipeRefreshLayout my_swipeRefreshLayout;
    private GridLayoutManager my_staggeredGridLayoutManager;
    private RecyclerView my_recyclerView;
    private int count = 2;
    private int lastVisibleItem ;
    private ArrayList<MeiZHI> main_meizhis = new ArrayList<>();
    private String content;
    private MeiZhiAdapter meiZhiAdapter;
    private ImageButton imageButtonhaha;
    //private int FLAGSS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initId();
        setToolbar();
        initViews();
        setListener();

    }


    private void initViews() {
       // my_staggeredGridLayoutManager = new GridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        my_staggeredGridLayoutManager = new GridLayoutManager(MainActivity.this,2,GridLayoutManager.VERTICAL,false);
        my_recyclerView.setLayoutManager(my_staggeredGridLayoutManager);
        meiZhiAdapter = new MeiZhiAdapter(this,main_meizhis);
        my_recyclerView.setAdapter(meiZhiAdapter);
        //my_recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(16);
        my_recyclerView.addItemDecoration(spacesItemDecoration);
        my_swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorWhite,R.color.colorYello,R.color.colorPrimary);
        my_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=my_staggeredGridLayoutManager.getItemCount()) {

                    new getData(MainActivity.this,0).execute("http://gank.io/api/data/福利/10/"+count);
                    count++;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
              //  int[] positions= my_staggeredGridLayoutManager.findLastVisibleItemPositions(null);
              //  lastVisibleItem =Math.max(positions[0],positions[1]);//根据StaggeredGridLayoutManager设置的列数来定
                lastVisibleItem = my_staggeredGridLayoutManager.findLastVisibleItemPosition();
            }
        });
        meiZhiAdapter.setOnItemClickListener(new MeiZhiAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(MainActivity.this,main_meizhis.get(postion).roString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Pics.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",main_meizhis.get(postion).roString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void setToolbar() {
        collapsingToolbarLayout.setTitle("Meizhi");
        collapsingToolbarLayout.setExpandedTitleMarginBottom(40);
        collapsingToolbarLayout.setExpandedTitleMarginStart(80);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPink));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
    }

    private void initId() {
        imageButtonhaha = (ImageButton) findViewById(R.id.iv);
        my_recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_swipe_refresh_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.myCollapsingToolbarLayout);
    }

    private int times = 0;
    private void setListener() {
        my_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(times ==0) {
                    new getData(MainActivity.this, 0).execute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                    times++;
                }else {
                    new getData(MainActivity.this, 1).execute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                }
            }
        });

    }

    private class getData extends AsyncTask<String,Void,List<MeiZHI>>{

        private int Flags;
        private String content;
        List<MeiZHI> meizhis;
        String jsonData;
        private MainActivity context;

        public getData(MainActivity context,int Flags){
            this.context = context;
            this.Flags = Flags;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            my_swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<MeiZHI> doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request
                    .Builder()
                    //.url("http://gank.io/api/data/福利/10/0x")
                    .url(params[0])
                    .build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.isSuccessful()){
                try {
                    content = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();

                try {
                    JSONObject jsonObject = new JSONObject(content);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Type type = new TypeToken<ArrayList<MeiZHI>>(){
                }.getType();
                try {
                    meizhis = gson.fromJson(jsonData,type);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                //Log.i("haha",meizhis.get(0).toString());
                //Log.i("haha",jsonData.toString());
                Log.i("hahaha",meizhis.get(1).url.toString());
            }

            return meizhis;
        }

        @Override
        protected void onPostExecute(List<MeiZHI> meiZHIS) {
            super.onPostExecute(meiZHIS);
            switch (Flags){
                case 0:
                    main_meizhis.addAll(meiZHIS);
                    context.meiZhiAdapter.notifyDataSetChanged();
                    context.my_swipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                  /*  main_meizhis.addAll(meiZHIS);
                    context.meiZhiAdapter.notifyDataSetChanged();*/
                    context.my_swipeRefreshLayout.setRefreshing(false);
                    break;
            }


   /*         if(FLAGSS == 1){
                context.my_swipeRefreshLayout.setRefreshing(false);

            }else if(main_meizhis.isEmpty()){
                main_meizhis.addAll(meiZHIS);

                context.meiZhiAdapter.notifyDataSetChanged();
                context.my_swipeRefreshLayout.setRefreshing(false);
               *//* for(int i = 0;i < meizhis.size();i++){
                    main_meizhis.add(meizhis.get(i));
                    FLAGSS = 1;
                    Log.i("tete",meizhis.get(i).url.toString());
                }*//*

            }if(main_meizhis.get(0).url != meiZHIS.get(0).url) {
                main_meizhis.addAll(meiZHIS);

                context.meiZhiAdapter.notifyDataSetChanged();
                context.my_swipeRefreshLayout.setRefreshing(false);
              *//*  for(int i = 0;i < meizhis.size();i++){
                    main_meizhis.add(meizhis.get(i));
                    Log.i("tete",meizhis.get(i).url.toString());
                }*//*
            }*/
           /* context.meiZhiAdapter.notifyDataSetChanged();
            context.my_swipeRefreshLayout.setRefreshing(false);*/
        }
    }
}
