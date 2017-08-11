package com.example.nenguou.meizhiday.Utils;

import android.support.design.widget.AppBarLayout;

/**
 * Created by b3 on 2017/8/10.
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State{
        EXPANDED,
        COLLAPSED,
        IDlE
    }
    private State mCurrentState = State.IDlE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if( i == 0 ){
            if(mCurrentState != State.EXPANDED){
                onStateChanged(appBarLayout,State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        }else if(Math.abs(i) >= appBarLayout.getTotalScrollRange()){
            if(mCurrentState != State.COLLAPSED){
                onStateChanged(appBarLayout,State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        }else {
            if(mCurrentState != State.IDlE){
                onStateChanged(appBarLayout,State.IDlE);
            }
            mCurrentState = State.IDlE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout,State state);
}
