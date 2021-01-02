package edu.jiangxin.droiddemo.saf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import edu.jiangxin.droiddemo.R;

/**
 * 本例中删除操作只示范型的   删除创建操作创建的对象。
 */
public class SAFActivity extends Activity implements OnClickListener {

    public final static int REQUEST_CODE_SHOW_IMAGE = 11;
    public final static int WRITE_REQUEST_CODE = 44;
    public final static int EDIT_REQUEST_CODE = 41;
    private Button showImage;
    private Button createFile;
    private Button deleteFile;
    private Button alterFile;
    private Bitmap image;
    private Bitmap newimage;
    private Uri tmpUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saf);
        showImage = findViewById(R.id.btn_show_image);
        showImage.setOnClickListener(this);
        createFile = findViewById(R.id.btn_create_file);
        createFile.setOnClickListener(this);
        deleteFile = findViewById(R.id.btn_delete_file);
        deleteFile.setOnClickListener(this);
        alterFile = findViewById(R.id.btn_alter_file);
        alterFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_show_image:
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE_SHOW_IMAGE);
                } else {
                    Toast.makeText(getApplicationContext(), "No Activity to handle the intent", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_create_file:
                intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, "test.txt");
                startActivityForResult(intent, WRITE_REQUEST_CODE);
                break;
            case R.id.btn_delete_file:// 只是删除document 也就是content
                // provider中的，存储设备上面的没有删除掉
                if (tmpUri != null) {
                    try {
                        if (DocumentsContract.deleteDocument(getContentResolver(),
                                tmpUri)) {

                            Toast.makeText(getApplicationContext(), "Delete Success!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "File not exist Or Create Failed!", Toast.LENGTH_SHORT)
                            .show();
                }
            case R.id.btn_alter_file:
                Intent alterIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                alterIntent.addCategory(Intent.CATEGORY_OPENABLE);
                alterIntent.setType("text/plain");
                startActivityForResult(alterIntent, EDIT_REQUEST_CODE);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SHOW_IMAGE) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Log.i("ypz", "Uri: " + uri.toString());
                    try {
                        findViewById(R.id.rlyt_bg).setBackground(
                                new BitmapDrawable(getResources(), getBitmapFromUri(uri)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == WRITE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        tmpUri = data.getData();
                        ParcelFileDescriptor pfd = SAFActivity.this
                                .getContentResolver().openFileDescriptor(tmpUri,
                                        "w");
                        FileOutputStream fos = new FileOutputStream(
                                pfd.getFileDescriptor());
                        fos.write(Calendar.getInstance().toString().getBytes());
                        fos.flush();
                        fos.close();
                        pfd.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                    }

                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                if (data != null) {
                    alterDocument(data.getData());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        recyleBitmap();
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver()
                .openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor
                .getFileDescriptor();
        image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        newimage = image;
        //以下代码是简单处理了下，如果当前图片过大（大于4096*4096），那么无法设置背景的问题.
        if (image.getHeight() > 4096 || image.getWidth() > 4096) {
            int max = image.getHeight() > image.getWidth() ? image.getHeight()
                    : image.getWidth();
            if (max == image.getHeight()) {
                newimage = Bitmap.createScaledBitmap(image, image.getWidth()
                        * 4096 / image.getHeight(), 4096, true);
            } else {
                newimage = Bitmap.createScaledBitmap(image, 4096,
                        image.getHeight() * 4096 / image.getWidth(), true);
            }
        }
        parcelFileDescriptor.close();
        return newimage;
    }

    /**
     * 回收Bitmap 以免OOM
     */
    private void recyleBitmap() {
        if (newimage != null && !newimage.isRecycled()) {
            newimage.recycle();
        }
        if (image != null && !image.isRecycled()) {
            image.recycle();
        }
    }

    /**
     * 修改文件
     *
     * @param uri
     */
    private void alterDocument(Uri uri) {
        try {

            ParcelFileDescriptor pfd = SAFActivity.this.getContentResolver()
                    .openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(
                    pfd.getFileDescriptor());
            fileOutputStream.write(("Overwritten by MyFile at "
                    + System.currentTimeMillis() + "\n").getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
