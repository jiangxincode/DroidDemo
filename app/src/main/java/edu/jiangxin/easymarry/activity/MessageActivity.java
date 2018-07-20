package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import edu.jiangxin.easymarry.R;

public class MessageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        TextView msg = findViewById(R.id.msg);
        msg.setText(getIntent().getStringExtra("msg"));
    }
}
