package edu.jiangxin.easymarry.activity.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.jiangxin.easymarry.R;

public class GridViewActivity extends Activity implements View.OnClickListener {

    private boolean visibility = true;
    private LinearLayout lt_menu;
    private ImageView iv_menu;
    private GridView gridView;
    private int[] itemLogo = {R.drawable.gv_baisheng, R.drawable.gv_caozheng, R.drawable.gv_chaijin,
            R.drawable.gv_likui, R.drawable.gv_lizhong, R.drawable.gv_ruanxiaoer,
            R.drawable.gv_shiqian, R.drawable.gv_wangying, R.drawable.gv_wuyong, R.drawable.gv_zhutong};
    private String[] itemName = {"白胜", "曹正", "柴进",
            "李逵", "李忠", "阮小二",
            "时迁", "王英", "吴用", "朱仝"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        gridView = findViewById(R.id.gv_test);
        lt_menu = findViewById(R.id.lt_grid_view_title_menu);
        iv_menu = findViewById(R.id.iv_grid_view_title);
        lt_menu.setOnClickListener(this);
        final List<GridViewEntity> list = new ArrayList<GridViewEntity>();
        for (int i = 0; i < itemLogo.length; ++i) {
            GridViewEntity data = new GridViewEntity();
            data.setImageView(itemLogo[i]);
            data.setTitleName(itemName[i]);
            list.add(data);
        }
        GridViewAdapter adapter = new GridViewAdapter(this, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridViewActivity.this, "您点击了" + list.get(position).getTitleName().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lt_grid_view_title_menu:
                if (visibility) {
                    iv_menu.setBackgroundResource(R.drawable.gv_left);
                    gridView.setVisibility(View.GONE);
                    visibility = false;
                } else {
                    visibility = true;
                    iv_menu.setBackgroundResource(R.drawable.gv_down);
                    gridView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}

class GridViewAdapter extends BaseAdapter {

    private List<GridViewEntity> list;
    private Context context;

    public GridViewAdapter(Context context, List<GridViewEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contertView, ViewGroup parent) {
        GridViewChildHolder holder = null;
        if (contertView == null) {
            holder = new GridViewChildHolder();
            contertView = LayoutInflater.from(context).inflate(R.layout.adaper_grid_view_item, null);
            holder.iv_icon = contertView.findViewById(R.id.iv_item);
            holder.tv_name = contertView.findViewById(R.id.tv_item);

            contertView.setTag(holder);
        } else {
            holder = (GridViewChildHolder) contertView.getTag();
        }
        holder.iv_icon.setBackgroundResource(list.get(position).getImageView());
        holder.tv_name.setText(list.get(position).getTitleName());

        return contertView;
    }


}

class GridViewChildHolder {
    ImageView iv_icon;
    TextView tv_name;
}

class GridViewEntity {
    private Integer imageView;
    private String titleName;

    public Integer getImageView() {
        return imageView;
    }

    public void setImageView(Integer imageView) {
        this.imageView = imageView;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}