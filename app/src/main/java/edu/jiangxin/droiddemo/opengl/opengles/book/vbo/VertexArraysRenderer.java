package edu.jiangxin.droiddemo.opengl.opengles.book.vbo;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.opengles.book.common.ESShader;

/**
 * 顶点数组（包括两种方式：结构数组和数组结构）
 * 由于效率比较低，现已不推荐这种方式了
 */
public class VertexArraysRenderer implements GLSurfaceView.Renderer {

    final int VERTEX_POS_SIZE = 3; // x, y and z
    final int VERTEX_COLOR_SIZE = 4; // r, g, b, and a

    final int VERTEX_POS_INDX = 0;
    final int VERTEX_COLOR_INDX = 1;

    private final float[] mVerticesData =
            {
                    -0.5f, 0.5f, 0.0f,       // v0
                    1.0f, 0.0f, 0.0f, 1.0f,  // c0
                    -1.0f, -0.5f, 0.0f,        // v1
                    0.0f, 1.0f, 0.0f, 1.0f,  // c1
                    0.0f, -0.5f, 0.0f,        // v2
                    0.0f, 0.0f, 1.0f, 1.0f,  // c2
            };
    private final float[] mVerticesPosData =
            {
                    0.5f, 0.5f, 0.0f,       // v0
                    0f, -0.5f, 0.0f,        // v1
                    1.0f, -0.5f, 0.0f,        // v2
            };
    private final float[] mVerticesColorData =
            {
                    1.0f, 0.0f, 0.0f, 1.0f,  // c0
                    0.0f, 1.0f, 0.0f, 1.0f,  // c1
                    0.0f, 0.0f, 1.0f, 1.0f,  // c2
            };

    // Handle to a program object
    private int mProgramObject;
    // Additional member variables
    private int mWidth;
    private int mHeight;
    private FloatBuffer mVertices;

    private FloatBuffer mVerticesPos;
    private FloatBuffer mVerticesColor;

    public VertexArraysRenderer(Context context) {

        mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(mVerticesData).position(0);

        mVerticesPos = ByteBuffer.allocateDirect(mVerticesPosData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesPos.put(mVerticesPosData).position(0);

        mVerticesColor = ByteBuffer.allocateDirect(mVerticesColorData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesColor.put(mVerticesColorData).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        String vShaderStr =
                "#version 300 es                            \n" +
                        "layout(location = 0) in vec4 a_position;      \n" +
                        "layout(location = 1) in vec4 a_color;   \n" +
                        "out vec4 v_color;                          \n" +
                        "void main()                                \n" +
                        "{                                          \n" +
                        "    v_color = a_color;                     \n" +
                        "    gl_Position = a_position;              \n" +
                        "}";


        String fShaderStr =
                "#version 300 es            \n" +
                        "precision mediump float;   \n" +
                        "in vec4 v_color;           \n" +
                        "out vec4 o_fragColor;      \n" +
                        "void main()                \n" +
                        "{                          \n" +
                        "    o_fragColor = v_color; \n" +
                        "}";

        // Load the shaders and get a linked program object
        mProgramObject = ESShader.loadProgram(vShaderStr, fShaderStr);

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Set the viewport
        GLES30.glViewport(0, 0, mWidth, mHeight);

        // Clear the color buffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        // Use the program object
        GLES30.glUseProgram(mProgramObject);
        drawWithArrayOfStructures();
        drawWithStructureOfArrays();
    }


    private void drawWithArrayOfStructures() {

        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);

        // Load the vertex position
        mVertices.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT,
                false,
                vtxStride, mVertices);
        mVertices.position(VERTEX_POS_SIZE);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDX, 4, GLES30.GL_FLOAT,
                false,
                vtxStride, mVertices);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDX);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDX);
    }

    private void drawWithStructureOfArrays() {
        // Load the vertex position
        mVerticesPos.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT,
                false,
                0, mVerticesPos);
        mVerticesColor.position(0);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDX, 4, GLES30.GL_FLOAT,
                false,
                0, mVerticesColor);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDX);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDX);

    }


    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}
