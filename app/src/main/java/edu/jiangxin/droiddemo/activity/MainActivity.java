package edu.jiangxin.droiddemo.activity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.DemosFragment;
import edu.jiangxin.droiddemo.HomeFragment;
import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.ToolsFragment;
import edu.jiangxin.droiddemo.adapter.MainActivityViewPagerAdapter;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mainActivityViewPager;
    private BottomNavigationView navigation;
    private MainActivityViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(threadPolicy);

        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setVmPolicy(vmPolicy);

        setContentView(R.layout.activity_main);

        View container = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
            int typeMask = WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout();
            Insets sysInsets = insets.getInsets(typeMask);
            v.setPadding(sysInsets.left, sysInsets.top, sysInsets.right, sysInsets.bottom);
            return insets;
        });

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    mainActivityViewPager.setCurrentItem(0);
                    return true;
                } else if (itemId == R.id.navigation_dashboard) {
                    mainActivityViewPager.setCurrentItem(1);
                    return true;
                } else if (itemId == R.id.navigation_notifications) {
                    mainActivityViewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

        mainActivityViewPager = findViewById(R.id.main_viewpager);

        mainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new ToolsFragment());
        adapter.addFragment(new DemosFragment());
        mainActivityViewPager.setAdapter(adapter);

        setupShortcuts();

    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        StrictMode.enableDefaults();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    private void setupShortcuts() {
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);

        List<ShortcutInfo> shortcutInfos = new ArrayList<>();
        String[] name = {"找车", "找饭店", "找宾馆", "买车票"};
        for (int i = 0; i < mShortcutManager.getMaxShortcutCountPerActivity(); i++) {
            Intent intent = new Intent(this, ShortcutTargetActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("shortcutItem", name[i % 4]);

            ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + i)
                    .setShortLabel(name[i % 4])
                    .setLongLabel("功能:" + name[i % 4])
                    .setIcon(Icon.createWithResource(this, R.drawable.icon))
                    .setIntent(intent)
                    .build();
            shortcutInfos.add(info);
        }

        mShortcutManager.setDynamicShortcuts(shortcutInfos);
    }


}
