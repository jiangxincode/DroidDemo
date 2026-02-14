package edu.jiangxin.droiddemo.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.service.aidl.AIDLActivity;
import edu.jiangxin.droiddemo.service.commonservice.CommonServiceActivity;
import edu.jiangxin.droiddemo.service.intentservice.IntentServiceActivity;
import edu.jiangxin.droiddemo.service.localbinder.LocalBinderActivity;
import edu.jiangxin.droiddemo.service.messanger.MessengerActivity;

public class VariousServiceActivity extends Activity implements View.OnClickListener {

    private TextView mBtn10, mBtn11, mBtn12, mBtn13, mBtn14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_service);
        mBtn10 = findViewById(R.id.btn10);
        mBtn11 = findViewById(R.id.btn11);
        mBtn12 = findViewById(R.id.btn12);
        mBtn13 = findViewById(R.id.btn13);
        mBtn14 = findViewById(R.id.btn14);

        mBtn10.setOnClickListener(this);
        mBtn11.setOnClickListener(this);
        mBtn12.setOnClickListener(this);
        mBtn13.setOnClickListener(this);
        mBtn14.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn10) {
            enterTestActivity(CommonServiceActivity.class);
        } else if (id == R.id.btn11) {
            enterTestActivity(IntentServiceActivity.class);
        } else if (id == R.id.btn12) {
            enterTestActivity(LocalBinderActivity.class);
        } else if (id == R.id.btn13) {
            enterTestActivity(MessengerActivity.class);
        } else if (id == R.id.btn14) {
            enterTestActivity(AIDLActivity.class);
        }
    }

    private void enterTestActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
