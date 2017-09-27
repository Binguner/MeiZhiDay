package com.example.nenguou.meizhiday.GetDatas;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.nenguou.meizhiday.Entity.Gank;

import java.util.List;

/**
 * Created by Nenguou on 2017/6/16.
 */

public class GetGankDatas extends AsyncTask<String,Void,List<Gank>>{
    //第二个参数在 onProgressUpdate 中使用
    private String url;
    private Fragment fragment;
    private SwipeRefreshLayout swipeRefreshLayout;

    public GetGankDatas(Fragment fragment, SwipeRefreshLayout swipeRefreshLayout,String url){
        this.fragment = fragment;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.url = url;
    }


    @Override
    protected void onPreExecute() {
        swipeRefreshLayout.setRefreshing(true);
        super.onPreExecute();
    }

    //第一个参数
    @Override
    protected List<Gank> doInBackground(String... strings) {
       // return GankOkhttp.getDatas(url);
        return  null;
    }


    //第三个参数
    @Override
    protected void onPostExecute(List<Gank> Ganks) {
        super.onPostExecute(Ganks);

    }
}
