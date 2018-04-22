package edu.jiangxin.easymarry.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.common.QuickSearchBoxContent;


public class QuickSearchBoxActivity extends Activity implements OnClickListener {

    public static String TAG = "QuickSearchBoxActivity";

    private Button mBtnInsert = null;
    private Button mBtnQuery = null;
    private Button mBtnDelete = null;
    private Button mBtnUpdate = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBtnInsert = (Button) findViewById(R.id.btnInsert);
        mBtnInsert.setOnClickListener(QuickSearchBoxActivity.this);

        mBtnQuery = (Button) findViewById(R.id.btnQuery);
        mBtnQuery.setOnClickListener(QuickSearchBoxActivity.this);

        mBtnDelete = (Button) findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(QuickSearchBoxActivity.this);

        mBtnUpdate = (Button) findViewById(R.id.btnUpdate);
        mBtnUpdate.setOnClickListener(QuickSearchBoxActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnInsert) {
            ContentValues values = new ContentValues();
            values.put("_ID", "_ID");
            values.put("SUGGEST_COLUMN_TEXT_1", "SUGGEST_COLUMN_TEXT_1");
            values.put("SUGGEST_COLUMN_TEXT_2", "SUGGEST_COLUMN_TEXT_2");

            getContentResolver().insert(QuickSearchBoxContent.CONTENT_URI, values);
            Log.i(TAG, "insert");

        } else if (view == mBtnQuery) {
            String[] PROJECTION = new String[]{
                    "_ID",         // 0
                    "SUGGEST_COLUMN_TEXT_1",         // 1
                    "SUGGEST_COLUMN_TEXT_2",     // 2
            };
            Cursor cursor = getContentResolver().query(QuickSearchBoxContent.CONTENT_URI, PROJECTION, null, null, "SUGGEST_COLUMN_TEXT_1 DESC");
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String _ID = cursor.getString(0);
                    String SUGGEST_COLUMN_TEXT_1 = cursor.getString(1);
                    String SUGGEST_COLUMN_TEXT_2 = cursor.getString(2);
                    Log.i(TAG, "_ID: " + _ID + ", SUGGEST_COLUMN_TEXT_1: " + SUGGEST_COLUMN_TEXT_1 + ", SUGGEST_COLUMN_TEXT_2: " + SUGGEST_COLUMN_TEXT_2);
                }
            }
            cursor.close();
            Log.i(TAG, "query");

        } else if (view == mBtnDelete) {

            String[] deleteValue = {"amaker"};
            String where = "name";
            getContentResolver().delete(QuickSearchBoxContent.CONTENT_URI, where + "=?", deleteValue);
            Log.i(TAG, "del");

        } else if (view == mBtnUpdate) {

            ContentValues values = new ContentValues();
            values.put("_ID", "_ID");
            values.put("SUGGEST_COLUMN_TEXT_1", "SUGGEST_COLUMN_TEXT_1");
            values.put("SUGGEST_COLUMN_TEXT_2", "SUGGEST_COLUMN_TEXT_2");

            String where = "SUGGEST_COLUMN_TEXT_1";
            String[] selectValue = {"SUGGEST_COLUMN_TEXT_1"};
            getContentResolver().update(QuickSearchBoxContent.CONTENT_URI, values, where + "=?", selectValue);
            Log.i(TAG, "update");
        }
    }
}