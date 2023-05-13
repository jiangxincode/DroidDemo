package com.itheima.googleplay_8.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_8.base.BaseProtocol;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午8:59:50
 * @des	TODO
 *
 * @version $Rev: 38 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 09:59:23 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class RecommendProtocol extends BaseProtocol<List<String>> {

	@Override
	public String getInterfaceKey() {
		// TODO
		return "recommend";
	}

	@Override
	public List<String> parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<String>>() {
		}.getType());
	}

}
