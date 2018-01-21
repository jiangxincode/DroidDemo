package edu.jiangxin.easymarry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import edu.jiangxin.easymarry.adapter.MainActivityViewPagerAdapter;
import edu.jiangxin.easymarry.fragment.DashboardFragment;
import edu.jiangxin.easymarry.fragment.HomeFragment;
import edu.jiangxin.easymarry.fragment.NotificationsFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mainActivityViewPager;
    private BottomNavigationView navigation;
    private MainActivityViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        //是否显示对应图标
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        //显示返回图标
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 显示标题
        actionBar.setDisplayShowTitleEnabled(true);


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
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

        mainActivityViewPager = (ViewPager) findViewById(R.id.main_viewpager);

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
        adapter.addFragment(new DashboardFragment());
        adapter.addFragment(new NotificationsFragment());
        mainActivityViewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }


}
