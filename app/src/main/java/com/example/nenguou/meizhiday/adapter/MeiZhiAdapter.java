package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.nenguou.meizhiday.Bean.MeiZHI;
import com.example.nenguou.meizhiday.R;

import java.util.ArrayList;

/**
 * Created by Nenguou on 2017/6/4.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<MeiZHI> meiZHIS;
    private onItemClickListener my_onItemClickListener= null;

    @Override
    public void onClick(View view) {
        if(my_onItemClickListener != null){
            //注意这里使用getTag方法获取position
            my_onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    public static interface onItemClickListener{
        void onItemClick(View view,int postion);
    }


    //构造方法
    public MeiZhiAdapter(Context context, ArrayList<MeiZHI> meiZHIS){
        this.context = context;
        this.meiZHIS = meiZHIS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_layout, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);

        return myViewHolder;
    }

    public void setOnItemClickListener(onItemClickListener my_onItemClickListener){
        this.my_onItemClickListener = my_onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        try {
            Glide.with(context).load(meiZHIS.get(i).url).into(((MyViewHolder)viewHolder).imageButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将position保存在imageButton的Tag中，以便点击时进行获取
        ((MyViewHolder) viewHolder).imageButton.setTag(i);
    }

    @Override
    public int getItemCount() {
        return meiZHIS.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageButton imageButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton = (ImageButton) itemView.findViewById(R.id.iv);
        }
    }
}
