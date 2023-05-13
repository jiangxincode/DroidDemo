package com.itheima.googleplay_8.activity;

import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseActivity;
import com.itheima.googleplay_8.base.LoadingPager;
import com.itheima.googleplay_8.base.LoadingPager.LoadedResult;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.holder.AppDetailBottomHolder;
import com.itheima.googleplay_8.holder.AppDetailDesHolder;
import com.itheima.googleplay_8.holder.AppDetailInfoHolder;
import com.itheima.googleplay_8.holder.AppDetailPicHolder;
import com.itheima.googleplay_8.holder.AppDetailSafeHolder;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.protocol.DetailProtocol;
import com.itheima.googleplay_8.utils.UIUtils;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午10:00:29
 * @des	TODO
 *
 * @version $Rev: 50 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 14:40:50 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class DetailActivity extends BaseActivity {

	private String					mPackageName;
	private AppInfoBean				mData;

	private FrameLayout						mContainerBottom;

	private FrameLayout						mContainerDes;

	private FrameLayout						mContainerInfo;

	private FrameLayout						mContainerPic;

	private FrameLayout						mContainerSafe;
	private LoadingPager			mLoadingPager;
	private AppDetailBottomHolder	mAppDetailBottomHolder;

	/*	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			init();
			initActionBar();
			initView();
			initData();

			// loadingpager
			// 视图显示
			// 数据加载
		}*/

	@Override
	public void init() {
		mPackageName = getIntent().getStringExtra("packageName");
	}

	@Override
	public void initView() {
		mLoadingPager = new LoadingPager(UIUtils.getContext()) {

			@Override
			public View initSuccessView() {
				return onLoadSuccessView();
			}

			@Override
			public LoadedResult initData() {
				return onInitData();
			}

		};
		setContentView(mLoadingPager);
	}

	@Override
	public void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("GooglePlay");
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void initData() {
		// 触发加载数据
		mLoadingPager.loadData();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private LoadedResult onInitData() {
		// 发起网络请求
		DetailProtocol protocol = new DetailProtocol(mPackageName);
		try {
			mData = protocol.loadData(0);

			if (mData == null) {
				return LoadedResult.ERROR;
			}

			return LoadedResult.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	private View onLoadSuccessView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.activity_detail, null);

		mContainerBottom = findViewById(R.id.app_detail_bottom);
		mContainerDes = findViewById(R.id.app_detail_des);
		mContainerInfo = findViewById(R.id.app_detail_info);
		mContainerPic = findViewById(R.id.app_detail_pic);
		mContainerSafe = findViewById(R.id.app_detail_safe);

		// 填充内容
		// 1.信息部分
		AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
		mContainerInfo.addView(appDetailInfoHolder.getHolderView());
		appDetailInfoHolder.setDataAndRefreshHolderView(mData);

		// 2.安全部分
		AppDetailSafeHolder appDetailSafeHolder = new AppDetailSafeHolder();
		mContainerSafe.addView(appDetailSafeHolder.getHolderView());
		appDetailSafeHolder.setDataAndRefreshHolderView(mData);

		// 3.截图部分
		AppDetailPicHolder appDetailPicHolder = new AppDetailPicHolder();
		mContainerPic.addView(appDetailPicHolder.getHolderView());
		appDetailPicHolder.setDataAndRefreshHolderView(mData);

		// 4.描述部分
		AppDetailDesHolder appDetailDesHolder = new AppDetailDesHolder();
		mContainerDes.addView(appDetailDesHolder.getHolderView());
		appDetailDesHolder.setDataAndRefreshHolderView(mData);

		mAppDetailBottomHolder = new AppDetailBottomHolder();
		mContainerBottom.addView(mAppDetailBottomHolder.getHolderView());
		mAppDetailBottomHolder.setDataAndRefreshHolderView(mData);

		DownloadManager.getInstance().addObserver(mAppDetailBottomHolder);
		return view;
	}

	@Override
	protected void onPause() {// 界面不可见的时候移除观察者
		if (mAppDetailBottomHolder != null) {
			DownloadManager.getInstance().deleteObserver(mAppDetailBottomHolder);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {// 界面可见的时候重新添加观察者
		if (mAppDetailBottomHolder != null) {
			// 开启监听的时候,手动的去获取一下最新的状态
			mAppDetailBottomHolder.addObserverAndRerefresh();
		}
		super.onResume();
	}

}
