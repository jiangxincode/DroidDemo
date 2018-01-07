package org.jiangxin.edu.easymarry;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private ListView mHomeListView;

    private ViewGroup viewGroup;

    private SimpleAdapter simpleAdapter = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {

                    if (mTextMessage.getParent() == viewGroup) {
                        viewGroup.removeView(mTextMessage);
                    }

                    if (mHomeListView.getParent() != viewGroup) {
                        viewGroup.addView(mHomeListView);
                    }

                    return true;
                }
                case R.id.navigation_dashboard:

                    if (mHomeListView.getParent() == viewGroup) {
                        viewGroup.removeView(mHomeListView);
                    }

                    if (mTextMessage.getParent() != viewGroup) {
                        viewGroup.addView(mTextMessage);
                    }

                    mTextMessage.setText(R.string.title_dashboard);

                    return true;
                case R.id.navigation_notifications:

                    if (mHomeListView.getParent() == viewGroup) {
                        viewGroup.removeView(mHomeListView);
                    }

                    if (mTextMessage.getParent() != viewGroup) {
                        viewGroup.addView(mTextMessage);
                    }

                    mTextMessage.setText(R.string.title_notifications);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        mHomeListView = (ListView) findViewById(R.id.home_list_view);
        mTextMessage = (TextView) findViewById(R.id.message);


        viewGroup = (ViewGroup) mTextMessage.getParent();

        String[] name = {"找车", "找饭店", "找宾馆", "买车票"};
        String[] desc = {"找车", "找饭店", "找宾馆", "买车票"};
        int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("head", images[i]);
            item.put("name", name[i]);
            item.put("desc", desc[i]);
            data.add(item);
        }

        simpleAdapter = new SimpleAdapter(this, data,
                R.layout.home_listview, new String[]{"name", "head", "desc"},
                new int[]{R.id.name, R.id.head, R.id.desc});

        mHomeListView.setAdapter(simpleAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
