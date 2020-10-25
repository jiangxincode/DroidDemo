package edu.jiangxin.droiddemo.graphics.opengles;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.graphics.opengles.renderer.BackgroundRenderer;
import edu.jiangxin.droiddemo.graphics.opengles.renderer.CircleRenderer;
import edu.jiangxin.droiddemo.graphics.opengles.renderer.SquareRenderer;
import edu.jiangxin.droiddemo.graphics.opengles.renderer.TriangleRenderer;

public class VariousRenderersActivity extends Activity {
    public static final String ACTION_NAME_BACKGROUND = "background";

    public static final String ACTION_NAME_TRIANGLE = "triangle";

    public static final String ACTION_NAME_SQUARE = "square";

    public static final String ACTION_NAME_CIRCLE = "circle";

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_layout);
        initView(getIntent().getAction());
    }

    private void initView(String action) {
        glSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
        //GLContext设置OpenGLES2.0
        glSurfaceView.setEGLContextClientVersion(2);
        // 在setRenderer之前，可以调用以下方法进行EGL设置
//        glSurfaceView.setEGLConfigChooser(true);//颜色，深度，模板等等设置
//        glSurfaceView.setEGLWindowSurfaceFactory(new GLSurfaceView.EGLWindowSurfaceFactory() {   //窗口设置
//            @Override
//            public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eglDisplay, EGLConfig eglConfig, Object o) {
//                return null;
//            }
//
//            @Override
//            public void destroySurface(EGL10 egl10, EGLDisplay eglDisplay, EGLSurface eglSurface) {
//
//            }
//        });
        switch (action) {
            case ACTION_NAME_TRIANGLE:
                glSurfaceView.setRenderer(new TriangleRenderer());
                break;
            case ACTION_NAME_SQUARE:
                glSurfaceView.setRenderer(new SquareRenderer());
                break;
            case ACTION_NAME_CIRCLE:
                glSurfaceView.setRenderer(new CircleRenderer());
                break;
            case ACTION_NAME_BACKGROUND:
            default:
                glSurfaceView.setRenderer(new BackgroundRenderer());
                break;
        }
        /*渲染方式，RENDERMODE_WHEN_DIRTY表示被动渲染，只有在调用requestRender或者onResume等方法时才会进行渲染。RENDERMODE_CONTINUOUSLY表示持续渲染*/
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
