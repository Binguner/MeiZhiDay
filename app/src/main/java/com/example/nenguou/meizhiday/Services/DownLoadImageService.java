package com.example.nenguou.meizhiday.Services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nenguou on 2017/6/14.
 */

public class DownLoadImageService implements Runnable {

    private String url;
    private Context context;
    private ImageDownLoadCallBack imageDownLoadCallBack;
    private File currentFile;

    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack imageDownLoadCallBack) {
        this.context = context;
        this.url = url;
        this.imageDownLoadCallBack = imageDownLoadCallBack;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                saveImageToGallery(context, bitmap);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null && currentFile.exists()) {
                imageDownLoadCallBack.onDownLoadSuccess(bitmap);
            } else {
                imageDownLoadCallBack.onDownLoadFailed();
            }
        }
    }

    private void saveImageToGallery(Context context, Bitmap bitmap) {
        //首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        String FileName = "MeiZhiPics";

        File appDir = new File(file,FileName);
        if(!appDir.exists()){
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis()+".jpg";
        currentFile = new File(appDir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(currentFile.getPath()))));

    }


    public interface ImageDownLoadCallBack {
        void onDownLoadSuccess(File file);

        void onDownLoadSuccess(Bitmap bitmap);

        void onDownLoadFailed();
    }
}
