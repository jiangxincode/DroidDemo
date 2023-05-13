package com.itheima.googleplay_8.protocol;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.AppInfoBean;

/**
 * @author  Administrator
 * @time 	2015-7-19 上午10:12:41
 * @des	TODO
 *
 * @version $Rev: 39 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 10:33:29 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public class DetailProtocol extends BaseProtocol<AppInfoBean> {
	private String	mPackageName;

	public DetailProtocol(String packageName) {
		super();
		mPackageName = packageName;
	}

	@Override
	public String getInterfaceKey() {
		// TODO
		return "detail";
	}

	@Override
	public AppInfoBean parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, AppInfoBean.class);
	}

	@Override
	public Map<String, String> getExtraParmas() {
		Map<String, String> extraParmas = new HashMap<String, String>();
		extraParmas.put("packageName", mPackageName);
		return extraParmas;
	}

}
