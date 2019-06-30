package edu.jiangxin.droiddemo.fragment;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.jiangxin.droiddemo.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }

    public void click1(View view){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), FragmentTest1Activity.class);
        startActivity(intent);
    }

    public void click2(View view){
    }
}
