package com.example.nenguou.meizhiday.Rx;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.example.nenguou.meizhiday.Bean.Gank;
import com.example.nenguou.meizhiday.Bean.SearchBean;
import com.example.nenguou.meizhiday.GankAty;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Services.SearchService;
import com.example.nenguou.meizhiday.adapter.Search_results_Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.internal.schedulers.SchedulerWhen;
import rx.schedulers.Schedulers;

/**
 * Created by b3 on 2017/8/2.
 */

public class GetSearchUtils {

    private GankAty gankAty;
    private Context context;
//    private Search_results_Adapter search_results_adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String searchingwhat;
    private String searchingType;
    //private int page;

    private  List<SearchBean> searchBeans = new ArrayList<>();
    private Search_results_Adapter search_results_adapter /*= new Search_results_Adapter(R.layout.card_layout_android_ios,searchBeans)*/;

    private  Type type = new TypeToken<ArrayList<SearchBean>>(){}.getType();

    public GetSearchUtils(GankAty context, Search_results_Adapter search_results_adapter, List<SearchBean> searchBeans, SwipeRefreshLayout swipeRefreshLayout,String searchingwhat,String searchingType){
        this.gankAty = context;
        this.search_results_adapter = search_results_adapter;
        //this.searchBeans = searchBeans;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.searchingwhat = searchingwhat;
        this.searchingType = searchingType;
    }

     Gson gson = new GsonBuilder()
            .create();
     JsonParser jsonParser = new JsonParser();

     Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("http://gank.io/api/search/query/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    SearchService searchService = retrofit.create(SearchService.class);

    public  void GetSearchReasults(int page){

        searchService.getAimType(searchingwhat, searchingType,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCmocc:","onCompleted");
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.d("shitt", "onError: "+ e);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String response = null;
                        try {
                            response = responseBody.string();
                            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                            String jsonDatas = jsonObject.get("results").toString();
                            searchBeans = gson.fromJson(jsonDatas, type);
                            /*Log.d("shit", searchBeans.get(0).getType());
                            Log.d("shit", searchBeans.get(1).getType());
                            Log.d("shit", searchBeans.get(2).getType());
                            Log.d("shit", searchBeans.get(3).getType());
                            Log.d("shit", searchBeans.get(4).getType());
                            Log.d("shit", searchBeans.get(5).getType());
                            Log.d("shit", searchBeans.get(6).getType());
                            Log.d("shit", searchBeans.get(7).getType());
                            Log.d("shit", searchBeans.get(8).getType());
                            Log.d("shit", searchBeans.get(9).getType());
                            Log.d("shit", Thread.currentThread().getName().toString());*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        search_results_adapter.setBeans(searchBeans);
                        Log.d("yunxn","yunxingle");
                        gankAty.mainSearchBeans.addAll(searchBeans);
                        search_results_adapter.notifyItemInserted(gankAty.mainSearchBeans.size());
                        Log.d("dcdsce",Thread.currentThread().getName()+"");
                        Log.d("dsdsdsd",searchBeans.size()+"");
                    }
                });

    }

    public Search_results_Adapter getAdapter(){
        return search_results_adapter;
    }

    public void stopCall(){

    }
}
