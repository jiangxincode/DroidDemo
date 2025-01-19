package edu.jiangxin.droiddemo.predictiveback;

import android.os.Build;
import android.os.Bundle;
import android.window.OnBackInvokedDispatcher;
import android.window.SystemOnBackInvokedCallbacks;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.R;

public class PbFinishAndRemoveTaskCallbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_finish_and_remove_task_callback);
        if ("Baklava".equals(Build.VERSION.CODENAME)) {
            OnBackInvokedDispatcher dispatcher = getOnBackInvokedDispatcher();
            dispatcher.registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, SystemOnBackInvokedCallbacks.finishAndRemoveTaskCallback(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ("Baklava".equals(Build.VERSION.CODENAME)) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(SystemOnBackInvokedCallbacks.finishAndRemoveTaskCallback(this));
        }
    }

}