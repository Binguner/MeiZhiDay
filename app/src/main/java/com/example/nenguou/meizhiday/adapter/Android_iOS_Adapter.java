package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nenguou.meizhiday.Bean.Gank;
import com.example.nenguou.meizhiday.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nenguou on 2017/6/19.
 */

public class Android_iOS_Adapter extends BaseQuickAdapter<Gank,Android_iOS_Adapter.MyViewHolder>{

    private ArrayList<Gank> ganks;
    private Context context;
    public Android_iOS_Adapter(int layoutResId, @Nullable ArrayList<Gank> data,Context context) {
        super(R.layout.card_layout_android_ios, data);
        this.ganks = data;
        this.context = context;
    }

    @Override
    protected void convert(MyViewHolder myViewHolder, Gank gank) {
        int postion = myViewHolder.getLayoutPosition();
        Log.i("asnddasnad","第"+postion+"个");
       // try {
            myViewHolder
                    .setText(R.id.android_ios_title,ganks.get(postion).desc)
                    .setText(R.id.android_ios_who,"via("+ganks.get(postion).who+")")
                    .setText(R.id.android_ios_time,ganks.get(postion).publishedAt);
       // }catch (NullPointerException e ){
       //     e.printStackTrace();
     //   }
               // .linkify(R.layout.card_layout_android_ios);超链
      try {
          Picasso.with(context).load(ganks.get(postion).images[0]).config(Bitmap.Config.RGB_565).fit().into(myViewHolder.android_ios_piscs);
         // Glide.with(context).load(ganks.get(postion).images[0]).into(myViewHolder.android_ios_piscs);
      }catch (NullPointerException e){
          e.printStackTrace();
      }
    }

    class MyViewHolder extends BaseViewHolder{
        private TextView android_ios_title;
        private TextView android_ios_who;
        private TextView android_ios_time;
        private ImageView android_ios_piscs;
        public MyViewHolder(View view) {
            super(view);
            android_ios_time = (TextView) view.findViewById(R.id.android_ios_time);
            android_ios_title = (TextView) view.findViewById(R.id.android_ios_title);
            android_ios_who = (TextView) view.findViewById(R.id.android_ios_who);
            android_ios_piscs = (ImageView) view.findViewById(R.id.android_ios_piscs);
        }
    }
}
