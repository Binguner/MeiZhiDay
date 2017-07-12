package com.example.nenguou.meizhiday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.github_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.github_copy_the_link:
                Toast.makeText(GithubPageActivity.this,"link",Toast.LENGTH_SHORT).show();
                break;
            case R.id.github_open_in_brosher:
                Toast.makeText(GithubPageActivity.this,"brosher",Toast.LENGTH_SHORT).show();
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
        /*github_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GithubPageActivity.this,"hh",Toast.LENGTH_SHORT).show();
                openOptionsMenu();
   *//*             new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(GithubPageActivity.this,"h",Toast.LENGTH_SHORT).show();
                        Instrumentation instrumentation = new Instrumentation();
                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
                    }
                }).start();*//*

            }
        });*/
    }

    private void initView() {
      //  github_title.setMovementMethod(new ScrollingMovementMethod());
        github_title.setText(title);
    }

    private void setWebView() {
        app_recommend_webview.getSettings().setJavaScriptEnabled(true);
        app_recommend_webview.loadUrl(url);
        app_recommend_webview.setWebViewClient(new WebViewClient());
    }

    private void getBundle() {
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        url = bundle.getString("url");
        title = bundle.getString("title");
    }

    private void initId() {
       // github_menu = (ImageView) findViewById(R.id.github_menu);
        gankgithubToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.gankgithubToolbar);
      //  setActionBar(gankgithubToolbar);
        github_title = (TextView) findViewById(R.id.github_title);
        app_recommend_webview = (WebView) findViewById(R.id.app_recommend_webview);
    }

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
