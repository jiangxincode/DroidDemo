package edu.jiangxin.droiddemo.applist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.view.IndexableListView;

public class AppListActivity extends AppCompatActivity implements SectionIndexer {

    private Context mContext;
    private View mProgressBarView;
    private ListView mAppListView;
    private List<AppInfo> mAllAppInfoList = new ArrayList<>();
    private final ArrayList<AppInfo> mSelectedAppInfoList = new ArrayList<>();
    private IndexableListView mIndexableListView;
    private TextView mDialog;
    private AppAdapter mAdapter;
    private LinearLayout mTitleLayout;
    private TextView mTitle;
    private TextView mNoContentTextView;

    private MenuItem mSearchItem;

    private AppInfoLoadTask mTask;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private Comparator<? super AppInfo> mComparator;

    private static final String TAG = "AppListActivity";

    private static final int EMPTY_LIST = 1;

    private static final int NON_EMPTY_LIST = 2;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EMPTY_LIST:
                    mNoContentTextView.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                    break;
                case NON_EMPTY_LIST:
                    mNoContentTextView.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        mContext = this;

        mComparator = (Comparator<AppInfo>) (appInfoFirst, appInfoSecond) -> {
            if ("@".equals(appInfoFirst.mSortLetter)
                    || "#".equals(appInfoSecond.mSortLetter)) {
                return -1;
            } else if ("#".equals(appInfoFirst.mSortLetter)
                    || "@".equals(appInfoSecond.mSortLetter)) {
                return 1;
            } else {
                return appInfoFirst.mSortLetter.compareTo(appInfoSecond.mSortLetter);
            }
        };

        mProgressBarView = findViewById(R.id.progress_bar_layout);
        mProgressBarView.setVisibility(View.VISIBLE);

        mTitleLayout = findViewById(R.id.title_layout);
        mTitle = findViewById(R.id.title_layout_catalog);
        mNoContentTextView = findViewById(R.id.title_layout_no_friends);

        mIndexableListView = findViewById(R.id.sidrbar);
        mDialog = findViewById(R.id.dialog);
        mIndexableListView.setTextView(mDialog);

        // 设置右侧触摸监听
        mIndexableListView.setOnTouchingLetterChangedListener(new IndexableListView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mAppListView.setSelection(position);
                }

            }
        });

        mAppListView = findViewById(R.id.am_listview);
        mAppListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> vAdapter, View view, int position, long id) {
                try {
                    AppInfo appInfo = mSelectedAppInfoList.get(position);
                    Intent intent = new Intent(mContext, SignaturesActivity.class);
                    intent.putExtra("packageName", appInfo.mPkgName);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, "getting details failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mAdapter = new AppAdapter(mContext, mSelectedAppInfoList);
        mAppListView.setAdapter(mAdapter);
        mAppListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= mSelectedAppInfoList.size()) {
                    return;
                }
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTitleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    mTitleLayout.setLayoutParams(params);
                    mTitle.setText(mSelectedAppInfoList.get(
                            getPositionForSection(section)).mSortLetter);
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = mTitleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTitleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            mTitleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                mTitleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTask = new AppInfoLoadTask();
        mTask.execute();
    }

    private class AppInfoLoadTask extends AsyncTask<String, Integer, List<AppInfo>> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<AppInfo> doInBackground(String... params) {
            List<AppInfo> appInfos = new ArrayList<>();
            PackageManager pManager = mContext.getPackageManager();
            List<PackageInfo> packageInfos = pManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            for (PackageInfo packageInfo : packageInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.mPkgName = packageInfo.packageName;
                appInfo.mLabel = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                String pinyin = convertToPinyin(appInfo.mLabel);
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    appInfo.mSortLetter = sortString.toUpperCase();
                } else {
                    appInfo.mSortLetter = "#";
                }
                appInfo.mIcon = pManager.getApplicationIcon(packageInfo.applicationInfo);
                appInfos.add(appInfo);
            }
            return appInfos;
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        @Override
        protected void onPostExecute(List<AppInfo> result) {
            mAllAppInfoList = result;
            mSelectedAppInfoList.clear();
            mSelectedAppInfoList.addAll(mAllAppInfoList);

            Collections.sort(mSelectedAppInfoList, mComparator);

            mProgressBarView.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return mSelectedAppInfoList.get(position).mSortLetter.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < mSelectedAppInfoList.size(); i++) {
            String sortStr = mSelectedAppInfoList.get(i).mSortLetter;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_applist, menu);
        mSearchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // below code committed will lead to a popup window when entering
                // search word, use the next line to work around the problem.
                // mMaterialListView.setFilterText(newText.toString());
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_desktop:
                mSelectedAppInfoList.clear();
                List<String> desktopApps = getDesktopApps();
                for (AppInfo appInfo : mAllAppInfoList) {
                    if (desktopApps.contains(appInfo.mPkgName)) {
                        mSelectedAppInfoList.add(appInfo);
                    }
                }
                Collections.sort(mSelectedAppInfoList, mComparator);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.action_show_all:
                mSelectedAppInfoList.clear();
                mSelectedAppInfoList.addAll(mAllAppInfoList);
                Collections.sort(mSelectedAppInfoList, mComparator);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_show_nosystem:
                mSelectedAppInfoList.clear();
                List<String> systemApps = getSystemApps();
                for (AppInfo appInfo : mAllAppInfoList) {
                    if (!systemApps.contains(appInfo.mPkgName)) {
                        mSelectedAppInfoList.add(appInfo);
                    }
                }
                Collections.sort(mSelectedAppInfoList, mComparator);
                mAdapter.notifyDataSetChanged();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> getDesktopApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        List<String> desktopApps = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            desktopApps.add(resolveInfo.activityInfo.packageName);
        }
        return desktopApps;
    }

    private List<String> getSystemApps() {
        List<String> systemApps = new ArrayList<>();
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(PackageManager.MATCH_SYSTEM_ONLY);
        for (PackageInfo packageInfo : packageInfos) {
            systemApps.add(packageInfo.packageName);
        }
        return systemApps;
    }

    private String convertToPinyin(String str) {
        try {
            StringBuffer sb = new StringBuffer();
            HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (char c : str.toCharArray()) {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, outputFormat);
                if (pinyinArray == null || pinyinArray.length == 0) {
                    sb.append("#");
                } else {
                    sb.append(pinyinArray[0]);
                }
                sb.append("-");
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            Log.e(TAG, "convertToPinyin failed");
            return "#";
        }
    }

    class AppAdapter extends BaseAdapter implements SectionIndexer, Filterable {

        private List<AppInfo> appInfoList;
        private final Context mContext;

        public AppAdapter(Context mContext, List<AppInfo> appInfoList) {
            this.mContext = mContext;
            this.appInfoList = appInfoList;
        }

        @Override
        public int getCount() {
            return appInfoList.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return appInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_app_list_item, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.iv_icon = convertView.findViewById(R.id.ai_igview);
                holder.tv_label = convertView.findViewById(R.id.ai_name_tv);
                holder.tv_pkgname = convertView.findViewById(R.id.ai_pack_tv);
                holder.tvLetter = convertView.findViewById(R.id.catalog);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AppInfo appInfo = this.getItem(position);
            holder.iv_icon.setImageDrawable(appInfo.mIcon); // 设置图标
            holder.tv_label.setText(appInfo.mLabel); // 设置app名
            holder.tv_pkgname.setText(appInfo.mPkgName); // 设置pack名

            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);

            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(appInfo.mSortLetter);
            } else {
                holder.tvLetter.setVisibility(View.GONE);
            }

            return convertView;
        }

        /**
         * 根据ListView的当前位置获取分类的首字母的Char ascii值
         */
        @Override
        public int getSectionForPosition(int position) {
            return appInfoList.get(position).mSortLetter.charAt(0);
        }

        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        @Override
        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = appInfoList.get(i).mSortLetter;
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public Object[] getSections() {
            return null;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults result = new FilterResults();

                    // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表\
                    List<AppInfo> filterDateList = new ArrayList<AppInfo>();

                    if (TextUtils.isEmpty(constraint)) {
                        filterDateList = mSelectedAppInfoList;
                    } else {
                        filterDateList.clear();
                        for (AppInfo appInfo : mSelectedAppInfoList) {
                            String label = appInfo.mLabel;
                            String pkgName = appInfo.mPkgName;
                            String pinyin = convertToPinyin(label);
                            if ((label.contains(constraint)) || (pinyin.contains(constraint)) || (pkgName.contains(constraint))
                            ) {
                                filterDateList.add(appInfo);
                            }
                        }
                    }

                    // 根据a-z进行排序
                    Collections.sort(filterDateList, mComparator);

                    if (filterDateList.size() == 0) {
                        mHandler.sendEmptyMessage(EMPTY_LIST);
                    } else {
                        mHandler.sendEmptyMessage(NON_EMPTY_LIST);
                    }

                    result.values = filterDateList; // 将得到的集合保存到FilterResults的value变量中
                    result.count = filterDateList.size();// 将集合的大小保存到FilterResults的count变量中


                    return result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    appInfoList = (List<AppInfo>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_label;
            TextView tv_pkgname;
            TextView tvLetter;
        }
    }
}

class AppInfo {
    String mPkgName;

    String mLabel;

    /**
     * 显示数据拼音的首字母
     */
    String mSortLetter;

    Drawable mIcon;
}