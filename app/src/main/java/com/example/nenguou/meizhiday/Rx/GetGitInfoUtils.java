package com.example.nenguou.meizhiday.Rx;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nenguou.meizhiday.Bean.GitUserBean;
import com.example.nenguou.meizhiday.Bean.MyEventsBean;
import com.example.nenguou.meizhiday.Bean.TokenBean;
import com.example.nenguou.meizhiday.Bean.WatchEventBean;
import com.example.nenguou.meizhiday.Fragments.MyEventsFragment;
import com.example.nenguou.meizhiday.Fragments.WatchEventsFragment;
import com.example.nenguou.meizhiday.Services.GithubService;
import com.example.nenguou.meizhiday.Utils.CallTokenBack;
import com.example.nenguou.meizhiday.Utils.CallWatchEventsBack;
import com.example.nenguou.meizhiday.adapter.MyEventAdapter;
import com.example.nenguou.meizhiday.adapter.WatchEventAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import okhttp3.ResponseBody;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by b3 on 2017/8/5.
 */

public class GetGitInfoUtils {

    private Context context;
    private String url;
    private String code;
    private TokenBean tokenBean = new TokenBean();
    private GitUserBean mgitUserBean = new GitUserBean();
    private CallTokenBack callTokenBack;
    //private CallUserBack callUserBack;
    private CallWatchEventsBack callWatchEventsBack;
    private String testToken = "06bcd841454480acbd71b3c48e147aa58fd6f255";
    private String User_avater_url;

    private BaseQuickAdapter baseQuickAdapter;
    private BaseQuickAdapter baseQuickAdapter1;
    //private List<WatchEventBean> watchEventBeans = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private WatchEventsFragment watchEventsFragment;
    private MyEventsFragment myEventsFragment;

    public void setCallBack(@Nullable CallTokenBack callTokenBack,@Nullable CallWatchEventsBack callWatchEventsBack){
        this.callTokenBack = callTokenBack;
        this.callWatchEventsBack = callWatchEventsBack;
    }
    //public void setCallBackUser(CallUserBack callUserBack){
     //   this.callUserBack = callUserBack;
    //}
    public GetGitInfoUtils(WatchEventsFragment watchEventsFragment, BaseQuickAdapter baseQuickAdapter, List<WatchEventBean> watchEventBeans, SwipeRefreshLayout swipeRefreshLayout){
        this.watchEventsFragment = watchEventsFragment;
        this.baseQuickAdapter = baseQuickAdapter;

        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    public GetGitInfoUtils(MyEventsFragment myEventsFragment,BaseQuickAdapter baseQuickAdapter,SwipeRefreshLayout swipeRefreshLayout){
        this.myEventsFragment = myEventsFragment;
        this.baseQuickAdapter1 = baseQuickAdapter;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    public GetGitInfoUtils(Context context){
        this.context = context;
    }
    public GetGitInfoUtils(Context context,String code){
        this.context = context;
        this.code = code;
    }
    public GetGitInfoUtils(){

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
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(url);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                });

    }

    public void getGitToken(){

        service.getToken(code)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
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
                            Log.d("GitToken",responseBody.string());

                            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                            String JsonDatas = jsonObject.toString();
                            tokenBean = gson.fromJson(JsonDatas,TokenBean.class);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("TokenBean",tokenBean.getAccess_token());
                        callTokenBack.callBackToken(tokenBean.getAccess_token());
                        testToken = tokenBean.getAccess_token();

                    }
                });
    }

    public void getGitUser(String token){
        service.getUser(token)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GitUserBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d("GitUserInfo","Completed: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("GitUserInfo","Erroe: "+ e);
                    }

                    @Override
                    public void onNext(GitUserBean gitUserBean) {
                        Log.d("Token is :",testToken);
                        Log.d("GitUserInfo",gitUserBean.getBio());
                        Log.d("GitUserInfo",gitUserBean.getBlog());
                        //callUserBack.callBackUserBean(gitUserBean);
                        callTokenBack.callUserBeanBack(gitUserBean);
                        User_avater_url = gitUserBean.getAvatar_url();
                        mgitUserBean = gitUserBean;
                    }
                });
    }

    public String showCode(){
        return code;
    }

    public void SetGitUser(){
        Flowable.create(new FlowableOnSubscribe<GitUserBean>() {
            @Override
            public void subscribe(FlowableEmitter<GitUserBean> e) throws Exception {
                e.onNext(mgitUserBean);
            }
        }, BackpressureStrategy.BUFFER).subscribe(new org.reactivestreams.Subscriber<GitUserBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d("GitUserinformathinz","onSubscribe");
            }

            @Override
            public void onNext(GitUserBean gitUserBean) {
                Log.d("Susssss",gitUserBean.getBlog());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("GitUserinformathinz",t.toString());
            }

            @Override
            public void onComplete() {
                Log.d("GitUserinformathinz","Completed");
            }
        });
    }

    public void getGitWatchEvent(String name,int page){
        service.getWatchEvent(name,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WatchEventBean>>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("WatchEventsTag","Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("WatchEventsTagError",e.toString());
                    }

                    @Override
                    public void onNext(List<WatchEventBean> watchEvents) {
                        //Log.d("WatchEventsTag",watchEvents.get(0).getType());
                        //Log.d("WatchEventsTag",watchEvents.get(1).getType());
                        //Log.d("WatchEventsTag", String.valueOf(watchEvents.get(1).getActor().getId()));
                        /*try {
                            Log.d("WatchEventsTag", String.valueOf(watchEvents.get(0).getPayload().getForkee().getOwner().getId()));
                        } catch (Exception e){
                            Log.d("WatchEventsTag",e.toString());
                        }*/
                        //Log.d("ahsdnjasds","aaa");
                        //callWatchEventsBack.callBackWatchEvents(watchEvents);
                        //Log.d("ahsdnjasds","bbb");
                        //watchEventAdapter.setBeans(watchEvents);
                        //Log.d("ahsdnjasds","ccc");
                        watchEventsFragment.watchEventBeans.addAll(watchEvents);
                        //Log.d("ahsdnjasds","ddd");
                        baseQuickAdapter.notifyItemInserted(watchEventsFragment.watchEventBeans.size());
                        swipeRefreshLayout.setRefreshing(false);
                        //Log.d("ahsdnjasds","eee");

                    }
                });
    }

    public void getGitMyEvent(String name,int page){
        service.getMyEvent(name,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MyEventsBean>>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("MyEventTag",e.toString());
                    }

                    @Override
                    public void onNext(List<MyEventsBean> myEventsBeans) {
                        //Log.d("MyEventTag",myEventsBeans.get(0).getPayload().getCommits().get(0).getSha());
                        //Log.d("MyEventTag",myEventsBeans.get(1).getPayload().getForkee().getOwner().getId()+"");
                        myEventsFragment.myEventsBeans.addAll(myEventsBeans);
                        baseQuickAdapter1.notifyItemInserted(myEventsFragment.myEventsBeans.size());
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
    }
}
