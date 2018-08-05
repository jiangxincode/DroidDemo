package com.itheima62.smartbj.domain;

import java.util.List;

/**
 * @author Administrator
 * @创建时间 2015-7-6 上午11:06:16
 * @描述 新闻中心的数据封装
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-06 11:15:56 +0800 (Mon, 06 Jul 2015) $
 * @ 当前版本: $Rev: 25 $
 */
public class NewsCenterData
{
	public int retcode;
	
	public List<NewsData> data;//新闻的数据
	
	public class NewsData{
		public List<ViewTagData> children;
		
		/**
		 * @author Administrator
		 * @创建时间 2015-7-6 上午11:12:20
		 * @描述 新闻中的页签的数据
		 *
		 * @ svn提交者：$Author: gd $
		 * @ 提交时间: $Date: 2015-07-06 11:15:56 +0800 (Mon, 06 Jul 2015) $
		 * @ 当前版本: $Rev: 25 $
		
		 */
		public class ViewTagData{
			public String id;
			public String title	;
			public int type ;
			public String url;
		}
		
		public String id;
		
		public String title;
		public int type;
		
	
		public String url;
		public String url1;
		
		public String dayurl;	
		public String excurl;	
		
		public String weekurl;	 
	}
	
	public List<String> extend;
	
}
