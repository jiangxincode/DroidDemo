package com.itheima.googleplay_8.bean;

import java.util.List;

/**
 * @author  Administrator
 * @time 	2015-7-18 下午3:59:47
 * @des	TODO
 *
 * @version $Rev: 35 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:19:02 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class CategoryInfoBean {
	public String	title;		// title
	public String	name1;		// 休闲
	public String	name2;		// 棋牌
	public String	name3;		// 益智
	public String	url1;		// image/category_game_0.jpg
	public String	url2;		// image/category_game_1.jpg
	public String	url3;		// image/category_game_2.jpg
	public boolean	isTitle;	// 自己添加的一个属性,确定是否是title

	@Override
	public String toString() {
		return "CategoryInfoBean [title=" + title + ", name1=" + name1 + ", name2=" + name2 + ", name3=" + name3
				+ ", url1=" + url1 + ", url2=" + url2 + ", url3=" + url3 + ", isTitle=" + isTitle + "]";
	}

}
