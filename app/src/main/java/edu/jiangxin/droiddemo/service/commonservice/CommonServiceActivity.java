package edu.jiangxin.droiddemo.service.commonservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;

public class CommonServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_service);
        Button button = findViewById(R.id.startCommonService);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(CommonServiceActivity.this , CommonService.class);
            startService(intent);
        });
    }
}