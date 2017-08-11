package com.example.nenguou.meizhiday.Utils;

import android.content.Context;
import android.util.Log;

import com.example.nenguou.meizhiday.GetDatas.GankOkhttp;



import java.util.Observable;

/**
 * Created by b3 on 2017/7/29.
 */

public class RxUtils {
    /*private Context context;
    private static String type = "Android";
    private static String datas = null;
    private static String url = "http://gank.io/api/search/query/listview/category/"+type+"/count/10/page/1";

    public static void getSetDatas(String type){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                datas = GankOkhttp.getDatas(url);
                e.onNext(datas);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.d("shit",s+"");
            }

            @Override
            public void onError(Throwable t) {
                Log.d("shit","onError:"+t);
            }

            @Override
            public void onComplete() {
                Log.d("shit","onComplete");
            }
        });

    }*/
}
