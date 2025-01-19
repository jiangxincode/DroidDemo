package edu.jiangxin.droiddemo.easymusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;
import edu.jiangxin.droiddemo.easymusic.bean.Music;
import edu.jiangxin.droiddemo.easymusic.utils.MediaUtils;

public class MyAdapter extends BaseAdapter {
    private final Context context;

    public MyAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        if (MediaUtils.songList != null) {
            return MediaUtils.songList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (MediaUtils.songList != null) {
            return MediaUtils.songList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_music, null);
            holder = new ViewHolder();
            convertView.setTag(holder);//设置tag的作用
            holder.tv_title = convertView.findViewById(R.id.tv_title);
            holder.tv_artist = convertView.findViewById(R.id.tv_artist);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Music music = MediaUtils.songList.get(position);
        holder.tv_title.setText(music.title);
        holder.tv_artist.setText(music.artist);

        if (MediaUtils.CURPOSITION == position) {
            holder.tv_title.setTextColor(Color.GREEN);
        } else {
            holder.tv_title.setTextColor(Color.WHITE);
        }
        holder.tv_title.setTag(position);
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_artist;
    }

}
