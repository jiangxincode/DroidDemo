package edu.jiangxin.droiddemo.opengl.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.Utils;

public class CircleRenderer implements GLSurfaceView.Renderer {
    private static final String vertexShaderCode =
            "attribute vec4 a_Position;" +
                    "uniform mat4 u_Matrix;" +
                    "void main() {" +
                    "gl_Position = u_Matrix*a_Position;" +
                    "}";
    private static final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 u_Color;" +
                    "void main() {" +
                    "gl_FragColor = u_Color;" +
                    "}";

    private static final int TRIANGLE_COUNT = 256;

    private static final int POSITION_DIMENSION_COUNT = 2;

    private final FloatBuffer mVertexBuffer;

    private final float[] matrix = new float[16];

    private int mProgram;

    public CircleRenderer() {
        int offset = 0;
        // 画圆可以近似于画N个以圆心为共顶点的等腰三角形。
        // N个等腰三角形理论上只需要N个顶点（不含圆心），但是为了方便首部和尾部闭合，我们使用N+1个定点（不含圆心），加上圆心就是N+2个定点
        // 每个定点包含x，y两个维度的坐标
        float[] vertexData = new float[(TRIANGLE_COUNT + 2) * POSITION_DIMENSION_COUNT];

        //设置圆的半径
        float radius = 0.5f;

        //设置圆心为中心(0,0)
        vertexData[offset++] = 0;
        vertexData[offset++] = 0;

        //给除了圆心外的每个定点复制
        for (int i = 0; i < (TRIANGLE_COUNT + 1); i++) {
            vertexData[offset++] = (float) (radius * Math.cos(i * 2 * Math.PI / TRIANGLE_COUNT));
            vertexData[offset++] = (float) (radius * Math.sin(i * 2 * Math.PI / TRIANGLE_COUNT));
        }

        mVertexBuffer = ByteBuffer.allocateDirect(vertexData.length * Float.BYTES)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexBuffer.put(vertexData).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mProgram = Utils.loadProgram(vertexShaderCode, fragmentShaderCode);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float aspectRatio = (float) width > (float) height ? (float) width / (float) height : (float) height / (float) width;
        if (width < height) {
            Matrix.orthoM(matrix, 0, -1, 1, -aspectRatio, aspectRatio, -1, 1);
        } else {
            Matrix.orthoM(matrix, 0, -aspectRatio, aspectRatio, -1, 1, -1, 1);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(mProgram);

        int positionLocation = GLES20.glGetAttribLocation(mProgram, "a_Position");
        GLES20.glVertexAttribPointer(positionLocation, POSITION_DIMENSION_COUNT, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(positionLocation);

        int colorLocation = GLES20.glGetUniformLocation(mProgram, "u_Color");
        GLES20.glUniform4f(colorLocation, 1.0f, 0f, 0f, 1.0f);

        int matrixLocation = GLES20.glGetUniformLocation(mProgram, "u_Matrix");
        GLES20.glUniformMatrix4fv(matrixLocation, 1, false, matrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, TRIANGLE_COUNT + 2);
    }
}
