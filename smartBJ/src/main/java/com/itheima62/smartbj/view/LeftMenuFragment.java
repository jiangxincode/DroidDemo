package com.itheima62.smartbj.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.domain.NewsCenterData;
import com.itheima62.smartbj.domain.NewsCenterData.NewsData;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午4:16:54
 * @描述 左侧菜单的fragment
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 15:54:09 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 30 $
 */
public class LeftMenuFragment extends BaseFragment
{
	public interface OnSwitchPageListener{
		void switchPage(int selectionIndex);
	}
	
	private OnSwitchPageListener switchListener;
	
	/**
	 * 设置监听回调接口
	 * @param listener
	 */
	public void setOnSwitchPageListener(OnSwitchPageListener listener){
		this.switchListener = listener;
	}
	
	private List<NewsData> data = new ArrayList<NewsCenterData.NewsData>();//新闻中心左侧菜单数据
	private ListView	lv_leftData;
	private MyAdapter	adapter;
	
	private int selectPosition;//选中的位置
	
	@Override
	public void initEvent() {
		//设置listview的选择事件
		lv_leftData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//保存选中的位置
				selectPosition = position;
				
				//更新界面
				adapter.notifyDataSetChanged();
				
				//控制新闻中心，四个新闻页面的显示
				//mainActivity.getMainMenuFragment().leftMenuClickSwitchPage(selectPosition);
				if (switchListener != null) {
					switchListener.switchPage(selectPosition);
				} else {
					mainActivity.getMainMenuFragment().leftMenuClickSwitchPage(selectPosition);
				}
				
				
				//切换SlidingMenu的开关
				mainActivity.getSlidingMenu().toggle();
				
			}
		});
		super.initEvent();
	}
	@Override
	public View initView() {
		// TODO Auto-generated method stub
		//listview显示左侧菜单
		lv_leftData = new ListView(mainActivity);
		//背景是黑色
		lv_leftData.setBackgroundColor(Color.BLACK);
		
		//选中拖动的背景色 设置成透明
		lv_leftData.setCacheColorHint(Color.TRANSPARENT);
		
		//设置选中时为透明背景
		lv_leftData.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		//没有分割线
		lv_leftData.setDividerHeight(0);
		
		//距顶部为45px
		lv_leftData.setPadding(0, 45, 0, 0);
		
		
		
		return lv_leftData;
	}
	
	public void setLeftMenuData(List<NewsData> data){
		this.data = data;
		adapter.notifyDataSetChanged();//设置好数据后，通知界面刷新数据,进行显示
	}
	
	@Override
	public void initData() {
		//组织数据
		
		adapter = new MyAdapter();
		lv_leftData.setAdapter(adapter);
		super.initData();
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//显示数据
			TextView tv_currentView;
			if (convertView == null) {
				tv_currentView = (TextView) View.inflate(mainActivity, R.layout.leftmenu_list_item, null);
			} else {
				tv_currentView = (TextView) convertView;
			}
			
			//设置数据
			tv_currentView.setText(data.get(position).title);
			
			//判断是否被选中
			tv_currentView.setEnabled(position == selectPosition);
			
			return tv_currentView;
		}
		
	}

}
