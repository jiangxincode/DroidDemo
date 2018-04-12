package edu.jiangxin.easymarry.activity.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ViewFlipper;

import edu.jiangxin.easymarry.R;

public class ViewFlipperActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);
        ViewFlipper filpper = (ViewFlipper) findViewById(R.id.vf_id);
        filpper.startFlipping();
    }
}
