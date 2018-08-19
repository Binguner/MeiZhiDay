package com.example.nenguou.meizhiday;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nenguou.meizhiday.Services.DownLoadImageService;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Pics extends AppCompatActivity {

    private PhotoView meizhi_Pic;
    private String url;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_main);
        setContentView(R.layout.activity_pics);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initId();

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        title = bundle.getString("title");
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
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(), url,title,new DownLoadImageService.ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(File file) {

            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
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

    public void SharePics(View view)  {
       // Toast.makeText(Pics.this,"Share",Toast.LENGTH_SHORT).show();
        //先保存图片到本地
        try{
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(), url,title,new DownLoadImageService.ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(File file) {

            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {

            }

            @Override
            public void onDownLoadFailed() {
            }
        });
        new Thread(service).start();
        //获取图片被保存的路径
        String results = "content://"+Environment.getExternalStorageDirectory()+"/Pictures/MeiZhiPics/"+title+"meizi.jpeg";
        //将路径转换为 Uri    注意：results 中的地址前没有“file://”，Uri 的地址前有，有了“file://”才能分享到微信。。。。
        //Uri imageUri = Uri.fromFile(new File(results));
        Log.i("pathpath1",results);
        Intent picsIntent = new Intent(Intent.ACTION_SEND);
        picsIntent.setType("image/*");
        picsIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(results));
        startActivity(Intent.createChooser(picsIntent,"分享照片"));
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        Toast.makeText(this, "Uri:" + uriPath, Toast.LENGTH_SHORT).show();
        return uriPath;
    }


    private Drawable loadingImageFromNet(String urladdr) {
        Drawable drawable = null;
        try{
            drawable = Drawable.createFromStream(new URL(urladdr).openStream(),"image.jpg");
        }catch (IOException e){
            e.printStackTrace();
        }
        return  drawable;
    }

    private void initId() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this,"为了部分功能的正常使用，请打开「存储」权限",Toast.LENGTH_LONG).show();
            //Snackbar.make(my_recyclerView,"为了部分功能的正常使用，请打开「存储」权限",Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
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
