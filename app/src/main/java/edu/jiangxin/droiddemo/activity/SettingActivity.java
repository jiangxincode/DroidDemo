package edu.jiangxin.droiddemo.activity;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.BaseAdapter;

import edu.jiangxin.droiddemo.R;

@SuppressWarnings("deprecation")
public class SettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener{
    CheckBoxPreference mCheckBoxPreference;
    EditTextPreference mEditTextPreference;
    BaseAdapter baseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        baseAdapter = (BaseAdapter)preferenceScreen.getRootAdapter();
        mCheckBoxPreference = (CheckBoxPreference)preferenceScreen.findPreference("boolean_value");
        mEditTextPreference = (EditTextPreference)preferenceScreen.findPreference("string_value");
        mCheckBoxPreference.setOnPreferenceChangeListener(this);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    /**
     * 显示当前设置
     */
    private void refresh() {
        boolean booleanValue = PreferenceManager.getDefaultSharedPreferences(
                this).getBoolean("boolean_value", false);
        mCheckBoxPreference.setChecked(booleanValue);
        String stringValue = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("string_value", "");
        mEditTextPreference.setText(stringValue);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if ("boolean_value".equals(preference.getKey())) {
            mCheckBoxPreference.setChecked((boolean)newValue);
        } else if ("string_value".equals(preference.getKey())) {
            mEditTextPreference.setText((String)newValue);
        }
        baseAdapter.notifyDataSetChanged();
        return true;
    }
}
