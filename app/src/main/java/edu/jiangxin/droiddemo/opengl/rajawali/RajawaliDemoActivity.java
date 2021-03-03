package edu.jiangxin.droiddemo.opengl.rajawali;

import android.app.Activity;
import android.os.Bundle;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

import edu.jiangxin.droiddemo.R;


public class RajawaliDemoActivity extends Activity {

    RajawaliSurfaceView mRajawaliSurfaceView;

    RajawaliDemoRenderer mRajawaliRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rajawali_demo);

        mRajawaliSurfaceView = findViewById(R.id.rajawaliSurfaceView);

        mRajawaliSurfaceView.setFrameRate(60.0);
        mRajawaliSurfaceView.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        mRajawaliRenderer = new RajawaliDemoRenderer(this);
        mRajawaliSurfaceView.setSurfaceRenderer(mRajawaliRenderer);
    }
}
