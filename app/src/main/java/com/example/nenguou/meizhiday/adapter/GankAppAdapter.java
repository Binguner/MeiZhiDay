package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nenguou.meizhiday.Bean.Gank;
import com.example.nenguou.meizhiday.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nenguou on 2017/6/16.
 */

public class GankAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<Gank> ganks;
    private onItemClickListener myonItemClickListener = null;

    public void setOnItemClickListener(onItemClickListener myonItemClickListener){
        this.myonItemClickListener = myonItemClickListener;
    }

    public static interface onItemClickListener{
        void onItemClick(View view);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        if(myonItemClickListener != null){
            myonItemClickListener.onItemClick(view);
        }
    }



    public GankAppAdapter(Context context,ArrayList<Gank> ganks){
        this.context = context;
        this.ganks = ganks;
    }

    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout_app_recommend,viewGroup,false);
        RecommendAppViewHolder recommendAppViewHolder = new RecommendAppViewHolder(view);
        view.setOnClickListener(this);
        return recommendAppViewHolder;
    }

    //给每一个 Item 设置内容
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        try {
            Picasso.with(context).load(ganks.get(i).images[0]).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder)
                    .into(((RecommendAppViewHolder) viewHolder).AppPics);
        }catch (NullPointerException e){
            //e.printStackTrace();
        }
        ((RecommendAppViewHolder)viewHolder).AppDesc.setText(ganks.get(i).desc.toString());

        try {
            ((RecommendAppViewHolder)viewHolder).AppWho.setText(ganks.get(i).who.toString());
        }catch (NullPointerException e){
            //e.printStackTrace();
        }
        ((RecommendAppViewHolder) viewHolder).AppPics.setFocusable(true);
    }

    @Override
    public int getItemCount() {
        return ganks.size();
    }


    class RecommendAppViewHolder extends RecyclerView.ViewHolder{
        private ImageView AppPics;
        private TextView AppDesc;
        private TextView AppWho;
        private CardView AppCard;
        public RecommendAppViewHolder(View itemView) {
            super(itemView);
            AppCard = (CardView) itemView.findViewById(R.id.app_recommend_cardview);
            AppPics = (ImageView) itemView.findViewById(R.id.app_recommend_pic);
            AppDesc = (TextView) itemView.findViewById(R.id.app_recommend_title_desc);
            AppWho = (TextView) itemView.findViewById(R.id.app_recommend_who);
        }
    }
}
