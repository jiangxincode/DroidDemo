package edu.jiangxin.droiddemo.mediastore;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.jiangxin.droiddemo.R;


public class MovieListViewAdapter extends BaseAdapter{
	
	private List<MovieInfo> movieinfo;
	private Context context;
	private LayoutInflater inflater;

	public MovieListViewAdapter(List<MovieInfo> movielist, Context ctx) {
		// TODO Auto-generated constructor stub
		this.movieinfo = movielist;
		this.context = ctx;
		inflater = LayoutInflater.from(context);
	}

	@Override
    public int getCount() {
		// TODO Auto-generated method stub
		return movieinfo.size();
	}

	@Override
    public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movieinfo.get(position);
	}

	@Override
    public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		   ViewHolder holder = null;  
           if(convertView == null){  
               holder = new ViewHolder();  
               convertView = LayoutInflater.from(context).inflate(R.layout.movielistviewitem, null);
               holder.thumbImage = convertView.findViewById(R.id.movie_cover);
               holder.titleText = convertView.findViewById(R.id.movie_title);
               convertView.setTag(holder);  
           }else{  
               holder = (ViewHolder)convertView.getTag();  
           }  
             
           //显示信息  
           holder.titleText.setText(movieinfo.get(position).getTitle());  
           if(movieinfo.get(position).getCover_url() != null){  
               holder.thumbImage.setImageURI(Uri.parse(movieinfo.get(position).getCover_url()));  
           }  
             
           return convertView;
	}
	class ViewHolder{  
        ImageView thumbImage;  
        TextView titleText;  
    }  

}
