package com.example.nenguou.meizhiday.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.nenguou.meizhiday.Bean.RepoBean;
import com.example.nenguou.meizhiday.R;

import java.util.List;

/**
 * Created by binguner on 2017/8/16.
 */

public class RepoAdapter extends BaseQuickAdapter<RepoBean,RepoAdapter.MyViewHolder>{

    public RepoAdapter(int layoutResId, @Nullable List<RepoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MyViewHolder helper, RepoBean item) {
        
    }

    public class MyViewHolder extends BaseViewHolder{

        private TextView card_layout_repos_reponame,card_layout_repos_fored_from_where,card_layout_repos_des,card_layout_repos_language,card_layout_repos_fork_numbwe,card_layout_repos_star_number;
        public MyViewHolder(View view) {
            super(view);
            card_layout_repos_reponame = (TextView) view.findViewById(R.id.card_layout_repos_reponame);
            card_layout_repos_fored_from_where = (TextView) view.findViewById(R.id.card_layout_repos_fored_from_where);
            card_layout_repos_des = (TextView) view.findViewById(R.id.card_layout_repos_des);
            card_layout_repos_language = (TextView) view.findViewById(R.id.card_layout_repos_language);
            card_layout_repos_fork_numbwe = (TextView) view.findViewById(R.id.card_layout_repos_fork_numbwe);
            card_layout_repos_star_number = (TextView) view.findViewById(R.id.card_layout_repos_star_number);
        }
    }
}
