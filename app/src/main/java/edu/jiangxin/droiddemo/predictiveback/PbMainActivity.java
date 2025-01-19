package edu.jiangxin.droiddemo.predictiveback;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.window.OnBackInvokedDispatcher;
import android.window.SystemOnBackInvokedCallbacks;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbMainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_main);

        Button btnToDefaultActivity = findViewById(R.id.btnToDefaultActivity);
        btnToDefaultActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbDefaultActivity.class);
            startActivity(intent);
        });
        
        Button btnToCustomizeCallbackActivity = findViewById(R.id.btnToCustomizeCallbackActivity);
        btnToCustomizeCallbackActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbCustomizeCallbackActivity.class);
            startActivity(intent);
        });

        Button btnToFinishAndRemoveTaskCallbackActivity = findViewById(R.id.btnToFinishAndRemoveTaskCallbackActivity);
        btnToFinishAndRemoveTaskCallbackActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbFinishAndRemoveTaskCallbackActivity.class);
            startActivity(intent);
        });

        Button btnMoveTaskToBackCallbackActivity = findViewById(R.id.btnMoveTaskToBackCallbackActivity);
        btnMoveTaskToBackCallbackActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbMoveTaskToBackCallbackActivity.class);
            startActivity(intent);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            OnBackInvokedDispatcher dispatcher = getOnBackInvokedDispatcher();
            dispatcher.registerOnBackInvokedCallback(0, SystemOnBackInvokedCallbacks.finishAndRemoveTaskCallback(this));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
            OnBackInvokedDispatcher dispatcher = getOnBackInvokedDispatcher();
            dispatcher.unregisterOnBackInvokedCallback(SystemOnBackInvokedCallbacks.finishAndRemoveTaskCallback(this));
        }
    }
}