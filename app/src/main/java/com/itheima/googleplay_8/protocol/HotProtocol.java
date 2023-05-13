package com.itheima.googleplay_8.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_8.base.BaseProtocol;

/**
 * @author  Administrator
 * @time 	2015-7-18 下午3:15:09
 * @des	TODO
 *
 * @version $Rev: 34 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 15:46:46 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class HotProtocol extends BaseProtocol<List<String>> {

	@Override
	public String getInterfaceKey() {
		// TODO
		return "hot";
	}

	@Override
	public List<String> parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<String>>() {
		}.getType());
	}

}
