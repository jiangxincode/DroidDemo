package edu.jiangxin.droiddemo.easychat;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import edu.jiangxin.droiddemo.easychat.dbhelper.ContactOpenHelper;
import edu.jiangxin.droiddemo.easychat.provider.ContactsProvider;

@RunWith(AndroidJUnit4.class)
public class TestContactsProvider {

    @Test
    public void testInsert() {
        /**
         public static final String ACCOUNT = "account";//账号
         public static final String NICKNAME = "nickname";//昵称
         public static final String AVATAR = "avatar";//头像
         */
        ContentValues values = new ContentValues();
        values.put(ContactOpenHelper.ContactTable.ACCOUNT, "billy@itheima.com");
        values.put(ContactOpenHelper.ContactTable.NICKNAME, "老伍");
        values.put(ContactOpenHelper.ContactTable.AVATAR, "0");
        InstrumentationRegistry.getTargetContext().getContentResolver().insert(ContactsProvider.URI_CONTACT, values);
    }

    @Test
    public void testDelete() {
        InstrumentationRegistry.getTargetContext().getContentResolver().delete(ContactsProvider.URI_CONTACT,
                ContactOpenHelper.ContactTable.ACCOUNT + "=?", new String[]{"billy@itheima.com"});
    }

    @Test
    public void testUpdate() {
        ContentValues values = new ContentValues();
        values.put(ContactOpenHelper.ContactTable.ACCOUNT, "billy@itheima.com");
        values.put(ContactOpenHelper.ContactTable.NICKNAME, "我是老伍");
        values.put(ContactOpenHelper.ContactTable.AVATAR, "0");
        InstrumentationRegistry.getTargetContext().getContentResolver().update(ContactsProvider.URI_CONTACT, values,
                ContactOpenHelper.ContactTable.ACCOUNT + "=?", new String[]{"billy@itheima.com"});
    }

    @Test
    public void testQuery() {
        Cursor c = InstrumentationRegistry.getTargetContext().getContentResolver().query(ContactsProvider.URI_CONTACT, null, null, null, null);
        int columnCount = c.getColumnCount();// 一共多少列
        while (c.moveToNext()) {
            // 循环打印列
            for (int i = 0; i < columnCount; i++) {
                System.out.print(c.getString(i) + "    ");
            }
            System.out.println();
        }
    }
}
