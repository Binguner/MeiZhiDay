package com.example.nenguou.meizhiday.Rx;

import android.content.Context;

import com.example.nenguou.meizhiday.UI.GithubUI.GithubUserPage;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by b3 on 2017/8/8.
 */

public class SetUserUtils {

   /* private Context context;
    //private String userBean_avatar_url;

    public SetUserUtils(Context context){
        this.context = context;
    }

    public void setGithubUserPage(String userBean_avatar_url){
        io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("userBean_avatar_url");
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {

                }
            });
    }*/
}
