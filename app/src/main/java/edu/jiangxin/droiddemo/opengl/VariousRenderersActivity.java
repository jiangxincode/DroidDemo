package edu.jiangxin.droiddemo.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.opengl.renderer.BackgroundRenderer;
import edu.jiangxin.droiddemo.opengl.renderer.CircleRenderer;
import edu.jiangxin.droiddemo.opengl.renderer.JniRenderer;
import edu.jiangxin.droiddemo.opengl.renderer.SquareRenderer;
import edu.jiangxin.droiddemo.opengl.renderer.TriangleRenderer;
import edu.jiangxin.droiddemo.opengl.vbo.MapBuffersRenderer;
import edu.jiangxin.droiddemo.opengl.vbo.SeparateVboPerAttributeRenderer;
import edu.jiangxin.droiddemo.opengl.vbo.VertexArrayObjectsRenderer;
import edu.jiangxin.droiddemo.opengl.vbo.VertexArraysRenderer;
import edu.jiangxin.droiddemo.opengl.vbo.VertexBufferObjectsRenderer;

public class VariousRenderersActivity extends Activity {
    public static final String ACTION_NAME_BACKGROUND = "background";

    public static final String ACTION_NAME_TRIANGLE = "triangle";

    public static final String ACTION_NAME_TRIANGLE_JNI = "triangle_jni";

    public static final String ACTION_NAME_SQUARE = "square";

    public static final String ACTION_NAME_CIRCLE = "circle";

    public static final String ACTION_NAME_VERTEX_ARRAYS = "vertexArrays";

    public static final String ACTION_NAME_VERTEX_BUFFER_OBJECTS = "vertexBufferObjects";

    public static final String ACTION_NAME_SEPARATE_VBO_PER_ATTRIBUTE = "separateVboPerAttribute";

    public static final String ACTION_NAME_VERTEX_ARRAY_OBJECTS = "vertexArrayObjects";

    public static final String ACTION_NAME_MAP_BUFFERS = "mapBuffers";

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_layout);
        initView(getIntent().getAction());
    }

    private void initView(String action) {
        glSurfaceView = findViewById(R.id.glSurfaceView);
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
            case ACTION_NAME_BACKGROUND:
                glSurfaceView.setRenderer(new BackgroundRenderer());
                break;
            case ACTION_NAME_TRIANGLE:
                glSurfaceView.setRenderer(new TriangleRenderer());
                break;
            case ACTION_NAME_TRIANGLE_JNI:
                glSurfaceView.setRenderer(new JniRenderer(this));
                break;
            case ACTION_NAME_SQUARE:
                glSurfaceView.setRenderer(new SquareRenderer());
                break;
            case ACTION_NAME_CIRCLE:
                glSurfaceView.setRenderer(new CircleRenderer());
                break;
            case ACTION_NAME_VERTEX_ARRAYS:
                glSurfaceView.setRenderer(new VertexArraysRenderer());
                break;
            case ACTION_NAME_VERTEX_BUFFER_OBJECTS:
                glSurfaceView.setRenderer(new VertexBufferObjectsRenderer());
                break;
            case ACTION_NAME_SEPARATE_VBO_PER_ATTRIBUTE:
                glSurfaceView.setRenderer(new SeparateVboPerAttributeRenderer());
                break;
            case ACTION_NAME_VERTEX_ARRAY_OBJECTS:
                glSurfaceView.setRenderer(new VertexArrayObjectsRenderer());
                break;
            case ACTION_NAME_MAP_BUFFERS:
                glSurfaceView.setRenderer(new MapBuffersRenderer());
                break;
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
