package edu.jiangxin.droiddemo.vibrator;

import android.app.Activity;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.widget.Button;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class VibratorActivity extends Activity {

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrator);

        VibratorManager vibratorManager = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
        vibrator = vibratorManager.getDefaultVibrator();

        Button btnOneShotVibrate = findViewById(R.id.btnOneShotVibrate);
        btnOneShotVibrate.setOnClickListener(v -> {
            if (vibrator != null) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(effect);
            }
        });

        Button btnWaveformVibrate = findViewById(R.id.btnWaveformVibrate);
        btnWaveformVibrate.setOnClickListener(v -> {
            if (vibrator != null) {
                long[] timings = {0, 200, 200, 200, 200};
                int[] amplitudes = {0, 50, 100, 150, 200};
                VibrationEffect effect = VibrationEffect.createWaveform(timings, amplitudes, -1);
                vibrator.vibrate(effect);
            }
        });

        Button btnComposeVibrate = findViewById(R.id.btnComposeVibrate);
        btnComposeVibrate.setOnClickListener(v -> {
            if (vibrator != null) {
                boolean[] arePrimitivesSupported = vibrator.arePrimitivesSupported(
                        VibrationEffect.Composition.PRIMITIVE_SLOW_RISE,
                        VibrationEffect.Composition.PRIMITIVE_QUICK_FALL,
                        VibrationEffect.Composition.PRIMITIVE_TICK);

                VibrationEffect.Composition composition = VibrationEffect.startComposition();
                boolean isEmpty = true;
                for (int i = 0; i < arePrimitivesSupported.length; i++) {
                    if (arePrimitivesSupported[i]) {
                        composition.addPrimitive(i, 1.0f);
                        isEmpty = false;
                    }
                }
                if (isEmpty) {
                    Toast.makeText(this, "No primitives are supported, so we can't compose a vibration.", Toast.LENGTH_LONG).show();
                } else {
                    VibrationEffect effect = composition.compose();
                    vibrator.vibrate(effect);
                }
            }
        });
    }
}
