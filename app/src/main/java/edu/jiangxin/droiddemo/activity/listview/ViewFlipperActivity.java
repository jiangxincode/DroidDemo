package edu.jiangxin.droiddemo.activity.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import edu.jiangxin.droiddemo.R;

public class ViewFlipperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        ViewFlipper viewFlipper = findViewById(R.id.vf_id);
        viewFlipper.startFlipping();
    }
}
