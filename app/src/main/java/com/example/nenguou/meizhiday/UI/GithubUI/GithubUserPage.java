package com.example.nenguou.meizhiday.UI.GithubUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.nenguou.meizhiday.Bean.GitUserBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.Utils.CallTokenBack;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.GET;

public class GithubUserPage extends AppCompatActivity {

    private CircleImageView gitPage_portrait;
    private GetGitInfoUtils getGitInfoUtils;
    private GitUserBean gitUserBean;
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
        Log.d("Textd","运行类");
        getGitInfoUtils = new GetGitInfoUtils(this);
        getGitInfoUtils.setCallBack(new CallTokenBack() {
            @Override
            public void callBackToken(String token) {

            }

            @Override
            public void callUserBeanBack(GitUserBean gitUserBean) {
                GithubUserPage.this.gitUserBean = gitUserBean;
                Log.d("Textd",gitUserBean.getAvatar_url());
            }
        });
    }

    private void initId() {
        gitPage_portrait = (CircleImageView) findViewById(R.id.gitPage_portrait);

    }
}
