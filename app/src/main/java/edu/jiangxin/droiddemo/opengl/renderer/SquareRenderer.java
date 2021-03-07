package edu.jiangxin.droiddemo.opengl.renderer;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.Utils;

public class SquareRenderer implements GLSurfaceView.Renderer {
    private static final String VERTEX_SHADER_STR =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;" +
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "}";

    private static final String FRAGMENT_SHADER_STR =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private static final float[] VERTICES_DATA = {
            -0.5f, 0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f, 0.5f, 0.0f  // top right
    };

    private static final short[] INDEX_DATA = {
            0, 1, 2, 0, 2, 3
    };

    private final float[] mViewMatrix = new float[16];
    private final float[] mProjectMatrix = new float[16];
    private final float[] mMVPMatrix = new float[16];

    private FloatBuffer mVertexBuffer;
    private ShortBuffer mIndexBuffer;

    private int mProgram;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mVertexBuffer = ByteBuffer.allocateDirect(VERTICES_DATA.length * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexBuffer.put(VERTICES_DATA).position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(INDEX_DATA.length * Short.BYTES).order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndexBuffer.put(INDEX_DATA).position(0);

        mProgram = Utils.loadProgram(VERTEX_SHADER_STR, FRAGMENT_SHADER_STR);

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        //计算宽高比
        float ratio = (float) width / height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        int matrixLocation = GLES20.glGetUniformLocation(mProgram, "vMatrix");
        GLES20.glUniformMatrix4fv(matrixLocation, 1, false, mMVPMatrix, 0);

        int positionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");

        GLES20.glVertexAttribPointer(positionLocation, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(positionLocation);

        int colorLocation = GLES20.glGetUniformLocation(mProgram, "vColor");
        float[] color = {1.0f, 0f, 0f, 1.0f};
        GLES20.glUniform4fv(colorLocation, 1, color, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, INDEX_DATA.length, GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);
        GLES20.glDisableVertexAttribArray(colorLocation);
    }
}
