package edu.jiangxin.easymarry.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import edu.jiangxin.easymarry.common.DBHelper;
import edu.jiangxin.easymarry.common.QuickSearchBoxContent;


public class QuickSearchBoxProvider extends ContentProvider {

    private static final String TAG = "QuickSearchBoxProvider";

    private DBHelper dbHelper = null;

    private static HashMap<String, String> empProjectionMap;

    static {
        empProjectionMap = new HashMap<String, String>();
        empProjectionMap.put("_ID", "_ID");
        empProjectionMap.put("SUGGEST_COLUMN_TEXT_1", "SUGGEST_COLUMN_TEXT_1");
        empProjectionMap.put("SUGGEST_COLUMN_TEXT_2", "SUGGEST_COLUMN_TEXT_2");
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(DBHelper.EMPLOYEES_TABLE_NAME, "SUGGEST_COLUMN_TEXT_1", values);
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(QuickSearchBoxContent.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        count = db.delete(DBHelper.EMPLOYEES_TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "uri: " + uri);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DBHelper.EMPLOYEES_TABLE_NAME);
        qb.setProjectionMap(empProjectionMap);

        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = "SUGGEST_COLUMN_TEXT_1 DESC";
        } else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        count = db.update(DBHelper.EMPLOYEES_TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}