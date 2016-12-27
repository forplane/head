package com.yidont.example.test;


import android.os.Bundle;
import android.view.Menu;

import com.yidont.example.R;

import activity.BaseMenuToolBarActivity;

/**
 * 头部测试类
 * Created by SunySan on 2016/12/13.
 */

public class HeadTestActivity extends BaseMenuToolBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_test);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);

    }
}
