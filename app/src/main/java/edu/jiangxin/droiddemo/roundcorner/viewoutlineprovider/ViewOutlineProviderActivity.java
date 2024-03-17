package edu.jiangxin.droiddemo.roundcorner.viewoutlineprovider;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.Utils;
import edu.jiangxin.droiddemo.roundcorner.utils.DisplayUtils;

public class ViewOutlineProviderActivity extends AppCompatActivity {
    private LinearLayout mLly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_outline_provider);

        initWidgets();
    }

    private void initWidgets() {
        mLly = findViewById(R.id.lly);

        mLly.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), Utils.dp2px(ViewOutlineProviderActivity.this, 10F));
            }
        });
        mLly.setClipToOutline(true);
    }
}