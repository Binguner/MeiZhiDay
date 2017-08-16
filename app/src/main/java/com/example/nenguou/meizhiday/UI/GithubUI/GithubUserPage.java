package com.example.nenguou.meizhiday.UI.GithubUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.nenguou.meizhiday.Bean.GitUserBean;
import com.example.nenguou.meizhiday.Fragments.MyEventsFragment;
import com.example.nenguou.meizhiday.Fragments.ReposFragment;
import com.example.nenguou.meizhiday.Fragments.WatchEventsFragment;
import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;
import com.example.nenguou.meizhiday.Rx.SetUserUtils;
import com.example.nenguou.meizhiday.Utils.AppBarStateChangeListener;
import com.example.nenguou.meizhiday.Utils.CallTokenBack;
import com.example.nenguou.meizhiday.Utils.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public class GithubUserPage extends AppCompatActivity {

    private CircleImageView gitPage_portrait;
    private GetGitInfoUtils getGitInfoUtils;
    private GitUserBean gitUserBean;
    private String userBean_avatar_url;
    private ImageView circleImage1,circleImage2,github_page_back;

    private TabLayout github_tablelayout1;
    private AppBarLayout github_page_appbarlayout1;
    private CircleImageView github_circleImageView1;
    private TextView github_desc;
    private int circleIVFlag = 1;//0 不可见 ，1 可见
    private ViewPager github_viewpager1;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyPagetAdapter myPagetAdapter;
    private String[] titles = {"WatchEventBean","MyEvents","Repos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_github_page);
        setContentView(R.layout.activity_github_user_page);
        initId();
        initViews();
        setListener();
    }

    private void setListener() {
        github_page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        github_page_appbarlayout1.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("AppBarState",state.name().toString());
                if(state == State.EXPANDED){
                    //展开状态
                    github_circleImageView1.setVisibility(View.VISIBLE);
                    circleIVFlag = 1;
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    github_circleImageView1.setVisibility(View.GONE);
                    circleIVFlag = 0;
                }else {
                    //中间状态
                    if(circleIVFlag == 1) {
                        Animation animation = AnimationUtils.loadAnimation(GithubUserPage.this, R.anim.small_animation);
                        github_circleImageView1.startAnimation(animation);
                        github_circleImageView1.setVisibility(View.INVISIBLE);
                    }else{
                        Animation animation = AnimationUtils.loadAnimation(GithubUserPage.this,R.anim.bigger_animation);
                        github_circleImageView1.startAnimation(animation);
                    }
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    try{
                        Log.d("ururur",userBean_avatar_url);
                        Picasso.with(GithubUserPage.this).load(userBean_avatar_url).placeholder(R.mipmap.gitcat)
                                .error(R.mipmap.gitcat).resize(80,80)
                                .into(github_circleImageView1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        }
    };

    private void initViews() {

        fragments.add(WatchEventsFragment.newInstance());
        fragments.add(MyEventsFragment.newInstance());
        fragments.add(ReposFragment.newInstance());
        myPagetAdapter = new MyPagetAdapter(getSupportFragmentManager());
        github_viewpager1.setAdapter(myPagetAdapter);
        github_tablelayout1.setupWithViewPager(github_viewpager1);
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
        },null);

        SharedPreferences sharedPreferences = getSharedPreferences("UseerBean", Context.MODE_PRIVATE);
        userBean_avatar_url = sharedPreferences.getString("userBean_avatar_url",null);
        Log.d("itsatest",userBean_avatar_url.toString());
        Message message = Message.obtain();
        message.what = 0;
        handler.sendMessage(message);

        //修改UI界面
        //设置 Tablayout




    }

    private void initId() {
        github_page_back = (ImageView) findViewById(R.id.github_page_back);
        github_viewpager1 = (ViewPager) findViewById(R.id.github_viewpager1);
        github_desc = (TextView) findViewById(R.id.github_desc);
        github_circleImageView1 = (CircleImageView) findViewById(R.id.github_circleImageView1);
        github_page_appbarlayout1 = (AppBarLayout) findViewById(R.id.github_page_appbarlayout1);
        github_tablelayout1 = (TabLayout) findViewById(R.id.github_tablelayout1);


       /* circleImage1 = (ImageView) findViewById(R.id.circleImage1);
        //circleImage2 = (ImageView) findViewById(R.id.circleImage2);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.circle_animation);
        circleImage1.startAnimation(animation);
        //circleImage2.startAnimation(animation);
        gitPage_portrait = (CircleImageView) findViewById(R.id.gitPage_portrait);*/

    }

    public class MyPagetAdapter extends FragmentPagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public MyPagetAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
