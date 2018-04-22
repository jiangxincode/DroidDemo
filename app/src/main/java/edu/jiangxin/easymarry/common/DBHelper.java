package edu.jiangxin.easymarry.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "easymarry.db";

    private static final int DATABASE_VERSION = 1;

    public static final String EMPLOYEES_TABLE_NAME = "employee";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EMPLOYEES_TABLE_NAME + " ("
                + Employees.Employee._ID + " INTEGER PRIMARY KEY,"
                + Employees.Employee.NAME + " TEXT,"
                + Employees.Employee.GENDER + " TEXT,"
                + Employees.Employee.AGE + " INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employee");
        onCreate(db);
    }
}