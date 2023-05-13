package com.itheima.googleplay_8.protocol;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.AppInfoBean;

/**
 * @author  Administrator
 * @time 	2015-7-18 上午8:57:24
 * @des	TODO
 *
 * @version $Rev: 26 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 09:26:07 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class GameProtocol extends BaseProtocol<List<AppInfoBean>> {

	@Override
	public String getInterfaceKey() {
		// TODO
		return "game";
	}

	@Override
	public List<AppInfoBean> parseJson(String jsonString) {
		Gson gson = new Gson();
		/*=============== 泛型解析 ===============*/
		// return gson.fromJson(jsonString, new TypeToken<List<AppInfoBean>>() {
		// }.getType());
		/*=============== 结点解析 ===============*/

		List<AppInfoBean> appInfoBeans = new ArrayList<AppInfoBean>();
		// 获得json的解析器
		JsonParser parser = new JsonParser();

		JsonElement rootJsonElement = parser.parse(jsonString);

		// JsonElement-->转换成jsonArray
		JsonArray rootJsonArray = rootJsonElement.getAsJsonArray();

		// 遍历jsonArray
		for (JsonElement itemJsonElement : rootJsonArray) {
			// jsonElement-->转换成JsonObject
			JsonObject itemJsonObject = itemJsonElement.getAsJsonObject();

			// 得到具体的jsonPrimitivie
			JsonPrimitive desPrimitivie = itemJsonObject.getAsJsonPrimitive("des");
			// jsonPrimitivie-->转换成具体的类型
			String des = desPrimitivie.getAsString();

			// 得到具体的jsonPrimitivie
			JsonPrimitive downloadUrlPrimitivie = itemJsonObject.getAsJsonPrimitive("downloadUrl");
			// jsonPrimitivie-->转换成具体的类型
			String downloadUrl = downloadUrlPrimitivie.getAsString();

			String iconUrl = itemJsonObject.get("iconUrl").getAsString();
			long id = itemJsonObject.get("id").getAsLong();
			String name = itemJsonObject.get("name").getAsString();
			String packageName = itemJsonObject.get("packageName").getAsString();
			long size = itemJsonObject.get("size").getAsLong();
			float stars = itemJsonObject.get("stars").getAsFloat();

			AppInfoBean info = new AppInfoBean();
			info.des = des;
			info.downloadUrl = downloadUrl;
			info.iconUrl = iconUrl;
			info.id = id;
			info.name = name;
			info.packageName = packageName;
			info.size = size;
			info.stars = stars;
			// 添加到集合
			appInfoBeans.add(info);

		}

		// 返回结果
		return appInfoBeans;
	}
}
