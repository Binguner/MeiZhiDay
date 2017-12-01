package com.example.nenguou.meizhiday;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.Entity.MeiZHI;
import com.example.nenguou.meizhiday.UI.others.About;
import com.example.nenguou.meizhiday.UI.GithubUI.GithubUserPage;
import com.example.nenguou.meizhiday.UI.others.GithubPageActivity;
import com.example.nenguou.meizhiday.UI.others.gittest;
import com.example.nenguou.meizhiday.adapter.MeiZhiAdapter;
import com.example.nenguou.meizhiday.network.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    private DrawerLayout mydrawerlayout;
    private SwipeRefreshLayout my_swipeRefreshLayout;
    private NavigationView navigationView_test;
    private Toolbar myToolbar;
    //private GridLayoutManager my_staggeredGridLayoutManager;
    private StaggeredGridLayoutManager my_staggeredGridLayoutManager;
    private RecyclerView my_recyclerView;
    private int count = 2;
    private int lastVisibleItem ;
    private ArrayList<MeiZHI> main_meizhis = new ArrayList<>();
    private String content;
    private MeiZhiAdapter meiZhiAdapter;
    private ImageButton imageButtonhaha;
    private ImageView menu_24, gitcat;

    //private int FLAGSS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FreelineCore.init(MainActivity.this);
        setTheme(R.style.AppTheme_main);
        setContentView(R.layout.activity_main);
        getGitInfo();
        askPermission();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initId();
        initViews();    //initViews()在前头，解决了刚进入后 title 位置的问题
        setToolbar();
        setClickListener();

        if (!Utils.isNetworkAvailable(MainActivity.this))
        {
            Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
        }else {
            firstLoad();
            setRefreshListener();
        }
    }

    private void getGitInfo() {
        String path = this.getCacheDir().toString();
        Log.d("cachePAth",path);
        SharedPreferences sharedPreferences = getSharedPreferences("cachePath",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putString("mCachePath",path);
        editor1.commit();

        try{
            Intent i_getValue = getIntent();
            String codeUrl = i_getValue.getAction();
            if(Intent.ACTION_VIEW.equals(codeUrl)){
                Uri uri = i_getValue.getData();
                if(uri != null){
                    String code = uri.getQueryParameter("code");

                    SharedPreferences sharedPreferences1 = getSharedPreferences("gitCode", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("code",code);
                    editor.commit();
            }
        }
        }catch (Exception e){
            Log.d("getGitInfoFaild","onError: "+e);
        }

        try{

        }catch (Exception e){

        }
    }

    private void askPermission() {
        //PermissionUtils.requestPermission(this,PermissionUtils.CODE_READ_EXTERNAL_STORAGE,);
    }

    //设置各种点击事件
    private void setClickListener() {
        //gitcat点击事件，进入个人主页
        gitcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GithubUserPage.class);
                startActivity(intent);
            }
        });


        //menu 点击事件 --打开侧滑菜单
        menu_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydrawerlayout.openDrawer(Gravity.LEFT);
            }
        });

        //侧边栏的点击事件
        navigationView_test.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                String msg = "";
                switch(menuItem.getItemId()){
                    case R.id.Android:
                        //msg +="Android";
                        Intent intent = new Intent(MainActivity.this,GankAty.class);
                        startActivity(intent);

                        break;
                    case R.id.About:
                        //msg +="关于";
                        Intent intent1 = new Intent(MainActivity.this,About.class);
                        startActivity(intent1);
                        break;

                    case R.id.Github_menu:
                        Intent intent2 = new Intent(MainActivity.this,gittest.class);
                        startActivity(intent2);
                        break;

                    case R.id.gitcat:
                        Log.d("doahfiluhnasofna;s","osdmsdno");
                        break;

                    case R.id.exit_app:
                        finish();
                        break;
                    case R.id.login_github:
                        Intent gitIntent = new Intent(MainActivity.this, GithubPageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title","GitHub - Build software better, together.");
                        bundle.putString("url","https://github.com/login?return_to=%2Fjoin%3Fsource%3Dbutton-home");
                        gitIntent.putExtras(bundle);
                        startActivity(gitIntent);
                        break;
                    default:
                        break;
                }
                if(!"".equals(msg)){
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
                mydrawerlayout.closeDrawer(Gravity.LEFT);

                return true;
            }
        });
    }

    //打开应用第一次加载数据
    private void firstLoad() {
        try{
            new getData(MainActivity.this, 0).execute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1");
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    //初始化页面
    private void initViews() {
       // my_staggeredGridLayoutManager = new GridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mydrawerlayout = (DrawerLayout) findViewById(R.id.mydrawerlayout);
        View LeftView = getLayoutInflater().inflate(R.layout.header_layout,null);
        navigationView_test.addHeaderView(LeftView);
        navigationView_test.setItemIconTintList(null);
        View headerView = navigationView_test.getHeaderView(0);
        gitcat = (ImageView) headerView.findViewById(R.id.gitcat);

        menu_24 = (ImageView) findViewById(R.id.menu_24);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);

        my_staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        //my_staggeredGridLayoutManager.setGapStrategy(2);
        meiZhiAdapter = new MeiZhiAdapter(this,main_meizhis);
        my_recyclerView.setAdapter(meiZhiAdapter);
        my_recyclerView.setHasFixedSize(true);
        //my_recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //设置 item 间隔
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(8);
        my_recyclerView.addItemDecoration(spacesItemDecoration);



        my_swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorWhite,R.color.colorYello,R.color.colorPrimary);

        //处理 item 乱动
        my_staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        my_recyclerView.setLayoutManager(my_staggeredGridLayoutManager);
        //my_recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        //my_recyclerView.setPadding(0,0,0,0);

    }

    private void setToolbar() {
        setSupportActionBar(myToolbar);
        collapsingToolbarLayout.setTitle("Meizhi");
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.LEFT);   //收缩后标题的位置
        //collapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT);  //设置展开后标题的位置
        collapsingToolbarLayout.setExpandedTitleMarginBottom(40);
        collapsingToolbarLayout.setExpandedTitleMarginStart(80);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPink));

    }

    private void initId() {
        navigationView_test = (NavigationView) findViewById(R.id.navigationView_test);
        imageButtonhaha = (ImageButton) findViewById(R.id.menu_24);
        my_recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_swipe_refresh_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.myCollapsingToolbarLayout);
    }


    public void Go_top(View view){

    }
    //private int times = 0;
    private void setRefreshListener() {
        my_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //if(times ==0) {
                   // Log.i("timessss",times+"2");
                  //  new getData(MainActivity.this, 0).execute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                   // times++;
              //  }else {
                  //  Log.i("timessss",times+"2");
                try {
                    new getData(MainActivity.this, 1).execute("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                }
               // }
            }
        });

        my_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //my_staggeredGridLayoutManager.invalidateSpanAssignments();
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("counttt",count+" ");
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=my_staggeredGridLayoutManager.getItemCount()&&my_staggeredGridLayoutManager.getItemCount()>2) {
                    try {
                        new getData(MainActivity.this,0).execute("http://gank.io/api/data/福利/20/"+(count++));
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
                    }
                }

                my_staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions= my_staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem =Math.max(positions[0],positions[1]);//根据StaggeredGridLayoutManager设置的列数来定
                //lastVisibleItem = my_staggeredGridLayoutManager.findLastVisibleItemPosition();
            }
        });


    }

    private class getData extends AsyncTask<String,Void,List<MeiZHI>>{

        private int Flags;
        private String content;
        List<MeiZHI> meizhis;
        String jsonData;
        private MainActivity context;

        public getData(MainActivity context,int Flags){
            this.context = context;
            this.Flags = Flags;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            my_swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<MeiZHI> doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request
                    .Builder()
                    //.url("http://gank.io/api/data/福利/10/0x")
                    .url(params[0])
                    .build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.isSuccessful()){
                try {
                    content = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();

                try {
                    JSONObject jsonObject = new JSONObject(content);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Type type = new TypeToken<ArrayList<MeiZHI>>(){
                }.getType();
                try {
                    meizhis = gson.fromJson(jsonData,type);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                  //  Log.i("hahaha",meizhis.get(0).roString());
            }

            return meizhis;
        }

        @Override
        protected void onPostExecute(List<MeiZHI> meiZHIS) {
            super.onPostExecute(meiZHIS);
            switch (Flags){
                case 0:
                    main_meizhis.addAll(meizhis);
                    //context.meiZhiAdapter.notifyDataSetChanged();
                    context.meiZhiAdapter.notifyItemInserted(main_meizhis.size());
                    context.my_swipeRefreshLayout.setRefreshing(false);
                    break;
                case 1:
                  /*  main_meizhis.addAll(meiZHIS);
                    context.meiZhiAdapter.notifyDataSetChanged();*/
                    context.my_swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this,"No more datas",Toast.LENGTH_SHORT).show();
                    break;
            }
            meiZhiAdapter.setOnItemClickListener(new MeiZhiAdapter.onItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    int postion = my_recyclerView.getChildAdapterPosition(view);
                    Intent intent = new Intent(MainActivity.this,Pics.class);
                    Bundle bundle = new Bundle();
                    Log.i("ueluel",main_meizhis.get(postion).roString());
                    bundle.putString("url",main_meizhis.get(postion).roString());
                    Log.i("fds",main_meizhis.get(postion).desc);
                    bundle.putString("title",main_meizhis.get(postion).desc);
                    intent.putExtras(bundle);
                    //Toast.makeText(MainActivity.this,"第"+postion+"个",Toast.LENGTH_SHORT).show();
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,(int)view.getWidth()/2,(int)view.getHeight()/2,0,0);
                    ActivityCompat.startActivity(MainActivity.this,intent,compat.toBundle());
                }
            });

   /*         if(FLAGSS == 1){
                context.my_swipeRefreshLayout.setRefreshing(false);

            }else if(main_meizhis.isEmpty()){
                main_meizhis.addAll(meiZHIS);

                context.meiZhiAdapter.notifyDataSetChanged();
                context.my_swipeRefreshLayout.setRefreshing(false);
               *//* for(int i = 0;i < meizhis.size();i++){
                    main_meizhis.add(meizhis.get(i));
                    FLAGSS = 1;
                    Log.i("tete",meizhis.get(i).url.toString());
                }*//*

            }if(main_meizhis.get(0).url != meiZHIS.get(0).url) {
                main_meizhis.addAll(meiZHIS);

                context.meiZhiAdapter.notifyDataSetChanged();
                context.my_swipeRefreshLayout.setRefreshing(false);
              *//*  for(int i = 0;i < meizhis.size();i++){
                    main_meizhis.add(meizhis.get(i));
                    Log.i("tete",meizhis.get(i).url.toString());
                }*//*
            }*/
           /* context.meiZhiAdapter.notifyDataSetChanged();
            context.my_swipeRefreshLayout.setRefreshing(false);*/
        }
    }
}
