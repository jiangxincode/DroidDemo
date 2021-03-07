package edu.jiangxin.droiddemo.opengl.vbo;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.Utils;

/**
 * 顶点数组（包括两种方式：结构数组和数组结构）
 * 由于效率比较低，现已不推荐这种方式了
 */
public class VertexArraysRenderer implements GLSurfaceView.Renderer {
    private static final int VERTEX_POS_SIZE = 3; // x, y and z
    private static final int VERTEX_COLOR_SIZE = 4; // r, g, b, and a

    private static final int VERTEX_POS_INDEX = 0;
    private static final int VERTEX_COLOR_INDEX = 1;

    private static final float[] VERTICES_DATA =
            {
                    -0.5f, 0.5f, 0.0f,       // v0
                    1.0f, 0.0f, 0.0f, 1.0f,  // c0
                    -1.0f, -0.5f, 0.0f,        // v1
                    0.0f, 1.0f, 0.0f, 1.0f,  // c1
                    0.0f, -0.5f, 0.0f,        // v2
                    0.0f, 0.0f, 1.0f, 1.0f,  // c2
            };
    private final float[] VERTICES_POS_DATA =
            {
                    0.5f, 0.5f, 0.0f,       // v0
                    0f, -0.5f, 0.0f,        // v1
                    1.0f, -0.5f, 0.0f,        // v2
            };
    private final float[] VERTICES_COLOR_DATA =
            {
                    1.0f, 0.0f, 0.0f, 1.0f,  // c0
                    0.0f, 1.0f, 0.0f, 1.0f,  // c1
                    0.0f, 0.0f, 1.0f, 1.0f,  // c2
            };

    private int mProgramObject;

    private final FloatBuffer mVertices;

    private final FloatBuffer mVerticesPos;
    private final FloatBuffer mVerticesColor;

    public VertexArraysRenderer() {

        mVertices = ByteBuffer.allocateDirect(VERTICES_DATA.length * Float.BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(VERTICES_DATA).position(0);

        mVerticesPos = ByteBuffer.allocateDirect(VERTICES_POS_DATA.length * Float.BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesPos.put(VERTICES_POS_DATA).position(0);

        mVerticesColor = ByteBuffer.allocateDirect(VERTICES_COLOR_DATA.length * Float.BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesColor.put(VERTICES_COLOR_DATA).position(0);
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

        mProgramObject = Utils.loadProgram(vShaderStr, fShaderStr);

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glUseProgram(mProgramObject);
        drawWithArrayOfStructures();
        drawWithStructureOfArrays();
    }


    private void drawWithArrayOfStructures() {
        int vertexStride = Float.BYTES * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);

        mVertices.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDEX, 3, GLES30.GL_FLOAT, false, vertexStride, mVertices);
        mVertices.position(VERTEX_POS_SIZE);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDEX, 4, GLES30.GL_FLOAT, false, vertexStride, mVertices);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDEX);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDEX);
    }

    private void drawWithStructureOfArrays() {
        mVerticesPos.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDEX, 3, GLES30.GL_FLOAT, false, 0, mVerticesPos);
        mVerticesColor.position(0);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDEX, 4, GLES30.GL_FLOAT, false, 0, mVerticesColor);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDEX);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDEX);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }
}
