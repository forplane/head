package com.yidont.example;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import view.HeadToolBar;

public class MainActivity extends Activity {

    protected HeadToolBar headToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headToolBar = (HeadToolBar) findViewById(R.id.head_toolbar);
        headToolBar.setBackgroundColor(Color.RED);
    }
}
