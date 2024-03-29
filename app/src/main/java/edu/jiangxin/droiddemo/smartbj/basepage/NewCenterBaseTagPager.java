

package edu.jiangxin.droiddemo.smartbj.basepage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.droiddemo.smartbj.activity.MainActivity;
import edu.jiangxin.droiddemo.smartbj.domain.NewsCenterData;
import edu.jiangxin.droiddemo.smartbj.newscenterpage.BaseNewsCenterPage;
import edu.jiangxin.droiddemo.smartbj.newscenterpage.InteractBaseNewsCenterPage;
import edu.jiangxin.droiddemo.smartbj.newscenterpage.NewsBaseNewsCenterPage;
import edu.jiangxin.droiddemo.smartbj.newscenterpage.PhotosBaseNewsCenterPage;
import edu.jiangxin.droiddemo.smartbj.utils.MyConstants;
import edu.jiangxin.droiddemo.smartbj.utils.SpTools;
import edu.jiangxin.droiddemo.smartbj.view.LeftMenuFragment;
import edu.jiangxin.droiddemo.smartbj.newscenterpage.TopicBaseNewsCenterPage;

public class NewCenterBaseTagPager extends BaseTagPage {
    // 新闻中心要显示的四个页面
    private List<BaseNewsCenterPage> newsCenterPages = new ArrayList<BaseNewsCenterPage>();
    private NewsCenterData newsCenterData;
    private Gson gson;

    public NewCenterBaseTagPager(MainActivity context) {
        super(context);
    }

    @Override
    public void initData() {
        //1.获取本地数据
        String jsonCache = SpTools.getString(mainActivity, MyConstants.NEWSCENTERURL, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            //有本地数据
            //从本地取数据显示
            parseData(jsonCache);
        }

        // 2.获取网络数据
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, MyConstants.NEWSCENTERURL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // 访问数据成功
                        String jsonData = responseInfo.result;
                        //保存到本地一份
                        SpTools.setString(mainActivity, MyConstants.NEWSCENTERURL, jsonData);
                        // System.out.println(jsonData);
                        // 2.解析数据
                        parseData(jsonData);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        // 访问数据失败
                        System.out.println("网络请求数据失败：" + error);
                    }

                });

        super.initData();
    }

    /**
     * 解析json数据
     *
     * @param jsonData 从网络获取到的json数据
     */
    protected void parseData(String jsonData) {
        if (gson == null)
            gson = new Gson();

        newsCenterData = gson.fromJson(jsonData,
                NewsCenterData.class);

        // 3.数据的处理

        // 在这里给左侧菜单设置数据
        // System.out.println(newsCenterData.data.get(0).children.get(0).title);
        mainActivity.getLeftMenuFragment().setLeftMenuData(newsCenterData.data);
        //设置左侧菜单的监听回调
        mainActivity.getLeftMenuFragment().setOnSwitchPageListener(new LeftMenuFragment.OnSwitchPageListener() {

            @Override
            public void switchPage(int selectionIndex) {
                System.out.println("直接掉自己实现...............");
                NewCenterBaseTagPager.this.switchPage(selectionIndex);
            }
        });

        // 读取的数据封装到界面容器中，通过左侧菜单点击，显示不同的界面
        // 根据服务的数据 创建四个页面（按顺序）
        for (NewsCenterData.NewsData newsData : newsCenterData.data) {
            BaseNewsCenterPage newsPage = null;
            // 遍历四个新闻中心页面
            switch (newsData.type) {
                case 1:// 新闻页面
                    newsPage = new NewsBaseNewsCenterPage(mainActivity, newsCenterData.data.get(0).children);
                    break;
                case 10:// 专题页面
                    newsPage = new TopicBaseNewsCenterPage(mainActivity);
                    break;
                case 2:// 组图页面
                    newsPage = new PhotosBaseNewsCenterPage(mainActivity);
                    break;
                case 3:// 互动页面
                    newsPage = new InteractBaseNewsCenterPage(mainActivity);
                    break;

                default:
                    break;
            }

            // 添加新闻中心的页面到容器中
            newsCenterPages.add(newsPage);
        }

        // 控制四个页面的显示,默认选择第一个新闻页面
        switchPage(0);
    }


    /**
     * @param position 根据位置，动态显示不同的新闻中心页面
     */
    public void switchPage(int position) {
        BaseNewsCenterPage baseNewsCenterPage = newsCenterPages.get(position);

        //显示数据
        // 设置本page的标题
        tv_title.setText(newsCenterData.data.get(position).title);

        //移动掉原来画的内容
        fl_content.removeAllViews();

        //初始化数据
        baseNewsCenterPage.initData();

        //判断 如果是组图 listgrid切换的按钮显示

        if (baseNewsCenterPage instanceof PhotosBaseNewsCenterPage) {
            //组图
            // 显示listgrid切换的按钮显示
            ib_listOrGrid.setVisibility(View.VISIBLE);
            //给事件,点击做list和grid切换
            ib_listOrGrid.setTag(baseNewsCenterPage);
            ib_listOrGrid.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((PhotosBaseNewsCenterPage) ib_listOrGrid.getTag()).switchListViewOrGridView(ib_listOrGrid);
                }
            });

        } else {
            // 隐藏listgrid切换的按钮显示
            ib_listOrGrid.setVisibility(View.GONE);
        }

        // 替换掉白纸
        fl_content.addView(baseNewsCenterPage.getRoot());// 添加自己的内容到白纸上
    }

}
