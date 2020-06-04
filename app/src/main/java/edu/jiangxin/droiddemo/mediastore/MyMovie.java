package edu.jiangxin.droiddemo.mediastore;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.jiangxin.droiddemo.R;

public class MyMovie extends Activity{

	private List<MovieInfo> movielist;
	private ListView movieListView;
	private MovieListViewAdapter movieAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mymovie);
		
		movielist = new ArrayList<MovieInfo>();
		movieListView = findViewById(R.id.movielistview);
		Context ctx = MyMovie.this;
		ContentResolver resolver = ctx.getContentResolver();
		Cursor c = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		int totalmovie = c.getCount();
		if(totalmovie>0)
		{
			c.moveToFirst();
			initmovielist(c,totalmovie);
		}
		else
		{
			Toast.makeText(MyMovie.this, "there is no movie on your phone", Toast.LENGTH_LONG).show();
		}
	}
	private void initmovielist(Cursor c, int totalmovie) {
		MovieInfo temp = new MovieInfo();
		for(int i =0 ;i<totalmovie;i++)
		{
			temp.setTitle(c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
			temp.setUrl(c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
			temp.setDuration(c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
			int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
			String cover_url = getcover_url(id);
			temp.setCover_url(cover_url);
            movielist.add(temp);
            c.moveToNext();
		}
		c.close();
		movieAdapter = new MovieListViewAdapter(movielist,MyMovie.this);
		movieListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
			}
		});
		movieListView.setAdapter(movieAdapter);
	}
	private String getcover_url(int id) {
		String cover_url =null;
		String selection = MediaStore.Video.Thumbnails.VIDEO_ID+"=?";
		String[] selectionArgs = new String[]{
				id+""
		};
	  String[] mediaColumns = new String[]{  
	                MediaStore.Video.Media.DATA,  
	                MediaStore.Video.Media._ID,  
	        };
		Cursor c = this.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, mediaColumns, selection, selectionArgs, null);
		if(c.moveToFirst())
		{
		cover_url = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
			
		}
		c.close();
        return cover_url;
	}

	
	
}