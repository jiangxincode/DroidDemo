package edu.jiangxin.droiddemo.speechrecognize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.jiangxin.droiddemo.R;

public class SpeechRecognizeActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SpeechRecognizeTAG";


    private Button speechBut;
    private TextView result;
    private SpeechRecognizer mSpeechRecognizer;

    private final String string = "what's your name";

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.speechBut) {
            doSpeechRecognition(v);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognize);

        //        OpenJurisdiction为权限开启
        final OpenJurisdiction openJurisdiction = new OpenJurisdiction();
        openJurisdiction.open(SpeechRecognizeActivity.this);

        this.speechBut = findViewById(R.id.speechBut);
        this.speechBut.setOnClickListener(this);
        this.result = findViewById(R.id.result);
        this.mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        this.mSpeechRecognizer.setRecognitionListener(new MyRecognitionListener());
    }

    public void doSpeechRecognition(View view) {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "isRecognitionAvailable false", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (!SpeechRecognizer.isOnDeviceRecognitionAvailable(this)) {
            Toast.makeText(this, "isOnDeviceRecognitionAvailable false", Toast.LENGTH_SHORT).show();
            return;
        }*/
        view.setEnabled(false);
        Intent recognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh-CN");
        Log.i(TAG, "before startListening");
        this.mSpeechRecognizer.startListening(recognitionIntent);
        Log.i(TAG, "after startListening");
    }

    private class MyRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.i(TAG, "onReadyForSpeech");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.i(TAG, "onBeginningOfSpeech");
            result.setText("");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.i(TAG, "onRmsChanged");

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.i(TAG, "onBufferReceived");
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(TAG, "onEndOfSpeech");
            speechBut.setEnabled(true);
        }

        @Override
        public void onError(int error) {
            Log.e(TAG, String.valueOf(error));
            if (!isNetworkAvailable(SpeechRecognizeActivity.this)) {
                Toast.makeText(SpeechRecognizeActivity.this, "未开启网络权限，无法进行语音识别", Toast.LENGTH_SHORT).show();
            }
            speechBut.setEnabled(true);
        }

        @Override
        public void onResults(Bundle results) {
            Log.i(TAG, "onResults");
            ArrayList<String> partialResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (partialResults != null && partialResults.size() > 0) {
                String bestResult = partialResults.get(0);
                bestResult = bestResult.substring(0, bestResult.length() - 1);
                result.setText(bestResult);
                if (bestResult.equals(string)) {
                    Toast.makeText(SpeechRecognizeActivity.this, "正确", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.i(TAG, "onPartialResults");
            ArrayList<String> partialResults = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (partialResults != null && partialResults.size() > 0) {
                String bestResult = partialResults.get(0);
                bestResult = bestResult.substring(0, bestResult.length() - 1);
                result.setText(bestResult);
                if (bestResult.equals(string)) {
                    Toast.makeText(SpeechRecognizeActivity.this, "正确", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mSpeechRecognizer.destroy();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Network network = connectivity.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities capabilities = connectivity.getNetworkCapabilities(network);
                    return capabilities != null &&
                            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                }
            } else {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return info.getState() == NetworkInfo.State.CONNECTED;
                }
            }
        }
        return false;
    }
}
