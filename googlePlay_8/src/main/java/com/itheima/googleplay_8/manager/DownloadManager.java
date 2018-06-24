package com.itheima.googleplay_8.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import com.itheima.googleplay_8.bean.AppInfoBean;
import com.itheima.googleplay_8.conf.Constants.URLS;
import com.itheima.googleplay_8.factory.ThreadPoolFactory;
import com.itheima.googleplay_8.utils.CommonUtils;
import com.itheima.googleplay_8.utils.FileUtils;
import com.itheima.googleplay_8.utils.IOUtils;
import com.itheima.googleplay_8.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author  Administrator
 * @time 	2015-7-20 上午8:46:48
 * @des	下载管理器,需要时刻记录当前的状态,暴露当前状态
 *
 * @version $Rev: 50 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 14:40:50 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class DownloadManager {

	public static final int				STATE_UNDOWNLOAD		= 0;									// 未下载
	public static final int				STATE_DOWNLOADING		= 1;									// 下载中
	public static final int				STATE_PAUSEDOWNLOAD		= 2;									// 暂停下载
	public static final int				STATE_WAITINGDOWNLOAD	= 3;									// 等待下载
	public static final int				STATE_DOWNLOADFAILED	= 4;									// 下载失败
	public static final int				STATE_DOWNLOADED		= 5;									// 下载完成
	public static final int				STATE_INSTALLED			= 6;									// 已安装

	public static DownloadManager		instance;
	// 记录正在下载的一些downLoadInfo
	public Map<String, DownLoadInfo>	mDownLoadInfoMaps		= new HashMap<String, DownLoadInfo>();

	private DownloadManager() {

	}

	public static DownloadManager getInstance() {
		if (instance == null) {
			synchronized (DownloadManager.class) {
				if (instance == null) {
					instance = new DownloadManager();
				}
			}
		}
		return instance;
	}

	/**用户点击了下载按钮*/
	public void downLoad(DownLoadInfo info) {
		mDownLoadInfoMaps.put(info.packageName, info);

		/*############### 当前状态: 未下载 ###############*/
		info.state = STATE_UNDOWNLOAD;
		notifyObservers(info);
		/*#######################################*/

		/*############### 当前状态: 等待状态 ###############*/
		info.state = STATE_WAITINGDOWNLOAD;
		notifyObservers(info);
		/*#######################################*/
		// 得到线程池,执行任务
		DownLoadTask task = new DownLoadTask(info);
		info.task = task;// downInfo身上的task赋值
		ThreadPoolFactory.getDownLoadPool().execute(task);
	}

	class DownLoadTask implements Runnable {
		DownLoadInfo	mInfo;

		public DownLoadTask(DownLoadInfo info) {
			super();
			mInfo = info;
		}

		@Override
		public void run() {
			// 正在发起网络请求下载apk
			// http://localhost:8080/GooglePlayServer/download?
			// name=app/com.itheima.www/com.itheima.www.apk&range=0
			try {
				/*############### 当前状态: 下载中 ###############*/
				mInfo.state = STATE_DOWNLOADING;
				notifyObservers(mInfo);
				/*#######################################*/

				long initRange = 0;
				File saveApk = new File(mInfo.savePath);
				if (saveApk.exists()) {
					initRange = saveApk.length();// 未下载完成的apk已有的长度
				}
				mInfo.curProgress = initRange;// ③处理初始进度
				// 下载地址
				String url = URLS.DOWNLOADBASEURL;
				HttpUtils httpUtils = new HttpUtils();
				// 相关参数
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("name", mInfo.downloadUrl);
				params.addQueryStringParameter("range", initRange + "");// ①处理请求
				ResponseStream responseStream = httpUtils.sendSync(HttpMethod.GET, url, params);
				if (responseStream.getStatusCode() == 200) {
					InputStream in = null;
					FileOutputStream out = null;
					boolean isPause = false;
					try {
						in = responseStream.getBaseStream();
						File saveFile = new File(mInfo.savePath);
						out = new FileOutputStream(saveFile, true);// ②处理文件的写入

						byte[] buffer = new byte[1024];
						int len = -1;

						while ((len = in.read(buffer)) != -1) {
							if (mInfo.state == STATE_PAUSEDOWNLOAD) {
								isPause = true;
								break;
							}
							out.write(buffer, 0, len);
							mInfo.curProgress += len;
							/*############### 当前状态: 下载中 ###############*/
							mInfo.state = STATE_DOWNLOADING;
							notifyObservers(mInfo);
							/*#######################################*/
						}

						if (isPause) {// 用户暂停了下载走到这里来了
							/*############### 当前状态: 暂停 ###############*/
							mInfo.state = STATE_PAUSEDOWNLOAD;
							notifyObservers(mInfo);
							/*#######################################*/
						} else {// 下载完成走到这里来
							/*############### 当前状态: 下载完成 ###############*/
							mInfo.state = STATE_DOWNLOADED;
							notifyObservers(mInfo);
							/*#######################################*/
						}
					} finally {
						IOUtils.close(out);
						IOUtils.close(in);
					}

				} else {
					/*############### 当前状态: 下载失败 ###############*/
					mInfo.state = STATE_DOWNLOADFAILED;
					notifyObservers(mInfo);
					/*#######################################*/
				}
			} catch (Exception e) {
				e.printStackTrace();
				/*############### 当前状态: 下载失败 ###############*/
				mInfo.state = STATE_DOWNLOADFAILED;
				notifyObservers(mInfo);
				/*#######################################*/
			}

		}
	}

	/**
	 * @des 暴露当前状态,也就是需要提供downLoadInfo
	 * @call 外界需要知道最新的state的时候
	 */
	public DownLoadInfo getDownLoadInfo(AppInfoBean data) {
		// 已安装
		if (CommonUtils.isInstalled(UIUtils.getContext(), data.packageName)) {
			DownLoadInfo info = generateDownLoadInfo(data);
			info.state = STATE_INSTALLED;// 已安装
			return info;
		}
		// 下载完成
		DownLoadInfo info = generateDownLoadInfo(data);
		File saveApk = new File(info.savePath);
		if (saveApk.exists()) {// 如果存在我们的下载目录里面
			if (saveApk.length() == data.size) {
				info.state = STATE_DOWNLOADED;// 下载完成
				return info;
			}
		}
		/**
		下载中	
		暂停下载	
		等待下载	
		下载失败 
		 */
		DownLoadInfo downLoadInfo = mDownLoadInfoMaps.get(data.packageName);
		if (downLoadInfo != null) {
			return downLoadInfo;
		}

		// 未下载
		DownLoadInfo tempInfo = generateDownLoadInfo(data);
		tempInfo.state = STATE_UNDOWNLOAD;// 未下载
		return tempInfo;
	}

	/**
	 * 根据AppInfoBean生成一个DownLoadInfo,进行一些常规的赋值,也就是对一些常规属性赋值(除了state之外的属性)
	 */
	public DownLoadInfo generateDownLoadInfo(AppInfoBean data) {
		String dir = FileUtils.getDir("download");// sdcard/android/data/包名/download
		File file = new File(dir, data.packageName + ".apk");// sdcard/android/data/包名/download/com.itheima.www.apk
		// 保存路径
		String savePath = file.getAbsolutePath();// sdcard/android/data/包名/download/com.itheima.www.apk

		// 初始化一个downLoadInfo
		DownLoadInfo info = new DownLoadInfo();
		// 相关赋值
		info.savePath = savePath;
		info.downloadUrl = data.downloadUrl;
		info.packageName = data.packageName;
		info.max = data.size;
		info.curProgress = 0;
		return info;
	}

	/**暂停下载*/
	public void pause(DownLoadInfo info) {
		/*############### 当前状态: 暂停 ###############*/
		info.state = STATE_PAUSEDOWNLOAD;
		notifyObservers(info);
		/*#######################################*/
	}
	/**取消下载*/
	public void cancel(DownLoadInfo info) {
		Runnable task = info.task;
		// 找到线程池,移除任务
		ThreadPoolFactory.getDownLoadPool().removeTask(task);
		
		/*############### 当前状态: 未下载 ###############*/
		info.state = STATE_UNDOWNLOAD;
		notifyObservers(info);
		/*#######################################*/
	}

	/*=============== 自定义观察者设计模式  begin ===============*/
	public interface DownLoadObserver {
		void onDownLoadInfoChange(DownLoadInfo info);
	}

	List<DownLoadObserver>	downLoadObservers	= new LinkedList<DownLoadObserver>();

	/**添加观察者*/
	public void addObserver(DownLoadObserver observer) {
		if (observer == null) {
			throw new NullPointerException("observer == null");
		}
		synchronized (this) {
			if (!downLoadObservers.contains(observer))
				downLoadObservers.add(observer);
		}
	}

	/**删除观察者*/
	public synchronized void deleteObserver(DownLoadObserver observer) {
		downLoadObservers.remove(observer);
	}

	/**通知观察者数据改变*/
	public void notifyObservers(DownLoadInfo info) {
		for (DownLoadObserver observer : downLoadObservers) {
			observer.onDownLoadInfoChange(info);
		}
	}

	/*=============== 自定义观察者设计模式  end ===============*/

}
