package com.itheima.googleplay_8.fragment;

import java.util.List;

import com.itheima.googleplay_8.adapter.AppItemAdapter;
import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.factory.ListViewFactory;
import com.itheima.googleplay_8.holder.AppItemHolder;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.protocol.GameProtocol;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

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
public class GameFragment extends BaseFragment {

	private List<AppInfoBean>	mDatas;
	private GameProtocol		mProtocol;
	private GameAdapter	mAdapter;

	@Override
	public LoadedResult initData() {// 真正加载数据
		mProtocol = new GameProtocol();
		try {
			mDatas = mProtocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		ListView listView = ListViewFactory.createListView();

		mAdapter = new GameAdapter(listView, mDatas);
		listView.setAdapter(mAdapter);

		return listView;
	}

	class GameAdapter extends AppItemAdapter {
		public GameAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
			super(absListView, dataSource);
		}

		@Override
		public List<AppInfoBean> onLoadMore() throws Exception {
			// TODO
			return mProtocol.loadData(mDatas.size());
		}
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
