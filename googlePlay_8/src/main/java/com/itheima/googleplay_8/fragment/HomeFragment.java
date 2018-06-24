package com.itheima.googleplay_8.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.googleplay_8.adapter.AppItemAdapter;
import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.base.SuperBaseAdapter;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.bean.HomeBean;
import com.itheima.googleplay_8.factory.ListViewFactory;
import com.itheima.googleplay_8.holder.AppItemHolder;
import com.itheima.googleplay_8.holder.PictureHolder;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.protocol.HomeProtocol;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:11:37
 * @des	TODO
 *
 * @version $Rev: 51 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 16:22:06 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class HomeFragment extends BaseFragment {

	private List<AppInfoBean>	mDatas;	// listview的数据源
	private List<String>		mPicutures; // 轮播图
	private HomeProtocol		mProtocol;
	private HomeAdapter			mAdapter;

	@Override
	public LoadedResult initData() {// 真正加载数据
		/*try {
			// shift+alt+z
			// 发送网络请求
			HttpUtils httpUtils = new HttpUtils();
			// http://localhost:8080/GooglePlayServer/home?index=0
			String url = URLS.BASEURL + "home";
			RequestParams parmas = new RequestParams();
			parmas.addQueryStringParameter("index", "0");
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, parmas);
			String result = responseStream.readString();
			// gson
			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(result, HomeBean.class);

			LoadedResult state = checkState(homeBean);
			if (state != LoadedResult.SUCCESS) {// 如果不成功,就直接返回,走到这里说明homeBean是ok
				return state;
			}

			state = checkState(homeBean.list);
			if (state != LoadedResult.SUCCESS) {// 如果不成功,就直接返回,走到这里说明homeBean.list是ok
				return state;
			}
			mDatas = homeBean.list;
			picutures = homeBean.picture;
			return LoadedResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}*/

		mProtocol = new HomeProtocol();
		try {
			HomeBean homeBean = mProtocol.loadData(0);

			LoadedResult state = checkState(homeBean);
			if (state != LoadedResult.SUCCESS) {// 如果不成功,就直接返回,走到这里说明homeBean是ok
				return state;
			}

			state = checkState(homeBean.list);
			if (state != LoadedResult.SUCCESS) {// 如果不成功,就直接返回,走到这里说明homeBean.list是ok
				return state;
			}
			mDatas = homeBean.list;
			mPicutures = homeBean.picture;
			return LoadedResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		// 返回成功的视图
		ListView listView = ListViewFactory.createListView();

		// 创建一个PictureHolder
		PictureHolder pictureHolder = new PictureHolder();
		// 触发加载数据
		pictureHolder.setDataAndRefreshHolderView(mPicutures);

		View headView = pictureHolder.getHolderView();
		listView.addHeaderView(headView);
		mAdapter = new HomeAdapter(listView, mDatas);
		listView.setAdapter(mAdapter);

		return listView;
	}

	class HomeAdapter extends AppItemAdapter {

		public HomeAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
			super(absListView, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception {
			return loadMore(mDatas.size());
		}
	}

	public List<AppInfoBean> loadMore(int index) throws Exception {
		/*	// 真正加载更多的数据
			// 发送网络请求
			HttpUtils httpUtils = new HttpUtils();
			// http://localhost:8080/GooglePlayServer/home?index=0
			String url = URLS.BASEURL + "home";
			RequestParams parmas = new RequestParams();
			parmas.addQueryStringParameter("index", index + "");// 20
			ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, parmas);
			String result = responseStream.readString();
			// gson
			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(result, HomeBean.class);
			if (homeBean == null) {
				return null;
			}
			if (homeBean.list == null || homeBean.list.size() == 0) {
				return null;
			}
			return homeBean.list;*/
		/*=============== 协议简单封装之后 ===============*/
		HomeBean homeBean = mProtocol.loadData(mDatas.size());
		if (homeBean == null) {
			return null;
		}
		if (homeBean.list == null || homeBean.list.size() == 0) {
			return null;
		}
		return homeBean.list;
	}

	@Override
	public void onResume() {
		// 重新添加监听
		if (mAdapter != null) {
			List<AppItemHolder> appItemHolders = mAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().addObserver(appItemHolder);//重新添加
			}
			// 手动刷新-->重新获取状态,然后更新ui
			mAdapter.notifyDataSetChanged();
		}
		
		super.onResume();
	}

	@Override
	public void onPause() {
		// 移除监听
		if (mAdapter != null) {
			List<AppItemHolder> appItemHolders = mAdapter.getAppItemHolders();
			for (AppItemHolder appItemHolder : appItemHolders) {
				DownloadManager.getInstance().deleteObserver(appItemHolder);//删除
			}
		}
		super.onPause();
	}

}
