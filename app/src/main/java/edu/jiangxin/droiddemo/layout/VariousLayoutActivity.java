package edu.jiangxin.droiddemo.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.service.intentservice.IntentServiceActivity;

public class VariousLayoutActivity extends Activity implements View.OnClickListener {

    private TextView mTvSimpleLayout, mTvConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_layout);
        mTvSimpleLayout = findViewById(R.id.btnSimpleLayout);
        mTvConstraintLayout = findViewById(R.id.btnContraintLayout);

        mTvSimpleLayout.setOnClickListener(this);
        mTvConstraintLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSimpleLayout) {
            enterTestActivity(SimpleLayoutActivity.class);
        } else if (id == R.id.btnContraintLayout) {
            enterTestActivity(ContraintLayoutActivity.class);
        }
    }

    private void enterTestActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
