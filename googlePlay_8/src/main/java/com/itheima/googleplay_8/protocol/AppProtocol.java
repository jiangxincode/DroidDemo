package com.itheima.googleplay_8.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.AppInfoBean;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午8:42:26
 * @des	TODO
 *
 * @version $Rev: 26 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 09:26:07 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class AppProtocol extends BaseProtocol<List<AppInfoBean>> {

	@Override
	public String getInterfaceKey() {
		// TODO
		return "app";
	}

	@Override
	public List<AppInfoBean> parseJson(String jsonString) {
		Gson gson = new Gson();

		/*=============== 泛型解析 ===============*/
		return gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>() {
		}.getType());

	}
}
