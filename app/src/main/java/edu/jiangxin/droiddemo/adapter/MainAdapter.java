/**
 * 
 */
package edu.jiangxin.droiddemo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;

/**
 * @author 1900<br>
 *         2014-4-8 下午2:43:17
 */

@SuppressLint({ "NewApi", "UseSparseArrays" })
public class MainAdapter extends BaseAdapter {

	public List<String> ringList;
	Context mContext;
	public Cursor cursor;
	public RingtoneManager rm;
	// public Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	public Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
	public ViewHolder mHodler;
	public ListView ringView;
	public int index;
	public boolean firstItemState = true;

	/**
	 * 构造方法，index参数作为记录所选铃声的position传入SharedPreferences记录并调取。
	 */
	public MainAdapter(Context context, int index) {
		this.mContext = context;
		this.index = index;
		if (firstItemState) {
			firstItemState = false;
			map.put(index, true);
		}
		getRing();
	}

	public void getRing() {
		/* 新建一个arraylist来接收从系统中获取的短信铃声数据 */
		ringList = new ArrayList<String>();
		/* 添加“跟随系统”选项 */
		ringList.add("跟随系统");
		/* 获取RingtoneManager */
		rm = new RingtoneManager(mContext);
		/* 指定获取类型为短信铃声 */
		rm.setType(RingtoneManager.TYPE_NOTIFICATION);
		/* 创建游标 */
		cursor = rm.getCursor();
		/* 游标移动到第一位，如果有下一项，则添加到ringlist中 */
		if (cursor.moveToFirst()) {
			do { // 游标获取RingtoneManager的列inde x
				ringList.add(cursor
						.getString(RingtoneManager.TITLE_COLUMN_INDEX));
			} while (cursor.moveToNext());
		}
	}

	@Override
	public int getCount() {
		return ringList.size();
	}

	@Override
	public Object getItem(int position) {
		return ringList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/* 将convertView封装在ViewHodler中，减少系统内存占用 */
		/* convertView为空则初始化 */
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.select_iamgebutton_adapter, null);
			mHodler = new ViewHolder(convertView);
			convertView.setTag(mHodler);
		} else {
			/* 不为空则直接使用已有的封装类 */
			mHodler = (ViewHolder) convertView.getTag();
		}
		/* 设定按钮背景图 */
		mHodler.iv
				.setBackgroundResource(map.get(position) == null ? R.drawable.pressed
						: R.drawable.checked);
		mHodler.tv.setText(ringList.get(position));
		/* 初始化时让position为0 的item(即跟随系统)被选中，如果点击别的item了，状态改为不被选中 */
//		if (position == index && firstItemState) {
//			firstItemState = false;
//			mHodler.iv.setBackgroundResource(R.drawable.checked);
//		}

		return convertView;
	}

	/* 封装类 */
	public static class ViewHolder {
		public TextView tv;
		public ImageView iv;

		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv = (TextView) v.findViewById(R.id.select_imagebtn_ring_tv);
			this.iv = (ImageView) v.findViewById(R.id.select_imagebtn_btn);
		}

	}

}
