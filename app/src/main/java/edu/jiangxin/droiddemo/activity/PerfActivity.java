package edu.jiangxin.droiddemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.widget.Button;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

public class PerfActivity extends AppCompatActivity {

    private Button mStartBtn;

    private Button mStopBtn;

    private TextView mTimeTv;

    private Handler mHandler;

    private long count;

    private boolean isCounting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perf);
        mHandler = new Handler(Looper.myLooper());
        count = 0;
        mStartBtn = findViewById(R.id.start);
        mStartBtn.setOnClickListener(view -> {
            Trace.beginSection("TimeTrace");
            isCounting = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isCounting) {
                        Trace.beginSection("TimeTraceChild[" + count + "]");
                        mTimeTv.setText("Time: " + (++count));
                        mHandler.postDelayed(this, 1000);
                        Trace.endSection();
                    }
                }
            }, 1000);
        });
        mStopBtn = findViewById(R.id.stop);
        mStopBtn.setOnClickListener(view -> {
            Trace.endSection();
            isCounting = false;
        });
        mTimeTv = findViewById(R.id.time);
    }
}