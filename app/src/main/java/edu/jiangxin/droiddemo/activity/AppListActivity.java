package edu.jiangxin.droiddemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.common.CharacterParser;
import edu.jiangxin.droiddemo.view.IndexableListView;

public class AppListActivity extends AppCompatActivity implements SectionIndexer {

    private Context mContext;
    private ListView mAppListView;
    private List<AppInfo> mAllAppInfoList = new ArrayList<>();
    private ArrayList<AppInfo> mAppInfoList = new ArrayList<>();
    private IndexableListView mIndexableListView;
    private TextView dialog;
    private AppAdapter adapter;
    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNofriends;

    private MenuItem mSearchItem;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser mCharacterParser;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private Comparator comparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        mContext = AppListActivity.this;

        titleLayout = findViewById(R.id.title_layout);
        title = this.findViewById(R.id.title_layout_catalog);
        tvNofriends = findViewById(R.id.title_layout_no_friends);
        // 实例化汉字转拼音类
        mCharacterParser = CharacterParser.getInstance();

        comparator = new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                if ("@".equals(o1.mSortLetter)
                        || "#".equals(o2.mSortLetter)) {
                    return -1;
                } else if ("#".equals(o1.mSortLetter)
                        || "@".equals(o2.mSortLetter)) {
                    return 1;
                } else {
                    return o1.mSortLetter.compareTo(o2.mSortLetter);
                }
            }
        };

        mIndexableListView = findViewById(R.id.sidrbar);
        dialog = findViewById(R.id.dialog);
        mIndexableListView.setTextView(dialog);

        // 设置右侧触摸监听
        mIndexableListView.setOnTouchingLetterChangedListener(new IndexableListView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mAppListView.setSelection(position);
                }

            }
        });

        mAppListView = this.findViewById(R.id.am_listview);
        // 初始化APP数据
        getAllApps();
        mAppInfoList.clear();
        mAppInfoList.addAll(mAllAppInfoList);
        mAppListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> vAdapter, View view, int position, long id) {
                try {
                    AppInfo appInfo = mAppInfoList.get(position);
                    Intent intent = new Intent(mContext, SignaturesActivity.class);
                    intent.putExtra("packName", appInfo.mPkgName);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(mContext, "getting details failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 根据a-z进行排序源数据
        Collections.sort(mAppInfoList, comparator);
        adapter = new AppAdapter(this, mAppInfoList);
        mAppListView.setAdapter(adapter);
        mAppListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(mAppInfoList.get(
                            getPositionForSection(section)).mSortLetter);
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private void getAllApps() {
        mAllAppInfoList.clear();
        PackageManager pManager = mContext.getPackageManager();
        List<PackageInfo> packageInfos = pManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packageInfos) {
            AppInfo appInfo = new AppInfo();
            appInfo.mPkgName = packageInfo.packageName;
            appInfo.mLabel = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            String pinyin = mCharacterParser.getSpelling(appInfo.mLabel);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                appInfo.mSortLetter = sortString.toUpperCase();
            } else {
                appInfo.mSortLetter = "#";
            }
            appInfo.mIcon = pManager.getApplicationIcon(packageInfo.applicationInfo);
            mAllAppInfoList.add(appInfo);
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
        return mAppInfoList.get(position).mSortLetter.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < mAppInfoList.size(); i++) {
            String sortStr = mAppInfoList.get(i).mSortLetter;
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
                // 这个时候不需要挤压效果 就把他隐藏掉
                titleLayout.setVisibility(View.GONE);

                // below code committed will lead to a popup window when entering
                // search word, use the next line to work around the problem.
                // mMaterialListView.setFilterText(newText.toString());
                adapter.getFilter().filter(newText);


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_desktop:
                mAppInfoList.clear();
                List<String> desktopApps = getDesktopApps();
                for (AppInfo appInfo : mAllAppInfoList) {
                    if (desktopApps.contains(appInfo.mPkgName)) {
                        mAppInfoList.add(appInfo);
                    }
                }
                adapter.notifyDataSetChanged();
                break;

            case R.id.action_show_all:
                mAppInfoList.clear();
                mAppInfoList.addAll(mAllAppInfoList);
                adapter.notifyDataSetChanged();
                break;
            case R.id.action_show_nosystem:
                mAppInfoList.clear();
                List<String> systemApps = getSystemApps();
                for (AppInfo appInfo : mAllAppInfoList) {
                    if (!systemApps.contains(appInfo.mPkgName)) {
                        mAppInfoList.add(appInfo);
                    }
                }
                adapter.notifyDataSetChanged();
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

    class AppAdapter extends BaseAdapter implements SectionIndexer, Filterable {

        private List<AppInfo> list = null;
        private Context mContext;

        public AppAdapter(Context mContext, List<AppInfo> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return list.get(position);
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
            return list.get(position).mSortLetter.charAt(0);
        }

        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        @Override
        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = list.get(i).mSortLetter;
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * 提取英文的首字母，非英文字母用#代替。
         *
         * @param str
         * @return
         */
        private String getAlpha(String str) {
            String sortStr = str.trim().substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortStr.matches("[A-Z]")) {
                return sortStr;
            } else {
                return "#";
            }
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
                        filterDateList = mAppInfoList;
                        tvNofriends.setVisibility(View.GONE);
                    } else {
                        filterDateList.clear();
                        for (AppInfo sortModel : mAppInfoList) {
                            String name = sortModel.mPkgName;
                            if (name.indexOf(constraint.toString()) != -1
                                    || mCharacterParser.getSpelling(name).startsWith(
                                    constraint.toString())) {
                                filterDateList.add(sortModel);
                            }
                        }
                    }

                    // 根据a-z进行排序
                    Collections.sort(filterDateList, comparator);

                    if (filterDateList.size() == 0) {
                        tvNofriends.setVisibility(View.VISIBLE);
                    }

                    result.values = filterDateList; // 将得到的集合保存到FilterResults的value变量中
                    result.count = filterDateList.size();// 将集合的大小保存到FilterResults的count变量中


                    return result;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    list = (List<AppInfo>) results.values;
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

/**
 * 签名信息
 */
class SignaturesMsg {

    // 如需要小写则把ABCDEF改成小写
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 检测应用程序是否是用"CN=Android Debug,O=Android,C=US"的debug信息来签名的
     * 判断签名是debug签名还是release签名
     */
    private final static X500Principal DEBUG_DN = new X500Principal(
            "CN=Android Debug,O=Android,C=US");

    /**
     * 进行转换
     */
    public static String toHexString(byte[] bData) {
        StringBuilder sb = new StringBuilder(bData.length * 2);
        for (int i = 0; i < bData.length; i++) {
            sb.append(HEX_DIGITS[(bData[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bData[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 返回MD5
     */
    public static String signatureMD5(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature s : signatures) {
                    digest.update(s.toByteArray());
                }
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA1
     */
    public static String signatureSHA1(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (signatures != null) {
                for (Signature s : signatures) {
                    digest.update(s.toByteArray());
                }
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * SHA256
     */
    public static String signatureSHA256(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (signatures != null) {
                for (Signature s : signatures) {
                    digest.update(s.toByteArray());
                }
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取App 证书对象
     */
    public static X509Certificate getX509Certificate(Signature[] signatures) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream(signatures[0].toByteArray());
            X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
            return cert;
        } catch (Exception e) {
        }
        return null;
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