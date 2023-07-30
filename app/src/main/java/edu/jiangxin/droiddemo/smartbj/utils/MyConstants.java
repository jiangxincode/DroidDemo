package edu.jiangxin.droiddemo.smartbj.utils;

public interface MyConstants {
    //apk发布修改该ip ip 或者 域名 www.henhao.com/zhbj/categories.json
    String SERVERURL = "http://192.168.0.103:8080/zhbj";
    String NEWSCENTERURL = SERVERURL + "/categories.json";
    String PHOTOSURL = SERVERURL + "/photos/photos_1.json";
    String CONFIGFILE = "cachevalue";//sp的文件名
    String ISSETUP = "issetup";//是否设置向导界面设置过数据
    String READNEWSIDS = "readnewsids";//保存读过的新闻id
}
