package com.itheima.easychat;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.itheima.easychat.activity.LoginActivity;
import com.itheima.easychat.utils.ThreadUtils;

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
