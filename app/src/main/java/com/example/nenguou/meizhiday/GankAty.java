package com.example.nenguou.meizhiday;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.Bean.Gank;
import com.example.nenguou.meizhiday.Fragments.AndroidFragment;
import com.example.nenguou.meizhiday.Fragments.IOSFragment;
import com.example.nenguou.meizhiday.Fragments.VideoFragment;
import com.example.nenguou.meizhiday.Fragments.appRecommendFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class GankAty extends AppCompatActivity {

    private SlidingTabLayout gankTab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] Titles = {"Android", "iOS", "App推荐", "前端"};
    private MyPageAdapter myPageAdapter;
    private ViewPager view_pager;
    private TextView gank_title;
    private EditText gank_search_edittext;
    private Toolbar gankToolbar;
    private Button search_all, search_android, search_iOS, search_front, search_app;
    private LinearLayout chooseTypeLinnearLayout;
    private static int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_gank_aty);
        initId();
        setListener();
        setButtonClick();
    }

    private void initId() {
        search_app = (Button) findViewById(R.id.search_app);
        search_front = (Button) findViewById(R.id.search_front);
        search_iOS = (Button) findViewById(R.id.search_iOS);
        search_android = (Button) findViewById(R.id.search_android);
        chooseTypeLinnearLayout = (LinearLayout) findViewById(R.id.chooseTypeLinnearLayout);
        search_all = (Button) findViewById(R.id.search_all);
        gankToolbar = (Toolbar) findViewById(R.id.gankToolbar);
        setSupportActionBar(gankToolbar);
        gank_search_edittext = (EditText) findViewById(R.id.gank_search_edittext);
        gank_title = (TextView) findViewById(R.id.gank_title);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        gankTab = (SlidingTabLayout) findViewById(R.id.gankTab);
        initViews();
    }

    private void setButtonClick() {
        search_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //search_all.setPressed(true);
                chooseBtn(search_all);
                //Drawable drawable = getResources().getDrawable(R.drawable.save_button_bg);
                // search_all.setBackground(drawable);
            }
        });

        search_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseBtn(search_android);
            }
        });
        search_iOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseBtn(search_iOS);
            }
        });
        search_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseBtn(search_front);
            }
        });
        search_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseBtn(search_app);
            }
        });
    }

    private void chooseBtn(Button button) {
        int btnId = button.getId();
        if (btnId == search_all.getId()) {
            search_all.setSelected(true);
            search_android.setSelected(false);
            search_iOS.setSelected(false);
            search_front.setSelected(false);
            search_app.setSelected(false);
        }
        if (btnId == search_android.getId()) {
            search_all.setSelected(false);
            search_android.setSelected(true);
            search_iOS.setSelected(false);
            search_front.setSelected(false);
            search_app.setSelected(false);
        }
        if (btnId == search_iOS.getId()) {
            search_all.setSelected(false);
            search_android.setSelected(false);
            search_iOS.setSelected(true);
            search_front.setSelected(false);
            search_app.setSelected(false);
        }
        if (btnId == search_front.getId()) {
            search_all.setSelected(false);
            search_android.setSelected(false);
            search_iOS.setSelected(false);
            search_front.setSelected(true);
            search_app.setSelected(false);
        }
        if (btnId == search_app.getId()) {
            search_all.setSelected(false);
            search_android.setSelected(false);
            search_iOS.setSelected(false);
            search_front.setSelected(false);
            search_app.setSelected(true);
        }
    }

    //创建搜索按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //1 是关闭
        if (flag == 1) {
            chooseBtn(search_all);
            chooseTypeLinnearLayout.setVisibility(View.VISIBLE);
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
                if (flag == 0) {
                    chooseTypeLinnearLayout.setVisibility(View.GONE);
                    gankTab.setVisibility(View.VISIBLE);
                    view_pager.setVisibility(View.VISIBLE);
                    gank_title.setVisibility(View.VISIBLE);
                    gank_search_edittext.setVisibility(View.GONE);
                    flag = 1;
                } else {
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
                //Toast.makeText(GankAty.this,charSequence,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(GankAty.this,"回d车",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void setListener() {
        gank_search_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (((i == EditorInfo.IME_ACTION_SEND) || (i == EditorInfo.IME_ACTION_DONE)
                        || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN))) {
                    Log.i("T2AG", "idid");
                    Toast.makeText(GankAty.this, "回车", Toast.LENGTH_SHORT).show();

                    new GetSearchDatas("http://gank.io/api/search/query/listview/category/Android/count/10/page/1", 0).execute();

                    return true;
                }
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class GetSearchDatas extends AsyncTask<String, Void, String> {
        private List<Gank> ganks;
        private String url;
        private String datas;
        private String jSONDatas;
        private int flag;

        public GetSearchDatas(String url, int flag) {
            this.url = url;
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public void Gank_go_top(View view) {

    }

    private class MyPageAdapter extends FragmentPagerAdapter {

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
        if (flag == 0) {
            chooseTypeLinnearLayout.setVisibility(View.GONE);
            gankTab.setVisibility(View.VISIBLE);
            view_pager.setVisibility(View.VISIBLE);
            gank_title.setVisibility(View.VISIBLE);
            gank_search_edittext.setVisibility(View.GONE);
            flag = 1;
        } else {
            finish();
        }
    }
}
