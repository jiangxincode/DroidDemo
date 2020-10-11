package edu.jiangxin.droiddemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.activity.ActivityTrackerActivity;
import edu.jiangxin.droiddemo.activity.AppListActivity;
import edu.jiangxin.droiddemo.activity.AudioFxHistogramActivity;
import edu.jiangxin.droiddemo.activity.AudioFxOscillogramActivity;
import edu.jiangxin.droiddemo.activity.BlurActivity;
import edu.jiangxin.droiddemo.activity.DecorViewActivity;
import edu.jiangxin.droiddemo.activity.DialogActivity;
import edu.jiangxin.droiddemo.activity.ForbidScreenShotActivity;
import edu.jiangxin.droiddemo.activity.ImageViewActivity;
import edu.jiangxin.droiddemo.activity.JNIActivity;
import edu.jiangxin.droiddemo.activity.LoaderDemoActivity;
import edu.jiangxin.droiddemo.activity.NetMusicActivity;
import edu.jiangxin.droiddemo.activity.QuickSearchBoxActivity;
import edu.jiangxin.droiddemo.activity.RingtoneSetting1Activity;
import edu.jiangxin.droiddemo.activity.RingtoneSettingActivity;
import edu.jiangxin.droiddemo.activity.RippleActivity;
import edu.jiangxin.droiddemo.activity.ScaleTextActivity;
import edu.jiangxin.droiddemo.activity.SettingActivity;
import edu.jiangxin.droiddemo.layout.SimpleLayoutActivity;
import edu.jiangxin.droiddemo.activity.SpannableStringActivity;
import edu.jiangxin.droiddemo.activity.SpinnerActivity;
import edu.jiangxin.droiddemo.activity.ThemeActivity;
import edu.jiangxin.droiddemo.activity.ThreadDemoActivity;
import edu.jiangxin.droiddemo.activity.VariousNotificationActivity;
import edu.jiangxin.droiddemo.activity.VideoViewActivity;
import edu.jiangxin.droiddemo.activity.listview.ListViewActivity;
import edu.jiangxin.droiddemo.animation.VariousAnimationActivity;
import edu.jiangxin.droiddemo.fragment.FragmentActivity;
import edu.jiangxin.droiddemo.graphics.VariousGraphicsActivity;
import edu.jiangxin.droiddemo.layout.VariousLayoutActivity;
import edu.jiangxin.droiddemo.mediastore.MediaStoreDemoActivity;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;
import edu.jiangxin.droiddemo.roundcorner.VariousRoundCornerActivity;
import edu.jiangxin.droiddemo.saf.SAFActivity;
import edu.jiangxin.droiddemo.service.VariousServiceActivity;

/**
 * Created by jiang on 2018/1/21.
 */
public class NotificationsFragment extends Fragment {

    private static final String TAG = "NotificationsFragment";

    private static final int REQUEST_CODE_CALL_LOG = 10001;
    private static final int REQUEST_CODE_RECORD_AUDIO_1 = 10011;
    private static final int REQUEST_CODE_RECORD_AUDIO_2 = 10012;
    private static final int REQUEST_CODE_VIDEO_VIEW = 10020;
    private static final int REQUEST_CODE_MEDIA_STORE = 10021;
    private static final int REQUEST_CODE_OVERLAY = 10002;
    private static final int REQUEST_CODE_ACCESSIBILITY = 10003;

    private Button mBtnShowInfoEntrance, mBtnDecorViewEntrance, mBtnVariousLayoutEntrance, mBtnVariousServiceEntrance, mBtnListViewEntrance, mBtnDialogEntrance,
            mBtnRippleEntrance, mBtnRoundCornerEntrance, mBtnAnimationEntrance, mBtnGraphicsEntrance, mBtnBlurEntrance, mBtnForbidScreenShotEntrance, mBtnAppListEntrance, mBtnVariousNotificationEntrance,
            mBtnScaleTextEntrance, mBtnSpannableStringEntrance, mBtnGlobalSearchEntrance, mBtnImageViewEntrance,
            mBtnAudioFxDemoOscillogramEntrance, mBtnAudioFxDemoHistogramEntrance, mBtnNetMusicEntrance, mBtnVideoViewEntrance,
            mBtnJNIEntrance, mBtnActivityTrackerEntrance, mBtnSoundEntrance, mBtnSoundEntrance1, mBtnPreferenceEntrance, mBtnThemeEntrance, mBtnSpinnerEntrance,
            mFragmentEntrance, mSAFEntrance, mMediaStoreDemoEntrance, mThreadEntrance, mLoaderDemoEntrance;
    private View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mBtnShowInfoEntrance = root.findViewById(R.id.btnShowInfoEntrance);
        mBtnShowInfoEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ShowInfoActivity.class);
            startActivity(intent);
        });

        mBtnVariousLayoutEntrance = root.findViewById(R.id.btnVariousLayoutEntrance);
        mBtnVariousLayoutEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousLayoutActivity.class);
            startActivity(intent);
        });

        mBtnDecorViewEntrance = root.findViewById(R.id.btnDecorViewEntrance);
        mBtnDecorViewEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), DecorViewActivity.class);
            startActivity(intent);
        });

        mBtnVariousServiceEntrance = root.findViewById(R.id.btnVariousServiceEntrance);
        mBtnVariousServiceEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousServiceActivity.class);
            startActivity(intent);
        });

        mBtnListViewEntrance = root.findViewById(R.id.btnListViewEntrance);
        mBtnListViewEntrance.setOnClickListener(new View.OnClickListener() {

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
        mBtnRippleEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), RippleActivity.class);
            startActivity(intent);
        });

        mBtnRoundCornerEntrance = root.findViewById(R.id.btnRoundCornerEntrance);
        mBtnRoundCornerEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousRoundCornerActivity.class);
            startActivity(intent);
        });

        mBtnAnimationEntrance = root.findViewById(R.id.btnAnimationEntrance);
        mBtnAnimationEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousAnimationActivity.class);
            startActivity(intent);
        });

        mBtnGraphicsEntrance = root.findViewById(R.id.btnGraphicsEntrance);
        mBtnGraphicsEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousGraphicsActivity.class);
            startActivity(intent);
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
                List<String> permissions = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.RECORD_AUDIO);
                    }

                    if (permissions.isEmpty()) {
                        startAudioFxOscillogramActivity();
                    } else {
                        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_RECORD_AUDIO_1);
                    }
                } else {
                    startAudioFxOscillogramActivity();
                }
            }
        });

        mBtnAudioFxDemoHistogramEntrance = root.findViewById(R.id.btnAudioFxDemoHistogramEntrance);
        mBtnAudioFxDemoHistogramEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<String> permissions = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.RECORD_AUDIO);
                    }

                    if (permissions.isEmpty()) {
                        startAudioFxHistogramActivity();
                    } else {
                        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_RECORD_AUDIO_2);
                    }
                } else {
                    startAudioFxHistogramActivity();
                }
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
                List<String> permissions = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }

                    if (permissions.isEmpty()) {
                        startVideoViewActivity();
                    } else {
                        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_VIDEO_VIEW);
                    }
                } else {
                    startVideoViewActivity();
                }

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {
                    startActivityForResult(
                            new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()))
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                            REQUEST_CODE_OVERLAY
                    );
                } else {
                    startTrackerActivity();
                }

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

        mMediaStoreDemoEntrance = root.findViewById(R.id.btnMediaStoreDemoEntrance);
        mMediaStoreDemoEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<String> permissions = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }

                    if (permissions.isEmpty()) {
                        startMediaStoreDemoActivity();
                    } else {
                        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_STORE);
                    }
                } else {
                    startMediaStoreDemoActivity();
                }

            }
        });

        mThreadEntrance = root.findViewById(R.id.btnThreadEntrance);
        mThreadEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ThreadDemoActivity.class);
                startActivity(intent);
            }
        });

        mLoaderDemoEntrance = root.findViewById(R.id.btnLoaderDemoEntrance);
        mLoaderDemoEntrance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                List<String> permissions = new ArrayList<String>();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.READ_CALL_LOG);
                    }

                    if (getActivity().checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.WRITE_CALL_LOG);
                    }

                    if (permissions.isEmpty()) {
                        startLoaderDemoActivity();
                    } else {
                        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_CALL_LOG);
                    }
                } else {
                    startLoaderDemoActivity();
                }

            }
        });

        return root;
    }

    private void startMediaStoreDemoActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), MediaStoreDemoActivity.class);
        startActivity(intent);
    }

    private void startVideoViewActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), VideoViewActivity.class);
        startActivity(intent);
    }


    private void startAudioFxOscillogramActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), AudioFxOscillogramActivity.class);
        startActivity(intent);
    }

    private void startAudioFxHistogramActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), AudioFxHistogramActivity.class);
        startActivity(intent);
    }

    private void startTrackerActivity() {
        if (isAccessibilitySettingsOn(this.getContext())) {
            Intent intent = new Intent();
            intent.setClass(getContext(), ActivityTrackerActivity.class);
            startActivity(intent);
        } else {
            // 引导至辅助功能设置页面
            startActivityForResult(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), REQUEST_CODE_ACCESSIBILITY
            );
        }

    }

    private void startLoaderDemoActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), LoaderDemoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CALL_LOG:
                if (checkPermissionRequested(grantResults)) {
                    startLoaderDemoActivity();
                } else {
                    Toast.makeText(this.getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_RECORD_AUDIO_1:
                if (checkPermissionRequested(grantResults)) {
                    startAudioFxOscillogramActivity();
                } else {
                    Toast.makeText(this.getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_RECORD_AUDIO_2:
                if (checkPermissionRequested(grantResults)) {
                    startAudioFxHistogramActivity();
                } else {
                    Toast.makeText(this.getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_VIDEO_VIEW:
                if (checkPermissionRequested(grantResults)) {
                    startVideoViewActivity();
                } else {
                    Toast.makeText(this.getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_MEDIA_STORE:
                if (checkPermissionRequested(grantResults)) {
                    startMediaStoreDemoActivity();
                } else {
                    Toast.makeText(this.getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OVERLAY) {
            if (Settings.canDrawOverlays(getActivity())) {
                startTrackerActivity();
                return;
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                return;
            }
        } else if (requestCode == REQUEST_CODE_ACCESSIBILITY) {
            if (isAccessibilitySettingsOn(getContext())) {
                startTrackerActivity();
                return;
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private boolean checkPermissionRequested(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, "get setting failed: " + Settings.Secure.ACCESSIBILITY_ENABLED);
            return true;
        }

        Log.i(TAG, "accessibilityEnabled : " + accessibilityEnabled);

        if (accessibilityEnabled != 1) {
            return false;
        }

        String services = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (services == null) {
            Log.e(TAG, "services is null");
            return false;
        }
        return services.toLowerCase().contains(context.getPackageName().toLowerCase());
    }


}
