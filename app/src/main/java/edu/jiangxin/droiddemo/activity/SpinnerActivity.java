package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

public class SpinnerActivity extends Activity {

    private static final String[] name = {"刘备", "关羽", "张飞", "曹操", "小乔"};

    private TextView text;

    private Spinner spinner;
    private Spinner spinner2;

    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        text = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
        adapter = ArrayAdapter.createFromResource(this, R.array.songs, android.R.layout.simple_spinner_item);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2 = new ArrayAdapter<CharSequence>(this,R.layout.spinner_item,name);adapter.setDropDownViewResource(R.layout.dropdown_stytle);


        //将adapter添加到spinner中
        spinner.setAdapter(adapter);

        spinner2.setAdapter(adapter2);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        spinner2.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            text.setText("我的名字是：" + name[arg2]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /*
    pinner有三个属性可以记一下：
android:spinnerMode="dropdown"
android:dropDownVerticalOffset="-50dp"
android:dropDownHorizontalOffset="20dp"
android:popupBackground="#f0000000"

spinnerMode=dropdown时，为下拉模式
spinnerMode=dialog时，会在界面中间弹出android:popupBackground=”#f0000000”，可以去除spinner的默认黑边
dropDownVerticalOffset和dropDownHorizontalOffset都是改变下拉框位置的
     */
}
