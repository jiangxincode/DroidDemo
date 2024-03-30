package edu.jiangxin.droiddemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorActivity extends Activity {
    private static final String TAG = "SensorActivity";

    private SensorManager mSensorManager = null;

    private final Map<String, SensorEventListener> mSensorEventListenerMap = new HashMap<>();

    private LinearLayout mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.activity_sensor);
        mLinearLayout = findViewById(R.id.linearLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);

            TextView sensorNameTextView = new TextView(this);
            sensorNameTextView.setText(sensor.getName());

            TextView sensorDataTextView = new TextView(this);

            mSensorEventListenerMap.put(sensor.getName(), new MySensorEventListener(sensorDataTextView));

            Button button = new Button(this);
            button.setText("register");
            button.setOnClickListener(v -> {
                if (button.getText().equals("register")) {
                    button.setText("unregister");
                    mSensorManager.registerListener(mSensorEventListenerMap.get(sensor.getName()), sensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    button.setText("register");
                    mSensorManager.unregisterListener(mSensorEventListenerMap.get(sensor.getName()));
                }
            });

            itemLayout.addView(sensorNameTextView);
            itemLayout.addView(button);
            itemLayout.addView(sensorDataTextView);

            mLinearLayout.addView(itemLayout);
        }
    }

    @Override
    protected void onStop() {
        for (SensorEventListener listener : mSensorEventListenerMap.values()) {
            mSensorManager.unregisterListener(listener);
        }
        super.onStop();
    }

    static class MySensorEventListener implements SensorEventListener {
        private final TextView mTextView;
        MySensorEventListener(TextView textView) {
            super();
            mTextView = textView;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                float[] values = event.values;
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < values.length; i++) {
                    sb.append(values[i]);
                    if (i < values.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                mTextView.setText(sb.toString());
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        }
    }
}