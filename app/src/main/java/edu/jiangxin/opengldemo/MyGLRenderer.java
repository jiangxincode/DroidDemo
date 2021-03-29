package edu.jiangxin.opengldemo;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class MyGLRenderer implements GLSurfaceView.Renderer {

    private native void DrawFrameNative();
    private native void SurfaceCreatedNative();
    private native void SurfaceChangedNative(int width, int height);


    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // called when the surface is created or recreated
        // create (or recreate) native objects that are required for rendering
        Log.d("MyGLRenderer", "onSurfaceCreated");
        SurfaceCreatedNative();

    }

    public void onDrawFrame(GL10 unused) {

        // called to draw the current frame
        // call the rendering functions in native
        DrawFrameNative();

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {

        // called after surface is created and GLES surface changes size
        // this can typically happen if device orientation changes
        // we need to set GLES viewport to handle the change in size
        Log.d("MyGLRenderer", "onSurfaceChanged");
        SurfaceChangedNative(width, height);

    }

}