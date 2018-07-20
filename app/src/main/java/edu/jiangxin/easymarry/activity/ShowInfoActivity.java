package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.runable.OthersInfoRunnable;
import edu.jiangxin.easymarry.runable.PackageInfoRunable;
import edu.jiangxin.easymarry.runable.ScreenInfoRunnable;
import edu.jiangxin.easymarry.runable.StatInfoRunnable;
import edu.jiangxin.easymarry.runable.StorageInfoRunnable;
import edu.jiangxin.easymarry.runable.UserInfoRunnable;
import edu.jiangxin.easymarry.view.PullDownTextView;

public class ShowInfoActivity extends Activity {

    private static final String TAG = "ShowInfoActivity";

    public static final int UPDATE_MESSAGE = 1;

    private ListView lv;

    private ShowInfoAdapter adapter;
    private List<ShowInfoContent> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        menuList = new ArrayList<ShowInfoContent>();

        new Thread(new StorageInfoRunnable(mHandler)).run();
        new Thread(new OthersInfoRunnable(mHandler)).run();
        new Thread(new ScreenInfoRunnable(mHandler, this)).run();
        new Thread(new StatInfoRunnable(mHandler)).run();
        new Thread(new UserInfoRunnable(mHandler)).run();
        new Thread(new PackageInfoRunable(mHandler)).run();

        lv = findViewById(R.id.lv_show_info);

        adapter = new ShowInfoAdapter(this, menuList);
        lv.setAdapter(adapter);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE:
                    Bundle bundle = msg.getData();
                    menuList.add(new ShowInfoContent(bundle.getString("title"), bundle.getString("content")));
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    class ShowInfoAdapter extends BaseAdapter {

        private List<ShowInfoContent> mMenuList;
        private Context mContext;

        public ShowInfoAdapter(Context context, List<ShowInfoContent> mMenuList) {
            this.mContext = context;
            this.mMenuList = mMenuList;
        }

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public ShowInfoContent getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            PullDownTextView pullDownTextView;

            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_show_info_item, null);
                pullDownTextView = view.findViewById(R.id.expand_text_view);
                view.setTag(pullDownTextView);
            } else {
                pullDownTextView = (PullDownTextView) view.getTag();
            }
            pullDownTextView.setTitleText(mMenuList.get(position).title);
            pullDownTextView.setContentText(mMenuList.get(position).content);
            return view;
        }
    }

    class ShowInfoContent {
        public ShowInfoContent(String title, String content) {
            this.title = title;
            this.content = content;
        }

        String title;
        String content;
    }
}


