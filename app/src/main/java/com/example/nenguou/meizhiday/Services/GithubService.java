package com.example.nenguou.meizhiday.Services;

import com.example.nenguou.meizhiday.Entity.CodeBean;
import com.example.nenguou.meizhiday.Entity.GitUserBean;
import com.example.nenguou.meizhiday.Entity.MyEventsBean;
import com.example.nenguou.meizhiday.Entity.RepoBean;
import com.example.nenguou.meizhiday.Entity.WatchEventBean;

import java.util.List;

import io.reactivex.annotations.Nullable;
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

    //获取 Token
    @Headers("Accept: application/json")
    @POST("oauth/access_token/?client_id=3d000218efdbb17b4a75&client_secret=3dfe3b88974c6c0bd6bc72fb53d08aaaef441c17&code=")
    Observable<ResponseBody> getToken(@Query("code") String code);

    //获取用户信息    //https://api.github.com/user?access_token=
    @GET("https://api.github.com/user?access_token=")
    Observable<GitUserBean> getUser(@Query("access_token") String token);

    //watchEvents
    //https://api.github.com/users/Nenguou/received_events
    @GET("https://api.github.com/users/{name}/received_events?page=")
    Observable<List<WatchEventBean>> getWatchEvent(@Path("name") String name,@Query("page") int page);

    //myEvents
    //https://api.github.com/users/Nenguou/events
    @GET("https://api.github.com/users/{name}/events?page=")
    Observable<List<MyEventsBean>> getMyEvent(@Path("name") String name,@Query("page") int page);

    //repos
    //https://api.github.com/users/Nenguou/repos
    @GET("https://api.github.com/users/{name}/repos")
    Observable<List<RepoBean>> getRepos(@Path("name") String name);

    //README
    //getCode
    @GET("https://api.github.com/repos/{username}/{repoName}/contents/{path}")
    Observable<List<CodeBean>> getCode(@Path("username") String username,@Path("repoName") String repoName,@Path("path") @Nullable String path);

    //https://api.github.com/repos/stormzhang/9GAG/contents/app/src/main/java/me/storm/ninegag/App.java?ref=master
    //getMardDown
    @GET("https://raw.githubusercontent.com/{username}/{repoName}/master/{path}")
    Observable<ResponseBody> getMarkdown(@Path("username") String username,@Path("repoName") String repoName,@Path("path") @Nullable String path);

}
