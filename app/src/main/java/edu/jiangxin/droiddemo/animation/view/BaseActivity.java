package edu.jiangxin.droiddemo.animation.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.jiangxin.droiddemo.R;


public abstract class BaseActivity extends Activity implements View.OnClickListener {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected ImageView mImage;
    private Button mBtn1, mBtn2;
    public Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mContext = getApplicationContext();
        mImage = findViewById(R.id.iv_test);
        mBtn1 = findViewById(R.id.button1);
        mBtn2 = findViewById(R.id.button2);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                starXmlAnimation();
                break;
            case R.id.button2:
                starCodeAnimation();
                break;

            default:
                break;

        }
    }

    protected abstract void starXmlAnimation();
    protected abstract void starCodeAnimation();
}
