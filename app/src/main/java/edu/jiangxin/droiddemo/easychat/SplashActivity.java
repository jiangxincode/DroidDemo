package edu.jiangxin.droiddemo.easychat;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.easychat.activity.LoginActivity;
import edu.jiangxin.droiddemo.easychat.utils.ThreadUtils;

import edu.jiangxin.droiddemo.R;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 停留3s,进入登录界面

		ThreadUtils.runInThread(new Runnable() {
			@Override
			public void run() {
				// 休眠3s
				SystemClock.sleep(3000);

				// 进入主界面
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
