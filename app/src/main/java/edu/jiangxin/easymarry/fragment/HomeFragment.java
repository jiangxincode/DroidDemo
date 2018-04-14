package edu.jiangxin.easymarry.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import edu.jiangxin.easymarry.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiang on 2018/1/21.
 */

public class HomeFragment extends Fragment {

    private ListView mHomeListView;

    private SimpleAdapter simpleAdapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mHomeListView = root.findViewById(R.id.home_list_view);

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

        simpleAdapter = new SimpleAdapter(getActivity(), data,
                R.layout.fragment_home_listview_item, new String[]{"name", "head", "desc"},
                new int[]{R.id.name, R.id.head, R.id.desc});

        mHomeListView.setAdapter(simpleAdapter);

        return root;
    }
}
