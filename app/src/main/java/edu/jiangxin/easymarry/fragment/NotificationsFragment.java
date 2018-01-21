package edu.jiangxin.easymarry.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import edu.jiangxin.easymarry.R;

/**
 * Created by jiang on 2018/1/21.
 */

public class NotificationsFragment extends Fragment {

    private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8;
    View root;
    View decorView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        decorView = getActivity().getWindow().getDecorView();

        //显示状态栏，Activity不全屏显示(恢复到有状态的正常情况)
        mBtn1 = (Button)root.findViewById(R.id.btn1);

        //隐藏状态栏，同时Activity会伸展全屏显示
        mBtn2 = (Button)root.findViewById(R.id.btn2);

        //Activity全屏显示，且状态栏被隐藏覆盖掉
        mBtn3 = (Button)root.findViewById(R.id.btn3);

        //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
        mBtn4 = (Button)root.findViewById(R.id.btn4);

        //同mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mBtn5 = (Button)root.findViewById(R.id.btn5);

        //同mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mBtn6 = (Button)root.findViewById(R.id.btn6);

        //隐藏虚拟按键(导航栏)
        mBtn7 = (Button)root.findViewById(R.id.btn7);

        //状态栏显示处于低能显示状态(low profile模式)，状态栏上一些图标显示会被隐藏。
        mBtn8 = (Button)root.findViewById(R.id.btn8);
        mBtn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.INVISIBLE);
            }
        });
        mBtn3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        });
        mBtn4.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        });
        mBtn5.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            }
        });
        mBtn6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
            }
        });
        mBtn7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        });
        mBtn8.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
        });
        return root;
    }
}
