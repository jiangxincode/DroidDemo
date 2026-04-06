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
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.DemosFragment;
import edu.jiangxin.droiddemo.HomeFragment;
import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.ToolsFragment;
import edu.jiangxin.droiddemo.adapter.MainActivityFragmentStateAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager2 mainActivityViewPager;
    private BottomNavigationView navigation;
    private MainActivityFragmentStateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(threadPolicy);

        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setVmPolicy(vmPolicy);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        View container = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(container, (v, insets) -> {
            int typeMask = WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout();
            Insets sysInsets = insets.getInsets(typeMask);
            v.setPadding(sysInsets.left, sysInsets.top, sysInsets.right, 0);
            return insets;
        });

        navigation = findViewById(R.id.navigation);
        ViewCompat.setOnApplyWindowInsetsListener(navigation, (v, insets) -> {
            Insets navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), navInsets.bottom);
            return insets;
        });

        mainActivityViewPager = findViewById(R.id.main_viewpager);

        adapter = new MainActivityFragmentStateAdapter(this);
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new ToolsFragment());
        adapter.addFragment(new DemosFragment());
        mainActivityViewPager.setAdapter(adapter);

        // Bidirectional sync: BottomNavigationView → ViewPager2
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    mainActivityViewPager.setCurrentItem(0, false);
                    return true;
                } else if (itemId == R.id.navigation_dashboard) {
                    mainActivityViewPager.setCurrentItem(1, false);
                    return true;
                } else if (itemId == R.id.navigation_notifications) {
                    mainActivityViewPager.setCurrentItem(2, false);
                    return true;
                }
                return false;
            }
        });

        // Bidirectional sync: ViewPager2 → BottomNavigationView
        mainActivityViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
            }
        });

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
