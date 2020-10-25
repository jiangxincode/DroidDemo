package edu.jiangxin.droiddemo.opengl.opengles.ball;

import android.opengl.Matrix;

public class MatrixState {

    static float[][] mStack = new float[10][16];
    static int stackTop = -1;
    private static final float[] mMVPMatrix = new float[16];
    private static final float[] mProjMatrix = new float[16];
    private static final float[] mVMatrix = new float[16];
    private static float[] currMatrix;

    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    public static void pushMatrix() {
        stackTop++;
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];
        }
    }

    public static void popMatrix() {
        if (stackTop <= 0)
            return;
        for (int i = 0; i < 16; i++) {
            currMatrix[i] = mStack[stackTop][i];
        }
        stackTop--;
    }

    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    public static void setCamera(float eyeX, float eyeY, float eyeZ,
                                 float centerX, float centerY, float centerZ, float upX, float upY,
                                 float upZ) {
        Matrix.setLookAtM(mVMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY,
                centerZ, upX, upY, upZ);
    }

    public static void setProjectFrustum(float left, float right, float bottom,
                                         float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setProjectOrtho(float left, float right, float bottom,
                                       float top, float near, float far) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public static float[] getFinalMatrix() {
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    public static float[] getMMatrix() {
        return currMatrix;
    }
}
