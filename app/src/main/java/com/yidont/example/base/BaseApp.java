package com.yidont.example.base;

import android.app.Application;


import com.yidont.example.R;

import view.HeadToolBar;

/**
 * Created by Administrator on 2016/12/13.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HeadToolBar.setConfig(R.color.theme, R.color.white, R.mipmap.al_head_line, com.yidont.head.R.drawable.head_back, 0, 0, 50);
    }

}
