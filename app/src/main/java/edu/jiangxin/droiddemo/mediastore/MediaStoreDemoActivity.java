package edu.jiangxin.droiddemo.mediastore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.jiangxin.droiddemo.R;

@SuppressWarnings("deprecation")
public class MediaStoreDemoActivity extends Activity {

    private ImageButton mPhotoImageButton;
    private ImageButton mMusicImageButton;
    private ImageButton mMovieImageButton;

    private ImageView mImageView;
    private Button mSaveToFileButton;
    private Button mSaveToMediaStoreButton;

    private Intent mSecondActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mPhotoImageButton = findViewById(R.id.myphoto);
        mMusicImageButton = findViewById(R.id.mymusic);
        mMovieImageButton = findViewById(R.id.mymovie);

        mPhotoImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mSecondActivityIntent = new Intent();
                mSecondActivityIntent.setClass(MediaStoreDemoActivity.this, MyPhoto.class);
                startActivity(mSecondActivityIntent);
            }
        });

        mMusicImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mSecondActivityIntent = new Intent();
                mSecondActivityIntent.setClass(MediaStoreDemoActivity.this, MyMusic.class);
                startActivity(mSecondActivityIntent);
            }
        });

        mMovieImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mSecondActivityIntent = new Intent();
                mSecondActivityIntent.setClass(MediaStoreDemoActivity.this, MyMovie.class);
                startActivity(mSecondActivityIntent);
            }
        });

        mImageView = findViewById(R.id.ivPicture);
        mSaveToFileButton = findViewById(R.id.btnSaveToFile);
        mSaveToMediaStoreButton = findViewById(R.id.btnSaveToMediaStore);

        mSaveToFileButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Drawable drawable = mImageView.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                saveImageToFile(MediaStoreDemoActivity.this, bitmap);
            }
        });
        mSaveToMediaStoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = mImageView.getDrawable();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                saveImageToGallery(MediaStoreDemoActivity.this, bitmap);
            }
        });
    }

    private void saveImageToFile(Context context, Bitmap bitmap) {
        // 首先保存图片
        File appDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //最后通知图库更新
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);

        Toast.makeText(this, "save image to own dirs", Toast.LENGTH_SHORT).show();

    }

    private void saveImageToGallery(Context context, Bitmap bitmap) {

        // 其次把文件插入到系统图库
        String fileName = System.currentTimeMillis() + ".jpg";
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, fileName, null);

        Toast.makeText(this, "save image to gallery", Toast.LENGTH_SHORT).show();

    }

}
