package edu.jiangxin.droiddemo.activity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.ToolsFragment;
import edu.jiangxin.droiddemo.DemosFragment;
import edu.jiangxin.droiddemo.HomeFragment;
import edu.jiangxin.droiddemo.adapter.MainActivityViewPagerAdapter;
import edu.jiangxin.droiddemo.R;

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

        ActionBar actionBar = getSupportActionBar();

        //是否显示对应图标
        /*actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);*/

        //显示返回图标
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 显示标题
        actionBar.setDisplayShowTitleEnabled(true);


        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home: {

                        mainActivityViewPager.setCurrentItem(0);

                        return true;
                    }
                    case R.id.navigation_dashboard:

                        mainActivityViewPager.setCurrentItem(1);

                        return true;
                    case R.id.navigation_notifications:

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_main, menu);

        MenuItem home = menu.findItem(R.id.navigation_home);
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mainActivityViewPager.setCurrentItem(0);
                return true;
            }
        });

        MenuItem dashboard = menu.findItem(R.id.navigation_dashboard);
        dashboard.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mainActivityViewPager.setCurrentItem(1);
                return true;
            }
        });

        MenuItem notifications = menu.findItem(R.id.navigation_notifications);
        notifications.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mainActivityViewPager.setCurrentItem(2);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
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
