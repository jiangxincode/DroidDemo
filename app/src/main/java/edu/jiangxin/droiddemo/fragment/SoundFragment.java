package edu.jiangxin.droiddemo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class SoundFragment extends Fragment {
    
    private static final String TAG = "SoundFragment";

    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        //为 fragment 声明  layout 文件, 然后 将layout 文件的 显示 转换为一个 view 对象

        // 之前 为了将 layout 文件转换为 view 对象时, 调用的是
        // View.inflate(context, resource, root)
        View view = inflater.inflate(R.layout.soundfragment, null);
        btn = (Button) view.findViewById(R.id.bbtn);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //所以, 以后 如果 需要acitvity 和fragment 之间传输 数据, 可以 使用  getActivity 来获得
                // 当前 fragment 所在的 acitvity 的实例
                EditText ed_text = (EditText) getActivity().findViewById(R.id.ed_text);
                String value = ed_text.getText().toString().trim();
                Toast.makeText(getActivity(), " value :" + value, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

}
