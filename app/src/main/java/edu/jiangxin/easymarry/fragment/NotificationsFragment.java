package edu.jiangxin.easymarry.fragment;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.activity.BlurActivity;
import edu.jiangxin.easymarry.activity.DecorViewActivity;
import edu.jiangxin.easymarry.activity.DialogActivity;
import edu.jiangxin.easymarry.activity.MessengerActivity;
import edu.jiangxin.easymarry.activity.RippleActivity;
import edu.jiangxin.easymarry.activity.listview.ListViewActivity;

/**
 * Created by jiang on 2018/1/21.
 */

public class NotificationsFragment extends Fragment {

    private static final String TAG = "NotificationsFragment";

    private Button mBtn9, mBtn10, mBtn11, mBtnDecorViewEntrance, mBtn13, mBtn14, mBtnDialogEntrance, mBtnRippleEntrance, mBtnBlurEntrance;
    private View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mBtn9 = root.findViewById(R.id.btn9);
        mBtn9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
                //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
                Log.i(TAG, "isScreenOn: " + pm.isScreenOn());

                //如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
                // 如果flag为false，表示目前未锁屏
                KeyguardManager mKeyguardManager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
                Log.i(TAG, "inKeyguardRestrictedInputMode: " + mKeyguardManager.inKeyguardRestrictedInputMode());
            }
        });

        mBtn10 = root.findViewById(R.id.btn10);
        mBtn10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "Process.myUid(): " + Process.myUid());
                Log.i(TAG, "Process.myPid(): " + Process.myPid());
                Log.i(TAG, "Process.myTid(): " + Process.myTid());

                try {
                    Method getUserId = UserHandle.class.getMethod("getUserId", int.class);
                    int userUserId = (Integer) getUserId.invoke(null, Process.myUid());
                    Log.i(TAG, "UserHandle.getUserId(int uid): " + userUserId);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        });

        mBtn11 = root.findViewById(R.id.btn11);
        mBtn11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DisplayMetrics metric = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);

                Log.i(TAG, "Screen width(px): " + metric.widthPixels); // 屏幕宽度（像素）
                Log.i(TAG, "Screen height(px): " + metric.heightPixels); // 屏幕高度（像素）
                Log.i(TAG, "Screen density: " + metric.density); // 屏幕密度（0.75 / 1.0 / 1.5）
                Log.i(TAG, "Screen densityDpi(dpi): " + metric.densityDpi); // 屏幕密度DPI（120 / 160 / 240）
                Log.i(TAG, "Screen width(inch): " + (float) metric.widthPixels / metric.densityDpi);
                Log.i(TAG, "Screen height(inch): " + (float) metric.heightPixels / metric.densityDpi);
            }
        });

        mBtnDecorViewEntrance = root.findViewById(R.id.btnDecorViewEntrance);
        mBtnDecorViewEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DecorViewActivity.class);
                startActivity(intent);
            }
        });

        mBtn13 = root.findViewById(R.id.btn13);
        mBtn13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), MessengerActivity.class);
                startActivity(intent);
            }
        });

        mBtn14 = root.findViewById(R.id.btn14);
        mBtn14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ListViewActivity.class);
                startActivity(intent);
            }
        });

        mBtnDialogEntrance = root.findViewById(R.id.btnDialogEntrance);
        mBtnDialogEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), DialogActivity.class);
                startActivity(intent);
            }
        });

        mBtnRippleEntrance = root.findViewById(R.id.btnRippleEntrance);
        mBtnRippleEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), RippleActivity.class);
                startActivity(intent);
            }
        });

        mBtnBlurEntrance = root.findViewById(R.id.btnBlurEntrance);
        mBtnBlurEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), BlurActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
