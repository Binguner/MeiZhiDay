package com.example.nenguou.meizhiday.Services;

import retrofit2.http.Multipart;
import rx.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by b3 on 2017/8/2.
 */
//http://gank.io/api/search/query/listview/category/Android/count/10/page/1
    //listview/category/Android/count/10/page/1
public interface SearchService {
    @GET("{SearhingAim}/category/{TYPE}/count/10/page/{page}")
    Observable<ResponseBody> getAimType(@Path("SearhingAim") String aim, @Path("TYPE") String type,@Path("page") int page);
}
