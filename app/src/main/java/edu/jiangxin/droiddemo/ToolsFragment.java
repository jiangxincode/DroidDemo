package edu.jiangxin.droiddemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.jiangxin.droiddemo.easychat.activity.EasyChatActivity;

import edu.jiangxin.droiddemo.tools.mortgagecalc.MainActivity;
import edu.jiangxin.droiddemo.easymusic.EasyMusicActivity;
import edu.jiangxin.droiddemo.smartbj.activity.SplashActivity;

/**
 * Created by jiang on 2018/1/21.
 */

public class ToolsFragment extends Fragment {
    private Button mBtnMortgageCalculator, mBtnEasyMusic, mBtnEasyChat, mBtnSmartBj, mBtnGooglePlay;

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tools, container, false);

        mBtnMortgageCalculator = root.findViewById(R.id.btnMortgageCalculator);
        mBtnMortgageCalculator.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), MainActivity.class);
            startActivity(intent);
        });

        mBtnEasyMusic = root.findViewById(R.id.btnEasyMusic);
        mBtnEasyMusic.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), EasyMusicActivity.class);
            startActivity(intent);
        });

        mBtnEasyChat = root.findViewById(R.id.btnEasyChat);
        mBtnEasyChat.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), EasyChatActivity.class);
            startActivity(intent);
        });

        mBtnSmartBj = root.findViewById(R.id.btnSmartBj);
        mBtnSmartBj.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SplashActivity.class);
            startActivity(intent);
        });

        mBtnGooglePlay = root.findViewById(R.id.btnGooglePlay);
        mBtnGooglePlay.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), com.itheima.googleplay_8.MainActivity.class);
            startActivity(intent);
        });
        return root;
    }
}
