package com.example.nenguou.meizhiday.Rx;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nenguou.meizhiday.Bean.TokenBean;
import com.example.nenguou.meizhiday.Fragments.AndroidFragment;
import com.example.nenguou.meizhiday.Services.GithubService;
import com.example.nenguou.meizhiday.UI.GithubPageActivity;
import com.example.nenguou.meizhiday.UI.gittest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
 * Created by b3 on 2017/8/5.
 */

public class GetGitInfoUtils {

    private Context context;
    private String url;
    private String code;
    private TokenBean tokenBean = new TokenBean();

    public GetGitInfoUtils(Context context){
        this.context = context;
    }
    public GetGitInfoUtils(String code){
        this.code = code;
    }

    Gson gson = new GsonBuilder()
            .create();

    JsonParser jsonParser = new JsonParser();

    Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("https://github.com/login/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    GithubService service = retrofit.create(GithubService.class);


    public void getGitInfo(){
        url = "https://github.com/login/oauth/authorize/?client_id=3d000218efdbb17b4a75&scope=user,public_repo" ;
        service.getCode()
               // .flatMap()
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

                        //Intent intent = new Intent(context, GithubPageActivity.class);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        //bundle.putString("url",url);
                        //intent.putExtras(bundle);
                        //context.startActivity(intent);
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(url);
                        intent.setData(uri);
                        context.startActivity(intent);

                        /*try {
                            //Log.d("GitCode",responseBody.string().toString());
                        } catch (IOException e) {
                            Log.d("GitCode","onError: " + e);
                        }*/
                    }
                });

    }

    public void getGitToken(){

        service.getToken(code)
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

                        try {
                            Log.d("GitToken",responseBody.string());
                            Log.d("GitToken",url);

                            JsonObject jsonObject = jsonParser.parse(responseBody.string()).getAsJsonObject();
                            String jsonDatas = jsonObject.get("Token").toString();

                            Log.d("jsonObjectshowUp",jsonObject.toString());
                            tokenBean = gson.fromJson(jsonDatas,TokenBean.class);
                            Log.d("TokenBean",tokenBean.getAccess_token());

                            //Log.d("GitToken","code:"+code);
                        } catch (IOException e) {

                            Log.d("TokenError","TokenError: " + e);

                        }

                    }
                });
    }

    public String showCode(){
        return code;
    }


}
