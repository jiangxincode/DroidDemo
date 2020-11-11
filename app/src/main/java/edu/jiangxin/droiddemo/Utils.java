package edu.jiangxin.droiddemo;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
    public static int dp2px(Context context, float dipValue) {
        float pxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue,
                context.getResources().getDisplayMetrics());
        return (int) (pxValue + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float pxValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                context.getResources().getDisplayMetrics());
        return (int) (pxValue + 0.5f);
    }

    public static float px2dp(Context context, int px) {
        final float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static float px2sp(Context context, int px) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }
}
