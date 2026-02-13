package edu.jiangxin.droiddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

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
import edu.jiangxin.droiddemo.activity.PerfActivity;
import edu.jiangxin.droiddemo.activity.QuickSearchBoxActivity;
import edu.jiangxin.droiddemo.activity.RingtoneSetting1Activity;
import edu.jiangxin.droiddemo.activity.RingtoneSettingActivity;
import edu.jiangxin.droiddemo.activity.RippleActivity;
import edu.jiangxin.droiddemo.activity.ScaleTextActivity;
import edu.jiangxin.droiddemo.activity.SettingActivity;
import edu.jiangxin.droiddemo.activity.SpannableStringActivity;
import edu.jiangxin.droiddemo.activity.SpinnerActivity;
import edu.jiangxin.droiddemo.activity.ThreadDemoActivity;
import edu.jiangxin.droiddemo.activity.VariousNotificationActivity;
import edu.jiangxin.droiddemo.activity.VideoViewActivity;
import edu.jiangxin.droiddemo.activity.listview.ListViewActivity;
import edu.jiangxin.droiddemo.activitytracker.ActivityTrackerActivity;
import edu.jiangxin.droiddemo.animation.VariousAnimationActivity;
import edu.jiangxin.droiddemo.applist.AppListActivity;
import edu.jiangxin.droiddemo.fragment.FragmentActivity;
import edu.jiangxin.droiddemo.ime.ImeActivity;
import edu.jiangxin.droiddemo.layout.VariousLayoutActivity;
import edu.jiangxin.droiddemo.mediastore.MediaStoreDemoActivity;
import edu.jiangxin.droiddemo.note.NoteActivity;
import edu.jiangxin.droiddemo.opengl.OpenGlEntranceActivity;
import edu.jiangxin.droiddemo.predictiveback.PbMainActivity;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;
import edu.jiangxin.droiddemo.roundcorner.VariousRoundCornerActivity;
import edu.jiangxin.droiddemo.saf.SafActivity;
import edu.jiangxin.droiddemo.service.VariousServiceActivity;
import edu.jiangxin.droiddemo.speechrecognize.SpeechRecognizeActivity;
import edu.jiangxin.droiddemo.tv.recycleview.RecycleViewTv;
import edu.jiangxin.droiddemo.usagestats.UsageStatsActivity;
import edu.jiangxin.droiddemo.vibrator.VibratorActivity;
import edu.jiangxin.droiddemo.activity.SystemShareActivity;

/**
 * Created by jiang on 2018/1/21.
 */
@SuppressWarnings("deprecation")
public class DemosFragment extends Fragment {

    private static final String TAG = "DemosFragment";

    private static final int REQUEST_CODE_CALL_LOG = 10001;
    private static final int REQUEST_CODE_RECORD_AUDIO_1 = 10011;
    private static final int REQUEST_CODE_RECORD_AUDIO_2 = 10012;
    private static final int REQUEST_CODE_VIDEO_VIEW = 10020;
    private static final int REQUEST_CODE_MEDIA_STORE = 10021;

    private static final String[] MEDIA_PERMISSIONS;

    static {
        MEDIA_PERMISSIONS = new String[]{
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demos, container, false);

        Button btnPredictiveBack = root.findViewById(R.id.btnPredictiveBack);
        btnPredictiveBack.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), PbMainActivity.class);
            startActivity(intent);
        });

        Button btnUsageStats = root.findViewById(R.id.btnUsageStats);
        btnUsageStats.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), UsageStatsActivity.class);
            startActivity(intent);
        });

        Button btnNote = root.findViewById(R.id.btnNote);
        btnNote.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), NoteActivity.class);
            startActivity(intent);
        });

        Button btnPerf = root.findViewById(R.id.btnPerf);
        btnPerf.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), PerfActivity.class);
            startActivity(intent);
        });

        Button btnShowInfoEntrance = root.findViewById(R.id.btnShowInfoEntrance);
        btnShowInfoEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ShowInfoActivity.class);
            startActivity(intent);
        });

        Button btnVariousLayoutEntrance = root.findViewById(R.id.btnVariousLayoutEntrance);
        btnVariousLayoutEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousLayoutActivity.class);
            startActivity(intent);
        });

        Button btnDecorViewEntrance = root.findViewById(R.id.btnDecorViewEntrance);
        btnDecorViewEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), DecorViewActivity.class);
            startActivity(intent);
        });

        Button btnVariousServiceEntrance = root.findViewById(R.id.btnVariousServiceEntrance);
        btnVariousServiceEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousServiceActivity.class);
            startActivity(intent);
        });

        Button btnListViewEntrance = root.findViewById(R.id.btnListViewEntrance);
        btnListViewEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ListViewActivity.class);
            startActivity(intent);
        });

        Button btnDialogEntrance = root.findViewById(R.id.btnDialogEntrance);
        btnDialogEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), DialogActivity.class);
            startActivity(intent);
        });

        Button btnRippleEntrance = root.findViewById(R.id.btnRippleEntrance);
        btnRippleEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), RippleActivity.class);
            startActivity(intent);
        });

        Button btnRoundCornerEntrance = root.findViewById(R.id.btnRoundCornerEntrance);
        btnRoundCornerEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousRoundCornerActivity.class);
            startActivity(intent);
        });

        Button btnAnimationEntrance = root.findViewById(R.id.btnAnimationEntrance);
        btnAnimationEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousAnimationActivity.class);
            startActivity(intent);
        });

        Button btnOpenglEntrance = root.findViewById(R.id.btnOpenglEntrance);
        btnOpenglEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), OpenGlEntranceActivity.class);
            startActivity(intent);
        });

        Button btnBlurEntrance = root.findViewById(R.id.btnBlurEntrance);
        btnBlurEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), BlurActivity.class);
            startActivity(intent);
        });

        Button btnForbidScreenShotEntrance = root.findViewById(R.id.btnForbidScreenShotEntrance);
        btnForbidScreenShotEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ForbidScreenShotActivity.class);
            startActivity(intent);
        });

        Button btnAppListEntrance = root.findViewById(R.id.btnAppListEntrance);
        btnAppListEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), AppListActivity.class);
            startActivity(intent);
        });

        Button btnVariousNotificationEntrance = root.findViewById(R.id.btnVariousNotificationEntrance);
        btnVariousNotificationEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VariousNotificationActivity.class);
            startActivity(intent);
        });

        Button btnScaleTextEntrance = root.findViewById(R.id.btnScaleTextEntrance);
        btnScaleTextEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScaleTextActivity.class);
            startActivity(intent);
        });

        Button btnSpannableStringEntrance = root.findViewById(R.id.btnSpannableStringEntrance);
        btnSpannableStringEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SpannableStringActivity.class);
            startActivity(intent);
        });

        Button btnGlobalSearchEntrance = root.findViewById(R.id.btnGlobalSearchEntrance);
        btnGlobalSearchEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), QuickSearchBoxActivity.class);
            startActivity(intent);
        });

        Button btnImageViewEntrance = root.findViewById(R.id.btnImageViewEntrance);
        btnImageViewEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ImageViewActivity.class);
            startActivity(intent);
        });

        Button btnAudioFxDemoOscillogramEntrance = root.findViewById(R.id.btnAudioFxDemoOscillogramEntrance);
        btnAudioFxDemoOscillogramEntrance.setOnClickListener(v -> {
            List<String> permissions = new ArrayList<String>();
            if (getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }

            if (permissions.isEmpty()) {
                startAudioFxOscillogramActivity();
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_RECORD_AUDIO_1);
            }
        });

        Button btnAudioFxDemoHistogramEntrance = root.findViewById(R.id.btnAudioFxDemoHistogramEntrance);
        btnAudioFxDemoHistogramEntrance.setOnClickListener(v -> {
            List<String> permissions = new ArrayList<String>();
            if (getActivity().checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }

            if (permissions.isEmpty()) {
                startAudioFxHistogramActivity();
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_RECORD_AUDIO_2);
            }
        });

        Button btnNetMusicEntrance = root.findViewById(R.id.btnNetMusicEntrance);
        btnNetMusicEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), NetMusicActivity.class);
            startActivity(intent);
        });

        Button btnVideoViewEntrance = root.findViewById(R.id.btnVideoViewEntrance);
        btnVideoViewEntrance.setOnClickListener(v -> {
            List<String> permissions = new ArrayList<>();
            for (String permission : MEDIA_PERMISSIONS) {
                if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(permission);
                }
            }
            if (permissions.isEmpty()) {
                startVideoViewActivity();
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_STORE);
            }
        });

        Button btnJNIEntrance = root.findViewById(R.id.btnJNIEntrance);
        btnJNIEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), JNIActivity.class);
            startActivity(intent);
        });

        Button btnActivityTrackerEntrance = root.findViewById(R.id.btnActivityTrackerEntrance);
        btnActivityTrackerEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ActivityTrackerActivity.class);
            startActivity(intent);
        });

        Button btnSpeechRecognizeEntrance = root.findViewById(R.id.btnSpeechRecognizeEntrance);
        btnSpeechRecognizeEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SpeechRecognizeActivity.class);
            startActivity(intent);
        });

        Button btnSoundEntrance = root.findViewById(R.id.btnSoundEntrance);
        btnSoundEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), RingtoneSettingActivity.class);
            startActivity(intent);
        });

        Button btnSoundEntrance1 = root.findViewById(R.id.btnSoundEntrance1);
        btnSoundEntrance1.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), RingtoneSetting1Activity.class);
            startActivity(intent);
        });

        Button btnPreferenceEntrance = root.findViewById(R.id.btnPreferenceEntrance);
        btnPreferenceEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SettingActivity.class);
            startActivity(intent);
        });

        Button btnSpinnerEntrance = root.findViewById(R.id.btnSpinnerEntrance);
        btnSpinnerEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SpinnerActivity.class);
            startActivity(intent);
        });

        Button btnFragmentEntrance = root.findViewById(R.id.btnFragmentEntrance);
        btnFragmentEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), FragmentActivity.class);
            startActivity(intent);
        });

        Button btnSAFEntrance = root.findViewById(R.id.btnSAFEntrance);
        btnSAFEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SafActivity.class);
            startActivity(intent);
        });

        Button btnMediaStoreDemoEntrance = root.findViewById(R.id.btnMediaStoreDemoEntrance);
        btnMediaStoreDemoEntrance.setOnClickListener(v -> {
            List<String> permissions = new ArrayList<>();
            for (String permission : MEDIA_PERMISSIONS) {
                if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(permission);
                }
            }
            if (permissions.isEmpty()) {
                startMediaStoreDemoActivity();
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_STORE);
            }
        });

        Button btnThreadEntrance = root.findViewById(R.id.btnThreadEntrance);
        btnThreadEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ThreadDemoActivity.class);
            startActivity(intent);
        });

        Button btnLoaderDemoEntrance = root.findViewById(R.id.btnLoaderDemoEntrance);
        btnLoaderDemoEntrance.setOnClickListener(v -> {

            List<String> permissions = new ArrayList<>();
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

        });

        Button btnRecycleViewTvEntrance = root.findViewById(R.id.btnRecycleViewTvEntrance);
        btnRecycleViewTvEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), RecycleViewTv.class);
            startActivity(intent);
        });

        Button btnVibratorEntrance = root.findViewById(R.id.btnVibratorEntrance);
        btnVibratorEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), VibratorActivity.class);
            startActivity(intent);
        });

        Button btnSensorEntrance = root.findViewById(R.id.btnSensorEntrance);
        btnSensorEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SensorActivity.class);
            startActivity(intent);
        });

        Button btnImeEntrance = root.findViewById(R.id.btnImeEntrance);
        btnImeEntrance.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), ImeActivity.class);
            startActivity(intent);
        });

        Button btnSystemShare = root.findViewById(R.id.btnSystemShare);
        btnSystemShare.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), SystemShareActivity.class);
            startActivity(intent);
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

    private boolean checkPermissionRequested(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
