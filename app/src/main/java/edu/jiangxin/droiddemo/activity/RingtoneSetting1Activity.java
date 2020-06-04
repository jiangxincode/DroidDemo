package edu.jiangxin.droiddemo.activity;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import edu.jiangxin.droiddemo.adapter.MainAdapter;
import edu.jiangxin.droiddemo.adapter.MainAdapter.ViewHolder;

import edu.jiangxin.droiddemo.R;

public class RingtoneSetting1Activity extends Activity {

    private MainAdapter mAdapter;
    ListView listView;
    Button sureBtn;
    SharedPreferences sp;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_setting1);
        /*初始化SharedPreferences*/
        sp = getSharedPreferences("ring", Context.MODE_PRIVATE);
        spe = sp.edit();

        /*初始化listView*/
        listView = findViewById(R.id.ring_lv);
        mAdapter = new MainAdapter(this, sp.getInt("ring", 0));
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(mOnItemClickListener);

        /*初始化返回按钮和保存按钮*/
        Button backBtn = findViewById(R.id.back_btn);
        sureBtn = findViewById(R.id.sure_btn);
        backBtn.setOnClickListener(mOnClickListener);
        sureBtn.setOnClickListener(mOnClickListener);

    }

    /*listView的按钮点击事件*/
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            ViewHolder mHolder = new ViewHolder(parent);
            /*设置Imageview不可被点击*/
            mHolder.iv.setClickable(false);
            /*清空map*/
            mAdapter.map.clear();
            // mAdapter.map.put(position, 1);
            /*将所点击的位置记录在map中*/
            mAdapter.map.put(position, true);
            /*刷新数据*/
            mAdapter.notifyDataSetChanged();
            /*判断位置不为0则播放的条目为position-1*/
            if (position != 0) {
                try {

                    RingtoneManager rm = new RingtoneManager(RingtoneSetting1Activity.this);
                    rm.setType(RingtoneManager.TYPE_NOTIFICATION);
                    rm.getCursor();
                    rm.getRingtone(position - 1).play();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*position为0是跟随系统，先得到系统所使用的铃声，然后播放*/
            if (position == 0) {

                Uri uri = RingtoneManager.getActualDefaultRingtoneUri(
                        RingtoneSetting1Activity.this, RingtoneManager.TYPE_NOTIFICATION);
                RingtoneManager.getRingtone(RingtoneSetting1Activity.this, uri).play();
            }

        }

    };

    /*按钮点击事件*/
    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /*返回按钮时退出demo*/
                case R.id.back_btn:
                    finish();
                    break;
                /*保存按钮则保存SharedPreferences中的数据*/
                case R.id.sure_btn:
                    spe.putInt("ring", listView.getCheckedItemPosition()).commit();
                    Toast.makeText(RingtoneSetting1Activity.this, "提示音保存成功", Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

}
