package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.jiangxin.easymarry.R;

public class NetMusicActivity extends Activity implements OnPreparedListener {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_music);
    }

    public void click(View v) {
        try {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
//			mMediaPlayer.setDataSource("http://192.168.1.100:8080/xpg.mp3");
            mMediaPlayer.setDataSource("mms://mediasrv2.iptv.xmg.com.cn/yinyue");
            mMediaPlayer.prepareAsync();//网络音乐.应该异步准备
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {//如果使用prepareAsync,应该把start()方法放到这个地方
        Toast.makeText(getApplicationContext(), "准备好了", 0).show();
        mMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }
}
