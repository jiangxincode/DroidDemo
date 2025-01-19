package edu.jiangxin.droiddemo.opengl.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class JniRenderer implements GLSurfaceView.Renderer {

    static {
        System.loadLibrary("DroidDemoJni");
    }

    private final String TAG = "JNI_RENDERER";

    private final AssetManager mAssetMgr;

    public native void nativeOnSurfaceCreated();

    public native void nativeOnDrawFrame();

    public native void nativeOnSurfaceChanged(int width, int height);

    public native void nativeReadShaderFromFile(AssetManager assetMgr);

    public JniRenderer(Context context) {
        mAssetMgr = context.getAssets();
        if (mAssetMgr == null) {
            Log.e(TAG, "mAssetMgr is null");
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        nativeReadShaderFromFile(mAssetMgr);
        nativeOnSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        nativeOnSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        nativeOnDrawFrame();
    }
}
