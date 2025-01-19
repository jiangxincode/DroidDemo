package edu.jiangxin.droiddemo.predictiveback;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbDefaultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_default);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}