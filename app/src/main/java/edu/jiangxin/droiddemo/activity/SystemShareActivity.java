package edu.jiangxin.droiddemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.jiangxin.droiddemo.R;

public class SystemShareActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (imageUri != null) {
                        shareSelectedImage(imageUri);
                    }
                }
            });

    private final ActivityResultLauncher<Intent> pickMultipleImagesLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        ArrayList<Uri> imageUris = new ArrayList<>();
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);
                        }
                        shareMultipleImages(imageUris);
                    } else if (result.getData().getData() != null) {
                        ArrayList<Uri> imageUris = new ArrayList<>();
                        imageUris.add(result.getData().getData());
                        shareMultipleImages(imageUris);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_share);

        Button btnShareText = findViewById(R.id.btnShareText);
        btnShareText.setOnClickListener(v -> shareText());

        Button btnShareImage = findViewById(R.id.btnShareImage);
        btnShareImage.setOnClickListener(v -> pickImage());

        Button btnShareMultiple = findViewById(R.id.btnShareMultiple);
        btnShareMultiple.setOnClickListener(v -> pickMultipleImages());
    }

    private void shareText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "分享标题");
        intent.putExtra(Intent.EXTRA_TEXT, "这是要分享的文本内容");
        startActivity(Intent.createChooser(intent, "分享到"));
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        try {
            pickImageLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法打开图库", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareSelectedImage(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(intent, "分享图片"));
    }

    private void pickMultipleImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        try {
            pickMultipleImagesLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法打开图库", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareMultipleImages(ArrayList<Uri> imageUris) {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivity(Intent.createChooser(intent, "分享多个图片"));
    }
} 