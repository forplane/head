package view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.yidont.head.R;

import java.lang.reflect.Field;

import interfaces.RightOnclick;

/**
 * Created by SunySan on 2016/10/30.</br>
 */
public class HeadToolBar extends Toolbar {
    //右边图标的位置
    public final static int POSITION_FIRST = 0;
    public final static int POSITION_SECOND = 1;
    public final static int POSITION_THIRD = 2;
    //右边图片的ID
    public final static int RIGHTIMG1 = 887;
    public final static int RIGHTIMG2 = 888;
    public final static int RIGHTIMG3 = 889;
    public final static String IMAGE = "image";
    public final static String TEXT = "text";

    public static int TOOLBAR_HEIGHT = 0;

    //全局变量,背景颜色，标题颜色等
    public static int BACKGROUND_COLOR = R.color.theme;
    public static int TITLE_COLOR  = R.color.toolbar_white;
    public static int LOGO_IMAGE  = R.mipmap.head_vertical_line;
    public static int NAVIGATION_ICON  = R.drawable.head_back;


    //rightText的文本
    private String defaultTextString;
    private String changeTextString;
    private int defaultTextInt;
    private int changeTextInt;
    private int textColor = -1;//设置字体颜色

    private boolean isChange = false;

    private PopupWindow popWind;

    private Context mContext;

    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private LinearLayout rightLay;
    private TextView rightText;
    private ImageView imageView;


    private CharSequence mTitleText;
    private CharSequence mSubtitleText;

    private int mTitleTextColor;
    private int mSubtitleTextColor;
    private int mTitleTextAppearance;
    private int mSubtitleTextAppearance;

    private
    @Nullable
    AttributeSet attrs;
    private int defStyleAttr;


    public HeadToolBar(Context context) {
        super(context);
        mContext = context;
        saveRawData(null, R.attr.toolbarStyle);
//        resolveAttribute(context, null,R.attr.toolbarStyle);
    }

    public HeadToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        saveRawData(attrs, R.attr.toolbarStyle);
//        resolveAttribute(context,attrs, R.attr.toolbarStyle);
        initView();
    }

    public HeadToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        saveRawData(attrs, R.attr.toolbarStyle);
//        resolveAttribute(context,attrs,defStyleAttr);
        initView();
    }

    /**
     * 重新设置标题的样式
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void reSetTitleStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.Toolbar, defStyleAttr, 0);
        final int titleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        if (titleTextAppearance != 0) {
            setTitleTextAppearance(context, titleTextAppearance);
        }
        if (mTitleTextColor != 0) {
            setTitleTextColor(mTitleTextColor);
        }
        a.recycle();
        post(new Runnable() {
            @Override
            public void run() {
                if (getLayoutParams() instanceof LayoutParams) {
                    ((LayoutParams) getLayoutParams()).gravity = Gravity.CENTER;
                }
            }
        });
    }


    /**
     * 重新设置标题的样式
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void reSetSubTitleStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.Toolbar, defStyleAttr, 0);
        final int titleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        if (titleTextAppearance != 0) {
            setSubtitleTextAppearance(context, titleTextAppearance);
        }
        if (mSubtitleTextColor != 0) {
            setTitleTextColor(mSubtitleTextColor);
        }
        a.recycle();
        post(new Runnable() {
            @Override
            public void run() {
                if (getLayoutParams() instanceof LayoutParams) {
                    ((LayoutParams) getLayoutParams()).gravity = Gravity.CENTER;
                }
            }
        });

    }

    private void initView() {
        //默认导航图标
        setNavigationIcon(NAVIGATION_ICON);
        setLogo(LOGO_IMAGE);
        //默认标题
        setTitle(R.string.app_name);
        //默认标题颜色
        setTitleColor(TITLE_COLOR);
        //默认背景颜色
        setBgColor(BACKGROUND_COLOR);
        //先不要调用该方法
//        reSetHeadToolBarHeight(52);

        setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "返回", Toast.LENGTH_LONG).show();
                scanForActivity(mContext).finish();
                Log.i("", "");
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                TOOLBAR_HEIGHT = getHeight();
            }
        });
    }


    /**
     * 设置右边图标或文字并实现监听
     * 【备注：text和icon 只能二选一，text为空时传“”，icon为空时传null】
     * 如果需要给按钮设置id的话，到时候再设置。
     *
     * @param text
     * @param icon
     * @param btnClick
     */
    public void setRight1(@Nullable Object text, @Nullable Integer icon, OnClickListener btnClick) {
        if (rightLay == null) {
            setLinearLayoutPamas();
            addRightView(rightLay);
        }

        initRight(text, icon, RIGHTIMG1, btnClick);

    }

    /**
     * 设置右边的第二个图标或文字
     *
     * @param text
     * @param icon
     * @param btnClick
     */
    public void setRight2(@Nullable Object text, @Nullable Integer icon, OnClickListener btnClick) {
        initRight(text, icon, RIGHTIMG2, btnClick);
    }

    /**
     * 设置右边的第三个图标或文字
     *
     * @param text
     * @param icon
     * @param btnClick
     */
    public void setRight3(@Nullable Object text, @Nullable Integer icon, OnClickListener btnClick) {
        initRight(text, icon, RIGHTIMG3, btnClick);
    }


    /**
     * 设置右边view显示，点击的时候会自动变换视图
     *
     * @param defaultText 默认的视图
     * @param changeText  点击修改的视图
     * @param type        视图类型（图片或是文字）
     * @param onClick     点击事件（new RightOnClick）
     */
    public void setRightView(Object defaultText, Object changeText, String type, final RightOnclick onClick) {
        View v = null;

        if (rightLay == null) {
            setLinearLayoutPamas();
            addRightView(rightLay);
        }

        if (type.equals(IMAGE)) {
            imageView = new ImageView(mContext);
            v = imageView;
            setViewPamas(imageView);
        } else if (type.equals(TEXT)) {
            rightText = new TextView(mContext);
            if (textColor > 0) {
                rightText.setTextColor(getResources().getColor(textColor));
            }else {
                rightText.setTextColor(getResources().getColor(R.color.toolbar_white));
            }
            v = rightText;
            setViewPamas(rightText);
        }

        if (defaultText instanceof String) {
            defaultTextString = (String) defaultText;
            rightText.setText(defaultTextString);
        } else if (defaultText instanceof Integer) {
            defaultTextInt = (int) defaultText;
            if (rightText != null) {
                rightText.setText(defaultTextInt);
            } else if (imageView != null) {
                imageView.setImageResource(defaultTextInt);
            }
        }

        if (changeText instanceof String) {
            changeTextString = (String) changeText;
        } else if (changeText instanceof Integer) {
            changeTextInt = (int) changeText;
        }

        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChange) {
                    if (!TextUtils.isEmpty(changeTextString)) {
                        rightText.setText(changeTextString);
                    } else if (changeTextInt > 0) {
                        if (imageView != null) {
                            imageView.setImageResource(changeTextInt);
                        } else if (rightText != null) {
                            rightText.setText(changeTextInt);
                        }
                    }
                    onClick.defaultOnClick();
                    isChange = true;
                } else {
                    if (!TextUtils.isEmpty(defaultTextString)) {
                        rightText.setText(defaultTextString);
                    } else if (defaultTextInt > 0) {
                        if (imageView != null) {
                            imageView.setImageResource(defaultTextInt);
                        } else if (rightText != null) {
                            rightText.setText(defaultTextInt);
                        }
                    }
                    onClick.selectOnClick();
                    isChange = false;
                }
            }
        });

        if (rightLay != null) {
            rightLay.addView(v);
        } else {
            rightLay.addView(v);
        }

    }

    /**
     * 设置textview的一些参数
     *
     * @param view
     */
    private void setViewPamas(View view) {
        ImageView imageView;
        TextView textView;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.RIGHT;
        params.setMargins(0, 0, 10, 0);
        if (view instanceof TextView) {
            textView = (TextView) view;
            textView.setLayoutParams(params);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            textView.setTextSize(16);
        } else if (view instanceof ImageView) {
            imageView = (ImageView) view;
            imageView.setLayoutParams(params);
        }

    }

    /**
     * 初始化LinearLayout
     */
    private void setLinearLayoutPamas() {
        rightLay = new LinearLayout(mContext);
        LinearLayout.LayoutParams paramLay = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        paramLay.weight = 1;
        paramLay.setMargins(0, 0, 10, 0);
        rightLay.setLayoutParams(paramLay);
        rightLay.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        rightLay.setOrientation(LinearLayout.HORIZONTAL);
    }


    private void initRight(@Nullable Object text, @Nullable Integer icon, int id, OnClickListener btnClick) {
        ImageView imageView = null;
        TextView textView = null;

        String rightViewTextString = null;//右边视图文本
        int rightViewTextInt = 0;//右边视图文本

        if (text != null) {
            if (text instanceof String) {
                rightViewTextString = (String) text;
            } else {
                if (text instanceof Integer) {
                    rightViewTextInt = (int) text;
                }
            }
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.RIGHT;
        params.setMargins(0, 0, 10, 0);

        if (icon == null && text != null) {
            textView = new TextView(mContext);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            textView.setTextSize(16);
            textView.setLayoutParams(params);
            if (textColor > 0) {
                textView.setTextColor(getResources().getColor(textColor));
            }else {
                textView.setTextColor(getResources().getColor(R.color.toolbar_white));
            }
            if (!TextUtils.isEmpty(rightViewTextString)) {
                textView.setText(rightViewTextString);
            } else {
                if (rightViewTextInt > 0)
                    textView.setText(rightViewTextInt);
            }
            textView.setId(id);
            textView.setOnClickListener(btnClick);
        } else if (icon != null && text == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setImageResource(icon);
            imageView.setId(id);
            imageView.setOnClickListener(btnClick);
        }

        if (rightLay != null) {
            if (textView != null)
                rightLay.addView(textView);
            if (imageView != null)
                rightLay.addView(imageView);
        }

    }


    //设置toolbar背景颜色
    public void setBgColor(int color) {
        super.setBackgroundColor(getResources().getColor(color));
    }

    //设置标题的字体颜色
    public void setTitleColor(int color) {
        mTitleTextColor = color;
        setTitleTextColor(getResources().getColor(color));
    }

    //设置副标题的字体颜色
    public void setSubtitleTitleColor(int color) {
        mSubtitleTextColor = color;
        setSubtitleTextColor(getResources().getColor(color));
    }


    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    /**
     * 利用反射来设置返回建的大小
     *
     * @param height
     */
    public void reSetHeadToolBarHeight(int height) {
        try {
            Field f = Toolbar.class.getDeclaredField("mNavButtonView");
            f.setAccessible(true);
            ImageButton mNavButtonView = (ImageButton) f.get(this);
            if (mNavButtonView != null) {
                LayoutParams params = (LayoutParams) mNavButtonView.getLayoutParams();
                params.gravity = Gravity.CENTER_VERTICAL;
                params.height += dip2px(height);
                params.width += dip2px(height);
                mNavButtonView.setLayoutParams(params);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置中间标题
     * 参考 http://www.jianshu.com/p/621225a55561
     *
     * @param title
     */
    public void setCenterTitle(CharSequence title) {
        reSetTitleStyle(mContext, attrs, defStyleAttr);
        setTitle("");
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                final Context context = getContext();
                mTitleTextView = new TextView(context);
                mTitleTextView.setSingleLine();
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleTextAppearance != 0) {
                    mTitleTextView.setTextAppearance(context, mTitleTextAppearance);
                }
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                }
            }
            if (mTitleTextView.getParent() != this) {
                addCenterView(mTitleTextView);
            }
        } else if (mTitleTextView != null && mTitleTextView.getParent() == this) {// 当title为空时，remove
            removeView(mTitleTextView);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
        mTitleText = title;
    }

    /**
     * 设置中间标题 (目前还不能设置在中间)
     *
     * @param subtitle
     */
    public void setCenterSubtitle(CharSequence subtitle) {
        reSetSubTitleStyle(mContext, attrs, defStyleAttr);
//        setSubtitle("");
        if (!TextUtils.isEmpty(subtitle)) {
            if (mSubtitleTextView == null) {
                final Context context = getContext();
                mSubtitleTextView = new TextView(context);
                mSubtitleTextView.setSingleLine();
                mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mSubtitleTextAppearance != 0) {
                    mSubtitleTextView.setTextAppearance(context, mSubtitleTextAppearance);
                }
                if (mSubtitleTextColor != 0) {
                    mSubtitleTextView.setTextColor(mSubtitleTextColor);
                }
            }
            if (mSubtitleTextView.getParent() != this) {
                addCenterView(mSubtitleTextView);
            }
        } else if (mSubtitleTextView != null && mSubtitleTextView.getParent() == this) {
            removeView(mSubtitleTextView);
//            mHiddenViews.remove(mSubtitleTextView);
        }
        if (mSubtitleTextView != null) {
            mSubtitleTextView.setText(subtitle);
        }
        mSubtitleText = subtitle;
    }

    /**
     * 提供一个获取右边对应控件的view
     * 设置字体颜色
     *
     * @param position
     * @param resource
     * @return
     */
    public View setRightTextColor(int position, int resource) {
        TextView view = (TextView) rightLay.getChildAt(position);
        view.setTextColor(getResources().getColor(resource));
        return view;
    }

    /**
     * 设置控件图标
     *
     * @param position 位置从0开始
     * @param resource
     * @return
     */
    public View getRightTextView(int position, int resource) {
        TextView view = (TextView) rightLay.getChildAt(position);
        view.setBackgroundResource(resource);
        return view;
    }

    /**
     * 对右边控件的简单操作
     *
     * @param position 位置从0开始
     * @return
     */
    public View getRightTextView(int position) {
        TextView view = (TextView) rightLay.getChildAt(position);
        return view;
    }

    /**
     * 改变右边图标文本字体颜色
     * @param position
     * @param color
     * @return
     */
    public View setRightChangeTextColor(int position,int color){
        TextView view = (TextView) rightLay.getChildAt(position);
        view.setTextColor(getResources().getColor(color));
        return view;
    }


    /**
     * 设置控件图标
     *
     * @param position 位置从0开始
     * @param resource
     * @return
     */
    public View getRightImageView(int position, int resource) {
        ImageView view = (ImageView) rightLay.getChildAt(position);
        view.setImageResource(resource);
        return view;
    }

    /**
     * 对右边控件的简单操作
     *
     * @param position 位置从0开始
     * @return
     */
    public View getRightImageView(int position) {
        ImageView view = (ImageView) rightLay.getChildAt(position);
        return view;
    }


    /**
     * 隐藏左边按钮
     */
    public void setNavigationIconGone() {
        setNavigationIcon(null);
    }

    /**
     * 替换右边menu三个点的图标
     *
     * @param icon
     */
    public void setOverflowIconChange(@Nullable Integer icon) {
        setOverflowIcon(mContext.getResources().getDrawable(icon));
    }


    /**
     * 设置中间标题显示位置（中间）
     *
     * @param v
     */
    private void addCenterView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        addView(v, lp);
    }

    /**
     * 设置右边图标靠右，解决靠右时与设置中间标题的冲突
     *
     * @param v
     */
    private void addRightView(View v) {
        final ViewGroup.LayoutParams vlp = v.getLayoutParams();
        final LayoutParams lp;
        if (vlp == null) {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.RIGHT;
        } else if (!checkLayoutParams(vlp)) {
            if (vlp instanceof LayoutParams) {
                lp = new LayoutParams((LayoutParams) vlp);
            } else if (vlp instanceof ActionBar.LayoutParams) {
                lp = new LayoutParams((ActionBar.LayoutParams) vlp);
            } else if (vlp instanceof MarginLayoutParams) {
                lp = new LayoutParams((MarginLayoutParams) vlp);
            } else {
                lp = new LayoutParams(vlp);
            }
            lp.gravity = Gravity.RIGHT;
        } else {
            lp = (LayoutParams) vlp;
        }
        addView(v, lp);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams lp = new LayoutParams(getContext(), attrs);
        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams lp;
        if (p instanceof LayoutParams) {
            lp = new LayoutParams((LayoutParams) p);
        } else if (p instanceof ActionBar.LayoutParams) {
            lp = new LayoutParams((ActionBar.LayoutParams) p);
        } else if (p instanceof MarginLayoutParams) {
            lp = new LayoutParams((MarginLayoutParams) p);
        } else {
            lp = new LayoutParams(p);
        }
        lp.gravity = Gravity.CENTER;
        return lp;
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        return lp;
    }

    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        mTitleTextAppearance = resId;
        if (mTitleTextView != null) {
            mTitleTextView.setTextAppearance(context, resId);
        }
    }

    @Override
    public void setSubtitleTextAppearance(Context context, @StyleRes int resId) {
        mSubtitleTextAppearance = resId;
        if (mSubtitleTextView != null) {
            mSubtitleTextView.setTextAppearance(context, resId);
        }
    }

    /**
     * 重写设置标题的颜色
     *
     * @param color
     */
    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    /**
     * 重写设置副标题的颜色
     *
     * @param color
     */
    @Override
    public void setSubtitleTextColor(@ColorInt int color) {
        mSubtitleTextColor = color;
        if (mSubtitleTextView != null) {
            mSubtitleTextView.setTextColor(color);
        }
    }


    /**
     * menu 的一个pop显示
     *
     * @param m
     */
    public void popShow(Context m, int layout) {
        /**
         * 定位PopupWindow，让它恰好显示在Action Bar的下方。 通过设置Gravity，确定PopupWindow的大致位置。
         * 首先获得状态栏的高度，再获取Action bar的高度，这两者相加设置y方向的offset样PopupWindow就显示在action
         * bar的下方了。 通过dp计算出px，就可以在不同密度屏幕统一X方向的offset.但是要注意不要让背景阴影大于所设置的offset，
         * 否则阴影的宽度为offset.
         */
        // 获取状态栏高度
        Rect frame = new Rect();
        ((Activity) m).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //        状态栏高度：frame.top
        int xOffset = frame.top + HeadToolBar.TOOLBAR_HEIGHT;//减去阴影宽度，适配UI.
        int yOffset = dip2px(3f); //设置x方向offset为3dp
//        View parentView = LayoutInflater.from(mContext).inflate(manyLay,null);
        View popView = LayoutInflater.from(mContext).inflate(
                layout, null);
        if (popWind == null) {
            popWind = new PopupWindow(popView,
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);//popView即popupWindow的布局，ture设置focusAble.
        }
        //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效。这里在XML中定义背景，所以这里设置为null;
        popWind.setBackgroundDrawable(new BitmapDrawable(getResources(),
                (Bitmap) null));
        popWind.setOutsideTouchable(false); //点击外部关闭。

        popWind.setAnimationStyle(android.R.style.Animation_Dialog);    //设置一个动画。
        //设置Gravity，让它显示在右上角。
        popWind.showAtLocation((View) getParent(), Gravity.RIGHT | Gravity.TOP,
                yOffset, xOffset);


        if (toolBarPopInterface != null) {
            toolBarPopInterface.toolBarInitView(popView);
        }
    }


    /**
     * 隐藏pop
     */
    public void dimissPop() {
        if (popWind != null && popWind.isShowing()) {
            popWind.dismiss();
        }
    }

    /**
     * 获取pop对象
     *
     * @return
     */
    public PopupWindow getPopWind() {
        return popWind;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue scale
     *                 （DisplayMetrics类中属性density）
     * @return
     */
    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 保存原有数据
     */
    private void saveRawData(@Nullable AttributeSet attrs, int defStyleAttr) {
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
    }


    /**
     * http://blog.csdn.net/u013062469/article/details/46981195
     * 参考自上面网站，在于解决直接使用context后的
     * android.view.ContextThemeWrapper cannot be cast to android.app.Activity
     * 的错误
     *
     * @param cont
     * @return
     */
    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }


    public interface ToolBarPopInterface {
        void toolBarInitView(View v);
    }

    private ToolBarPopInterface toolBarPopInterface;

    public void setToolBarPopInterface(ToolBarPopInterface toolBarPopInterface) {
        this.toolBarPopInterface = toolBarPopInterface;
    }



}
