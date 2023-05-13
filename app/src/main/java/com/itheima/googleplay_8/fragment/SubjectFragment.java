package com.itheima.googleplay_8.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.base.SuperBaseAdapter;
import com.itheima.googleplay_8.bean.SubjectInfoBean;
import com.itheima.googleplay_8.factory.ListViewFactory;
import com.itheima.googleplay_8.holder.SubjectHolder;
import com.itheima.googleplay_8.protocol.SubjectProtocol;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:11:37
 * @des	TODO
 *
 * @version $Rev: 36 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:35:29 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class SubjectFragment extends BaseFragment {

	private List<SubjectInfoBean>	mDatas;

	@Override
	public LoadedResult initData() {// 真正加载数据
		SubjectProtocol protocol = new SubjectProtocol();
		try {
			mDatas = protocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		ListView listView = ListViewFactory.createListView();
		listView.setAdapter(new SubjectAdapter(listView, mDatas));
		return listView;
	}

	class SubjectAdapter extends SuperBaseAdapter<SubjectInfoBean> {

		public SubjectAdapter(AbsListView absListView, List<SubjectInfoBean> dataSource) {
			super(absListView, dataSource);
		}

		@Override
		public BaseHolder<SubjectInfoBean> getSpecialHolder(int position) {
			return new SubjectHolder();
		}

	}

}
