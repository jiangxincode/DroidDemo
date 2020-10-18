package edu.jiangxin.droiddemo.graphics.opengles.ball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private SceneRenderer mRenderer;
	private Ball ball;

	private float mPreviousY;
	private float mPreviousX;

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		this.mRenderer = new SceneRenderer();
		this.setRenderer(this.mRenderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		float y = e.getY();
		float x = e.getX();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dy = y - mPreviousY;
			float dx = x - mPreviousX;
			ball.yAngle += dx * TOUCH_SCALE_FACTOR;
			ball.xAngle += dy * TOUCH_SCALE_FACTOR;
		}
		mPreviousY = y;
		mPreviousX = x;
		return true;
	}

	private class SceneRenderer implements Renderer {

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
					| GLES20.GL_DEPTH_BUFFER_BIT);
			MatrixState.pushMatrix();
			MatrixState.pushMatrix();
			ball.drawSelf();
			MatrixState.popMatrix();
			MatrixState.popMatrix();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			GLES20.glViewport(0, 0, width, height);
			Constant.ratio = (float) width / height;

			MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1,
					1, 20, 100);
			MatrixState.setCamera(0, 0, 30, 0, 0, 0, 0, 1, 0);

			MatrixState.setInitStack();
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			GLES20.glClearColor(0, 0, 0, 1.0f);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			GLES20.glEnable(GLES20.GL_CULL_FACE);
			ball = new Ball(MySurfaceView.this);
		}

	}
}
