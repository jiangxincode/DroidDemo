package edu.jiangxin.droiddemo.common;

import android.net.Uri;

import static android.app.SearchManager.SUGGEST_URI_PATH_QUERY;


public class QuickSearchBoxContent {

    public static final String AUTHORITY = "edu.jiangxin.droiddemo.SEARCH_AUTHORITY";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SUGGEST_URI_PATH_QUERY);
}