package org.jiangxin.edu.easymarry;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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

    private String[] name = {"找车", "找饭店", "找宾馆", "买车票"};

    private String[] desc = {"找车", "找饭店", "找宾馆", "买车票"};

    private int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

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

        mHomeListView = (ListView) findViewById(R.id.home_list_view);
        mTextMessage = (TextView) findViewById(R.id.message);


        viewGroup = (ViewGroup) mTextMessage.getParent();

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("head", images[i]);
            item.put("name", name[i]);
            item.put("desc", desc[i]);
            items.add(item);
        }

        /*SimpleAdapter的参数说明
         * 第一个参数 表示访问整个android应用程序接口，基本上所有的组件都需要
         * 第二个参数表示生成一个Map(String ,Object)列表选项
         * 第三个参数表示界面布局的id  表示该文件作为列表项的组件
         * 第四个参数表示该Map对象的哪些key对应value来生成列表项
         * 第五个参数表示来填充的组件 Map对象key对应的资源一依次填充组件 顺序有对应关系
         * 注意的是map对象可以key可以找不到 但组件的必须要有资源填充  因为 找不到key也会返回null 其实就相当于给了一个null资源
         * 下面的程序中如果 new String[] { "name", "head", "desc","name" } new int[] {R.id.name,R.id.head,R.id.desc,R.id.head}
         * 这个head的组件会被name资源覆盖
         * */
        simpleAdapter = new SimpleAdapter(this, items,
                R.layout.home_listview, new String[]{"name", "head", "desc"},
                new int[]{R.id.name, R.id.head, R.id.desc});

        mHomeListView.setAdapter(simpleAdapter);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


}
