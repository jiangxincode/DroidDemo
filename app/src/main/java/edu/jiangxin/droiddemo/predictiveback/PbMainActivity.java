package edu.jiangxin.droiddemo.predictiveback;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.window.OnBackInvokedDispatcher;
import android.window.SystemOnBackInvokedCallbacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.jiangxin.droiddemo.R;

public class PbMainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_pb_main);

        View container = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
            int typeMask = WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout();
            Insets sysInsets = insets.getInsets(typeMask);
            v.setPadding(sysInsets.left, sysInsets.top, sysInsets.right, 0);
            return insets;
        });

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

        Button btnToDoubleBackActivity = findViewById(R.id.btnToDoubleBackActivity);
        btnToDoubleBackActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbDoubleBackActivity.class);
            startActivity(intent);
        });

        Button btnToCustomAnimationActivity = findViewById(R.id.btnToCustomAnimationActivity);
        btnToCustomAnimationActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbCustomAnimationActivity.class);
            startActivity(intent);
        });

        Button btnToSystemNavigationObserverActivity = findViewById(R.id.btnToSystemNavigationObserverActivity);
        btnToSystemNavigationObserverActivity.setOnClickListener(v -> {
            Intent intent = new Intent(PbMainActivity.this, PbSystemNavigationObserverActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}