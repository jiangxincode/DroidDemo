package edu.jiangxin.droiddemo.saf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.jiangxin.droiddemo.R;

public class SafActivity extends Activity {

    public final static int REQUEST_CODE_SHOW_IMAGE = 11;
    public final static int WRITE_REQUEST_CODE = 44;
    public final static int EDIT_REQUEST_CODE = 41;
    private Bitmap originalImage;
    private Bitmap modifiedImage;
    private Uri tmpUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saf);
        Button showImage = findViewById(R.id.btn_show_image);
        showImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_SHOW_IMAGE);
        });
        Button createFile = findViewById(R.id.btn_create_file);
        createFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "test.txt");
            startActivityForResult(intent, WRITE_REQUEST_CODE);
        });
        Button deleteFile = findViewById(R.id.btn_delete_file);
        deleteFile.setOnClickListener(v -> {
            // 只是删除document 也就是content provider中的，存储设备上面的没有删除掉
            if (tmpUri == null) {
                Toast.makeText(this, "tmpUri is null", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (DocumentsContract.deleteDocument(getContentResolver(), tmpUri)) {
                    Toast.makeText(this, "Delete Success!", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Button alterFile = findViewById(R.id.btn_alter_file);
        alterFile.setOnClickListener(v -> {
            Intent alterIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            alterIntent.addCategory(Intent.CATEGORY_OPENABLE);
            alterIntent.setType("text/plain");
            startActivityForResult(alterIntent, EDIT_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SHOW_IMAGE) {
                if (data != null) {
                    showImage(data.getData());
                }
            } else if (requestCode == WRITE_REQUEST_CODE) {
                if (data != null) {
                    createFile(data.getData());
                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                if (data != null) {
                    alterDocument(data.getData());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImage(Uri uri) {
        //回收Bitmap 以免OOM
        if (modifiedImage != null && !modifiedImage.isRecycled()) {
            modifiedImage.recycle();
        }
        if (originalImage != null && !originalImage.isRecycled()) {
            originalImage.recycle();
        }
        try (ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r")) {
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            originalImage = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            modifiedImage = originalImage;
            //以下代码是简单处理了下，如果当前图片过大（大于4096*4096），那么无法设置背景的问题.
            if (originalImage.getHeight() > 4096 || originalImage.getWidth() > 4096) {
                if (originalImage.getHeight() > originalImage.getWidth()) {
                    modifiedImage = Bitmap.createScaledBitmap(originalImage, originalImage.getWidth()
                            * 4096 / originalImage.getHeight(), 4096, true);
                } else {
                    modifiedImage = Bitmap.createScaledBitmap(originalImage, 4096,
                            originalImage.getHeight() * 4096 / originalImage.getWidth(), true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable drawable = new BitmapDrawable(getResources(), modifiedImage);
        findViewById(R.id.saf_background).setBackground(drawable);
    }

    private void createFile(Uri uri) {
        tmpUri = uri;
        try (ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(tmpUri, "w");
             FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor())) {
            fos.write(("Now: " + System.currentTimeMillis() + "\n").getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alterDocument(Uri uri) {
        try (ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
             FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor())) {
            fos.write(("Now: " + System.currentTimeMillis() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
