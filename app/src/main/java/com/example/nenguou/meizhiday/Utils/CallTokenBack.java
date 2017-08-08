package com.example.nenguou.meizhiday.Utils;

import com.example.nenguou.meizhiday.Bean.GitUserBean;
import com.example.nenguou.meizhiday.Bean.TokenBean;

/**
 * Created by b3 on 2017/8/6.
 */

public interface CallTokenBack {
    public void callBackToken(String token);
    public void callUserBeanBack(GitUserBean gitUserBean);

}
