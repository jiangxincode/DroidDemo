package com.itheima.googleplay_8.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.itheima.googleplay_8.base.BaseFragment;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.base.SuperBaseAdapter;
import com.itheima.googleplay_8.bean.CategoryInfoBean;
import com.itheima.googleplay_8.factory.ListViewFactory;
import com.itheima.googleplay_8.holder.CategoryItemHolder;
import com.itheima.googleplay_8.holder.CategoryTitleHolder;
import com.itheima.googleplay_8.protocol.CategoryProtocol;

/**
 * @author  Administrator
 * @time 	2015-7-15 下午3:11:37
 * @des	TODO
 *
 * @version $Rev: 37 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:55:48 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class CategoryFragment extends BaseFragment {

	private List<CategoryInfoBean>	mDatas;

	@Override
	public LoadedResult initData() {// 真正加载数据
		CategoryProtocol protocol = new CategoryProtocol();
		try {
			mDatas = protocol.loadData(0);
			return checkState(mDatas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	@Override
	public View initSuccessView() {
		// 返回成功的视图
		ListView listView = ListViewFactory.createListView();
		listView.setAdapter(new CategoryAdapter(listView, mDatas));
		return listView;
	}

	class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

		public CategoryAdapter(AbsListView absListView, List<CategoryInfoBean> dataSource) {
			super(absListView, dataSource);
		}

		@Override
		public BaseHolder<CategoryInfoBean> getSpecialHolder(int position) {
			CategoryInfoBean categoryInfoBean = mDatas.get(position);
			// 如果是title-->titleHolder
			if (categoryInfoBean.isTitle) {
				return new CategoryTitleHolder();
			} else {// 如果不是title-->itemHolder
				return new CategoryItemHolder();
			}
		}

		@Override
		public int getViewTypeCount() {
			// TODO
			return super.getViewTypeCount() + 1;// 2+1 = 3;
		}

		@Override
		public int getNormalViewType(int position) {// 0 1 2 range 0--getViewTypeCount()-1
			CategoryInfoBean categoryInfoBean = mDatas.get(position);
			if (categoryInfoBean.isTitle) {
				return super.getNormalViewType(position) + 1;// 1
			} else {
				return super.getNormalViewType(position);// 1
			}
		}
	}

}
