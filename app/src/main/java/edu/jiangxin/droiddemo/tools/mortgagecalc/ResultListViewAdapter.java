package edu.jiangxin.droiddemo.tools.mortgagecalc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import edu.jiangxin.droiddemo.R;

public class ResultListViewAdapter extends BaseAdapter {
    private final Context context;
    private final String[] timeStrings;
    private final String[] capitalStrings;
    private final String[] interestStrings;
    private final String[] monthPayStrings;

    public ResultListViewAdapter(Context context, String[] timeStrings, String[] capitalStrings, String[] interestStrings, String[] monthPayStrings) {
        this.context = context;
        this.timeStrings = timeStrings;
        this.capitalStrings = capitalStrings;
        this.interestStrings = interestStrings;
        this.monthPayStrings = monthPayStrings;
    }

    @Override
    public int getCount() {
        return timeStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return timeStrings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.list_item, null);

        TextView timeTextView = view.findViewById(R.id.List_Time_TextView);
        TextView capitalTextView = view.findViewById(R.id.List_Capital_TextView);
        TextView interestTextView = view.findViewById(R.id.List_Interest_TextView);
        TextView monthPayTextView = view.findViewById(R.id.List_MonthPay_TextView);

        timeTextView.setText(timeStrings[position]);
        capitalTextView.setText(capitalStrings[position]);
        interestTextView.setText(interestStrings[position]);
        monthPayTextView.setText(monthPayStrings[position]);

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
