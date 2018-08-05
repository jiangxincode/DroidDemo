package edu.jiangxin.easymarry.saf;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * 用来保存Bitmap，当系统一些config变化的时候，比如屏幕翻转。
 */
public class RetainedFragment extends Fragment {
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

}
