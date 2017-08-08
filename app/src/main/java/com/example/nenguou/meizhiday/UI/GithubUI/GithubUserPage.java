package com.example.nenguou.meizhiday.UI.GithubUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.nenguou.meizhiday.Bean.GitUserBean;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.Rx.SetUserUtils;
import com.example.nenguou.meizhiday.Utils.CallTokenBack;
import com.example.nenguou.meizhiday.Utils.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.GET;

public class GithubUserPage extends AppCompatActivity {

    private CircleImageView gitPage_portrait;
    private GetGitInfoUtils getGitInfoUtils;
    private GitUserBean gitUserBean;
    private String userBean_avatar_url;
    private ImageView circleImage1,circleImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_github_page);
        setContentView(R.layout.activity_github_user_page);
        initId();
        initViews();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try{
                        ImageLoader.with(GithubUserPage.this,userBean_avatar_url,gitPage_portrait);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        }
    };

    private void initViews() {
        //获取回调的userbean
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

        SharedPreferences sharedPreferences = getSharedPreferences("UseerBean", Context.MODE_PRIVATE);
        userBean_avatar_url = sharedPreferences.getString("userBean_avatar_url",null);
        //Log.d("itsatest",userBean_avatar_url);
        Message message = Message.obtain();
        message.what = 0;
        handler.sendMessage(message);

        //修改UI界面

    }

    private void initId() {
        circleImage1 = (ImageView) findViewById(R.id.circleImage1);
        //circleImage2 = (ImageView) findViewById(R.id.circleImage2);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.circle_animation);
        circleImage1.startAnimation(animation);
        //circleImage2.startAnimation(animation);
        gitPage_portrait = (CircleImageView) findViewById(R.id.gitPage_portrait);

    }
}
