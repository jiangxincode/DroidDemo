package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class JNIActivity extends Activity {

    static{
        System.loadLibrary("DroidDemoJni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

    }


    public native String helloFromC();

    public native int resultFromC(int a,int b); 

    public void click1(View view){
        Toast.makeText(this, helloFromC(), Toast.LENGTH_LONG).show();
    }

    public void click2(View view){
        Toast.makeText(this, "3 + 5 = " + resultFromC(3, 5), Toast.LENGTH_LONG).show();
    }
}
