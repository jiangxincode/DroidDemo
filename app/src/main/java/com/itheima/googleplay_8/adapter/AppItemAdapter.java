package com.itheima.googleplay_8.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.itheima.googleplay_8.activity.DetailActivity;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.base.SuperBaseAdapter;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.holder.AppItemHolder;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午9:53:37
 * @des	TODO
 *
 * @version $Rev: 51 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 16:22:06 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class AppItemAdapter extends SuperBaseAdapter<AppInfoBean> {
	private List<AppItemHolder>	mAppItemHolders	= new LinkedList<AppItemHolder>();

	public List<AppItemHolder> getAppItemHolders() {
		return mAppItemHolders;
	}

	public AppItemAdapter(AbsListView absListView, List<AppInfoBean> dataSource) {
		super(absListView, dataSource);
	}

	@Override
	public BaseHolder<AppInfoBean> getSpecialHolder(int position) {

		AppItemHolder appItemHolder = new AppItemHolder();

		mAppItemHolders.add(appItemHolder);//保存Adapter里面对应的Holder
		
		// 初始化的时候把AppItemHolder加到观察者集合里面去
		DownloadManager.getInstance().addObserver(appItemHolder);

		return appItemHolder;
	}

	@Override
	public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
		goToDetailActivity(mDataSource.get(position).packageName);
	}

	private void goToDetailActivity(String packageName) {
		Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("packageName", packageName);
		UIUtils.getContext().startActivity(intent);
	}
}
