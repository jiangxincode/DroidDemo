package edu.jiangxin.droiddemo.easychat;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.jiangxin.droiddemo.easychat.dbhelper.SmsOpenHelper;
import edu.jiangxin.droiddemo.easychat.provider.SmsProvider;

@RunWith(AndroidJUnit4.class)
public class TestSmsProvider {

	@Test
	public void testInsert() {
		/**
		 public static final String FROM_ACCOUNT = "from_account";
		 public static final String TO_ACCOUNT = "to_account";
		 public static final String BODY = "body";
		 public static final String STATUS = "status";
		 public static final String TYPE = "type";
		 public static final String TIME = "time";
		 public static final String SESSION_ACCOUNT = "session_account";
		 */
		ContentValues values = new ContentValues();
		values.put(SmsOpenHelper.SmsTable.FROM_ACCOUNT, "billy@itheima.com");
		values.put(SmsOpenHelper.SmsTable.TO_ACCOUNT, "cang@itheima.com");
		values.put(SmsOpenHelper.SmsTable.BODY, "今晚约吗?");
		values.put(SmsOpenHelper.SmsTable.STATUS, "offline");
		values.put(SmsOpenHelper.SmsTable.TYPE, "chat");
		values.put(SmsOpenHelper.SmsTable.TIME, System.currentTimeMillis());
		values.put(SmsOpenHelper.SmsTable.SESSION_ACCOUNT, "cang@itheima.com");
		InstrumentationRegistry.getTargetContext().getContentResolver().insert(SmsProvider.URI_SMS, values);
	}

	@Test
	public void testDelete() {
		InstrumentationRegistry.getTargetContext().getContentResolver().delete(SmsProvider.URI_SMS, SmsOpenHelper.SmsTable.FROM_ACCOUNT + "=?",
				new String[] { "billy@itheima.com" });
	}

	@Test
	public void testUpdate() {
		ContentValues values = new ContentValues();
		values.put(SmsOpenHelper.SmsTable.BODY, "今晚约吗?我好久没有看到你了.");
		values.put(SmsOpenHelper.SmsTable.TIME, System.currentTimeMillis());
		values.put(SmsOpenHelper.SmsTable.SESSION_ACCOUNT, "cang@itheima.com");
		InstrumentationRegistry.getTargetContext().getContentResolver().update(SmsProvider.URI_SMS, values,
				SmsOpenHelper.SmsTable.FROM_ACCOUNT + "=?", new String[] { "billy@itheima.com" });
	}

	@Test
	public void testQuery() {
		Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(SmsProvider.URI_SMS, null, null, null, null);
		// 得到所有的列
		int columnCount = c.getColumnCount();
		while (c.moveToNext()) {
			for (int i = 0; i < columnCount; i++) {
				System.out.print(c.getString(i) + "  ");
			}
			System.out.println("");
		}
	}
}
