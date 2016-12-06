package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 头部升级，直接在头部就默认操作了状态栏，无需开发人员再考虑状态栏的问题
 * Created by SunySan on 2016/11/24.
 */
public class HeadToolBarSuper extends LinearLayout{
    public static HeadToolBar headToolBar;

    public HeadToolBarSuper(Context context) {
        super(context);
        initView(context);
    }

    public HeadToolBarSuper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HeadToolBarSuper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);

        LinearLayout statusLay = new LinearLayout(context);

        addView(statusLay);

    }

}
