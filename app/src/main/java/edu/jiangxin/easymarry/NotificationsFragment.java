package edu.jiangxin.easymarry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.activity.ActivityTrackerActivity;
import edu.jiangxin.easymarry.activity.AppListActivity;
import edu.jiangxin.easymarry.activity.AudioFxHistogramActivity;
import edu.jiangxin.easymarry.activity.AudioFxOscillogramActivity;
import edu.jiangxin.easymarry.activity.BlurActivity;
import edu.jiangxin.easymarry.activity.DecorViewActivity;
import edu.jiangxin.easymarry.activity.DialogActivity;
import edu.jiangxin.easymarry.activity.ForbidScreenShotActivity;
import edu.jiangxin.easymarry.activity.FriendsListActivity;
import edu.jiangxin.easymarry.activity.ImageViewActivity;
import edu.jiangxin.easymarry.activity.JNIActivity;
import edu.jiangxin.easymarry.activity.MessengerActivity;
import edu.jiangxin.easymarry.activity.NetMusicActivity;
import edu.jiangxin.easymarry.activity.QuickSearchBoxActivity;
import edu.jiangxin.easymarry.activity.RingtoneSetting1Activity;
import edu.jiangxin.easymarry.activity.RingtoneSettingActivity;
import edu.jiangxin.easymarry.activity.RippleActivity;
import edu.jiangxin.easymarry.activity.ScaleTextActivity;
import edu.jiangxin.easymarry.activity.SettingActivity;
import edu.jiangxin.easymarry.fragment.FragmentActivity;
import edu.jiangxin.easymarry.quickshow.activity.ShowInfoActivity;
import edu.jiangxin.easymarry.activity.SpannableStringActivity;
import edu.jiangxin.easymarry.activity.SpinnerActivity;
import edu.jiangxin.easymarry.activity.ThemeActivity;
import edu.jiangxin.easymarry.activity.VariousNotificationActivity;
import edu.jiangxin.easymarry.activity.VideoViewActivity;
import edu.jiangxin.easymarry.activity.listview.ListViewActivity;
import edu.jiangxin.easymarry.saf.SAFActivity;

/**
 * Created by jiang on 2018/1/21.
 */

public class NotificationsFragment extends Fragment {

    private static final String TAG = "NotificationsFragment";

    private Button mBtnShowInfoEntrance, mBtnDecorViewEntrance, mBtn13, mBtn14, mBtnDialogEntrance,
            mBtnRippleEntrance, mBtnBlurEntrance, mBtnForbidScreenShotEntrance, mBtnAppListEntrance, mBtnVariousNotificationEntrance,
            mBtnScaleTextEntrance, mBtnSearchEntrance, mBtnSpannableStringEntrance, mBtnGlobalSearchEntrance, mBtnImageViewEntrance,
            mBtnAudioFxDemoOscillogramEntrance, mBtnAudioFxDemoHistogramEntrance, mBtnNetMusicEntrance, mBtnVideoViewEntrance,
            mBtnJNIEntrance, mBtnActivityTrackerEntrance, mBtnSoundEntrance, mBtnSoundEntrance1, mBtnPreferenceEntrance, mBtnThemeEntrance, mBtnSpinnerEntrance,
            mFragmentEntrance, mSAFEntrance;
    private View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mBtnShowInfoEntrance = root.findViewById(R.id.btnShowInfoEntrance);
        mBtnShowInfoEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ShowInfoActivity.class);
                startActivity(intent);
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

        mBtnForbidScreenShotEntrance = root.findViewById(R.id.btnForbidScreenShotEntrance);
        mBtnForbidScreenShotEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ForbidScreenShotActivity.class);
                startActivity(intent);
            }
        });

        mBtnAppListEntrance = root.findViewById(R.id.btnAppListEntrance);
        mBtnAppListEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AppListActivity.class);
                startActivity(intent);
            }
        });

        mBtnVariousNotificationEntrance = root.findViewById(R.id.btnVariousNotificationEntrance);
        mBtnVariousNotificationEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), VariousNotificationActivity.class);
                startActivity(intent);
            }
        });

        mBtnScaleTextEntrance = root.findViewById(R.id.btnScaleTextEntrance);
        mBtnScaleTextEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ScaleTextActivity.class);
                startActivity(intent);
            }
        });

        mBtnSearchEntrance = root.findViewById(R.id.btnSearchEntrance);
        mBtnSearchEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), FriendsListActivity.class);
                startActivity(intent);
            }
        });

        mBtnSpannableStringEntrance = root.findViewById(R.id.btnSpannableStringEntrance);
        mBtnSpannableStringEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SpannableStringActivity.class);
                startActivity(intent);
            }
        });

        mBtnGlobalSearchEntrance = root.findViewById(R.id.btnGlobalSearchEntrance);
        mBtnGlobalSearchEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), QuickSearchBoxActivity.class);
                startActivity(intent);
            }
        });

        mBtnImageViewEntrance = root.findViewById(R.id.btnImageViewEntrance);
        mBtnImageViewEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ImageViewActivity.class);
                startActivity(intent);
            }
        });

        mBtnAudioFxDemoOscillogramEntrance = root.findViewById(R.id.btnAudioFxDemoOscillogramEntrance);
        mBtnAudioFxDemoOscillogramEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AudioFxOscillogramActivity.class);
                startActivity(intent);
            }
        });

        mBtnAudioFxDemoHistogramEntrance = root.findViewById(R.id.btnAudioFxDemoHistogramEntrance);
        mBtnAudioFxDemoHistogramEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AudioFxHistogramActivity.class);
                startActivity(intent);
            }
        });

        mBtnNetMusicEntrance = root.findViewById(R.id.btnNetMusicEntrance);
        mBtnNetMusicEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), NetMusicActivity.class);
                startActivity(intent);
            }
        });

        mBtnVideoViewEntrance = root.findViewById(R.id.btnVideoViewEntrance);
        mBtnVideoViewEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), VideoViewActivity.class);
                startActivity(intent);
            }
        });

        mBtnJNIEntrance = root.findViewById(R.id.btnJNIEntrance);
        mBtnJNIEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), JNIActivity.class);
                startActivity(intent);
            }
        });

        mBtnActivityTrackerEntrance = root.findViewById(R.id.btnActivityTrackerEntrance);
        mBtnActivityTrackerEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivityTrackerActivity.class);
                startActivity(intent);
            }
        });

        mBtnSoundEntrance = root.findViewById(R.id.btnSoundEntrance);
        mBtnSoundEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), RingtoneSettingActivity.class);
                startActivity(intent);
            }
        });

        mBtnSoundEntrance1 = root.findViewById(R.id.btnSoundEntrance1);
        mBtnSoundEntrance1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), RingtoneSetting1Activity.class);
                startActivity(intent);
            }
        });

        mBtnPreferenceEntrance = root.findViewById(R.id.btnPreferenceEntrance);
        mBtnPreferenceEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        mBtnThemeEntrance = root.findViewById(R.id.btnThemeEntrance);
        mBtnThemeEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ThemeActivity.class);
                startActivity(intent);
            }
        });

        mBtnSpinnerEntrance = root.findViewById(R.id.btnSpinnerEntrance);
        mBtnSpinnerEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SpinnerActivity.class);
                startActivity(intent);
            }
        });

        mFragmentEntrance = root.findViewById(R.id.btnFragmentEntrance);
        mFragmentEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), FragmentActivity.class);
                startActivity(intent);
            }
        });

        mSAFEntrance = root.findViewById(R.id.btnSAFEntrance);
        mSAFEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SAFActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
