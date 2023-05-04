package edu.jiangxin.droiddemo.easychat.utils;


import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    /**
     * 可以在子线程中弹出toast
     *
     * @param context
     * @param text
     */
    public static void showToastSafe(final Context context, final String text) {
        ThreadUtils.runInUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
