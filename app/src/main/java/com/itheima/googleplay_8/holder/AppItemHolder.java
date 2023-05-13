package com.itheima.googleplay_8.holder;

import java.io.File;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;
import com.itheima.googleplay_8.base.BaseHolder;
import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.manager.DownLoadInfo;
import com.itheima.googleplay_8.manager.DownloadManager;
import com.itheima.googleplay_8.manager.DownloadManager.DownLoadObserver;
import com.itheima.googleplay_8.utils.BitmapHelper;
import com.itheima.googleplay_8.utils.CommonUtils;
import com.itheima.googleplay_8.utils.PrintDownLoadInfo;
import com.itheima.googleplay_8.utils.StringUtils;
import com.itheima.googleplay_8.utils.UIUtils;
import com.itheima.googleplay_8.views.CircleProgressView;

/**
 * @author  Administrator
 * @time 	2015-7-16 上午10:11:25
 * @des	TODO
 *
 * @version $Rev: 51 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 16:22:06 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class AppItemHolder extends BaseHolder<AppInfoBean> implements OnClickListener, DownLoadObserver {

	private ImageView			mIvIcon;

	private RatingBar			mRbStars;

	private TextView			mTvDes;

	private TextView			mTvSize;

	private TextView			mTvTitle;

	private CircleProgressView	mCircleProgressView;

	private AppInfoBean	mData;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);
		mIvIcon = view.findViewById(R.id.item_appinfo_iv_icon);
		mRbStars = view.findViewById(R.id.item_appinfo_rb_stars);
		mTvDes = view.findViewById(R.id.item_appinfo_tv_des);
		mTvSize = view.findViewById(R.id.item_appinfo_tv_size);
		mTvTitle = view.findViewById(R.id.item_appinfo_tv_title);
		mCircleProgressView = view.findViewById(R.id.item_appinfo_circleprogressview);

		mCircleProgressView.setOnClickListener(this);
		return view;
	}

	@Override
	public void refreshHolderView(AppInfoBean data) {
		// 清除复用convertView之后的progress效果
		mCircleProgressView.setProgress(0);
		
		

		mData = data;
		mTvDes.setText(data.des);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mTvTitle.setText(data.name);

		mIvIcon.setImageResource(R.drawable.ic_default);// 默认图片
		// BitmapUtils bitmapUtils = new BitmapUtils(UIUtils.getContext());
		// http://localhost:8080/GooglePlayServer/image?name=
		// data.iconUrl
		String uri = URLS.IMAGEBASEURL + data.iconUrl;
		BitmapHelper.display(mIvIcon, uri);

		mRbStars.setRating(data.stars);

		/*=============== 根据不同的状态给用户提示 ===============*/
		DownLoadInfo info = DownloadManager.getInstance().getDownLoadInfo(data);
		refreshCircleProgressViewUI(info);

	}

	public void refreshCircleProgressViewUI(DownLoadInfo info) {
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
			mCircleProgressView.setNote("下载");
			mCircleProgressView.setIcon(R.drawable.ic_download);
			break;
		case DownloadManager.STATE_DOWNLOADING:// 下载中
			mCircleProgressView.setProgressEnable(true);
			mCircleProgressView.setMax(info.max);
			mCircleProgressView.setProgress(info.curProgress);
			int progress = (int) (info.curProgress * 100.f / info.max + .5f);
			mCircleProgressView.setNote(progress + "%");
			mCircleProgressView.setIcon(R.drawable.ic_pause);
			break;
		case DownloadManager.STATE_PAUSEDOWNLOAD:// 暂停下载
			mCircleProgressView.setNote("继续下载");
			mCircleProgressView.setIcon(R.drawable.ic_resume);
			break;
		case DownloadManager.STATE_WAITINGDOWNLOAD:// 等待下载
			mCircleProgressView.setNote("等待中...");
			mCircleProgressView.setIcon(R.drawable.ic_pause);
			break;
		case DownloadManager.STATE_DOWNLOADFAILED:// 下载失败
			mCircleProgressView.setNote("重试");
			mCircleProgressView.setIcon(R.drawable.ic_redownload);
			break;
		case DownloadManager.STATE_DOWNLOADED:// 下载完成
			mCircleProgressView.setProgressEnable(false);
			mCircleProgressView.setNote("安装");
			mCircleProgressView.setIcon(R.drawable.ic_install);
			break;
		case DownloadManager.STATE_INSTALLED:// 已安装
			mCircleProgressView.setNote("打开");
			mCircleProgressView.setIcon(R.drawable.ic_install);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_appinfo_circleprogressview:

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
				refreshCircleProgressViewUI(info);
			}
		});
	}

}
