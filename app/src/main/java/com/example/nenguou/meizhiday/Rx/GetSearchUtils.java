package com.example.nenguou.meizhiday.Rx;

import android.content.Context;
import android.util.Log;

import com.example.nenguou.meizhiday.Bean.SearchBean;
import com.example.nenguou.meizhiday.Services.SearchService;
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

    private Context context;

    private static List<SearchBean> searchBeans = new ArrayList<>();
    private static Type type = new TypeToken<ArrayList<SearchBean>>(){}.getType();

    public GetSearchUtils(Context context){
        this.context = context;
    }

    static Gson gson = new GsonBuilder()
            .create();
    static JsonParser jsonParser = new JsonParser();

    static Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("http://gank.io/api/search/query/")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    static SearchService searchService = retrofit.create(SearchService.class);

    public static void GetSearchReasults(){
        searchService.getAimType("listview", "Android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String response = null;
                        try {
                            response = responseBody.string();
                            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                            String jsonDatas = jsonObject.get("results").toString();
                            searchBeans = gson.fromJson(jsonDatas, type);
                            Log.d("shit", searchBeans.get(0).getWho());
                            Log.d("shit", searchBeans.get(9).getWho());
                            Log.d("shit", Thread.currentThread().getName().toString());


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
}
