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
import edu.jiangxin.easymarry.common.Employees.Employee;


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
            values.put(Employee.NAME, "zhangsan");
            values.put(Employee.GENDER, "male");
            values.put(Employee.AGE, 30);

            getContentResolver().insert(Employee.CONTENT_URI, values);
            Log.i(TAG, "insert");

        } else if (view == mBtnQuery) {
            String[] PROJECTION = new String[]{
                    Employee._ID,         // 0
                    Employee.NAME,         // 1
                    Employee.GENDER,     // 2
                    Employee.AGE         // 3
            };
            Cursor cursor = getContentResolver().query(Employee.CONTENT_URI, PROJECTION, null, null, Employee.DEFAULT_SORT_ORDER);
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String gender = cursor.getString(2);
                    int age = cursor.getInt(3);
                    Log.i(TAG, "db第" + i + "个数据：" + "--name:" + name + "--gender:" + gender + "--age:" + age);
                }
            }
            cursor.close();
            Log.i(TAG, "query");

        } else if (view == mBtnDelete) {

            //这是删除名字为：amaker的数据的方法：
            String[] deleteValue = {"amaker"};
            String where = "name";
            getContentResolver().delete(Employee.CONTENT_URI, where + "=?", deleteValue);
            Log.i(TAG, "del");

        } else if (view == mBtnUpdate) {

            ContentValues values = new ContentValues();
            values.put(Employee.NAME, "testUpdate");
            values.put(Employee.GENDER, "female");
            values.put(Employee.AGE, 39);

            String where = "name";
            String[] selectValue = {"amaker"};
            getContentResolver().update(Employee.CONTENT_URI, values, where + "=?", selectValue);
            Log.i(TAG, "update");
        }
    }
}