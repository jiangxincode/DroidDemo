package edu.jiangxin.droiddemo.easychat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import edu.jiangxin.droiddemo.easychat.utils.ToastUtils;

import org.jivesoftware.smack.packet.Message;

public class PushService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("--------------PushService- onCreate-------------");

		IMService.conn.addSyncStanzaListener(packet -> {
			Message message = (Message) packet;
			String body = message.getBody();
			ToastUtils.showToastSafe(getApplicationContext(), body);
		}, null);

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		System.out.println("--------------PushService- onDestroy-------------");
		super.onDestroy();
	}
}
