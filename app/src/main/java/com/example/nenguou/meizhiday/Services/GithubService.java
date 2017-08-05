package com.example.nenguou.meizhiday.Services;

import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by b3 on 2017/8/5.
 */

//https://github.com/login/oauth/authorize/?client_id=3d000218efdbb17b4a75?scope=user,public_repo
public interface GithubService {
    String client_id = "3d000218efdbb17b4a75";
    String client_secret = "3dfe3b88974c6c0bd6bc72fb53d08aaaef441c17";
    String scope = "user,public_repo";
    //获取 code 信息
    @GET("oauth/authorize/?client_id="+client_id+"?scope="+scope)
    Observable<ResponseBody> getCode();

    //https://github.com/login/")
    //https://github.com/login/oauth/access_token/?client_id=3d000218efdbb17b4a75&client_secret=3dfe3b88974c6c0bd6bc72fb53d08aaaef441c17&code=7b8d9c46ba13a3862bb3
    //@POST("oauth/access_token/?client_id="+client_id+"&client_secret="+client_secret+"&code={code}")
    //https://github.com/login/oauth/access_token/?client_id=3d000218efdbb17b4a75&client_secret=3dfe3b88974c6c0bd6bc72fb53d08aaaef441c17&code=79134e039d3159800cd7
    @Headers("Accept: application/json")
    @POST("oauth/access_token/?client_id=3d000218efdbb17b4a75&client_secret=3dfe3b88974c6c0bd6bc72fb53d08aaaef441c17&code=")
    Observable<ResponseBody> getToken(@Query("code") String code);


}
