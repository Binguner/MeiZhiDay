package com.example.nenguou.meizhiday.GetDatas;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Nenguou on 2017/6/16.
 */

public class GankOkhttp {

    private static OkHttpClient okHttpClient = new OkHttpClient();
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
