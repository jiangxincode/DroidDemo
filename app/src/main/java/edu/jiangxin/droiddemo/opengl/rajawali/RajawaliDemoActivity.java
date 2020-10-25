package edu.jiangxin.droiddemo.opengl.rajawali;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

import edu.jiangxin.droiddemo.R;


public class RajawaliDemoActivity extends Activity {

    RajawaliDemoRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rajawali_demo);

        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate(60.0);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        // Add mSurface to your root view
        addContentView(surface, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));

        renderer = new RajawaliDemoRenderer(this);
        surface.setSurfaceRenderer(renderer);
    }
}
