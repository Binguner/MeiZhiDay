package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nenguou.meizhiday.Bean.MyEventsBean;
import com.example.nenguou.meizhiday.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by binguner on 2017/8/16.
 */

public class MyEventAdapter extends BaseQuickAdapter<MyEventsBean, MyEventAdapter.MyViewHolder> {

    private Context context;

    public MyEventAdapter(int layoutResId, @Nullable List<MyEventsBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(MyViewHolder helper, MyEventsBean item) {
        helper.addOnClickListener(R.id.my_event_card_layout);
        try {
            Picasso.with(context).load(item.getActor().getAvatar_url()).placeholder(R.mipmap.gitcat).error(R.mipmap.gitcat).resize(60, 60).into(helper.card_layout_my_event_circleimageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (item.getType().equals("ForkEvent")) {
                helper.setText(R.id.card_layout_my_event_who, item.getActor().getLogin())
                        .setText(R.id.card_layout_my_event_action, "Forked")
                        .setText(R.id.card_layout_my_event_forked_stared_action, item.getRepo().getName())
                        .setText(R.id.card_layout_my_event_time, item.getCreated_at())
                        .setText(R.id.card_layout_my_event_push_what, "");
            } else if (item.getType().equals("WatchEvent")) {
                helper.setText(R.id.card_layout_my_event_who, item.getActor().getLogin())
                        .setText(R.id.card_layout_my_event_action, "Starred")
                        .setText(R.id.card_layout_my_event_forked_stared_action, item.getRepo().getName())
                        .setText(R.id.card_layout_my_event_time, item.getCreated_at())
                        .setText(R.id.card_layout_my_event_push_what, "");
            } else if (item.getType().equals("PushEvent")) {
                helper.setText(R.id.card_layout_my_event_who, item.getActor().getLogin())
                        .setText(R.id.card_layout_my_event_action, "Pushed")
                        .setText(R.id.card_layout_my_event_forked_stared_action, "to " + item.getPayload().getRef() + " at " + item.getRepo().getName())
                        .setText(R.id.card_layout_my_event_time, item.getCreated_at())
                        .setText(R.id.card_layout_my_event_push_what, item.getPayload().getCommits().get(0).getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class MyViewHolder extends BaseViewHolder {
        private CircleImageView card_layout_my_event_circleimageView;
        private TextView card_layout_my_event_who, card_layout_my_event_action, card_layout_my_event_forked_stared_action, card_layout_my_event_time, card_layout_my_event_push_what;

        public MyViewHolder(View view) {
            super(view);
            card_layout_my_event_circleimageView = (CircleImageView) view.findViewById(R.id.card_layout_my_event_circleimageView);
            card_layout_my_event_who = (TextView) view.findViewById(R.id.card_layout_my_event_who);
            card_layout_my_event_action = (TextView) view.findViewById(R.id.card_layout_my_event_action);
            card_layout_my_event_forked_stared_action = (TextView) view.findViewById(R.id.card_layout_my_event_forked_stared_action);
            card_layout_my_event_time = (TextView) view.findViewById(R.id.card_layout_my_event_time);
            card_layout_my_event_push_what = (TextView) view.findViewById(R.id.card_layout_my_event_push_what);
        }
    }
}
