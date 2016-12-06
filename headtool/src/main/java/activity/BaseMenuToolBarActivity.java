package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 需要使用menu来设置图标的话，就需要继承自该Activity
 * 因为要想menu所设置的图片会显示，就必须调用方法：
 * setSupportActionBar(ToolBar);
 * Created by SunySan on 2016/10/27.
 */
public class BaseMenuToolBarActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
