package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

public class ShortcutTargetActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        TextView msg = findViewById(R.id.msg);
        msg.setText(getIntent().getStringExtra("shortcutItem"));
    }
}
