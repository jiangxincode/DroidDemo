package edu.jiangxin.droiddemo.service.messanger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.constant.Constant;

public class MessengerActivity extends Activity {

    private static final String TAG = "MessengerActivity";

    private Button button;

    private Messenger mService;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_SERVICE:
                    String message = msg.getData().getString("msg");
                    Log.i(TAG, "receive message from service:" + message);
                    Toast.makeText(ApplicationExt.getContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.i(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        button = findViewById(R.id.notifyService);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                Bundle data = new Bundle();
                data.putString("msg", "hello, this is client.");
                msg.setData(data);
                msg.replyTo = new Messenger(new MessengerHandler());
                try {
                    Log.i(TAG, "send message to service");
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MessengerService.class);
        Log.i(TAG, "bindService");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "unbindService");
        unbindService(mConnection);
        super.onDestroy();
    }
}
