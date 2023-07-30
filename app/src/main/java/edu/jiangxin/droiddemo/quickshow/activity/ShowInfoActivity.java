package edu.jiangxin.droiddemo.quickshow.activity;

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
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.quickshow.runable.OthersInfoRunnable;
import edu.jiangxin.droiddemo.quickshow.runable.PackageInfoRunable;
import edu.jiangxin.droiddemo.quickshow.runable.ScreenInfoRunnable;
import edu.jiangxin.droiddemo.quickshow.runable.StorageInfoRunnable;
import edu.jiangxin.droiddemo.quickshow.runable.UserInfoRunnable;
import edu.jiangxin.droiddemo.quickshow.view.PullDownTextView;
import edu.jiangxin.droiddemo.R;

public class ShowInfoActivity extends Activity {

    private static final String TAG = "ShowInfoActivity";

    private static final int ITEM_NUM = 6;

    public static final int UPDATE_MESSAGE = 1;

    private ListView lv;

    private ShowInfoAdapter adapter;
    private List<ShowInfoContent> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        lv = findViewById(R.id.lv_show_info);
        menuList = new ArrayList<ShowInfoContent>();
        adapter = new ShowInfoAdapter(this, menuList, ITEM_NUM);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // do not invoke run method, use start instead
        new Thread(new StorageInfoRunnable(mHandler)).start();
        new Thread(new OthersInfoRunnable(mHandler)).start();
        new Thread(new ScreenInfoRunnable(mHandler)).start();
        new Thread(new UserInfoRunnable(mHandler)).start();
        new Thread(new PackageInfoRunable(mHandler)).start();
        adapter.notifyDataSetChanged();

    }

    private final Handler mHandler = new Handler() {

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

        private final List<ShowInfoContent> mMenuList;
        private final Context mContext;
        private final int mNum;

        public ShowInfoAdapter(Context context, List<ShowInfoContent> mMenuList, int num) {
            this.mContext = context;
            this.mMenuList = mMenuList;
            this.mNum = num;
        }

        @Override
        public int getCount() {
            return mNum;
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

            Holder holder;

            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_show_info_item, null);
                holder = new Holder();
                holder.pullDownTextView = view.findViewById(R.id.expand_text_view);
                holder.progressBar = view.findViewById(R.id.progressBar);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            if (position >= mMenuList.size()) {
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.pullDownTextView.setVisibility(View.GONE);
            } else {
                ShowInfoContent content = mMenuList.get(position);
                holder.progressBar.setVisibility(View.GONE);
                holder.pullDownTextView.setVisibility(View.VISIBLE);
                holder.pullDownTextView.setTitleText(content.title);
                holder.pullDownTextView.setContentText(content.content);
            }


            return view;
        }
    }

    class Holder {
        PullDownTextView pullDownTextView;
        ProgressBar progressBar;
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


