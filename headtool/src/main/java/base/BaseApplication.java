package base;

import android.app.Application;

import utils.ToolBarStatusUtils;

/**
 * Created by SunySan on 2016/11/4.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ToolBarStatusUtils.statusHeight = ToolBarStatusUtils.getStatusBarH(this);
    }
}
