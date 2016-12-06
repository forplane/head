package utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yidont.head.R;


/**
 * 这里提供一个toast的工具，可以解决由于沉浸式状态栏所造成的toast问题偏移问题
 * Created by SunySan on 2016/11/24.
 */
public class ToastUtils {
    private static Toast toast = null;

    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (toast != null) {
                toast.cancel();
            }
        }
    };

    public static void show(Context context, String message) {
        showToast(context,message);
    }

    private static void showToast(Context context, String message) {
        View view;
        mHandler.removeCallbacks(runnable);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.toast_style_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setAlpha(0.5f);
        textView.setText(message);

        toast = new Toast(context);
        toast.setView(view);
        toast.show();
        mHandler.postDelayed(runnable,1000);

    }

}
