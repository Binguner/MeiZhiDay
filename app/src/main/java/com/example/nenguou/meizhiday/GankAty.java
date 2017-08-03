package com.example.nenguou.meizhiday;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextPaint;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.nenguou.meizhiday.Bean.Gank;
import com.example.nenguou.meizhiday.Bean.SearchBean;
import com.example.nenguou.meizhiday.Fragments.AndroidFragment;
import com.example.nenguou.meizhiday.Fragments.IOSFragment;
import com.example.nenguou.meizhiday.Fragments.VideoFragment;
import com.example.nenguou.meizhiday.Fragments.appRecommendFragment;
import com.example.nenguou.meizhiday.GetDatas.GankOkhttp;
import com.example.nenguou.meizhiday.Rx.GetSearchUtils;
import com.example.nenguou.meizhiday.Utils.RxUtils;
import com.example.nenguou.meizhiday.adapter.Search_results_Adapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.io.IOException;
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
    private SwipeRefreshLayout search_swipeRefreshlayout;
    private RecyclerView search_recyclerview;
    private LinearLayoutManager linearLayoutManager;
    public Search_results_Adapter search_results_adapter;
    private int lastViewItem;

    public List<SearchBean> mainSearchBeans = new ArrayList<>();

    private String searchingwhat = null;

    private GetSearchUtils getSearchUtils = null/*new GetSearchUtils(this,search_results_adapter)*/;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_gank_aty);
        initId();
        setRecyclerView();
        setListener();
        setButtonClick();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                search_swipeRefreshlayout.setRefreshing(true);
            }if(msg.what == 0){
                Log.d("thresdas",Thread.currentThread().getName());
                search_results_adapter.DeleteAllBeans();
                search_results_adapter.no
            }
        }
    };

    @SuppressLint("ResourceAsColor")
    private void setRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        search_recyclerview.setLayoutManager(linearLayoutManager);
        search_recyclerview.hasFixedSize();
        search_swipeRefreshlayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorYello, R.color.colorAccent, R.color.colorTablayout);

        //search_results_adapter = getSearchUtils.getAdapter();
        search_results_adapter = new Search_results_Adapter(R.layout.card_layout_android_ios,mainSearchBeans);

        search_recyclerview.setAdapter(search_results_adapter);
        search_results_adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        search_results_adapter.isFirstOnly(true);
       // getSearchUtils = new GetSearchUtils(this,search_results_adapter,mainSearchBeans,search_swipeRefreshlayout,searchingwhat);



        search_results_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("dianjile","dianjile"+" "+position);
            }
        });


        search_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastViewItem +3 >= linearLayoutManager.getItemCount()){

                    getSearchUtils.GetSearchReasults(++page);
                    Log.d("pagege","第"+page+"个");
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastViewItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private void initId() {
        search_swipeRefreshlayout = (SwipeRefreshLayout) findViewById(R.id.search_swipeRefreshlayout);
        search_recyclerview = (RecyclerView) findViewById(R.id.search_recyclerview);
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
                Message message1 = new Message();
                message1.what = 0;
                handler.sendMessage(message1);
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

    //搜索按钮功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //1 是关闭
        int BtnId = 0;
        Button button = null;
        if (flag == 1) {
            chooseBtn(search_all);
            chooseTypeLinnearLayout.setVisibility(View.VISIBLE);
            gankTab.setVisibility(View.GONE);
            view_pager.setVisibility(View.GONE);
            gank_title.setVisibility(View.GONE);
            gank_search_edittext.setVisibility(View.VISIBLE);
            search_swipeRefreshlayout.setVisibility(View.VISIBLE);
            search_recyclerview.setVisibility(View.VISIBLE);
            mainSearchBeans.clear();


            flag = 0;
        }
        try {
             Search();
        } catch (IOException e) {
             e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Search() throws IOException {


        if(search_all.isSelected()==true){




            getSearchUtils = new GetSearchUtils(this,search_results_adapter,mainSearchBeans,search_swipeRefreshlayout,searchingwhat);

            Message message = Message.obtain();
            message.what = 1;
            handler.sendMessage(message);

            getSearchUtils.GetSearchReasults(page);

        }
        if(search_android.isSelected() == true){
            Toast.makeText(this,"Android", Toast.LENGTH_SHORT).show();
        }
        if(search_iOS.isSelected()==true){
            Toast.makeText(this,"iOS", Toast.LENGTH_SHORT).show();
        }
        if(search_front.isSelected()==true){
            Toast.makeText(this,"front", Toast.LENGTH_SHORT).show();
        }
        if(search_app.isSelected()==true){
            Toast.makeText(this,"app", Toast.LENGTH_SHORT).show();
        }
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
                    search_swipeRefreshlayout.setVisibility(View.GONE);
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
                searchingwhat = editable.toString();
                //Toast.makeText(GankAty.this,searchingwhat,Toast.LENGTH_SHORT).show();
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
            search_swipeRefreshlayout.setVisibility(View.GONE);
            flag = 1;
        } else {
            finish();
        }
    }
}
