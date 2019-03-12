package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.jiangxin.droiddemo.R;

public class ActivityTrackerActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(
                        new Intent(ActivityTrackerActivity.this, TrackerService.class)
                                .putExtra(TrackerService.COMMAND, TrackerService.COMMAND_OPEN)
                );
                finish();
            }
        });
    }
}
