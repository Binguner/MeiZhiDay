package com.example.nenguou.meizhiday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class GithubPageActivity extends AppCompatActivity {

    private WebView app_recommend_webview;
    private String url;
    private String title;
    private TextView github_title;
    private ImageButton back_24_white;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_github_page);

        initId();
        getBundle();
        setWebView();
        initView();
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
        github_title = (TextView) findViewById(R.id.github_title);
        back_24_white = (ImageButton) findViewById(R.id.github_back);
        back_24_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
