package com.example.nenguou.meizhiday.UI.GithubUI;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.example.nenguou.meizhiday.R;

public class RepoActivity extends AppCompatActivity {

    private Toolbar repo_toolbar;
    private CollapsingToolbarLayout repo_collapsingtoolbarlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github_page);
        setContentView(R.layout.activity_repo);
        initId();
        initViews();
        setListener();
    }

    private void initViews() {
        setSupportActionBar(repo_toolbar);
        repo_toolbar.setNavigationIcon(R.mipmap.back_24_white);
        repo_collapsingtoolbarlayout.setTitle("DroidReverse");
        repo_collapsingtoolbarlayout.setCollapsedTitleGravity(Gravity.LEFT);
       // repo_collapsingtoolbarlayout.setExpandedTitleGravity(Gravity.LEFT);
        repo_collapsingtoolbarlayout.setExpandedTitleMarginBottom(40);
        repo_collapsingtoolbarlayout.setExpandedTitleMarginStart(80);
        repo_collapsingtoolbarlayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        repo_collapsingtoolbarlayout.setExpandedTitleColor(getResources().getColor(R.color.colorPink));
    }

    private void setListener() {
        repo_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initId() {
        repo_toolbar = (Toolbar) findViewById(R.id.repo_toolbar);
        repo_collapsingtoolbarlayout = (CollapsingToolbarLayout) findViewById(R.id.repo_collapsingtoolbarlayout);
    }
}
