package edu.jiangxin.droiddemo.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.common.CharacterParser;
import edu.jiangxin.droiddemo.view.IndexableListView;
import edu.jiangxin.droiddemo.view.IndexableListView.OnTouchingLetterChangedListener;

public class FriendsListActivity extends Activity implements SectionIndexer {
    private ListView sortedListView;
    private IndexableListView indexableListView;
    private TextView dialog;
    private SortGroupMemberAdapter adapter;
    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNofriends;

    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private List<GroupMemberBean> sourceDataList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private Comparator comparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        ActionBar actionBar = getActionBar();
        titleLayout = findViewById(R.id.title_layout);
        title = this.findViewById(R.id.title_layout_catalog);
        tvNofriends = this
                .findViewById(R.id.title_layout_no_friends);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        comparator = new Comparator<GroupMemberBean>() {
            @Override
            public int compare(GroupMemberBean o1, GroupMemberBean o2) {
                if ("@".equals(o1.getSortLetters())
                        || "#".equals(o2.getSortLetters())) {
                    return -1;
                } else if ("#".equals(o1.getSortLetters())
                        || "@".equals(o2.getSortLetters())) {
                    return 1;
                } else {
                    return o1.getSortLetters().compareTo(o2.getSortLetters());
                }
            }
        };

        indexableListView = findViewById(R.id.sidrbar);
        dialog = findViewById(R.id.dialog);
        indexableListView.setTextView(dialog);

        // 设置右侧触摸监听
        indexableListView.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortedListView.setSelection(position);
                }

            }
        });

        sortedListView = findViewById(R.id.country_lvcountry);
        sortedListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(
                        getApplication(),
                        ((GroupMemberBean) adapter.getItem(position)).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        sourceDataList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据
        Collections.sort(sourceDataList, comparator);
        adapter = new SortGroupMemberAdapter(this, sourceDataList);
        sortedListView.setAdapter(adapter);
        sortedListView.setOnScrollListener(new OnScrollListener() {
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
                    MarginLayoutParams params = (MarginLayoutParams) titleLayout
                            .getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(sourceDataList.get(
                            getPositionForSection(section)).getSortLetters());
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        MarginLayoutParams params = (MarginLayoutParams) titleLayout
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

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupMemberBean> filledData(String[] date) {
        List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

        for (int i = 0; i < date.length; i++) {
            GroupMemberBean sortModel = new GroupMemberBean();
            sortModel.setName(date[i]);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

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
        return sourceDataList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < sourceDataList.size(); i++) {
            String sortStr = sourceDataList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
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

    class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer, Filterable {
        private List<GroupMemberBean> list = null;
        private Context mContext;

        public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
            this.mContext = mContext;
            this.list = list;
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup arg2) {
            ViewHolder viewHolder = null;
            final GroupMemberBean mContent = list.get(position);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_group_member_item, null);
                viewHolder.tvTitle = view.findViewById(R.id.title);
                viewHolder.tvLetter = view.findViewById(R.id.catalog);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            // 根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);

            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(mContent.getSortLetters());
            } else {
                viewHolder.tvLetter.setVisibility(View.GONE);
            }

            viewHolder.tvTitle.setText(this.list.get(position).getName());

            return view;

        }


        /**
         * 根据ListView的当前位置获取分类的首字母的Char ascii值
         */
        @Override
        public int getSectionForPosition(int position) {
            return list.get(position).getSortLetters().charAt(0);
        }

        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        @Override
        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = list.get(i).getSortLetters();
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
                    List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

                    if (TextUtils.isEmpty(constraint)) {
                        filterDateList = sourceDataList;
                        tvNofriends.setVisibility(View.GONE);
                    } else {
                        filterDateList.clear();
                        for (GroupMemberBean sortModel : sourceDataList) {
                            String name = sortModel.getName();
                            if (name.indexOf(constraint.toString()) != -1
                                    || characterParser.getSelling(name).startsWith(
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
                    list = (List<GroupMemberBean>) results.values;
                    notifyDataSetChanged();

                }
            };
        }
    }


}

class ViewHolder {
    TextView tvLetter;
    TextView tvTitle;
}


class GroupMemberBean {

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
