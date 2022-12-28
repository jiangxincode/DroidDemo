package edu.jiangxin.droiddemo.activitytracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;

public class ActivityTrackerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            Intent service = new Intent(ActivityTrackerActivity.this, TrackerService.class);
            service.putExtra(TrackerService.KEY_ACTION, TrackerService.ACTION_OPEN);
            startService(service);
            finish();
        });
    }
}
