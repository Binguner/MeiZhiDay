package com.example.nenguou.meizhiday.Utils;

import com.example.nenguou.meizhiday.Entity.WatchEventBean;

import java.util.List;

/**
 * Created by b3 on 2017/8/11.
 */

public interface CallWatchEventsBack {
    void callBackWatchEvents(List<WatchEventBean> watchEventBeans);
}
