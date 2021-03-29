package edu.jiangxin.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        try {
            // Create GLES context. Even though we are specifying OpenGL ES 2, it will try to
            // create the highest possible context on a phone
            setEGLContextClientVersion(2);

            // set our custom Renderer for drawing on the created SurfaceView
            mRenderer = new MyGLRenderer();
            setRenderer(mRenderer);

            // calls onDrawFrame(...) continuously
            setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        } catch (Exception e) {

            // Trouble, something's wrong!
            Log.e("MyGLSurfaceView", "Unable to create GLES context!", e);

        }

    }

}
