package edu.jiangxin.droiddemo.mediastore;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;


public class MusicListViewAdapter extends BaseAdapter{
	
	private List<MusicInfo> musicinfo = null;
	private Context context = null;
	private LayoutInflater inflater = null;

	public MusicListViewAdapter(List<MusicInfo> musiclist, Context ctx) {
		// TODO Auto-generated constructor stub
		this.musicinfo = musiclist;
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return musicinfo.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return musicinfo.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.musiclistviewitem, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.album_cover);
		TextView title = (TextView)convertView.findViewById(R.id.music_title);
		TextView artist = (TextView)convertView.findViewById(R.id.music_artist);
		iv.setImageDrawable(musicinfo.get(position).getAlbum_cover());
		title.setText(musicinfo.get(position).getName());
		artist.setText(musicinfo.get(position).getArtist());
		
		return convertView;
	}

}
