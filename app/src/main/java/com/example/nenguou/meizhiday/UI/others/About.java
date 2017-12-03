package com.example.nenguou.meizhiday.UI.others;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nenguou.meizhiday.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Url;

public class About extends AppCompatActivity {

    @BindView(R.id.aboutPage_back)
    ImageView aboutPage_back;
    @BindView(R.id.aboutPage_share)
    ImageView aboutPage_share;
    @BindView(R.id.aboutPage_findme)
    TextView aboutPage_findme;
    @BindView(R.id.aboutPage_CollapsingToolbarLayout)
    CollapsingToolbarLayout aboutPage_CollapsingToolbarLayout;
    @BindView(R.id.aboutPage_Toolbar)
    Toolbar aboutPage_Toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_github);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        aboutPage_CollapsingToolbarLayout.setTitle("「啊」");
        aboutPage_CollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        aboutPage_CollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorTransparent));
    }

    @OnClick(R.id.aboutPage_back)
    void goBack(){
        onBackPressed();
    }
    @OnClick(R.id.aboutPage_share)
    void shareApp(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"我正在使用「啊」- Gank 非正式客户端，每天分享妹子图和技术干货，完全开源！\n欢迎下载：https://fir.im/aGank");
        startActivity(Intent.createChooser(shareIntent,"分享"));
    }
    @OnClick(R.id.aboutPage_checkUpdate)
    void checkUpdate(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse("https://fir.im/aGank");
        intent.setData(uri);
        startActivity(intent);
    }
    @OnClick(R.id.aboutPage_star)
    void giveaStar(){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse("https://github.com/Nenguou/MeiZhiDay");
        intent.setData(uri);
        startActivity(intent);
    }
    @OnClick(R.id.aboutPage_findme)
    void findMe(View view){
        openQQ(view,"819985138");
    }


    private void openQQ(View view, @Nullable String qqNum){
        if(checkAppExist(this,"com.tencent.mobileqq")){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1")));
        }else if (checkAppExist(this,"com.tencent.tim")){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1")));
        }else {
            Snackbar.make(view,"未安装QQ",Snackbar.LENGTH_SHORT).show();
        }
    }
    private boolean checkAppExist(Context context, String packageName){
        if(packageName == null || "".equals(packageName)){
            return false;
        }try{
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
