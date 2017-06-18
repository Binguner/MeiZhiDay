package com.example.nenguou.meizhiday;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nenguou.meizhiday.Services.DownLoadImageService;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Pics extends AppCompatActivity {

    private PhotoView meizhi_Pic;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initId();

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        url = bundle.getString("url");
        Glide.with(this).load(url).into(meizhi_Pic);

        meizhi_Pic.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                backToMainAty(view);
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(Pics.this,"保存成功",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(Pics.this,"保存失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void DownloadPics(View view){
        Log.i("clickk","ls");
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(), url, new DownLoadImageService.ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(File file) {

                //handler.sendMessage(msg);
            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
             //   Message message = new Message();
               // message.what = MSG_VISIBLE;
               // handler.sendMessageDelayed(message, delayTime);
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }

            @Override
            public void onDownLoadFailed() {
               Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
        new Thread(service).start();
    }

    private void initId() {
        meizhi_Pic = (PhotoView) findViewById(R.id.meizhi_Pic);
    }

    public void backToMainAty(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  ActivityCompat.finishAffinity(this);
        ActivityCompat.finishAfterTransition(this);
    }
}
