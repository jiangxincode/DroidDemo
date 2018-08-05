package com.itheima62.smartbj.utils;
/**
 * @author Administrator
 * @创建时间 2015-7-4 上午10:42:26
 * @描述 TODO
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-10 14:59:24 +0800 (Fri, 10 Jul 2015) $
 * @ 当前版本: $Rev: 62 $
 */
public interface MyConstants 
{
	//apk发布修改该ip ip 或者 域名 www.henhao.com/zhbj/categories.json
	String SERVERURL = "http://10.0.2.2:8080/zhbj";
	String NEWSCENTERURL = SERVERURL + "/categories.json";
	String PHOTOSURL = SERVERURL + "/photos/photos_1.json";
	String CONFIGFILE = "cachevalue";//sp的文件名
	String ISSETUP = "issetup";//是否设置向导界面设置过数据
	String READNEWSIDS = "readnewsids";//保存读过的新闻id
}
