package com.itheima.googleplay_8.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.googleplay_8.base.BaseProtocol;
import com.itheima.googleplay_8.bean.CategoryInfoBean;
import com.itheima.googleplay_8.utils.LogUtils;

/**
 * @author  Administrator
 * @time 	2015-7-18 下午3:58:41
 * @des	TODO
 *
 * @version $Rev: 35 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-18 16:19:02 +0800 (星期六, 18 七月 2015) $
 * @updateDes TODO
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>> {

	@Override
	public String getInterfaceKey() {
		// TODO
		return "category";
	}

	@Override
	public List<CategoryInfoBean> parseJson(String jsonString) {
		/*=============== android sdk 自带的json解析 ===============*/
		List<CategoryInfoBean> categoryInfoBeans = new ArrayList<CategoryInfoBean>();
		try {
			JSONArray rootJsonArray = new JSONArray(jsonString);

			// 遍历jsonArray
			for (int i = 0; i < rootJsonArray.length(); i++) {

				JSONObject itemJsonObject = rootJsonArray.getJSONObject(i);

				String title = itemJsonObject.getString("title");
				CategoryInfoBean titleBean = new CategoryInfoBean();
				titleBean.title = title;
				titleBean.isTitle = true;
				categoryInfoBeans.add(titleBean);

				JSONArray infosJsonArray = itemJsonObject.getJSONArray("infos");
				// 遍历infosJsonArray
				for (int j = 0; j < infosJsonArray.length(); j++) {
					JSONObject infoJsonObject = infosJsonArray.getJSONObject(j);
					String name1 = infoJsonObject.getString("name1");
					String name2 = infoJsonObject.getString("name2");
					String name3 = infoJsonObject.getString("name3");
					String url1 = infoJsonObject.getString("url1");
					String url2 = infoJsonObject.getString("url2");
					String url3 = infoJsonObject.getString("url3");

					CategoryInfoBean infoBean = new CategoryInfoBean();
					infoBean.name1 = name1;
					infoBean.name2 = name2;
					infoBean.name3 = name3;
					infoBean.url1 = url1;
					infoBean.url2 = url2;
					infoBean.url3 = url3;

					categoryInfoBeans.add(infoBean);
				}

			}

			LogUtils.printList(categoryInfoBeans);

			return categoryInfoBeans;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
