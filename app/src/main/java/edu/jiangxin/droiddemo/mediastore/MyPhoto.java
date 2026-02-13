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
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.jiangxin.droiddemo.R;


@SuppressWarnings("deprecation")
public class MyPhoto extends Activity{

	
	private GridView mGridView;
	private List<PhotoInfo> photolist;
	private PhotoGridViewAdapter photoadapter;
	private int totalPhoto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myphoto);
		mGridView = findViewById(R.id.gvphoto);
		photolist = new ArrayList<PhotoInfo>();
		Context ctx = MyPhoto.this;
		ContentResolver resolver = ctx.getContentResolver();
		Cursor c = resolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, null, null, null, null);
		totalPhoto = c.getCount();
		
		c.moveToFirst();
		for(int i = 0;i<totalPhoto;i++)
		{
			int index = c.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
			String src = c.getString(index);
			PhotoInfo temp = new PhotoInfo(src);
			photolist.add(temp);
			c.moveToNext();
		}
		c.close();
		photoadapter = new PhotoGridViewAdapter(photolist, MyPhoto.this);
        mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Toast.makeText(MyPhoto.this, "you select: "+arg2, Toast.LENGTH_LONG).show();
			}
		});
        mGridView.setAdapter(photoadapter);
	}

	
}

