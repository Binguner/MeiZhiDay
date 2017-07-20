package com.example.nenguou.meizhiday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.Fragments.AndroidFragment;
import com.example.nenguou.meizhiday.Fragments.IOSFragment;
import com.example.nenguou.meizhiday.Fragments.VideoFragment;
import com.example.nenguou.meizhiday.Fragments.appRecommendFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class GankAty extends AppCompatActivity {

    private SlidingTabLayout gankTab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] Titles ={"Android","iOS","App推荐","前端"};
    private MyPageAdapter myPageAdapter;
    private ViewPager view_pager;
    private TextView gank_title;
    private EditText gank_search_edittext;
    private Toolbar gankToolbar;
    private static int flag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_gank_aty);
        initId();
    }

    private void initId() {
        gankToolbar = (Toolbar) findViewById(R.id.gankToolbar);
        setSupportActionBar(gankToolbar);
        gank_search_edittext = (EditText) findViewById(R.id.gank_search_edittext);
        gank_title = (TextView) findViewById(R.id.gank_title);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        gankTab = (SlidingTabLayout) findViewById(R.id.gankTab);
        initViews();
    }

    //创建搜索按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //1 是关闭
        if(flag == 1){
            gankTab.setVisibility(View.GONE);
            view_pager.setVisibility(View.GONE);
            gank_title.setVisibility(View.GONE);
            gank_search_edittext.setVisibility(View.VISIBLE);
            flag = 0;
        }/*else {
            gankTab.setVisibility(View.VISIBLE);
            view_pager.setVisibility(View.VISIBLE);
            gank_title.setVisibility(View.VISIBLE);
            gank_search_edittext.setVisibility(View.GONE);
            flag = 1;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        fragments.add(AndroidFragment.newInstance());
        fragments.add(IOSFragment.newInstance());
        fragments.add(appRecommendFragment.newInstance());
        fragments.add(VideoFragment.newInstance());
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        view_pager.setAdapter(myPageAdapter);
        view_pager.setOffscreenPageLimit(4);
        gankTab.setViewPager(view_pager);

        gankToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0){
                    gankTab.setVisibility(View.VISIBLE);
                    view_pager.setVisibility(View.VISIBLE);
                    gank_title.setVisibility(View.VISIBLE);
                    gank_search_edittext.setVisibility(View.GONE);
                    flag = 1;
                }else {
                    finish();
                }
            }
        });

        gank_search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(GankAty.this,charSequence,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void Gank_go_top(View view){

    }

    private class MyPageAdapter extends FragmentPagerAdapter{

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void onBackPressed() {
        if(flag == 0){
            gankTab.setVisibility(View.VISIBLE);
            view_pager.setVisibility(View.VISIBLE);
            gank_title.setVisibility(View.VISIBLE);
            gank_search_edittext.setVisibility(View.GONE);
            flag = 1;
        }else {
            finish();
        }
    }
}
