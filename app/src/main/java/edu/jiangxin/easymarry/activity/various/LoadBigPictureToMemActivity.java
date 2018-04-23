package edu.jiangxin.easymarry.activity.various;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.common.PermisionUtils;

public class LoadBigPictureToMemActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_big_picture_to_mem);

        iv = (ImageView) findViewById(R.id.iv);
        PermisionUtils.verifyStoragePermissions(this);
    }

    public void loadBitmap(View view) {
        String path = Environment.getExternalStorageDirectory() + "/jiangxin/img_big_1.jpg";
        //String path =  Environment.getExternalStorageDirectory() + "/jiangxin/img_small_1.jpg";

        // 通过手机的屏幕的宽高和图片的宽高来计算采样率

        // 1. 屏幕宽高
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;// 屏幕宽度
        int screenHeight = metrics.heightPixels;// 屏幕高

        // 2. 图片的宽高
        try {
            ExifInterface exif = new ExifInterface(path);
            int picWidth = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH,
                    0);
            int picHeight = exif.getAttributeInt(
                    ExifInterface.TAG_IMAGE_LENGTH, 0);

            // 用图片的宽度/屏幕的宽度
            int widthSample = (int) (picWidth * 1f / screenWidth + 0.5f);// 四舍五入
            int heightSample = (int) (picHeight * 1f / screenHeight + 0.5f);// 四舍五入

            int sample = (int) (Math.sqrt(widthSample * widthSample
                    + heightSample * heightSample) + 0.5f);

            // 加载图片
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 宽和高: 1000 * 1000 --> 4 1/4 * 1/4 = 1 / 16
            opts.inSampleSize = sample;// 采样率

            Bitmap bitmap = BitmapFactory.decodeFile(path, opts);

            iv.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
