package com.example.nenguou.meizhiday.Rx;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Config;
import android.util.Log;


import com.example.nenguou.meizhiday.BuildConfig;
import com.example.nenguou.meizhiday.MainActivity;

import java.io.File;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by binguner on 2017/8/30.
 */

public class NewCilent {
    private static Context context;
    public NewCilent(Context context){
        this.context = context
    }
    public static OkHttpClient getNewCilent() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cachePath",Context.MODE_PRIVATE);
        String path = sharedPreferences.getString("mCachePath",null);
        Log.d("fuckyoubitch",path);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        // File cacheFile = new File(,"MZCache");




        return null;
    }

}
