package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.jiangxin.droiddemo.R;

/**
 * 使用加载器加载通话记录
 */
public class LoaderDemoActivity extends Activity {

    private static final String TAG = "LoaderDemoActivity";
    // 查询指定的条目
    private static final String[] CALLLOG_PROJECTION = new String[]{
            CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
            CallLog.Calls.TYPE, CallLog.Calls.DATE};
    static final int DAY = 1440; // 一天的分钟值
    private static final int ALL = 0; // 默认显示所有
    private static final int INCOMING = CallLog.Calls.INCOMING_TYPE; // 来电
    private static final int OUTCOMING = CallLog.Calls.OUTGOING_TYPE; // 拔号
    private static final int MISSED = CallLog.Calls.MISSED_TYPE; // 未接
    private ListView mListView;
    private MyLoaderCallbacks mLoaderCallback;
    private MyCursorAdapter mAdapter;
    private int mCallLogShowType = ALL;
    private boolean mFirstLoadingFinish = false; // 第一次加载完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_demo);
        initWidgets();
        mLoaderCallback = new MyLoaderCallbacks();
        getLoaderManager().initLoader(0, null, mLoaderCallback);
    }

    private void initWidgets() {
        mListView = (ListView) findViewById(R.id.lv_list);
        Button btn = (Button) findViewById(R.id.btn_all);
        btn.setOnClickListener(new buttonListener());
        btn = (Button) findViewById(R.id.btn_incoming);
        btn.setOnClickListener(new buttonListener());
        btn = (Button) findViewById(R.id.btn_outcoming);
        btn.setOnClickListener(new buttonListener());
        btn = (Button) findViewById(R.id.btn_missed);
        btn.setOnClickListener(new buttonListener());
        mAdapter = new MyCursorAdapter(LoaderDemoActivity.this, null);
        mListView.setAdapter(mAdapter);
    }

    private class MyLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.i(TAG, "onCreateLoader");
            mFirstLoadingFinish = false;
            CursorLoader cursor = new CursorLoader(LoaderDemoActivity.this,
                    CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, null, null,
                    CallLog.Calls.DEFAULT_SORT_ORDER);
            return cursor;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data == null) {
                return;
            }
            Cursor tmpCursor = data;
            if (tmpCursor.getCount() == 0) {
                Log.i(TAG, "onLoadFinished count = 0");
                mAdapter.swapCursor(null);
                return;
            }
            if (mFirstLoadingFinish) {
                String selection = null;
                String[] selectionArgs = null;
                if (mCallLogShowType == INCOMING) {
                    selection = CallLog.Calls.TYPE + "=?";
                    selectionArgs = new String[]{"1"};
                } else if (mCallLogShowType == OUTCOMING) {
                    selection = CallLog.Calls.TYPE + "=?";
                    selectionArgs = new String[]{"2"};
                } else if (mCallLogShowType == MISSED) {
                    selection = CallLog.Calls.TYPE + "=?";
                    selectionArgs = new String[]{"3"};
                }
                tmpCursor = getContentResolver().query(
                        CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION,
                        selection, selectionArgs,
                        CallLog.Calls.DEFAULT_SORT_ORDER);
            }
            mAdapter.swapCursor(tmpCursor);
            Log.i(TAG, "onLoadFinished count = " + data.getCount());
            mFirstLoadingFinish = true;
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset");
            mAdapter.swapCursor(null);
        }
    }

    private class buttonListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_all:
                    allCalllog();
                    break;
                case R.id.btn_incoming:
                    incomingCalllog();
                    break;
                case R.id.btn_outcoming:
                    outcomingCalllog();
                    break;
                case R.id.btn_missed:
                    missedCalllog();
                    break;
                default:
                    break;
            }
        }
    }

    private void allCalllog() {
        mCallLogShowType = ALL;
        String selection = null;
        String[] selectionArgs = null;
        Cursor allCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, selection,
                selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        mAdapter.swapCursor(allCursor);
    }

    private void incomingCalllog() {
        mCallLogShowType = INCOMING;
        String selection = CallLog.Calls.TYPE + "=?";
        String[] selectionArgs = new String[]{"1"};
        Cursor incomingCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, selection,
                selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        mAdapter.swapCursor(incomingCursor);
    }

    private void outcomingCalllog() {
        mCallLogShowType = OUTCOMING;
        String selection = CallLog.Calls.TYPE + "=?";
        String[] selectionArgs = new String[]{"2"};
        Cursor outcomingCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, selection,
                selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        mAdapter.swapCursor(outcomingCursor);
    }

    private void missedCalllog() {
        mCallLogShowType = MISSED;
        String selection = CallLog.Calls.TYPE + "=?";
        String[] selectionArgs = new String[]{"3"};
        Cursor missedCursor = getContentResolver().query(
                CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, selection,
                selectionArgs, CallLog.Calls.DEFAULT_SORT_ORDER);
        mAdapter.swapCursor(missedCursor);
    }
}

class MyCursorAdapter extends CursorAdapter {

    private static final String TAG = "MyCursorAdapter";
    private final Context mContext;

    public MyCursorAdapter(Context context, Cursor c) {
        this(context, c, true);
    }

    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.loader_listview_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor == null) {
            return;
        }
        final String id = cursor.getString(0);
        String number = cursor.getString(1);
        String name = cursor.getString(2);
        int type = cursor.getInt(3);
        String date = cursor.getString(4);
        ImageView TypeView = (ImageView) view.findViewById(R.id.bt_icon);
        TextView nameCtrl = (TextView) view.findViewById(R.id.tv_name);
        if (name == null) {
            nameCtrl.setText(mContext.getString(R.string.name_unknown));
        } else {
            nameCtrl.setText(name);
        }
        TextView numberCtrl = (TextView) view.findViewById(R.id.tv_number);
        numberCtrl.setText(number);
        String value = computeDate(date);
        TextView dateCtrl = (TextView) view.findViewById(R.id.tv_date);
        dateCtrl.setText(value);
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                TypeView.setImageResource(R.drawable.calllog_incoming);
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                TypeView.setImageResource(R.drawable.calllog_outcoming);
                break;
            case CallLog.Calls.MISSED_TYPE:
                TypeView.setImageResource(R.drawable.calllog_missed);
                break;
            case 4: // CallLog.Calls.VOICEMAIL_TYPE

                break;
            default:
                break;
        }

        ImageButton dailBtn = (ImageButton) view.findViewById(R.id.btn_call);
        dailBtn.setTag(number);
        dailBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Intent.ACTION_CALL_PRIVILEGED 由于Intent中隐藏了，只能用字符串代替
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts(
                        "tel", (String) v.getTag(), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        ImageButton deleteBtn = (ImageButton) view
                .findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 根据ID进行记录删除
                String where = CallLog.Calls._ID + "=?";
                String[] selectionArgs = new String[]{id};
                int result = mContext.getContentResolver().delete(
                        CallLog.Calls.CONTENT_URI, where, selectionArgs);
                Log.d(TAG, "11result = " + result);
            }
        });
    }

    private String computeDate(String date) {
        long callTime = Long.parseLong(date);
        long nowTime = new Date().getTime();
        long duration = (nowTime - callTime) / (1000 * 60);
        String value;
        // 进行判断拨打电话的距离现在的时间，然后进行显示说明
        if (duration < 60) {
            value = duration + "分钟前";
        } else if (duration >= 60 && duration < LoaderDemoActivity.DAY) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
                    Locale.getDefault());
            value = sdf.format(new Date(callTime));
        } else if (duration >= LoaderDemoActivity.DAY
                && duration < LoaderDemoActivity.DAY * 2) {
            value = "昨天";
        } else if (duration >= LoaderDemoActivity.DAY * 2
                && duration < LoaderDemoActivity.DAY * 3) {
            value = "前天";
        } else if (duration >= LoaderDemoActivity.DAY * 7) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd",
                    Locale.getDefault());
            value = sdf.format(new Date(callTime));
        } else {
            value = (duration / LoaderDemoActivity.DAY) + "天前";
        }
        return value;
    }


}
