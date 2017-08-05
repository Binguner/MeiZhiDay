package com.example.nenguou.meizhiday.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.R;
import com.example.nenguou.meizhiday.Rx.GetGitInfoUtils;

public class gittest extends AppCompatActivity {

    private Button getgithubinfo,GetCode;
    private GetGitInfoUtils getGitInfoUtils = null;
    private TextView github_code;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_gittest);
        initId();
        setListener();
        getGitCode();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    github_code.setText("code:"+code);
            }
        }
    };


    private void getGitCode() {
        SharedPreferences sharedPreferences = getSharedPreferences("gitCode", Context.MODE_PRIVATE);
        code = sharedPreferences.getString("code",null);
        Message message = new Message();
        message.what = 0;
        handler.sendMessage(message);

    }

    private void initId() {
        github_code = (TextView) findViewById(R.id.github_code);
        getgithubinfo = (Button) findViewById(R.id.getgithubinfo);
        GetCode = (Button) findViewById(R.id.GetCode);
    }

    private void setListener(){
        getgithubinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGitInfoUtils = new GetGitInfoUtils(gittest.this);
                getGitInfoUtils.getGitInfo();
            }
        });

        GetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGitInfoUtils = new GetGitInfoUtils(code);
                Log.d("showCodedede",getGitInfoUtils.showCode().toString());
                try {
                    getGitInfoUtils.getGitToken();
                }catch (Exception e){
                    Log.d("GetTokenError","ErrorOnToken: " + e);
                }
            }
        });
    }

    public String getCodeBack(){
        return code;
    }
}
