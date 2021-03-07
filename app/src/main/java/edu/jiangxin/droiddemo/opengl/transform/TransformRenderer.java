package edu.jiangxin.droiddemo.opengl.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.jiangxin.droiddemo.opengl.Utils;

public class TransformRenderer implements GLSurfaceView.Renderer {

    private final float[] mVerticesData =
            {
                    -1f, 1f, 0.0f, // Position 0
                    0.0f, 0.0f, // TexCoord 0
                    -1f, -1f, 0.0f, // Position 1
                    0.0f, 1.0f, // TexCoord 1
                    1f, -1f, 0.0f, // Position 2
                    1.0f, 1.0f, // TexCoord 2
                    1f, 1f, 0.0f, // Position 3
                    1.0f, 0.0f // TexCoord 3
            };
    private final short[] mIndicesData =
            {
                    0, 1, 2, 0, 2, 3
            };
    // Handle to a program object
    private int mProgramObject;
    // Sampler location
    private int mSamplerLoc;
    // Texture handle
    private int mTextureId;
    // Additional member variables
    private int mWidth;
    private int mHeight;
    private final FloatBuffer mVertices;
    private final ShortBuffer mIndices;
    private Context mContext;
    private String mResPath;

    public TransformRenderer(Context context, String resPath) {
        mContext = context;
        mResPath = resPath;

        mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(mVerticesData).position(0);
        mIndices = ByteBuffer.allocateDirect(mIndicesData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndices.put(mIndicesData).position(0);
    }

    private int createSimpleTexture2D() {
        // Texture object handle
        int[] textureId = new int[1];

        Bitmap bitmap = loadTextureFromAsset(mResPath);
        ByteBuffer pixelBuffer = ByteBuffer.allocateDirect(bitmap.getWidth() * bitmap.getHeight() * 4);
        bitmap.copyPixelsToBuffer(pixelBuffer);
        pixelBuffer.position(0);

        // Use tightly packed data
        GLES30.glPixelStorei(GLES30.GL_UNPACK_ALIGNMENT, 1);

        //  Generate a texture object
        GLES30.glGenTextures(1, textureId, 0);

        // Bind the texture object
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

        // Set the filtering mode
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);

        return textureId[0];
    }

    private Bitmap loadTextureFromAsset(String fileName) {
        Bitmap bitmap = null;
        InputStream is = null;

        try {
            is = mContext.getAssets().open(fileName);
        } catch (IOException ioe) {
            is = null;
        }

        if (is == null) {
            return bitmap;
        }

        bitmap = BitmapFactory.decodeStream(is);

        return bitmap;
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        String vShaderStr =
                "#version 300 es              				\n" +
                        "layout(location = 0) in vec4 a_position;   \n" +
                        "layout(location = 1) in vec2 a_texCoord;   \n" +
                        "out vec2 v_texCoord;     	  				\n" +
                        "void main()                  				\n" +
                        "{                            				\n" +
                        "   gl_Position = a_position; 				\n" +
                        "   v_texCoord = a_texCoord;  				\n" +
                        "}                            				\n";

        String fShaderStr =
                "#version 300 es                                     \n" +
                        "precision mediump float;                            \n" +
                        "in vec2 v_texCoord;                            	 \n" +
                        "layout(location = 0) out vec4 outColor;             \n" +
                        "uniform sampler2D s_texture;                        \n" +
                        "void main()                                         \n" +
                        "{                                                   \n" +
                        "  outColor = texture( s_texture, v_texCoord );      \n" +
                        "}                                                   \n";

        // Load the shaders and get a linked program object
        mProgramObject = Utils.loadProgram(vShaderStr, fShaderStr);

        // Get the sampler location
        mSamplerLoc = GLES30.glGetUniformLocation(mProgramObject, "s_texture");

        // Load the texture
        mTextureId = createSimpleTexture2D();

        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    public void onDrawFrame(GL10 glUnused) {
        // Set the viewport
        GLES30.glViewport(0, 0, mWidth, mHeight);

        // Clear the color buffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        // Use the program object
        GLES30.glUseProgram(mProgramObject);

        // Load the vertex position
        mVertices.position(0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT,
                false,
                5 * 4, mVertices);
        // Load the texture coordinate
        mVertices.position(3);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT,
                false,
                5 * 4,
                mVertices);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        // Bind the texture
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureId);

        // Set the sampler texture unit to 0
        GLES30.glUniform1i(mSamplerLoc, 0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_SHORT, mIndices);
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
    }
}
