package edu.jiangxin.droiddemo.mediastore;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import edu.jiangxin.droiddemo.R;

public class MediaStoreActivity extends Activity {

	 private ImageButton myphoto;
	    private ImageButton mymusic;
	    private ImageButton mymovie;
	    private Intent myintent;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_media_store_demo);
			  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	          .detectDiskReads()
	          .detectDiskWrites()
	          .detectNetwork()   // or .detectAll() for all detectable problems
	          .penaltyLog()
	          .build());
	          StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	          .detectLeakedSqlLiteObjects()
	          .detectLeakedClosableObjects()
	          .penaltyLog()
	          .penaltyDeath()
	          .build());
			myphoto = (ImageButton)findViewById(R.id.myphoto);
			mymusic = (ImageButton)findViewById(R.id.mymusic);
			mymovie = (ImageButton)findViewById(R.id.mymovie);
			
			myphoto.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					myintent = new Intent();
					myintent.setClass(MediaStoreActivity.this, MyPhoto.class);
					startActivity(myintent);
				}
			});
			
			mymusic.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					myintent = new Intent();
					myintent.setClass(MediaStoreActivity.this, MyMusic.class);
					startActivity(myintent);
				}
			});
			
			mymovie.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					myintent = new Intent();
					myintent.setClass(MediaStoreActivity.this, MyMovie.class);
					startActivity(myintent);
				}
			});
			
		}
	
}
