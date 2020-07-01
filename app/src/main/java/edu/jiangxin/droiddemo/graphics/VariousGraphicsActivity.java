package edu.jiangxin.droiddemo.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.graphics.opengles.OpenGLDemoActivity;
import edu.jiangxin.droiddemo.graphics.rajawali.RajawaliDemoActivity;

public class VariousGraphicsActivity extends Activity implements View.OnClickListener {

    private TextView mTvRajaWaliDemo, mTvOpenGLDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_graphics);

        mTvRajaWaliDemo = findViewById(R.id.tv_rajawali);
        mTvOpenGLDemo = findViewById(R.id.tv_opengl);

        mTvRajaWaliDemo.setOnClickListener(this);
        mTvOpenGLDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rajawali:
                enterTestActivity(RajawaliDemoActivity.class);
                break;
            case R.id.tv_opengl:
                enterTestActivity(OpenGLDemoActivity.class);
                break;
            default:
                break;
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}