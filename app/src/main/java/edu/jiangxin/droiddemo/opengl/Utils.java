package edu.jiangxin.droiddemo.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES30;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.jiangxin.droiddemo.ApplicationExt;

public class Utils {
    public static final int OPENGL_ES_VERSION = 3;

    private static final String TAG = "OPENGL_Utils";

    public static boolean isOpenGlEsVersionOk() {
        ActivityManager activityManager =
                (ActivityManager) ApplicationExt.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= OPENGL_ES_VERSION);
    }

    private static String readShader(Context context, String fileName) {
        String shaderSource = null;

        if (fileName == null) {
            return shaderSource;
        }

        InputStream is = null;
        byte[] buffer;

        try {
            is = context.getAssets().open(fileName);
            buffer = new byte[is.available()];
            is.read(buffer);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            os.write(buffer);
            os.close();
            is.close();
            shaderSource = os.toString();
        } catch (IOException ioe) {
            is = null;
        }

        if (is == null) {
            return shaderSource;
        }

        return shaderSource;
    }

    public static int loadShader(int type, String shaderSrc) {
        int shader;
        int[] compiled = new int[1];

        shader = GLES30.glCreateShader(type);

        if (shader == 0) {
            return 0;
        }

        GLES30.glShaderSource(shader, shaderSrc);

        GLES30.glCompileShader(shader);

        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);

        if (compiled[0] == 0) {
            Log.e(TAG, GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    public static int loadProgram(String vertShaderSrc, String fragShaderSrc) {
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertShaderSrc);

        if (vertexShader == 0) {
            return 0;
        }

        fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragShaderSrc);

        if (fragmentShader == 0) {
            GLES30.glDeleteShader(vertexShader);
            return 0;
        }

        programObject = GLES30.glCreateProgram();

        if (programObject == 0) {
            return 0;
        }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);

        GLES30.glLinkProgram(programObject);

        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return 0;
        }

        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        return programObject;
    }

    public static int loadProgramFromAsset(Context context, String vertexShaderFileName, String fragShaderFileName) {
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];

        String vertShaderSrc = readShader(context, vertexShaderFileName);
        if (vertShaderSrc == null) {
            return 0;
        }

        String fragShaderSrc = readShader(context, fragShaderFileName);
        if (fragShaderSrc == null) {
            return 0;
        }

        vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertShaderSrc);

        if (vertexShader == 0) {
            return 0;
        }

        fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragShaderSrc);

        if (fragmentShader == 0) {
            GLES30.glDeleteShader(vertexShader);
            return 0;
        }

        programObject = GLES30.glCreateProgram();

        if (programObject == 0) {
            return 0;
        }

        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);

        GLES30.glLinkProgram(programObject);

        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return 0;
        }

        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        return programObject;
    }
}
