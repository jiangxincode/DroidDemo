package edu.jiangxin.droiddemo.graphics.opengles.ball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * OpenGL ES 3. 绘制球体 实战: https://blog.csdn.net/u010281924/article/details/105327231
 */
public class BallActivity extends Activity {

	private MySurfaceView mGLSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.mGLSurfaceView = new MySurfaceView(this);

		setContentView(this.mGLSurfaceView);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.mGLSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mGLSurfaceView.onResume();
	}

}
