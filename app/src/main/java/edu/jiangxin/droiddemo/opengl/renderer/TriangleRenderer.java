package edu.jiangxin.droiddemo.opengl.renderer;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.Utils;

public class TriangleRenderer implements GLSurfaceView.Renderer {
    private static final String VERTEX_SHADER_STR =
            "#version 300 es 			  \n"
                    + "in vec4 vPosition;           \n"
                    + "void main()                  \n"
                    + "{                            \n"
                    + "   gl_Position = vPosition;  \n"
                    + "}                            \n";

    private static final String FRAGMENT_SHADER_STR =
            "#version 300 es		 			          	\n"
                    + "precision mediump float;					  	\n"
                    + "out vec4 fragColor;	 			 		  	\n"
                    + "void main()                                  \n"
                    + "{                                            \n"
                    + "  fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );	\n"
                    + "}                                            \n";

    private static final float[] VERTICES_DATA =
            {0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f};

    private int mProgramObject;
    private FloatBuffer mVertexBuffer;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        mVertexBuffer = ByteBuffer.allocateDirect(VERTICES_DATA.length * Float.BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(VERTICES_DATA).position(0);

        mProgramObject = Utils.loadProgram(VERTEX_SHADER_STR, FRAGMENT_SHADER_STR);

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(mProgramObject);

        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertexBuffer);
        GLES30.glEnableVertexAttribArray(0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
