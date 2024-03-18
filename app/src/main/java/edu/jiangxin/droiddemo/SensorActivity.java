package edu.jiangxin.droiddemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends Activity {
    private static final String TAG = "SensorActivity";

    private SensorManager sm = null;

    private List<SensorEventListener> listeners = new ArrayList<>();

    private LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.activity_sensor);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList) {
            TextView textView = new TextView(this);
            textView.setText(sensor.getName());
            SensorEventListener listener = new MySensorEventListener(textView);
            listeners.add(listener);
            sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            linearLayout.addView(textView);
        }
    }

    @Override
    protected void onStop() {
        for (SensorEventListener listener : listeners) {
            sm.unregisterListener(listener);
        }
        super.onStop();
    }

    static class MySensorEventListener implements SensorEventListener {
        private TextView mTextView;
        MySensorEventListener(TextView textView) {
            super();
            mTextView = textView;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                float[] values = event.values;
                Sensor sensor = event.sensor;
                StringBuilder sb = new StringBuilder();
                sb.append(sensor.getName()).append("[");
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