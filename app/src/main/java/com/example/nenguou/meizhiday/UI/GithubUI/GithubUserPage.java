package com.example.nenguou.meizhiday.UI.GithubUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nenguou.meizhiday.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class GithubUserPage extends AppCompatActivity {

    private CircleImageView gitPage_portrait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_github_page);
        setContentView(R.layout.activity_github_user_page);
        initId();
        initViews();
    }

    private void initViews() {
        //获取回调的userbean
        //RxJava 设置界面

    }

    private void initId() {
        gitPage_portrait = (CircleImageView) findViewById(R.id.gitPage_portrait);

    }
}
