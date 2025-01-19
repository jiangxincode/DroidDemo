// The MIT License (MIT)
//
// Copyright (c) 2013 Dan Ginsburg, Budirijanto Purnomo
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

//
// Book:      OpenGL(R) ES 3.0 Programming Guide, 2nd Edition
// Authors:   Dan Ginsburg, Budirijanto Purnomo, Dave Shreiner, Aaftab Munshi
// ISBN-10:   0-321-93388-5
// ISBN-13:   978-0-321-93388-1
// Publisher: Addison-Wesley Professional
// URLs:      http://www.opengles-book.com
//            http://my.safaribooksonline.com/book/animation-and-3d/9780133440133
//

// ESTransform
//
//    Utility class for handling transformations
//

package edu.jiangxin.droiddemo.opengl.book.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class ESTransform {
    private float[] mMatrix = new float[16];
    private final FloatBuffer mMatrixFloatBuffer;

    public ESTransform() {
        mMatrixFloatBuffer = ByteBuffer.allocateDirect(16 * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public void scale(float sx, float sy, float sz) {
        mMatrix[0] *= sx;
        mMatrix[1] *= sx;
        mMatrix[2] *= sx;
        mMatrix[3] *= sx;

        mMatrix[4] *= sy;
        mMatrix[4 + 1] *= sy;
        mMatrix[4 + 2] *= sy;
        mMatrix[4 + 3] *= sy;

        mMatrix[2 * 4] *= sz;
        mMatrix[2 * 4 + 1] *= sz;
        mMatrix[2 * 4 + 2] *= sz;
        mMatrix[2 * 4 + 3] *= sz;
    }

    public void translate(float tx, float ty, float tz) {
        mMatrix[3 * 4] += (mMatrix[0] * tx + mMatrix[4]
                * ty + mMatrix[2 * 4] * tz);
        mMatrix[3 * 4 + 1] += (mMatrix[1] * tx + mMatrix[4 + 1]
                * ty + mMatrix[2 * 4 + 1] * tz);
        mMatrix[3 * 4 + 2] += (mMatrix[2] * tx + mMatrix[4 + 2]
                * ty + mMatrix[2 * 4 + 2] * tz);
        mMatrix[3 * 4 + 3] += (mMatrix[3] * tx + mMatrix[4 + 3]
                * ty + mMatrix[2 * 4 + 3] * tz);
    }

    public void rotate(float angle, float x, float y, float z) {
        float sinAngle, cosAngle;
        float mag = (float) Math.sqrt(x * x + y * y + z * z);

        sinAngle = (float) Math.sin(angle * Math.PI / 180.0);
        cosAngle = (float) Math.cos(angle * Math.PI / 180.0);

        if (mag > 0.0f) {
            float xx, yy, zz, xy, yz, zx, xs, ys, zs;
            float oneMinusCos;
            float[] rotMat = new float[16];

            x /= mag;
            y /= mag;
            z /= mag;

            xx = x * x;
            yy = y * y;
            zz = z * z;
            xy = x * y;
            yz = y * z;
            zx = z * x;
            xs = x * sinAngle;
            ys = y * sinAngle;
            zs = z * sinAngle;
            oneMinusCos = 1.0f - cosAngle;

            rotMat[0] = (oneMinusCos * xx) + cosAngle;
            rotMat[1] = (oneMinusCos * xy) - zs;
            rotMat[2] = (oneMinusCos * zx) + ys;
            rotMat[3] = 0.0F;

            rotMat[4] = (oneMinusCos * xy) + zs;
            rotMat[4 + 1] = (oneMinusCos * yy) + cosAngle;
            rotMat[4 + 2] = (oneMinusCos * yz) - xs;
            rotMat[4 + 3] = 0.0F;

            rotMat[2 * 4] = (oneMinusCos * zx) - ys;
            rotMat[2 * 4 + 1] = (oneMinusCos * yz) + xs;
            rotMat[2 * 4 + 2] = (oneMinusCos * zz) + cosAngle;
            rotMat[2 * 4 + 3] = 0.0F;

            rotMat[3 * 4] = 0.0F;
            rotMat[3 * 4 + 1] = 0.0F;
            rotMat[3 * 4 + 2] = 0.0F;
            rotMat[3 * 4 + 3] = 1.0F;

            matrixMultiply(rotMat, mMatrix);
        }
    }

    public void frustum(float left, float right, float bottom, float top,
                        float nearZ, float farZ) {
        float deltaX = right - left;
        float deltaY = top - bottom;
        float deltaZ = farZ - nearZ;
        float[] frust = new float[16];

        if ((nearZ <= 0.0f) || (farZ <= 0.0f) || (deltaX <= 0.0f)
                || (deltaY <= 0.0f) || (deltaZ <= 0.0f)) {
            return;
        }

        frust[0] = 2.0f * nearZ / deltaX;
        frust[1] = frust[2] = frust[3] = 0.0f;

        frust[4 + 1] = 2.0f * nearZ / deltaY;
        frust[4] = frust[4 + 2] = frust[4 + 3] = 0.0f;

        frust[2 * 4] = (right + left) / deltaX;
        frust[2 * 4 + 1] = (top + bottom) / deltaY;
        frust[2 * 4 + 2] = -(nearZ + farZ) / deltaZ;
        frust[2 * 4 + 3] = -1.0f;

        frust[3 * 4 + 2] = -2.0f * nearZ * farZ / deltaZ;
        frust[3 * 4] = frust[3 * 4 + 1] = frust[3 * 4 + 3] = 0.0f;

        matrixMultiply(frust, mMatrix);
    }

    public void perspective(float fovy, float aspect, float nearZ, float farZ) {
        float frustumW, frustumH;

        frustumH = (float) Math.tan(fovy / 360.0 * Math.PI) * nearZ;
        frustumW = frustumH * aspect;

        frustum(-frustumW, frustumW, -frustumH, frustumH, nearZ, farZ);
    }

    public void ortho(float left, float right, float bottom, float top,
                      float nearZ, float farZ) {
        float deltaX = right - left;
        float deltaY = top - bottom;
        float deltaZ = farZ - nearZ;
        float[] orthoMat = makeIdentityMatrix();

        if ((deltaX == 0.0f) || (deltaY == 0.0f) || (deltaZ == 0.0f)) {
            return;
        }

        orthoMat[0] = 2.0f / deltaX;
        orthoMat[3 * 4] = -(right + left) / deltaX;
        orthoMat[4 + 1] = 2.0f / deltaY;
        orthoMat[3 * 4 + 1] = -(top + bottom) / deltaY;
        orthoMat[2 * 4 + 2] = -2.0f / deltaZ;
        orthoMat[3 * 4 + 2] = -(nearZ + farZ) / deltaZ;

        matrixMultiply(orthoMat, mMatrix);
    }

    public void matrixMultiply(float[] srcA, float[] srcB) {
        float[] tmp = new float[16];
        int i;

        for (i = 0; i < 4; i++) {
            tmp[i * 4] = (srcA[i * 4] * srcB[0])
                    + (srcA[i * 4 + 1] * srcB[4])
                    + (srcA[i * 4 + 2] * srcB[2 * 4])
                    + (srcA[i * 4 + 3] * srcB[3 * 4]);

            tmp[i * 4 + 1] = (srcA[i * 4] * srcB[1])
                    + (srcA[i * 4 + 1] * srcB[4 + 1])
                    + (srcA[i * 4 + 2] * srcB[2 * 4 + 1])
                    + (srcA[i * 4 + 3] * srcB[3 * 4 + 1]);

            tmp[i * 4 + 2] = (srcA[i * 4] * srcB[2])
                    + (srcA[i * 4 + 1] * srcB[4 + 2])
                    + (srcA[i * 4 + 2] * srcB[2 * 4 + 2])
                    + (srcA[i * 4 + 3] * srcB[3 * 4 + 2]);

            tmp[i * 4 + 3] = (srcA[i * 4] * srcB[3])
                    + (srcA[i * 4 + 1] * srcB[4 + 3])
                    + (srcA[i * 4 + 2] * srcB[2 * 4 + 3])
                    + (srcA[i * 4 + 3] * srcB[3 * 4 + 3]);
        }

        mMatrix = tmp;
    }

    public void matrixLoadIdentity() {
        for (int i = 0; i < 16; i++) {
            mMatrix[i] = 0.0f;
        }

        mMatrix[0] = 1.0f;
        mMatrix[4 + 1] = 1.0f;
        mMatrix[2 * 4 + 2] = 1.0f;
        mMatrix[3 * 4 + 3] = 1.0f;
    }

    private float[] makeIdentityMatrix() {
        float[] result = new float[16];

        for (int i = 0; i < 16; i++) {
            result[i] = 0.0f;
        }

        result[0] = 1.0f;
        result[4 + 1] = 1.0f;
        result[2 * 4 + 2] = 1.0f;
        result[3 * 4 + 3] = 1.0f;

        return result;
    }

    public FloatBuffer getAsFloatBuffer() {
        mMatrixFloatBuffer.put(mMatrix).position(0);
        return mMatrixFloatBuffer;
    }

    public float[] get() {
        return mMatrix;
    }

}