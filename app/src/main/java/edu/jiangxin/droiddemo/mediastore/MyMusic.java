package edu.jiangxin.droiddemo.mediastore;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.jiangxin.droiddemo.R;


public class MyMusic extends Activity{

	private ListView musicListView;
	private List<MusicInfo> musiclist;
	private MusicListViewAdapter musicAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mymusic);
		musicListView = (ListView)findViewById(R.id.musiclistview);
		musiclist = new ArrayList<MusicInfo>();
		Context ctx = MyMusic.this;
		ContentResolver resolver = ctx.getContentResolver();
		Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		c.moveToFirst();
		int totalmusic = c.getCount();
		
		if(totalmusic>0)
			initmusiclist(c,totalmusic);
		else
			Toast.makeText(MyMusic.this, "there is no music on your phone", Toast.LENGTH_LONG).show();
		
		musicListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		musicListView.setAdapter(musicAdapter);
	   }
	
	
	
	
	private void initmusiclist(Cursor c, int totalmusic) {
		// TODO Auto-generated method stub
		 int album_id;		 
		 
		
		for(int i = 0;i<totalmusic;i++)
		{
			Bitmap bm = null;
			MusicInfo temp = new MusicInfo();
			temp.setId(c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
			temp.setName(c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
			temp.setArtist(c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
			temp.setAlbum(c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
			temp.setSrc(c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
			temp.setDuration(c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
			temp.setSize(c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
	        album_id =(c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
	        String albumArt = getAlbumArt(album_id); 
	        if (albumArt == null) {  
	        	Drawable d = getResources().getDrawable(R.drawable.music);
	        	BitmapDrawable bd = (BitmapDrawable)d;
	            temp.setAlbum_cover(bd);  
	        } else {  
	            bm = BitmapFactory.decodeFile(albumArt);  
	            BitmapDrawable bmpDraw = new BitmapDrawable(bm);  
	            temp.setAlbum_cover(bmpDraw);  
	        }
	        musiclist.add(temp);
	        c.moveToNext();
		}
		c.close();
		musicAdapter = new MusicListViewAdapter(musiclist,MyMusic.this);
		
		
	}
	private String getAlbumArt(int album_id) {
		// TODO Auto-generated method stub
		String mUriAlbums = "content://media/external/audio/albums";  
	    String[] projection = new String[] { "album_art" };  
	    Cursor cur = this.getContentResolver().query(  
	            Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  
	            projection, null, null, null);  
	    String album_art = null;  
	    if (cur.getCount() > 0 && cur.getColumnCount() > 0) {  
	        cur.moveToNext();  
	        album_art = cur.getString(0);  
	    }  
	    cur.close();  
	    cur = null;  
	    return album_art;  
	}

}

