package com.example.nenguou.meizhiday.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.R;

public class GithubPageActivity extends AppCompatActivity {

    private WebView app_recommend_webview;
    private String url;
    private String title;
    private TextView github_title;
    private ImageButton back_24_white;
    private ImageView github_menu;
    private android.support.v7.widget.Toolbar gankgithubToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_github_page);
        initId();
        getBundle();
        setWebView();
        initView();
        setListener();
    }

    //创建三个点的菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.github_menu,menu);
        return true;
    }

    //设置 menu 中的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.github_copy_the_link:
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("github_url",url));
                Toast.makeText(GithubPageActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.github_open_in_brosher:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.github_share:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT,title+" "+url+" from「啊」https://fir.im/aGank");
                startActivity(Intent.createChooser(intent1,"分享"));
                Toast.makeText(GithubPageActivity.this,"Share",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListener() {;
        gankgithubToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        github_title.setText(title);
        //app_recommend_webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //app_recommend_webview.getSettings().setDomStorageEnabled(false);

        //CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
        //CookieManager cookieManager = CookieManager.getInstance();
       // cookieManager.removeAllCookie();



    }

    private void setWebView() {
        app_recommend_webview.getSettings().setJavaScriptEnabled(true);
        app_recommend_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        app_recommend_webview.getSettings().setLoadWithOverviewMode(true);
        app_recommend_webview.getSettings().setUseWideViewPort(true);
        app_recommend_webview.loadUrl(url);
        app_recommend_webview.setWebViewClient(new WebViewClient());
    }

    private void getBundle() {
        try {
            Bundle bundle = new Bundle();
            bundle = this.getIntent().getExtras();
            url = bundle.getString("url");
            title = bundle.getString("title");
        }catch (Exception e){
            Log.d("ErrorInfo",e+"");
        }
    }

    private void initId() {
        gankgithubToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.gankgithubToolbar);
        //没这句TM不显示 menu 菜单啊啊啊啊啊啊啊啊啊啊啊
        setSupportActionBar(gankgithubToolbar);
        github_title = (TextView) findViewById(R.id.github_title);
        app_recommend_webview = (WebView) findViewById(R.id.app_recommend_webview);
    }

    //处理按下 back 的事物，优先返回 前一页，最后结束该 activity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && app_recommend_webview.canGoBack()){
            app_recommend_webview.goBack();
            return true;
        }else {
            finish();
        }
        return true;
    }
}
