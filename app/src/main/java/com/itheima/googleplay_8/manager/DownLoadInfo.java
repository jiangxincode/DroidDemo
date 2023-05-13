package com.itheima.googleplay_8.manager;

/**
 * @author  Administrator
 * @time 	2015-7-20 上午9:29:24
 * @des	TODO
 *
 * @version $Rev: 50 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-20 14:40:50 +0800 (星期一, 20 七月 2015) $
 * @updateDes TODO
 */
public class DownLoadInfo {
	public String	savePath;
	public String	downloadUrl;
	public int		state	= DownloadManager.STATE_UNDOWNLOAD; // 默认状态就是未下载
	public String	packageName;								// 包名
	public long		max;
	public long		curProgress;
	public Runnable	task;
}
