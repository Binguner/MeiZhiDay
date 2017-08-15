package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nenguou.meizhiday.Bean.WatchEventBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.Utils.CallWatchEventsBack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by b3 on 2017/8/11.
 */

public class WatchEventAdapter extends BaseQuickAdapter<WatchEventBean,WatchEventAdapter.MyViewHolder> {

    private GetGitInfoUtils getGitInfoUtils = new GetGitInfoUtils();
    private List<WatchEventBean> watchEventBeans = null;
    private Context context;

    public WatchEventAdapter(int layoutResId, @Nullable List<WatchEventBean> data,Context context) {
        super(R.layout.card_layout_watch_events, data);
        this.context = context;
        /*Log.d("myTag","构造方法1");
        getGitInfoUtils.setCallBack(null, new CallWatchEventsBack() {
            @Override
            public void callBackWatchEvents(List<WatchEventBean> watchEventBeans) {
                WatchEventAdapter.this.watchEventBeans = watchEventBeans;
                Log.d("myTag",watchEventBeans.get(0).getId());
            }
        });
        Log.d("myTag","构造方法2");*/
    }

    public void setBeans(List<WatchEventBean> watchEventBeans){
        this.watchEventBeans.addAll(watchEventBeans);
        Log.d("myTag1",watchEventBeans.get(0).getCreated_at());
    }

    @Override
    protected void convert(MyViewHolder helper, WatchEventBean item) {
        int position = helper.getLayoutPosition();
        Log.d("myTag1","before");
        Log.d("myTag3",item.getType().toString());
        if(item/*.get(position)*/.getType().endsWith("WatchEvent")){
            Log.d("myTag2","inwatch");
            helper.setText(R.id.card_layout_watch_events_who,item/*.get(position)*/.getActor().getLogin())
                    .setText(R.id.card_layout_watch_events_action,"Starred")
                    .setText(R.id.card_layout_watch_events_actioin_name,item.getRepo().getName())
                    .setText(R.id.card_layout_watch_events_time,item/*.get(position)*/.getCreated_at());
        }else if(item/*.get(position)*/.getType().endsWith("ForkEvent")){
            Log.d("myTag2","infork");
            helper.setText(R.id.card_layout_watch_events_who,item/*.get(position)*/.getActor().getLogin())
                    .setText(R.id.card_layout_watch_events_action,"Forked")
                    .setText(R.id.card_layout_watch_events_actioin_name,item.getRepo().getName())
                    .setText(R.id.card_layout_watch_events_time,item/*.get(position)*/.getCreated_at());
        }
        Log.d("myTag1","after");
        try{
            Picasso.with(context).load(item.getActor().getAvatar_url()).centerCrop().placeholder(R.mipmap.gitcat).error(R.mipmap.gitcat).resize(60,60).into(helper.card_layout_watch_events_circleimageview);
        }catch (Exception e){
            e.printStackTrace();
        }
        helper.addOnClickListener(R.id.watch_events_card_layout);
    }

    public class MyViewHolder extends BaseViewHolder{
        private CircleImageView card_layout_watch_events_circleimageview;
        private TextView card_layout_watch_events_who,card_layout_watch_events_action,card_layout_watch_events_actioin_name,card_layout_watch_events_time;
        public MyViewHolder(View view) {
            super(view);
            card_layout_watch_events_circleimageview = (CircleImageView) view.findViewById(R.id.card_layout_watch_events_circleimageview);
            card_layout_watch_events_who = (TextView) view.findViewById(R.id.card_layout_watch_events_who);
            card_layout_watch_events_action = (TextView) view.findViewById(R.id.card_layout_watch_events_action);
            card_layout_watch_events_actioin_name = (TextView) view.findViewById(R.id.card_layout_watch_events_actioin_name);
            card_layout_watch_events_time = (TextView) view.findViewById(R.id.card_layout_watch_events_time);

        }
    }
}
