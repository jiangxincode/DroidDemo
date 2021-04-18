package edu.jiangxin.droiddemo.opengl.assimp;

import android.app.Activity;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import edu.jiangxin.droiddemo.R;

public class AssimpActivity extends Activity{
    private GLSurfaceView mGLView = null;
    private native void CreateObjectNative(AssetManager assetManager, String pathToInternalDir);
    private native void DeleteObjectNative();
    GestureClass mGestureObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssetManager assetManager = getAssets();
        String pathToInternalDir = getFilesDir().getAbsolutePath();

        // call the native constructors to create an object
        CreateObjectNative(assetManager, pathToInternalDir);

        // layout has only two components, a GLSurfaceView and a TextView
        setContentView(R.layout.assimp_layout);
        mGLView = (MyGLSurfaceView) findViewById (R.id.gl_surface_view);

        // mGestureObject will handle touch gestures on the screen
        mGestureObject = new GestureClass(this);
        mGLView.setOnTouchListener(mGestureObject.TwoFingerGestureListener);
    }

    @Override
    protected void onResume() {

        super.onResume();

        // Android suggests that we call onResume on GLSurfaceView
        if (mGLView != null) {
            mGLView.onResume();
        }

    }

    @Override
    protected void onPause() {

        super.onPause();

        // Android suggests that we call onPause on GLSurfaceView
        if(mGLView != null) {
            mGLView.onPause();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        // We are exiting the activity, let's delete the native objects
        DeleteObjectNative();

    }


    /**
     * load libModelAssimpNative.so since it has all the native functions
     */
    static {
        System.loadLibrary("DroidDemoJni");
    }
}
