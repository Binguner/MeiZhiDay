package com.example.nenguou.meizhiday.adapter;

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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by b3 on 2017/8/11.
 */

public class WatchEventAdapter extends BaseQuickAdapter<WatchEventBean,WatchEventAdapter.MyViewHolder> {

    private GetGitInfoUtils getGitInfoUtils = new GetGitInfoUtils();
    private List<WatchEventBean> watchEventBeans = null;

    public WatchEventAdapter(int layoutResId, @Nullable List<WatchEventBean> data) {
        super(R.layout.card_layout_watch_events, data);
        getGitInfoUtils.setCallBack(null, new CallWatchEventsBack() {
            @Override
            public void callBackWatchEvents(List<WatchEventBean> watchEventBeans) {
                WatchEventAdapter.this.watchEventBeans = watchEventBeans;
                Log.d("myTag",watchEventBeans.get(0).getId());
            }
        });
    }

    @Override
    protected void convert(MyViewHolder helper, WatchEventBean item) {
        int position = helper.getLayoutPosition();
            //helper.setText(R.id.card_layout_watch_events_who);
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
