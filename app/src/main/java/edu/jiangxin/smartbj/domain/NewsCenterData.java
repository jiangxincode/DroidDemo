package edu.jiangxin.smartbj.domain;

import java.util.List;

/**
 * 新闻中心的数据封装
 */
public class NewsCenterData {
    public int retcode;

    public List<NewsData> data;//新闻的数据

    public class NewsData {
        public List<ViewTagData> children;

        /**
         * 新闻中的页签的数据
         */
        public class ViewTagData {
            public String id;
            public String title;
            public int type;
            public String url;
        }

        public String id;

        public String title;
        public int type;


        public String url;
        public String url1;

        public String dayurl;
        public String excurl;

        public String weekurl;
    }

    public List<String> extend;

}
