

package com.itheima62.smartbj.newscenterpage;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.Config;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itheima62.smartbj.R;
import com.itheima62.smartbj.activity.MainActivity;
import com.itheima62.smartbj.domain.PhotosData;
import com.itheima62.smartbj.domain.PhotosData.PhotosData_Data.PhotosNews;
import com.itheima62.smartbj.utils.BitmapCacheUtils;
import com.itheima62.smartbj.utils.MyConstants;
import com.itheima62.smartbj.utils.SpTools;
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
 * @创建时间 2015-7-6 下午2:20:16
 * @描述 TODO
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-10 14:59:24 +0800 (Fri, 10
 *     Jul 2015) $ @ 当前版本: $Rev: 66 $
 */
public class PhotosBaseNewsCenterPage extends BaseNewsCenterPage
{

	@ViewInject(R.id.lv_newscenter_photos)
	private ListView			lv_photos;

	@ViewInject(R.id.gv_newscenter_photos)
	private GridView			gv_photos;

	private MyAdapter			adapter;

	private List<PhotosNews>	photosNews	= new ArrayList<PhotosData.PhotosData_Data.PhotosNews>();

	private BitmapUtils			bitmapUtils;

	private boolean				isShowList	= true;														// 是否显示listview

	private BitmapCacheUtils	bitmapCacheUtils;

	public PhotosBaseNewsCenterPage(MainActivity mainActivity) {
		super(mainActivity);
		bitmapUtils = new BitmapUtils(mainActivity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		bitmapCacheUtils = new BitmapCacheUtils(mainActivity);
	}

	@Override
	public View initView() {
		View photos_root = View.inflate(mainActivity,
				R.layout.newscenter_photos, null);

		ViewUtils.inject(this, photos_root);
		return photos_root;
	}

	public void switchListViewOrGridView(ImageButton ib_listOrGrid) {
		if (isShowList) {
			//按钮的背景设置成list
			ib_listOrGrid.setImageResource(R.drawable.icon_pic_list_type);
			// 显示Gridview
			lv_photos.setVisibility(View.GONE);
			// 隐藏listView
			gv_photos.setVisibility(View.VISIBLE);
		} else {
			//按钮的背景设置成list
			ib_listOrGrid.setImageResource(R.drawable.icon_pic_grid_type);
			// 显示Gridview
			lv_photos.setVisibility(View.VISIBLE);
			// 隐藏listView
			gv_photos.setVisibility(View.GONE);
		}
		isShowList = !isShowList;
	}

	@Override
	public void initData() {
		if (adapter == null) {
			// 创建适配器
			adapter = new MyAdapter();

			lv_photos.setAdapter(adapter);

			gv_photos.setAdapter(adapter);
		}
		if (isShowList) {
			lv_photos.setVisibility(View.VISIBLE);
			gv_photos.setVisibility(View.GONE);
		} else {
			lv_photos.setVisibility(View.GONE);
			gv_photos.setVisibility(View.VISIBLE);
		}

		// 本地取数据（缓存）
		String photosJsonData = SpTools.getString(mainActivity,
				MyConstants.PHOTOSURL, null);
		if (!TextUtils.isEmpty(photosJsonData)) {
			// 有数据
			// 解析json数据
			PhotosData photosData = parsePhotosJson(photosJsonData);

			// 处理组图数据
			processPhotosData(photosData);
		}

		// 网络取数据
		getDataFromNet();
		super.initData();
	}

	private void getDataFromNet() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, MyConstants.PHOTOSURL,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// 请求数据成功
						// 获取组图的json数据
						String jsonData = responseInfo.result;

						// 缓存
						SpTools.setString(mainActivity, MyConstants.PHOTOSURL,
								jsonData);

						// 解析json数据
						PhotosData photosData = parsePhotosJson(jsonData);

						// 处理组图数据
						processPhotosData(photosData);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// 请求数据失败

					}
				});
	}

	/**
	 * @param photosData
	 *            组图的数据
	 */
	protected void processPhotosData(PhotosData photosData) {
		// 获取组图的所有数据
		photosNews = photosData.data.news;
		adapter.notifyDataSetChanged();// 通知界面更新数据
	}

	private class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return photosNews.size();
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
			// TODO Auto-generated method stub
			// 自定义View 显示
			ViewHolder holder = null;

			// 判断是否存在view缓存
			if (convertView == null) {
				// 没有界面缓存
				convertView = View.inflate(mainActivity,
						R.layout.photoslist_item, null);
				holder = new ViewHolder();

				holder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_photos_list_item_pic);
				holder.tv_desc = (TextView) convertView
						.findViewById(R.id.tv_photos_list_item_desc);
				convertView.setTag(holder);
			} else {
				// 有界面缓存
				holder = (ViewHolder) convertView.getTag();
			}

			holder.iv_pic.setImageResource(R.drawable.pic_item_list_default);
			// 赋值
			// 取当前数据
			PhotosNews pn = photosNews.get(position);
			// 设置名字
			holder.tv_desc.setText(pn.title);

			// 设置tup
			bitmapCacheUtils.display(holder.iv_pic, pn.listimage);
			//BitmapCacheUtils

			return convertView;
		}

	}

	private class ViewHolder
	{
		ImageView	iv_pic;
		TextView	tv_desc;
	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonData
	 *            photos的json数据
	 */
	protected PhotosData parsePhotosJson(String jsonData) {
		Gson gson = new Gson();
		// 组图的所有数据
		PhotosData photosData = gson.fromJson(jsonData, PhotosData.class);
		return photosData;
	}

}
