package edu.jiangxin.easymarry.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.jiangxin.easymarry.R;

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
