package com.example.nenguou.meizhiday.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nenguou.meizhiday.Entity.SearchBean;
import com.example.nenguou.meizhiday.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b3 on 2017/8/3.
 */

public class Search_results_Adapter extends BaseQuickAdapter<SearchBean,Search_results_Adapter.searchViewHolder>{

    private List<SearchBean> searchBeans = new ArrayList<>();
    private Context context;

    public Search_results_Adapter(int layoutResId, @Nullable List<SearchBean> data) {
        super(R.layout.card_layout_android_ios,data);
    }

    public void DeleteAllBeans(){
        searchBeans.clear();
        Log.d("sizesize","还有"+searchBeans.size());
    }

    public void setBeans(List<SearchBean> searchBeans){
        this.searchBeans.addAll(searchBeans);
        //Log.d("yunxffrfn","" + this.searchBeans.get(0).getWho());
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(searchViewHolder helper, SearchBean item) {
        int postion = helper.getLayoutPosition();
        //Log.d("shishoidhfsiodufd","第" + postion + "个");
        try {
            helper.setText(R.id.android_ios_title, searchBeans.get(postion).getDesc())
                    .setText(R.id.android_ios_time, searchBeans.get(postion).getPublishedAt())
                    .setText(R.id.android_ios_who, searchBeans.get(postion).getWho())
                    .addOnClickListener(R.id.card_layout_id_android_ios);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        // Log.d("asbfuabefkabfkabf",Thread.currentThread().getName());

    }

    class searchViewHolder extends BaseViewHolder{
        private TextView android_ios_title;
        private TextView android_ios_who;
        private TextView android_ios_time;
        //@SuppressLint("LongLogTag")
        public searchViewHolder(View view) {
            super(view);
            //Log.d("hfhfhew","YUfsnmfd");
            android_ios_title = (TextView) view.findViewById(R.id.android_ios_title);
            android_ios_who = (TextView) view.findViewById(R.id.android_ios_who);
            android_ios_time = (TextView) view.findViewById(R.id.android_ios_time);
        }
    }
}
