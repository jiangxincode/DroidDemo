package edu.jiangxin.droiddemo.easymusic.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import edu.jiangxin.droiddemo.easymusic.conf.Constants;
import edu.jiangxin.droiddemo.easymusic.utils.MediaUtils;

public class MusicService extends Service implements OnErrorListener, OnPreparedListener, OnCompletionListener,
		OnAudioFocusChangeListener {

	private MediaPlayer mPlayer;
	private Messenger mMessenger;
	private Timer mTimer;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		mPlayer = new MediaPlayer();
		//设置监听器
		mPlayer.setOnErrorListener(this);//设置资源的时候出错了
		mPlayer.setOnPreparedListener(this);//设置资源的时候出错了
		mPlayer.setOnCompletionListener(this);//设置资源的时候出错了
		//创建audioManger
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {//每次启动都会来到此方法
		String option = intent.getStringExtra("option");
		if (mMessenger == null) {
			mMessenger = (Messenger) intent.getExtras().get("messenger");
		}
		if ("播放".equals(option)) {
			String path = intent.getStringExtra("path");
			play(path);
		} else if ("暂停".equals(option)) {
			pause();
		} else if ("继续".equals(option)) {
			continuePlay();
		} else if ("停止".equals(option)) {
			stop();
		} else if ("进度".equals(option)) {
			int progress = intent.getIntExtra("progress", -1);
			seekPlay(progress);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {//销毁
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		super.onDestroy();
	}

	/**---------------封装音乐播放常见的方法 begin---------------**/

	//activity想要调用service中方法
	//1.bindService启动服务
	//2.aidl
	//3.发送广播
	//4.消息机制
	//5.利用service多次启动,会重复调用 onStartCommd()方法的特性

	/**
	 * 播放音乐
	 * @param path
	 */
	public void play(String path) {
		try {
			mPlayer.reset();//idle
			mPlayer.setDataSource(path);//设置歌曲的路径
			mPlayer.prepare();//开始准备,本地音乐使用同步准备就可以了
			mPlayer.start();//开始播放
			MediaUtils.CURSTATE = Constants.STATE_PLAY;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 暂停
	 */
	public void pause() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.pause();
			MediaUtils.CURSTATE = Constants.STATE_PAUSE;
		}
	}

	/**
	 * 继续播放
	 */
	public void continuePlay() {
		if (mPlayer != null && !mPlayer.isPlaying()) {
			mPlayer.start();
			MediaUtils.CURSTATE = Constants.STATE_PLAY;
		}
	}

	/**
	 * 停止播放
	 */
	public void stop() {
		if (mPlayer != null) {
			mPlayer.stop();
			MediaUtils.CURSTATE = Constants.STATE_STOP;
			if (mTimer != null) {
				mTimer.cancel();
				mTimer = null;
			}
		}
	}

	/**
	 * 进度播放
	 * @param progress
	 */
	public void seekPlay(int progress) {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.seekTo(progress);
		}
	}

	/**---------------封装音乐播放常见的方法 end---------------**/

	/**---------------相关的回调方法---------------**/
	@Override
	public void onCompletion(MediaPlayer mp) {
		try {
			//service发送消息.告诉activity,当前的歌曲播放完了
			Message msg = Message.obtain();
			msg.what = Constants.MSG_ONCOMPLETION;
			//发送消息
			mMessenger.send(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		if (mTimer == null) {
			mTimer = new Timer();
		}
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					//1.准备好的时候.告诉activity,当前歌曲的总时长
					int currentPosition = mPlayer.getCurrentPosition();
					int totalDuration = mPlayer.getDuration();
					Message msg = Message.obtain();
					msg.what = Constants.MSG_ONPREPARED;
					msg.arg1 = currentPosition;
					msg.arg2 = totalDuration;

					//发送消息
					mMessenger.send(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}, 0, 1000);

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		System.out.println("-----------onError-----------------");
		Toast.makeText(getApplicationContext(), "亲,资源有问题", 0).show();
		return true;
	}

	/**---------------音频焦点处理相关的方法---------------**/
	@Override
	public void onAudioFocusChange(int focusChange) {
		switch (focusChange) {
		case AudioManager.AUDIOFOCUS_GAIN://你已经得到了音频焦点。 
			System.out.println("-------------AUDIOFOCUS_GAIN---------------");
			// resume playback
			mPlayer.start();
			mPlayer.setVolume(1.0f, 1.0f);
			break;
		case AudioManager.AUDIOFOCUS_LOSS://你已经失去了音频焦点很长时间了。你必须停止所有的音频播放
			System.out.println("-------------AUDIOFOCUS_LOSS---------------");
			// Lost focus for an unbounded amount of time: stop playback and release media player
			if (mPlayer.isPlaying())
				mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://你暂时失去了音频焦点
			System.out.println("-------------AUDIOFOCUS_LOSS_TRANSIENT---------------");
			// Lost focus for a short time, but we have to stop
			// playback. We don't release the media player because playback
			// is likely to resume
			if (mPlayer.isPlaying())
				mPlayer.pause();
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
			System.out.println("-------------AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK---------------");
			// Lost focus for a short time, but it's ok to keep playing
			// at an attenuated level
			if (mPlayer.isPlaying())
				mPlayer.setVolume(0.1f, 0.1f);
			break;
		}

	}
}
