package com.example.nenguou.meizhiday.GetDatas;

import android.os.Environment;
import android.util.Log;


import com.example.nenguou.meizhiday.network.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nenguou on 2017/6/16.
 */

public class GankOkhttp {

   // private static final long cacheSize = 1024 * 1024 * 20;
    //private static String cacheDictionary = Environment.getExternalStorageDirectory() + "meizhicache";
    // static Cache cache = new Cache(new File(cacheDictionary),cacheSize);
    private static OkHttpClient okHttpClient = new OkHttpClient();



    //private static List<Gank> ganks = new ArrayList<>();

    public static String getDatas(String url) throws IOException {
        Request request = new Request
                .Builder()
                //.cacheControl(new CacheControl.Builder().maxAge(60*60*24*15, java.util.concurrent.TimeUnit.DAYS).build())
                .url(url)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                //   Log.i("apptuijian",response.body().string());
                //Log.i("apptuijian",response.body().string());
                return response.body().string();
            }else {
                throw new IOException("Unexpected code " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
