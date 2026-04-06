package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import edu.jiangxin.droiddemo.R;

public class SpinnerActivity extends Activity {

    private static final String[] name = {"刘备", "关羽", "张飞", "曹操", "小乔"};

    private TextView text;

    private MaterialAutoCompleteTextView spinner;
    private MaterialAutoCompleteTextView spinner2;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        text = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.songs, android.R.layout.simple_dropdown_item_1line);

        adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_dropdown_item_1line, name);

        //将adapter添加到spinner中
        spinner.setAdapter(adapter);

        spinner2.setAdapter(adapter2);
        //添加事件监听
        spinner.setOnItemClickListener((parent, view, position, id) ->
                text.setText("我的名字是：" + name[position]));

        spinner2.setOnItemClickListener((parent, view, position, id) ->
                text.setText("我的名字是：" + name[position]));
    }
}
