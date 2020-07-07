package edu.jiangxin.droiddemo.graphics.opengles.simple.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CircleRender extends BaseRenderer implements GLSurfaceView.Renderer {
    private final int POSITION_CONMONENT_COUNT = 2;
    private final int FLOAT_PER_BYTE = 4;
    private final FloatBuffer floatBuffer;
    private final String A_POSITION = "a_Position";
    private final String U_COLOR = "u_Color";
    private final String U_MATRIX = "u_Matrix";
    private int aPositionLocation;
    private int uColorLocation;
    private int uMatrixLocation;
    private final float[] vertexs;
    float[] proMatrix = new float[16];
    private int mProgram;

    private final String vertexShaderCode =
            "attribute vec4 a_Position;" +
                    "uniform mat4 u_Matrix;" +
                    "void main() {" +
                    "gl_Position = u_Matrix*a_Position;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 u_Color;" +
                    "void main() {" +
                    "gl_FragColor = u_Color;" +
                    "}";

    public CircleRender() {
        //首先定义圆需要32个顶点,但是圆的闭合的 换句话说就是圆的首部和尾部是重合 所以圆一共需要33个点 多一个点用来和首部重合 实现闭合
        //这里设置圆心为中心(0,0)
        vertexs = new float[34 * POSITION_CONMONENT_COUNT];
        //设置圆的半径
        float radius = 0.5f;
        int offset = 0;
        vertexs[offset++] = 0;
        vertexs[offset++] = 0;
        for (int i = 0; i < 33; i++) {
            vertexs[offset++] = (float) (radius * Math.cos(i * 2 * Math.PI / 32));
            vertexs[offset++] = (float) (radius * Math.sin(i * 2 * Math.PI / 32));
        }

        //完成圆的顶点数据的
        floatBuffer = ByteBuffer.allocateDirect(vertexs.length * FLOAT_PER_BYTE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(vertexs);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);

        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);
        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        //这个让本地缓存从0开始读取数据 是非常重要的 如果不写的话会出现显示混乱
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_CONMONENT_COUNT, GLES20.GL_FLOAT, false, 0, floatBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glUseProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float aspectRatio = (float) width > (float) height ? (float) width / (float) height : (float) height / (float) width;
        if (width < height) {
            //宽大于高
            Matrix.orthoM(proMatrix, 0, -1, 1, -aspectRatio, aspectRatio, -1, 1);
        } else {
            //宽小于高
            Matrix.orthoM(proMatrix, 0, -aspectRatio, aspectRatio, -1, 1, -1, 1);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, proMatrix, 0);
        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 34);

    }
}
