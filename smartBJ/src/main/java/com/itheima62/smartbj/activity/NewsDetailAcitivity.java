package com.itheima62.smartbj.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itheima62.smartbj.R;
import com.itheima62.smartbj.utils.SharedAppUtils;

/**
 * @author Administrator
 * @创建时间 2015-7-10 上午9:57:22
 * @描述 新闻详情页面
 *
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-10 14:13:10 +0800 (Fri, 10 Jul 2015) $
 * @ 当前版本: $Rev: 61 $
 */
public class NewsDetailAcitivity extends Activity
{

	private ImageButton	ib_back;
	private ImageButton	ib_setTextSize;
	private ImageButton	ib_share;
	private WebView	wv_news;
	private ProgressBar	pb_loadingnews;
	private WebSettings	wv_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//初始化界面
		initView();
		
		initData();
		
		initEvent();
	}

	private void initEvent() {
		//创建三个按钮公用的监听器
		OnClickListener listener = new OnClickListener() {
			int textSizeIndex = 2;// 0. 超大号 1,大号  2 正常  3 小号  4 超小号
			private AlertDialog	dialog;
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.ib_base_content_back://返回键
					//关闭当前新闻页面
					finish();
					
					break;
				case R.id.ib_base_content_textsize://修改字体大小
					//通过对话框来修改字体大小 五种字体大小
					showChangeTextSizeDialog();
					//设置字体大小 wv_setting.setTextSize(TextSize.)
				case R.id.ib_base_content_share://分享
					SharedAppUtils.showShare(getApplicationContext());
					break;
				default:
					break;
				}
			}

			private void showChangeTextSizeDialog() {
				AlertDialog.Builder ab = new AlertDialog.Builder(NewsDetailAcitivity.this);
				ab.setTitle("改变字体大小");
				String[] textSize = new String[]{"超大号","大号","正常","小号","超小号"};
				ab.setSingleChoiceItems(textSize, textSizeIndex, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						textSizeIndex = which;
						setTextSize();
						
					}
				});
				dialog = ab.create();
				dialog.show();
			}

			private void setTextSize() {
				switch (textSizeIndex) {
				case 0://超大号
					wv_setting.setTextSize(TextSize.LARGEST);
					break;
				case 1: //大号
					wv_setting.setTextSize(TextSize.LARGER);
					break;
				case 2: //正常
					wv_setting.setTextSize(TextSize.NORMAL);
					break;
				case 3: //小号
					wv_setting.setTextSize(TextSize.SMALLER);
					break;
				case 4: //最小号
					wv_setting.setTextSize(TextSize.SMALLEST);
					break;
				default:
					break;
				}
				dialog.dismiss();
			}
		};
		//给返回键添加事件
		ib_back.setOnClickListener(listener);
		ib_setTextSize.setOnClickListener(listener);
		ib_share.setOnClickListener(listener);
		
		//给WebView添加一个新闻加载完成的监听事件
		wv_news.setWebViewClient(new WebViewClient(){

			/* (non-Javadoc)
			 * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
			 * 页面加载完成的事件处理
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				//隐藏进度条
				pb_loadingnews.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
			
		});
	}

	private void initData() {
		//获取数据
		String url = getIntent().getStringExtra("newsurl");
		if (TextUtils.isEmpty(url)){
			Toast.makeText(getApplicationContext(), "链接错误", 1).show();
		} else {
			//有新闻
			//加载新闻
			// url 改成黑马官网
			//url = "http://www.itheima.com";
			wv_news.loadUrl(url);
		}
	}

	private void initView() {
		setContentView(R.layout.newscenter_newsdetail);
		//设置菜单按钮隐藏
		findViewById(R.id.ib_base_content_menu).setVisibility(View.GONE);
		
		//隐藏标题
		findViewById(R.id.tv_base_content_title).setVisibility(View.GONE);
		
		//返回的按钮
		ib_back = (ImageButton) findViewById(R.id.ib_base_content_back);
		ib_back.setVisibility(View.VISIBLE);
		//修改新闻的字体
		ib_setTextSize = (ImageButton) findViewById(R.id.ib_base_content_textsize);
		ib_setTextSize.setVisibility(View.VISIBLE);
		
		//分享
		ib_share = (ImageButton) findViewById(R.id.ib_base_content_share);
		ib_share.setVisibility(View.VISIBLE);
		//显示新闻
		wv_news = (WebView) findViewById(R.id.wv_newscenter_newsdetail);
		
		//控制WebView的显示设置
		wv_setting = wv_news.getSettings();
		
		//设置放大缩小
		wv_setting.setBuiltInZoomControls(true);
		
		wv_setting.setJavaScriptEnabled(true);//可以去编译javsscript脚本
		
		//设置双击放大或缩小
		wv_setting.setUseWideViewPort(true);//双击放大或缩小
		
		
		
		//加载新闻的进度
		pb_loadingnews = (ProgressBar) findViewById(R.id.pb_newscenter_newsdtail_loading);
	}
	
	

}
