package edu.jiangxin.droiddemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.jiangxin.droiddemo.tools.mortgagecalc.MainActivity;
import edu.jiangxin.easymusic.EasyMusicActivity;

/**
 * Created by jiang on 2018/1/21.
 */

public class ToolsFragment extends Fragment {
    private Button mBtnMortgageCalculator, mBtnEasyMusic;

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
        return root;
    }
}
