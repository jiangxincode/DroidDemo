package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

import edu.jiangxin.droiddemo.R;

@SuppressWarnings("deprecation")
public class ThreadDemoActivity extends Activity {

    private static final String TAG = "ASYNC_TASK";

    private Button execute;
    private Button cancel;
    private ProgressBar progressBar;
    private TextView textView;

    private MyTask mTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_demo);

        execute = findViewById(R.id.execute);
        execute.setOnClickListener(v -> {
            //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
            mTask = new MyTask();
            mTask.execute("https://www.baidu.com");

            execute.setEnabled(false);
            cancel.setEnabled(true);
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.text_view);

    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
            textView.setText("loading...");
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG, "doInBackground(Params... params) called");
            try {
                URL url = new URL(params[0]);
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
                        baos.write(buffer, 0, len);
                        count += len;
                        publishProgress((int) ((count / (float) totalLength) * 100));
                        //为了演示进度,休眠500毫秒
                        Thread.sleep(500);
                    }
                    return baos.toString("gb2312");
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
            progressBar.setProgress(progresses[0]);
            textView.setText("loading..." + progresses[0] + "%");
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
            textView.setText(result);

            execute.setEnabled(true);
            cancel.setEnabled(false);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
            textView.setText("cancelled");
            progressBar.setProgress(0);

            execute.setEnabled(true);
            cancel.setEnabled(false);
        }
    }
}
