package edu.jiangxin.easymarry.activity.various;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.easymarry.R;

public class VariousMainActivity extends AppCompatActivity {
    private Button mBtn01, mBtn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_main);

        mBtn01 = findViewById(R.id.btn01);
        mBtn01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoadPictureToMemActivity.class);
                startActivity(intent);
            }
        });

        mBtn02 = findViewById(R.id.btn02);
        mBtn02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), LoadBigPictureToMemActivity.class);
                startActivity(intent);
            }
        });
    }
}
