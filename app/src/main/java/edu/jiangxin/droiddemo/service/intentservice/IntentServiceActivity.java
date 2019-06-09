package edu.jiangxin.droiddemo.service.intentservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;

/**
 * @author jiangxin
 */
public class IntentServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        Button button = findViewById(R.id.startIntentService);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(IntentServiceActivity.this , IntentServiceImpl.class);
            startService(intent);
        });
    }
}