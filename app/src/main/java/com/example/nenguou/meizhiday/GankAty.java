package com.example.nenguou.meizhiday;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nenguou.meizhiday.Fragments.appRecommendFragment;
import com.example.nenguou.meizhiday.Fragments.AndroidFragment;
import com.example.nenguou.meizhiday.Fragments.IOSFragment;
import com.example.nenguou.meizhiday.Fragments.VideoFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class GankAty extends AppCompatActivity {

    private SlidingTabLayout gankTab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] Titles ={"Android","IOS","App推荐","休息视频"};
    private MyPageAdapter myPageAdapter;
    private ViewPager view_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_main);
        setContentView(R.layout.activity_gank_aty);
        initId();
    }

    private void initId() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        gankTab = (SlidingTabLayout) findViewById(R.id.gankTab);
        initViews();
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
}
