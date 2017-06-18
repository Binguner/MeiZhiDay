package com.example.nenguou.meizhiday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nenguou.meizhiday.Bean.MeiZHI;
import com.example.nenguou.meizhiday.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nenguou on 2017/6/4.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<MeiZHI> meiZHIS;
    private onItemClickListener my_onItemClickListener= null;

    public void setOnItemClickListener(onItemClickListener my_onItemClickListener){
        this.my_onItemClickListener = my_onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if(my_onItemClickListener != null){
            my_onItemClickListener.onItemClick(view);
        }
    }

    public static interface onItemClickListener{
        void onItemClick(View view);
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



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {

            /*Glide.with(context).load(meiZHIS.get(i).url)
                    .centerCrop()
                    .into(((MyViewHolder)viewHolder).imageButton)
                    .getSize((width,height)->{
                        if(!((MyViewHolder) viewHolder).imageButton.isShown()){
                            ((MyViewHolder) viewHolder).imageButton.setVisibility(View.VISIBLE);
                        }
                    });*/
            Picasso.with(context).load(meiZHIS.get(i).url).placeholder(R.mipmap.placeholder).error(R.mipmap.placeholder).into(((MyViewHolder) viewHolder).imageButton);

    }

    @Override
    public int getItemCount() {
        return meiZHIS.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
