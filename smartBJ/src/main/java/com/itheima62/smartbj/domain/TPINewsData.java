

package com.itheima62.smartbj.domain;

import java.util.List;

/**
 * @author Administrator
 * @创建时间 2015-7-7 上午10:21:22
 * @描述 页签对应的新闻数据
 * 
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-07 10:35:57 +0800 (Tue, 07 Jul 2015) $ @ 当前版本: $Rev: 39 $
 */
public class TPINewsData
{
	public int				retcode;
	public TPINewsData_Data	data;

	public class TPINewsData_Data
	{
		public String								countcommenturl;
		public String								more;
		public String								title;
		public List<TPINewsData_Data_ListNewsData>	news;
		public List<TPINewsData_Data_TopicData>		topic;
		public List<TPINewsData_Data_LunBoData>		topnews;

		public class TPINewsData_Data_ListNewsData
		{
			public String	commentlist;
			public String	commenturl;
			public String	id;
			public String	listimage;
			public String	pubdate;
			public String	title;
			public String	type;
			public String	url;
		}

		public class TPINewsData_Data_TopicData
		{

			public String	description;
			public String	listimage;
			public String	id;
			public String	sort;
			public String	title;
			public String	url;
		}

		public class TPINewsData_Data_LunBoData
		{

			public String	comment;
			public String	commentlist;
			public String	commenturl;
			public String	id;
			public String	pubdate;
			public String	title;
			public String	topimage;
			public String	type;
			public String	url;

		}

	}
}
