package com.example.nenguou.meizhiday.GetDatas;

import android.os.Environment;

import com.example.nenguou.meizhiday.Utils.TimeUnit;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nenguou on 2017/6/16.
 */

public class GankOkhttp {

    private static final long cacheSize = 1024 * 1024 * 20;
    private static String cacheDictionary = Environment.getExternalStorageDirectory() + "meizhicache";
    private static Cache cache = new Cache(new File(cacheDictionary,cacheSize));
    private static OkHttpClient okHttpClient = new OkHttpClient();

    static {
        //如果无法生存缓存文件目录，检测权限使用已经加上，检测手机是否把文件读写权限禁止了
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS); // 设置连接超时时间
        builder.writeTimeout(8, TimeUnit.SECONDS);// 设置写入超时时间
        builder.readTimeout(8, TimeUnit.SECONDS);// 设置读取数据超时时间
        builder.retryOnConnectionFailure(true);// 设置进行连接失败重试
        builder.cache(cache);// 设置缓存
        okHttpClient = builder.build();

    }
     //private static List<Gank> ganks = new ArrayList<>();

    public static String getDatas(String url) throws IOException {
        Request request = new Request
                .Builder()
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
