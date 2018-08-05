package com.itheima62.smartbj.newstpipage;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima62.smartbj.R;
import com.itheima62.smartbj.activity.MainActivity;
import com.itheima62.smartbj.activity.NewsDetailAcitivity;
import com.itheima62.smartbj.domain.NewsCenterData.NewsData.ViewTagData;
import com.itheima62.smartbj.domain.TPINewsData;
import com.itheima62.smartbj.domain.TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData;
import com.itheima62.smartbj.domain.TPINewsData.TPINewsData_Data.TPINewsData_Data_LunBoData;
import com.itheima62.smartbj.utils.DensityUtil;
import com.itheima62.smartbj.utils.MyConstants;
import com.itheima62.smartbj.utils.SpTools;
import com.itheima62.smartbj.view.RefreshListView.OnRefreshDataListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Administrator
 * @创建时间 2015-7-7 上午9:53:48
 * @描述 新闻中心页签对应的页面
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-10 11:27:20 +0800 (Fri, 10 Jul 2015) $
 * @ 当前版本: $Rev: 60 $
 */
public class TPINewsNewsCenterPager
{
	private static final Class<Object>	TPINewsData	= null;

	//所有组件
	
	@ViewInject(R.id.vp_tpi_news_lunbo_pic)
	private ViewPager vp_lunbo ; //轮播图的显示组件
	
	@ViewInject(R.id.tv_tpi_news_pic_desc)
	private TextView tv_pic_desc;//图片的描述信息
	
	@ViewInject(R.id.ll_tpi_news_pic_points)
	private LinearLayout ll_points;//轮播图的每张图片对应的点组合
	
	@ViewInject(R.id.lv_tpi_news_listnews)
	private com.itheima62.smartbj.view.RefreshListView lv_listnews;// 显示列表新闻的组件
	
	//数据
	private MainActivity mainActivity;
	private View	root;
	private ViewTagData viewTagData;//页签对应的数据
	
	private boolean isFresh = false;//记录是否是刷新数据

	private Gson	gson;

	//轮播图的数据
	private List<TPINewsData_Data_LunBoData>	lunboDatas = new ArrayList<TPINewsData.TPINewsData_Data.TPINewsData_Data_LunBoData>();

	//轮播图的适配器
	private LunBoAdapter	lunboAdapter ;

	private BitmapUtils	bitmapUtils;

	private int	picSelectIndex;

	private Handler	handler;

	private LunBoTask	lunboTask;
    //新闻列表的数据
	private List<TPINewsData_Data_ListNewsData>	listNews = new ArrayList<TPINewsData.TPINewsData_Data.TPINewsData_Data_ListNewsData>();

	private ListNewsAdapter	listNewsAdapter;
	
	private String loadingMoreDatasUrl;//加载更多数据的url
	private String loadingDataUrl;
	
	public TPINewsNewsCenterPager(MainActivity mainActivity, ViewTagData viewTagData){
		this.mainActivity = mainActivity;
		this.viewTagData = viewTagData;
		gson = new Gson();
		//轮播的任务
		lunboTask = new LunBoTask();
		
		
		//xutils bitmag 组件
		bitmapUtils = new BitmapUtils(mainActivity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		
		initView();//初始化界面
		initData();//初始化数据
		initEvent();//初始化事件
	}
	
	//刷新数据
	


	private void initEvent() {
		//给新闻加点击事件
		lv_listnews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position + ":" + position);
				//获取点击当前新闻的链接
				TPINewsData_Data_ListNewsData tpiNewsData_Data_ListNewsData = listNews.get(position - 1);
				String newsurl = tpiNewsData_Data_ListNewsData.url;
				//获取新闻的id
				String newsid = tpiNewsData_Data_ListNewsData.id;
				
				
				//获取新闻的标记 id
				//保存id sharedpreferences
				String readIDs = SpTools.getString(mainActivity, MyConstants.READNEWSIDS, null);
				if (TextUtils.isEmpty(readIDs)) {
					//第一次 没有保存过id
					readIDs = newsid;//保存当前新闻的id
				} else {
					//添加保存新闻id
					readIDs += "," + newsid;
				}
				//重新保存读过的新闻的id
				SpTools.setString(mainActivity,MyConstants.READNEWSIDS, readIDs);
				
				// 修改读过的新闻字体颜色
				//告诉界面更新
				listNewsAdapter.notifyDataSetChanged();
				
				
				
				
				//跳转到新闻页面显示新闻
				
				Intent newsActivity = new Intent(mainActivity,NewsDetailAcitivity.class);
				newsActivity.putExtra("newsurl", newsurl);
				mainActivity.startActivity(newsActivity);
			}
		});
		
		//刷新listview数据的监听器
		lv_listnews.setOnRefreshDataListener(new OnRefreshDataListener() {
			
			@Override
			public void refresdData() {
				isFresh = true;
				//刷新数据
				getDataFromNet(MyConstants.SERVERURL + viewTagData.url,false);
				//改变listview的状态
			}

			@Override
			public void loadingMore() {
				//判断是否有更多的数据
				if(TextUtils.isEmpty(loadingMoreDatasUrl)) {
					Toast.makeText(mainActivity, "没有更多数据", 1).show();
					//关闭刷新数据的状态
					lv_listnews.refreshStateFinish();
				} else {
					System.out.println("url:" + loadingMoreDatasUrl);
					//有数据
					getDataFromNet(loadingMoreDatasUrl,true);
				}
			}
		});
		//给轮播图添加页面切换事件
		vp_lunbo.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				picSelectIndex = position;
				setPicDescAndPointSelect(picSelectIndex);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initData() {
		//轮播图的适配器
		lunboAdapter  = new LunBoAdapter();
		//给轮播图
		vp_lunbo.setAdapter(lunboAdapter );
		
		//新闻列表的适配器
		listNewsAdapter = new  ListNewsAdapter();
		
		//设置新闻列表适配
		lv_listnews.setAdapter(listNewsAdapter);
		
		//从本地获取数据
		String jsonCache = SpTools.getString(mainActivity, loadingDataUrl, "");
		if (!TextUtils.isEmpty(jsonCache)) {
			//有数据，解析数据
			com.itheima62.smartbj.domain.TPINewsData newsData = parseJson(jsonCache);
			//处理数据
			processData(newsData);
		}
		loadingDataUrl = MyConstants.SERVERURL + viewTagData.url;
		getDataFromNet(loadingDataUrl,false);//从网络获取数据
	}
	
	/**
	 * @param newsData
	 */
	private void processData(com.itheima62.smartbj.domain.TPINewsData newsData){
		//完成数据的处理
		
		//1.设置轮播图的数据
		setLunBoData(newsData);
		
		//2.轮播图对应的点处理
		initPoints();//初始化轮播图的点
		
		//3. 设置图片描述和点的选中效果
		setPicDescAndPointSelect(picSelectIndex);
		
		//4. 开始轮播图
		lunboTask.startLunbo();
		
		//5. 新闻列表的数据
		setListViewNews(newsData);
	}
	
	/**
	 * 设置新闻列表的数据
	 * @param newsData
	 */
	private void setListViewNews(
			com.itheima62.smartbj.domain.TPINewsData newsData) {
		
		listNews  = newsData.data.news;
		//更新界面
		listNewsAdapter.notifyDataSetChanged();
	}

	/**
	 * 处理轮播图
	 */
	private void lunboProcess() {
		if (handler == null) {
		
			handler = new Handler();
		}
		//清空掉原来所有的任务
		handler.removeCallbacksAndMessages(null);
		
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//任务
				//控制轮播图的显示
				vp_lunbo.setCurrentItem((vp_lunbo.getCurrentItem() + 1) % vp_lunbo.getAdapter().getCount());
				handler.postDelayed(this, 1500);
			}
		}, 1500);
	}
	
	private class LunBoTask extends Handler implements Runnable{

		public void stopLunbo(){
			//移除当前所有的任务
			removeCallbacksAndMessages(null);
		}
		public void startLunbo(){
			stopLunbo();
			postDelayed(this, 2000);
		}
		@Override
		public void run() {
			//控制轮播图的显示
			vp_lunbo.setCurrentItem((vp_lunbo.getCurrentItem() + 1) % vp_lunbo.getAdapter().getCount());
			postDelayed(this, 2000);
		}
		
	}

	private void setPicDescAndPointSelect(int picSelectIndex) {
		if (picSelectIndex < 0 || picSelectIndex > lunboDatas.size() - 1){
			return ;
		}
		//设置描述信息
		tv_pic_desc.setText(lunboDatas.get(picSelectIndex).title);
		
		//设置点是否是选中
		for (int i = 0; i < lunboDatas.size(); i++) {
			ll_points.getChildAt(i).setEnabled(i == picSelectIndex);
		}
		
	}

	private void initPoints() {
		//清空以前存在的点
		ll_points.removeAllViews();
		//轮播图有几张 加几个点
		for (int i = 0; i < lunboDatas.size() ; i++) {
			View v_point = new View(mainActivity);
			//设置点的背景选择器
			v_point.setBackgroundResource(R.drawable.point_seletor);
			v_point.setEnabled(false);//默认是默认的灰色点
			
			//设置点的大小
			LayoutParams params = new LayoutParams(DensityUtil.dip2px(mainActivity,5), DensityUtil.dip2px(mainActivity,5));
			//设置点与点直接的间距
			params.leftMargin = DensityUtil.dip2px(mainActivity,10);
			
			//设置参数
			v_point.setLayoutParams(params);
			ll_points.addView(v_point);
		}
	}

	private void setLunBoData(com.itheima62.smartbj.domain.TPINewsData newsData) {
		//获取轮播图的数据
		lunboDatas = newsData.data.topnews;
		
		lunboAdapter.notifyDataSetChanged();//更新界面
	}
	
	private class ListNewsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listNews.size();
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
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mainActivity, R.layout.tpi_news_listview_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_icon);
				holder.iv_newspic = (ImageView) convertView.findViewById(R.id.iv_tpi_news_listview_item_pic);
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_title);
				holder.tv_time = (TextView) convertView.findViewById(R.id.tv_tpi_news_listview_item_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
		
			
			TPINewsData_Data_ListNewsData tpiNewsData_Data_ListNewsData = listNews.get(position);
			
			//判断该新闻是否读取过
			String newsId = tpiNewsData_Data_ListNewsData.id;
			String readnewsIds = SpTools.getString(mainActivity, MyConstants.READNEWSIDS, "");
			if (TextUtils.isEmpty(readnewsIds) || !readnewsIds.contains(newsId)) {
				//空 没有保存过id
				holder.tv_title.setTextColor(Color.BLACK);
				holder.tv_time.setTextColor(Color.BLACK);
			} else {
				holder.tv_title.setTextColor(Color.GRAY);
				holder.tv_time.setTextColor(Color.GRAY);
			}
			
			//设置标题
			holder.tv_title.setText(tpiNewsData_Data_ListNewsData.title);
			
			//设置时间
			holder.tv_time.setText(tpiNewsData_Data_ListNewsData.pubdate);
			
			//设置图片
			bitmapUtils.display(holder.iv_newspic, tpiNewsData_Data_ListNewsData.listimage);
			
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		ImageView iv_newspic;
		TextView tv_title;
		TextView tv_time;
		ImageView iv_icon;
	}
	
	/**
	 * @author Administrator
	 * @创建时间 2015-7-7 上午10:54:11
	 * @描述 轮播图的适配器
	 *
	 * @ svn提交者：$Author: gd $
	 * @ 提交时间: $Date: 2015-07-10 11:27:20 +0800 (Fri, 10 Jul 2015) $
	 * @ 当前版本: $Rev: 60 $
	
	 */
	private class LunBoAdapter extends PagerAdapter{

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv_lunbo_pic = new ImageView(mainActivity);
			iv_lunbo_pic.setScaleType(ScaleType.FIT_XY);
			
			//设备默认的图片,网络缓慢
			iv_lunbo_pic.setImageResource(R.drawable.home_scroll_default);
			
			
			//给图片添加数据
			TPINewsData_Data_LunBoData tpiNewsData_Data_LunBoData = lunboDatas.get(position);
			
			//图片的url
			String topimageUrl = tpiNewsData_Data_LunBoData.topimage;
			
			
			//把url的图片给iv_lunbo_pic
			//异步加载图片，并且显示到组件中
			bitmapUtils.display(iv_lunbo_pic, topimageUrl);
			
			//给图片添加触摸事件
			iv_lunbo_pic.setOnTouchListener(new OnTouchListener() {
				
				private float	downX;
				private float	downY;
				private long	downTime;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN://按下停止轮播
						System.out.println("按下");
						downX = event.getX();
						downY = event.getY();
						downTime = System.currentTimeMillis();
						lunboTask.stopLunbo();
						break;
					case MotionEvent.ACTION_CANCEL://事件取消
						lunboTask.startLunbo();
						break;
					case MotionEvent.ACTION_UP://松开
						float upX = event.getX();
						float upY = event.getY();
						
						if (upX == downX && upY == downY) {
							long upTime = System.currentTimeMillis();
							if (upTime - downTime < 500) {
								//点击
								lunboPicClick("被单击了。。。。。");
							}
						}
						System.out.println("松开");
						lunboTask.startLunbo();//开始轮播
						break;
					default:
						break;
					}
					return true;
				}

				private void lunboPicClick(Object data) {
					//处理图片的点击事件
					System.out.println(data);
					
				}
			});
			container.addView(iv_lunbo_pic);
			
			return iv_lunbo_pic;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lunboDatas.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
	}

	private TPINewsData parseJson(String jsonData){
		//解析json数据
		
		TPINewsData tpiNewsData = gson.fromJson(jsonData, TPINewsData.class);
		System.out.println("tpiNewsData.data.more:" + tpiNewsData.data.more);
		if (!TextUtils.isEmpty(tpiNewsData.data.more)) {
			loadingMoreDatasUrl = MyConstants.SERVERURL + tpiNewsData.data.more;
			System.out.println("loadingMoreDatasUrl:" + loadingMoreDatasUrl);
		} else {
			loadingMoreDatasUrl = "";
			//System.out.println("loadingMoreDatasUrl:" + loadingMoreDatasUrl);
		}
		return tpiNewsData;
	}

	private void getDataFromNet(final String url,final boolean isLoadingMore) {
		//httpUtils
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET,  url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				//请求数据成功
				String jsonData = responseInfo.result;
				
				//保存数据到本地
				SpTools.setString(mainActivity, url, jsonData);
				
				//解析数据
				com.itheima62.smartbj.domain.TPINewsData newsData = parseJson(jsonData);
			
				//判断是否是加载更多数据 
				if (isLoadingMore){
					//原有的数据 +　新数据
					System.out.println(listNews.size() + "<<");
					listNews.addAll(newsData.data.news);
					System.out.println(listNews.size() + ">>");
					//更新界面
					listNewsAdapter.notifyDataSetChanged();
					Toast.makeText(mainActivity, "加载数据成功", 1).show();
				} else {
				    //第一次取数据或刷新数据
					//处理数据
					processData(newsData);
					if (isFresh) {
						//设置listview头隐藏
						
						Toast.makeText(mainActivity, "刷新数据成功", 1).show();
					}
				}
				lv_listnews.refreshStateFinish();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				//请求数据失败
				if (isFresh) {
					lv_listnews.refreshStateFinish();
					Toast.makeText(mainActivity, "刷新数据失败", 1).show();
				}
			}
		});
	}

	private void initView() {
		//页签对应页面的根布局
		root = View.inflate(mainActivity, R.layout.tpi_news_centent, null);
		
		ViewUtils.inject(this, root);
		
		View lunBoPic = View.inflate(mainActivity, R.layout.tpi_news_lunbopic, null);
		ViewUtils.inject(this, lunBoPic);
		
		lv_listnews.setIsRefreshHead(true);
		//把轮播图加到listView中
		lv_listnews.addHeaderView(lunBoPic);
	}
	
	public View getRootView(){
		return root;
	}
	
	
	
	
}
