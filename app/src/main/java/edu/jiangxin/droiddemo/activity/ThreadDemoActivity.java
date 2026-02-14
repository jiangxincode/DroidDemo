package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.jiangxin.droiddemo.R;

public class ThreadDemoActivity extends Activity {

    private static final String TAG = "ASYNC_TASK";

    private Button execute;
    private Button cancel;
    private ProgressBar progressBar;
    private TextView textView;

    private ExecutorService executorService;
    private Handler mainHandler;
    private Future<?> currentTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_demo);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        execute = findViewById(R.id.execute);
        execute.setOnClickListener(v -> {
            executeTask("https://www.baidu.com");
            execute.setEnabled(false);
            cancel.setEnabled(true);
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消一个正在执行的任务
                if (currentTask != null && currentTask.cancel(true)) {
                    onTaskCancelled();
                }
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.text_view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void executeTask(String urlString) {
        // onPreExecute
        mainHandler.post(() -> {
            Log.i(TAG, "onPreExecute() called");
            textView.setText("loading...");
        });

        // doInBackground
        currentTask = executorService.submit(() -> {
            Log.i(TAG, "doInBackground called");
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                int totalLength;
                String contentLength = httpURLConnection.getHeaderField("Content-Length");
                if (!TextUtils.isEmpty(contentLength)) {
                    totalLength = Integer.parseInt(contentLength);
                } else {
                    totalLength = httpURLConnection.getContentLength();
                }
                if (totalLength == 0 || totalLength == -1) {
                    totalLength = 10240;
                }

                InputStream inputStream = httpURLConnection.getInputStream();
                int respondCode = httpURLConnection.getResponseCode();
                if (respondCode == HttpURLConnection.HTTP_OK) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024*8];
                    int len;
                    int count = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        if (Thread.currentThread().isInterrupted()) {
                            return;
                        }
                        baos.write(buffer, 0, len);
                        count += len;
                        final int progress = (int) ((count / (float) totalLength) * 100);
                        // onProgressUpdate
                        mainHandler.post(() -> {
                            Log.i(TAG, "onProgressUpdate called");
                            progressBar.setProgress(progress);
                            textView.setText("loading..." + progress + "%");
                        });
                        //为了演示进度,休眠500毫秒
                        Thread.sleep(500);
                    }
                    String result = baos.toString("gb2312");
                    // onPostExecute
                    mainHandler.post(() -> {
                        Log.i(TAG, "onPostExecute called");
                        textView.setText(result);
                        execute.setEnabled(true);
                        cancel.setEnabled(false);
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                mainHandler.post(() -> {
                    textView.setText("Error: " + e.getMessage());
                    execute.setEnabled(true);
                    cancel.setEnabled(false);
                });
            }
        });
    }

    private void onTaskCancelled() {
        mainHandler.post(() -> {
            Log.i(TAG, "onCancelled() called");
            textView.setText("cancelled");
            progressBar.setProgress(0);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        });
    }
}
