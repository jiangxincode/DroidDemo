package edu.jiangxin.droiddemo.service.localbinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class LocalBinderActivity extends Activity {
    LocalBinderService mService;

    private Button button;

    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_binder);
        button = findViewById(R.id.invokeServiceMethod);
        button.setOnClickListener(view -> {
            if (mBound) {
                // Call a method from the LocalBinderService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                int num = mService.getRandomNumber();
                Toast.makeText(LocalBinderActivity.this, "number: " + num, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalBinderService
        Intent intent = new Intent(this, LocalBinderService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalBinderService, cast the IBinder and get LocalBinderService instance
            LocalBinderService.LocalBinder binder = (LocalBinderService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}