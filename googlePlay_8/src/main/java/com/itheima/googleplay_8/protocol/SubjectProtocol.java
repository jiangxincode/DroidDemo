package com.itheima.googleplay_8.protocol;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.SubjectInfoBean;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午11:04:40
 * @des	TODO
 *
 * @version $Rev: 30 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 11:15:47 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfoBean>> {

	@Override
	public String getInterfaceKey() {
		return "subject";
	}

	@Override
	public List<SubjectInfoBean> parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<SubjectInfoBean>>() {
		}.getType());
	}

}
