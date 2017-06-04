package com.example.nenguou.meizhiday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Pics extends AppCompatActivity {

    private ImageView meizhi_Pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initId();

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        Glide.with(this).load(url).into(meizhi_Pic);
    }

    private void initId() {
        meizhi_Pic = (ImageView) findViewById(R.id.meizhi_Pic);
    }
}
