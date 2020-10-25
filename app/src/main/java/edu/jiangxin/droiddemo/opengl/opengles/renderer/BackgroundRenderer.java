package edu.jiangxin.droiddemo.opengl.opengles.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BackgroundRenderer implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //Set the background frame color
        GLES20.glClearColor(0f, 0f, 1f, 1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //Redraw background color
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
