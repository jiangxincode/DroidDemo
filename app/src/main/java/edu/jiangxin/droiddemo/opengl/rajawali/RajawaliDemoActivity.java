package edu.jiangxin.droiddemo.opengl.rajawali;

import android.app.Activity;
import android.os.Bundle;


import org.rajawali3d.view.ISurface;
import org.rajawali3d.view.SurfaceView;

import edu.jiangxin.droiddemo.R;


public class RajawaliDemoActivity extends Activity {

    SurfaceView mRajawaliSurfaceView;

    RajawaliDemoRenderer mRajawaliRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rajawali_demo);

        mRajawaliSurfaceView = findViewById(R.id.rajawaliSurfaceView);

        mRajawaliSurfaceView.setFrameRate(60.0);
        mRajawaliSurfaceView.setRenderMode(ISurface.RENDERMODE_WHEN_DIRTY);

        mRajawaliRenderer = new RajawaliDemoRenderer(this);
        mRajawaliSurfaceView.setSurfaceRenderer(mRajawaliRenderer);
    }
}
