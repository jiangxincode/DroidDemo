package com.itheima.googleplay_8.holder;

import java.io.File;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itheima.googleplay_8.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.manager.DownLoadInfo;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.manager.DownloadManager.DownLoadObserver;
import com.itheima.googleplay_8.utils.CommonUtils;
import com.itheima.googleplay_8.utils.PrintDownLoadInfo;
import com.itheima.googleplay_8.utils.UIUtils;
import com.itheima.googleplay_8.views.ProgressButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午11:05:30
 * @des	TODO
 *
 * @version $Rev: 50 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 14:40:50 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfoBean> implements OnClickListener, DownLoadObserver {
	@ViewInject(R.id.app_detail_download_btn_share)
	Button				mBtnShare;
	@ViewInject(R.id.app_detail_download_btn_favo)
	Button				mBtnFavo;
	@ViewInject(R.id.app_detail_download_btn_download)
	ProgressButton		mProgressButton;
	private AppInfoBean	mData;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_detail_bottom, null);
		ViewUtils.inject(this, view);
		mBtnShare.setOnClickListener(this);
		mBtnFavo.setOnClickListener(this);
		mProgressButton.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		mData = data;
		/*=============== 根据不同的状态给用户提示 ===============*/
		DownLoadInfo info = DownloadManager.getInstance().getDownLoadInfo(data);
		refreshProgressBtnUI(info);
	}

	public void refreshProgressBtnUI(DownLoadInfo info) {

		mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
		switch (info.state) {
		/**
		 状态(编程记录)  	|  给用户的提示(ui展现)   
		----------------|----------------------
		未下载			|下载				    
		下载中			|显示进度条		   		 
		暂停下载			|继续下载				   
		等待下载			|等待中...				 
		下载失败 			|重试					 
		下载完成 			|安装					 
		已安装 			|打开					 
		 */
		case DownloadManager.STATE_UNDOWNLOAD:// 未下载
			mProgressButton.setText("下载");
			break;
		case DownloadManager.STATE_DOWNLOADING:// 下载中
			mProgressButton.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
			mProgressButton.setProgressEnable(true);
			mProgressButton.setMax(info.max);
			mProgressButton.setProgress(info.curProgress);
			int progress = (int) (info.curProgress * 100.f / info.max + .5f);
			mProgressButton.setText(progress + "%");
			break;
		case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
			mProgressButton.setText("继续下载");
			break;
		case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
			mProgressButton.setText("等待中...");
			break;
		case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
			mProgressButton.setText("重试");
			break;
		case DownloadManager.STATE_DOWNLOADED:// 下载完成
			mProgressButton.setProgressEnable(false);
			mProgressButton.setText("安装");
			break;
		case DownloadManager.STATE_INSTALLED:// 已安装
			mProgressButton.setText("打开");
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_detail_download_btn_download:

			DownLoadInfo info = DownloadManager.getInstance().getDownLoadInfo(mData);

			switch (info.state) {
			/**
			状态(编程记录)     | 用户行为(触发操作) 
			----------------| -----------------
			未下载			| 去下载
			下载中			| 暂停下载
			暂停下载			| 断点继续下载
			等待下载			| 取消下载
			下载失败 			| 重试下载
			下载完成 			| 安装应用
			已安装 			| 打开应用
			 */
			case DownloadManager.STATE_UNDOWNLOAD:// 未下载
				doDownLoad(info);
				break;
			case DownloadManager.STATE_DOWNLOADING:// 下载中
				pauseDownLoad(info);
				break;
			case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
				doDownLoad(info);
				break;
			case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
				cancelDownLoad(info);
				break;
			case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
				doDownLoad(info);
				break;
			case DownloadManager.STATE_DOWNLOADED:// 下载完成
				installApk(info);
				break;
			case DownloadManager.STATE_INSTALLED:// 已安装
				openApk(info);
				break;

			default:
				break;
			}

			break;
		default:
			break;
		}
	}

	/**
	 * 打开应用
	 * @param info
	 */
	private void openApk(DownLoadInfo info) {
		CommonUtils.openApp(UIUtils.getContext(), info.packageName);
	}

	/**
	 * 安装应用
	 * @param info
	 */
	private void installApk(DownLoadInfo info) {
		File apkFile = new File(info.savePath);
		CommonUtils.installApp(UIUtils.getContext(), apkFile);
	}

	/**
	 * 取消下载
	 * @param info
	 */
	private void cancelDownLoad(DownLoadInfo info) {
		DownloadManager.getInstance().cancel(info);

	}

	/**
	 * 暂停下载
	 * @param info
	 */
	private void pauseDownLoad(DownLoadInfo info) {
		DownloadManager.getInstance().pause(info);
	}

	/**
	 * 开始下载
	 * @param info
	 */
	private void doDownLoad(DownLoadInfo info) {
		/*=============== 根据不同的状态触发不同的操作 ===============*/
		/*// 下载apk放置的目录
		String dir = FileUtils.getDir("download");// sdcard/android/data/包名/download
		File file = new File(dir, mData.packageName + ".apk");// sdcard/android/data/包名/download/com.itheima.www.apk
		// 保存路径
		String savePath = file.getAbsolutePath();// sdcard/android/data/包名/download/com.itheima.www.apk

		DownLoadInfo info = new DownLoadInfo();
		info.savePath = savePath;
		info.downloadUrl = mData.downloadUrl;
		info.packageName = mData.packageName;*/

		DownloadManager.getInstance().downLoad(info);
	}

	/*=============== 收到数据改变,更新ui ===============*/
	@Override
	public void onDownLoadInfoChange(final DownLoadInfo info) {
		// 过滤DownLoadInfo
		if (!info.packageName.equals(mData.packageName)) {
			return;
		}

		PrintDownLoadInfo.printDownLoadInfo(info);
		UIUtils.postTaskSafely(new Runnable() {
			@Override
			public void run() {
				refreshProgressBtnUI(info);
			}
		});
	}

	public void addObserverAndRerefresh() {
		DownloadManager.getInstance().addObserver(this);
		// 手动刷新
		DownLoadInfo downLoadInfo = DownloadManager.getInstance().getDownLoadInfo(mData);
		DownloadManager.getInstance().notifyObservers(downLoadInfo);// 方式一
		// refreshProgressBtnUI(downLoadInfo);//方式二
	}

}
