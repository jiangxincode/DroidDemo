package edu.jiangxin.droiddemo.easymusic;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.easymusic.conf.Constants;
import edu.jiangxin.droiddemo.easymusic.utils.LrcUtil;
import edu.jiangxin.droiddemo.easymusic.adapter.MyAdapter;
import edu.jiangxin.droiddemo.easymusic.service.MusicService;
import edu.jiangxin.droiddemo.easymusic.utils.MediaUtils;
import edu.jiangxin.droiddemo.easymusic.views.LyricShow;
import edu.jiangxin.droiddemo.easymusic.views.ScrollableViewGroup;
import edu.jiangxin.droiddemo.easymusic.views.ScrollableViewGroup.OnCurrentViewChangedListener;

public class EasyMusicActivity extends Activity implements OnClickListener {

	private TextView mTv_curduration;
	private TextView mTv_minilrc;
	private TextView mTv_totalduration;
	private SeekBar mSk_duration;
	private ImageView mIv_bottom_model;
	private ImageView mIv_bottom_play;
	private ListView mLv_list;
	private ScrollableViewGroup mSvg_main;
	private EasyMusicActivity mInstance;
	private LrcUtil mLrcUtil;
	private final Handler handler = new Handler(Looper.getMainLooper()) {//接收结果,刷新ui

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.MSG_ONPREPARED:
				int currentPosition = msg.arg1;
				int totalDuration = msg.arg2;
				mTv_curduration.setText(MediaUtils.duration2Str(currentPosition));
				mTv_totalduration.setText(MediaUtils.duration2Str(totalDuration));
				mSk_duration.setMax(totalDuration);
				mSk_duration.setProgress(currentPosition);
				if (mLrcUtil == null) {
					mLrcUtil = new LrcUtil(mInstance);
				}
				//序列化歌词
				File f = MediaUtils.getLrcFile(MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				mLrcUtil.ReadLRC(f);
				//使用功能
				mLrcUtil.RefreshLRC(currentPosition);
				//1. 设置集合
				mTv_lyricShow.SetTimeLrc(LrcUtil.lrclist);
				//2. 更新滚动歌词
				mTv_lyricShow.SetNowPlayIndex(currentPosition);
				break;
			case Constants.MSG_ONCOMPLETION:
				//更加当前的播放model做对应的处理
				if (MediaUtils.CURMODEL == Constants.MODEL_NORMAL) {//当前是顺序播放
					if (MediaUtils.CURPOSITION < MediaUtils.songList.size() - 1) {
						changeColorWhite();
						MediaUtils.CURPOSITION++;//MediaUtils.CURPOSITION最大值就是MediaUtils.songList.size() - 1
						changeColorGreen();
						startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
					} else {
						startMediaService("停止");
					}
				} else if (MediaUtils.CURMODEL == Constants.MODEL_RANDOM) {//当前是随机播放
					Random random = new Random();
					int position = random.nextInt(MediaUtils.songList.size());
					changeColorWhite();
					MediaUtils.CURPOSITION = position;
					changeColorGreen();
					startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				} else if (MediaUtils.CURMODEL == Constants.MODEL_REPEAT) {//当前是重复播放
					if (MediaUtils.CURPOSITION < MediaUtils.songList.size() - 1) {
						changeColorWhite();
						MediaUtils.CURPOSITION++;//MediaUtils.CURPOSITION最大值就是MediaUtils.songList.size() - 1
						changeColorGreen();
					} else {
						changeColorWhite();
						MediaUtils.CURPOSITION = 0;
						changeColorGreen();
						startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
					}
				} else if (MediaUtils.CURMODEL == Constants.MODEL_SINGLE) {//当前是单曲循环
					startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				}
				break;
			default:
				break;
			}
		}
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easymusic);
		mInstance = this;//当前activity的引用
		executorService = Executors.newSingleThreadExecutor();
		mainHandler = new Handler(Looper.getMainLooper());
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (executorService != null) {
			executorService.shutdown();
		}
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mTv_curduration = findViewById(R.id.tv_curduration);
		mTv_minilrc = findViewById(R.id.tv_minilrc);
		mTv_totalduration = findViewById(R.id.tv_totalduration);
		mSk_duration = findViewById(R.id.sk_duration);
		mIv_bottom_model = findViewById(R.id.iv_bottom_model);
		mIv_bottom_play = findViewById(R.id.iv_bottom_play);
		mLv_list = findViewById(R.id.lv_list);
		mSvg_main = findViewById(R.id.svg_main);
		mTv_lyricShow = findViewById(R.id.tv_lrc);
		//默认选中第一个
		findViewById(R.id.ib_top_play).setSelected(true);
	}

	/**
	 * 数据的加载
	 */
	private void initData() {
		MediaUtils.initSongList(this);
		mAdapter = new MyAdapter(this);
		mLv_list.setAdapter(mAdapter);
	}

	/**
	 * 初始化监听
	 */
	private void initListener() {
		findViewById(R.id.ib_top_play).setOnClickListener(this);
		findViewById(R.id.ib_top_list).setOnClickListener(this);
		findViewById(R.id.ib_top_lrc).setOnClickListener(this);
		findViewById(R.id.ib_top_volumn).setOnClickListener(this);
		findViewById(R.id.ib_bottom_model).setOnClickListener(this);
		findViewById(R.id.ib_bottom_last).setOnClickListener(this);
		findViewById(R.id.ib_bottom_play).setOnClickListener(this);
		findViewById(R.id.ib_bottom_next).setOnClickListener(this);
		findViewById(R.id.ib_bottom_update).setOnClickListener(this);

		mSvg_main.setOnCurrentViewChangedListener(new OnCurrentViewChangedListener() {

			@Override
			public void onCurrentViewChanged(View view, int currentview) {
				System.out.println("-------------" + currentview + "---------------");
				setTopSelected(topArr[currentview]);
			}
		});
		mLv_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO
				//1.修改curposition
				changeColorWhite();
				MediaUtils.CURPOSITION = position;
				changeColorGreen();
				//2.播放
				startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				//3.修改图标
				mIv_bottom_play.setImageResource(R.drawable.appwidget_pause);
			}
		});
		mSk_duration.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {//停止拖拽
				mSk_duration.setProgress(seekBar.getProgress());
				startMediaService("进度", seekBar.getProgress());
				//音乐播放器,跳转到指定的位置播放
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {//触摸到拖拽按钮
				// TODO

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//进度改变
				// TODO

			}
		});
	}

	private final int[] topArr = { R.id.ib_top_play, R.id.ib_top_list, R.id.ib_top_lrc, R.id.ib_top_volumn };
	private LyricShow mTv_lyricShow;

	/**
	 * 顶部按钮的选中效果
	 * @param selectedId
	 */
	private void setTopSelected(int selectedId) {
		//1.还原所有控件的效果,让top上面的4个按钮显示效果都是未选中
		findViewById(R.id.ib_top_play).setSelected(false);
		findViewById(R.id.ib_top_list).setSelected(false);
		findViewById(R.id.ib_top_lrc).setSelected(false);
		findViewById(R.id.ib_top_volumn).setSelected(false);

		//2.让传递进来的控件有选中效果
		findViewById(selectedId).setSelected(true);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.ib_top_play) {
			mSvg_main.setCurrentView(0);
			setTopSelected(R.id.ib_top_play);
		} else if (id == R.id.ib_top_list) {
			mSvg_main.setCurrentView(1);
			setTopSelected(R.id.ib_top_list);
		} else if (id == R.id.ib_top_lrc) {
			mSvg_main.setCurrentView(2);
			setTopSelected(R.id.ib_top_lrc);
		} else if (id == R.id.ib_bottom_play) {//播放按钮,点击同一个按钮.有两个操作.需要定义一个变量进行控制
			//启动服务.而且让服务播放音乐
			if (MediaUtils.CURSTATE == Constants.STATE_STOP) {//默认是停止,点击就变播放
				startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				//修改图标
				mIv_bottom_play.setImageResource(R.drawable.appwidget_pause);
			} else if (MediaUtils.CURSTATE == Constants.STATE_PLAY) {//第二次点击的时候.当前的状态是播放
				startMediaService("暂停");
				//修改图标
				mIv_bottom_play.setImageResource(R.drawable.img_playback_bt_play);
			} else if (MediaUtils.CURSTATE == Constants.STATE_PAUSE) {//第三次点击的时候.当前的状态是暂停
				startMediaService("继续");
				//修改图标
				mIv_bottom_play.setImageResource(R.drawable.appwidget_pause);
			}
		} else if (id == R.id.ib_bottom_last) {
			if (MediaUtils.CURPOSITION > 0) {
				changeColorWhite();
				MediaUtils.CURPOSITION--;
				changeColorGreen();
				//2.播放
				startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				//3.修改图标
				mIv_bottom_play.setImageResource(R.drawable.appwidget_pause);
			}
		} else if (id == R.id.ib_bottom_next) {
			if (MediaUtils.CURPOSITION < MediaUtils.songList.size() - 1) {//MediaUtils.songList.size() - 1
				changeColorWhite();
				MediaUtils.CURPOSITION++;
				changeColorGreen();
				//2.播放
				startMediaService("播放", MediaUtils.songList.get(MediaUtils.CURPOSITION).path);
				//3.修改图标
				mIv_bottom_play.setImageResource(R.drawable.appwidget_pause);

			}
		} else if (id == R.id.ib_bottom_model) {
			if (MediaUtils.CURMODEL == Constants.MODEL_NORMAL) {//当前是顺序播放
				MediaUtils.CURMODEL = Constants.MODEL_RANDOM;//切换成随机播放
				//更新ui
				mIv_bottom_model.setImageResource(R.drawable.icon_playmode_shuffle);
				//提示
				Toast.makeText(getApplicationContext(), "随机播放", Toast.LENGTH_SHORT).show();
			} else if (MediaUtils.CURMODEL == Constants.MODEL_RANDOM) {//当前是随机播放
				MediaUtils.CURMODEL = Constants.MODEL_REPEAT;//切换成重复播放
				//更新ui
				mIv_bottom_model.setImageResource(R.drawable.icon_playmode_repeat);
				//提示
				Toast.makeText(getApplicationContext(), "重复播放", Toast.LENGTH_SHORT).show();
			} else if (MediaUtils.CURMODEL == Constants.MODEL_REPEAT) {//当前是重复播放
				MediaUtils.CURMODEL = Constants.MODEL_SINGLE;//切换成单曲循环
				//更新ui
				mIv_bottom_model.setImageResource(R.drawable.icon_playmode_single);
				//提示
				Toast.makeText(getApplicationContext(), "单曲循环", Toast.LENGTH_SHORT).show();
			} else if (MediaUtils.CURMODEL == Constants.MODEL_SINGLE) {//当前是单曲循环
				MediaUtils.CURMODEL = Constants.MODEL_NORMAL;//切换成顺序播放
				//更新ui
				mIv_bottom_model.setImageResource(R.drawable.icon_playmode_normal);
				//提示
				Toast.makeText(getApplicationContext(), "顺序播放", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.ib_bottom_update) {
			reflash();
		} else if (id == R.id.ib_top_volumn) {
			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			//音乐可以设置的最大音量
			int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			//设置音量
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 2, AudioManager.FLAG_PLAY_SOUND);
		}
	}

	public void startMediaService(String option) {
		Intent service = new Intent(EasyMusicActivity.this, MusicService.class);
		service.putExtra("messenger", new Messenger(handler));
		service.putExtra("option", option);
		startService(service);
	}

	public void startMediaService(String option, String path) {
		Intent service = new Intent(EasyMusicActivity.this, MusicService.class);
		service.putExtra("option", option);
		service.putExtra("messenger", new Messenger(handler));
		service.putExtra("path", path);
		startService(service);
	}

	public void startMediaService(String option, int progress) {
		Intent service = new Intent(EasyMusicActivity.this, MusicService.class);
		service.putExtra("messenger", new Messenger(handler));
		service.putExtra("option", option);
		service.putExtra("progress", progress);
		startService(service);
	}

	/**
	 * 修改颜色.只要我们的curPostion修改了.那么颜色值就需要修改
	 * @param color
	 */
	public void changeColorWhite() {
		TextView tv = mLv_list.findViewWithTag(MediaUtils.CURPOSITION);
		if (tv != null) {
			tv.setTextColor(Color.WHITE);
		}
	}

	public void changeColorGreen() {
		TextView tv = mLv_list.findViewWithTag(MediaUtils.CURPOSITION);
		if (tv != null) {
			tv.setTextColor(Color.GREEN);
		}
	}

	/**
	 * 修改minilrc的文本
	 * @param lrcString
	 */
	public void setMiniLrc(String lrcString) {
		mTv_minilrc.setText(lrcString);
	}

	/**
	 * 1.发送特定的广播,让操作系统更新多媒体数据
	 * 2.系统扫描完成,会发出一个特定的的广播.我们只需要去监听特定的广播
	 */
	public void reflash() {
		/**---------------接收系统扫描完成的广播---------------**/
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter.addDataScheme("file");
		//注册广播
		registerReceiver(receiver, filter);

		/**---------------发送广播,让系统更新媒体数据库---------------**/
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
		intent.setData(Uri.parse("file://" + Environment.getExternalStorageDirectory()));
		sendBroadcast(intent);
	}

	MyBroadcastReceiver receiver = new MyBroadcastReceiver();
	private MyAdapter mAdapter;
	private ExecutorService executorService;
	private Handler mainHandler;

	class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {//onReceive这个方法里面不应该执行耗时的操作
			//反注册广播
			unregisterReceiver(receiver);
			//执行task
			scanMusic();
		}
	}

	private void scanMusic() {
		ProgressDialog dialog = ProgressDialog.show(EasyMusicActivity.this, "提示", "努力更新中");
		
		executorService.execute(() -> {
			//重新更新songList
			MediaUtils.initSongList(EasyMusicActivity.this);//contentProvider-->sqlite
			
			mainHandler.post(() -> {
				dialog.dismiss();
				//listview刷新
				mAdapter.notifyDataSetChanged();
			});
		});
	}

	@Override
	public void onBackPressed() {
		// TODO
		//1.回到桌面-->跳到桌面-->开启一个桌面隐式意图
		// 直接开启手机桌面  
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
		//2.显示notification
		showNotification();
	}

	private void showNotification() {

		NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.icon = R.mipmap.ic_launcher;

		Intent intent = new Intent(this, EasyMusicActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//notification.setLatestEventInfo(this, "黑马59期", null, contentIntent);

		notifManager.notify(0, notification);
	}
}
