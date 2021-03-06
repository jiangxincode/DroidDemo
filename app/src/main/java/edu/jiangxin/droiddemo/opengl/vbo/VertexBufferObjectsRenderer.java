package edu.jiangxin.droiddemo.opengl.vbo;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.book.common.ESShader;

public class VertexBufferObjectsRenderer implements GLSurfaceView.Renderer {

    final int VERTEX_POS_SIZE = 3; // x, y and z
    final int VERTEX_COLOR_SIZE = 4; // r, g, b, and a
    final int VERTEX_POS_INDX = 0;
    final int VERTEX_COLOR_INDX = 1;
    final int VERTEX_STRIDE = (4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE));
    // 3 vertices, with (x,y,z) ,(r, g, b, a) per-vertex
    private final float[] mVerticesData =
            {
                    -0.5f, 0.5f, 0.0f,       // v0
                    1.0f, 0.0f, 0.0f, 1.0f,  // c0
                    -1.0f, -0.5f, 0.0f,        // v1
                    0.0f, 1.0f, 0.0f, 1.0f,  // c1
                    0.0f, -0.5f, 0.0f,        // v2
                    0.0f, 0.0f, 1.0f, 1.0f,  // c2
            };
    // Index buffer data
    private final short[] mIndicesData =
            {
                    0, 1, 2
            };
    // Handle to a program object
    private int mProgramObject;
    // Additional member variables
    private int mWidth;
    private int mHeight;
    private final FloatBuffer mVertices;
    private final FloatBuffer mVertices1;
    private final ShortBuffer mIndices;
    // VertexBufferObject Ids
    private final int[] mVBOIds = new int[2];

    public VertexBufferObjectsRenderer() {
        mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(mVerticesData).position(0);

        // Offset the vertex positions so both can be seen
        for (int i = 0; i < 3; i++) {
            mVerticesData[i * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE) + 0] += 1.0f;
        }

        mVertices1 = ByteBuffer.allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices1.put(mVerticesData).position(0);

        mIndices = ByteBuffer.allocateDirect(mIndicesData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndices.put(mIndicesData).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        String vShaderStr =
                "#version 300 es                            \n" +
                        "layout(location = 0) in vec4 a_position;   \n" +
                        "layout(location = 1) in vec4 a_color;      \n" +
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

        mVBOIds[0] = 0;
        mVBOIds[1] = 0;

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

        // without VBOs
        drawPrimitiveWithoutVBOs();

        // with VBOs
        drawPrimitiveWithVBOs();
    }

    private void drawPrimitiveWithoutVBOs() {
        int numIndices = 3;
        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDX);

        mVertices.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, mVertices);

        mVertices.position(VERTEX_POS_SIZE);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, mVertices);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices,
                GLES30.GL_UNSIGNED_SHORT, mIndices);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDX);
    }

    private void drawPrimitiveWithVBOs() {
        int offset = 0;
        int numVertices = 3;
        int numIndices = 3;
        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);

        // mVBOIds[0] - used to store vertex attribute data
        // mVBOIds[l] - used to store element indices
        if (mVBOIds[0] == 0 && mVBOIds[1] == 0) {
            // Only allocate on the first draw
            GLES30.glGenBuffers(2, mVBOIds, 0);

            mVertices1.position(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vtxStride * numVertices,
                    mVertices1, GLES30.GL_STATIC_DRAW);

            mIndices.position(0);
            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, 2 * numIndices,
                    mIndices, GLES30.GL_STATIC_DRAW);
        }

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDX);

        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, VERTEX_POS_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, offset);
        offset += VERTEX_POS_SIZE * 4;

        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDX, VERTEX_COLOR_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, offset);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, numIndices,
                GLES30.GL_UNSIGNED_SHORT, 0);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDX);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}
