package edu.jiangxin.droiddemo.roundcorner.draweeview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import edu.jiangxin.droiddemo.R;

public class DraweeViewActivity extends AppCompatActivity {

    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Fresco
        Fresco.initialize(this);

        setContentView(R.layout.activity_drawee_view);

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simpleDraweeView);
    }

    @Override
    protected void onDestroy() {
        Fresco.shutDown();
        super.onDestroy();
    }
}