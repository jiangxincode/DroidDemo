package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.view.RipplesView;


public class RippleActivity extends Activity {
    private Button btn1;
    private Button btn2;

    RipplesView wave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        wave = findViewById(R.id.wave);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wave.isStarting() ){
                    btn1.setText("继续");
                    wave.stop();
                }
                else{
                    btn1.setText("暂停");
                    wave.start();
                }
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave.breath();//让波呼吸一次
            }
        });



    }
}
