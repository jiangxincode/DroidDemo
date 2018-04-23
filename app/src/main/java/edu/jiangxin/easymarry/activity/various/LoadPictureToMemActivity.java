package edu.jiangxin.easymarry.activity.various;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.common.PermisionUtils;

public class LoadPictureToMemActivity extends Activity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_picture_to_mem);

        iv = (ImageView) findViewById(R.id.iv);
        PermisionUtils.verifyStoragePermissions(this);
    }

    public void loadBitmap(View view) {
        String path = Environment.getExternalStorageDirectory() + "/jiangxin/img_small_1.jpg";
        //老版本对加载图片大小有限制，加载大图片会导致OutOfMemory
        //String path =  Environment.getExternalStorageDirectory() + "/jiangxin/img_big_1.jpg";
        // 加载图片
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        iv.setImageBitmap(bitmap);
    }
}
