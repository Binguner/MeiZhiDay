package com.example.nenguou.meizhiday.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.nenguou.meizhiday.R;
import com.squareup.picasso.Picasso;

/**
 * Created by b3 on 2017/8/8.
 */

public class ImageLoader {
    public static void with(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context).load(imageUrl).placeholder(R.mipmap.gitcat).error(R.mipmap.gitcat).into(imageView);
    }
}
