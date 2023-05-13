package com.itheima.googleplay_8.protocol;

import com.google.gson.Gson;
import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.HomeBean;

/**
 * @author  Administrator
 * @time 	2015-7-16 下午4:14:05
 * @des	TODO
 *
 * @version $Rev: 25 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-16 17:13:38 +0800 (星期四, 16 七月 2015) $
 * @updateDes TODO
 */
public class HomeProtocol extends BaseProtocol<HomeBean> {

	@Override
	public String getInterfaceKey() {
		return "home";
	}

	@Override
	public HomeBean parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, HomeBean.class);
	}

}
