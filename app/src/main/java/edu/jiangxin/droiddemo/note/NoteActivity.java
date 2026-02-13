package edu.jiangxin.droiddemo.note;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import edu.jiangxin.droiddemo.R;


public class NoteActivity extends AppCompatActivity {

    private static final int TITLE_MAX_CHAR = 30;
    private static final String PATTERN_FOR_FILE_NAME_RESERVED_CHAR =
            "[" + Pattern.quote("/\\?%*:|\"<>. ") + "]";
    private static final int FILE_SELECT_CODE = 1;

    private TextView titleView;
    private TextView contentView;
    @Nullable
    private File openedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleView = findViewById(R.id.titleView);
        titleView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                openedFile = null;
            }
        });

        contentView = findViewById(R.id.contentView);

        handlePasteToTitle();
        handlePasteToContent();
        handleClean();
        handleLoad();
        handleSave();
        handleOpen();
        handlePasteAppendToContext();
    }

    private void handleOpen() {
        View openBtn = findViewById(R.id.openBtn);
        openBtn.setOnClickListener(view -> {
            if (!hasTitle()) {
                Toast.makeText(NoteActivity.this, "No title specified", Toast.LENGTH_LONG).show();
                return;
            }
            File targetFile = getFileToSave();
            if (!targetFile.exists() && !save(targetFile) ) {
                return;
            }
            Uri path = Uri.fromFile(targetFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(NoteActivity.this,
                        "No Application Available to View File " + targetFile.getPath(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleSave() {
        View saveBtn = findViewById(R.id.saveButton);
        saveBtn.setOnClickListener(view -> {
            if (!hasTitle()) {
                Toast.makeText(NoteActivity.this, "No title specified", Toast.LENGTH_LONG).show();
                return;
            }
            File targetFile = getFileToSave();
            save(targetFile);
        });
    }

    private void handlePasteToContent() {
        View pasteToContentBtn = findViewById(R.id.pasteToContentBtn);
        pasteToContentBtn.setOnClickListener(view -> {
            String clipText = getPasteContent();
            if (clipText != null) {
                contentView.setText(clipText.trim());
            }
        });
    }

    private void handleClean() {
        View cleanBtn = findViewById(R.id.cleanBtn);
        cleanBtn.setOnClickListener(view -> {
            titleView.setText("");
            contentView.setText("");
        });
    }

    private void handleLoad() {
        View loadBtn = findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/plain");

            Uri uri = Uri.fromFile(getFolderToSave());
            intent.setDataAndType(uri, "text/plain");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivityForResult(intent, FILE_SELECT_CODE);
            } catch (ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(NoteActivity.this, "Please install a file browser", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FILE_SELECT_CODE:
                Uri url = data.getData();
                if (url == null) {
                    break;
                }
                try {
                    loadFile(url);
                } catch (Exception e) {
                    Log.e("fail to load", e.getMessage(), e);
                    Toast.makeText(NoteActivity.this, "Fail to load file " + url.getPath(), Toast.LENGTH_LONG);
                }
                break;
        }
    }

    private void loadFile(Uri uri) throws URISyntaxException, IOException {
        File f = new File(new URI(uri.toString()));
        String title = FilenameUtils.getBaseName(f.getName());
        String content = FileUtils.readFileToString(f);
        titleView.setText(title);
        contentView.setText(content);
        openedFile = f;
    }

    private void handlePasteToTitle() {
        View pasteToTitleBtn = findViewById(R.id.pasteToTitleBtn);
        pasteToTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clipText = getPasteContent();
                if (clipText == null) {
                    return;
                }
                clipText = clipText.trim();
                if (clipText.length() > TITLE_MAX_CHAR) {
                    clipText = clipText.substring(0, TITLE_MAX_CHAR);
                }
                int lineSep = clipText.indexOf("\n");
                if (lineSep != -1) {
                    clipText = clipText.substring(0, lineSep);
                }
                titleView.setText(clipText);
            }
        });
    }

    private boolean save(File targetFile) {
        try {
            if (!targetFile.exists()) {
                targetFile.getParentFile().mkdirs();
                targetFile.createNewFile();
            }
            String content = getContentToSave();
            FileWriter out = new FileWriter(targetFile);
            try {
                out.write(content);
            } finally {
                out.close();
            }
            String msg = MessageFormat.format("Save to {0}", targetFile.getPath());
            Toast.makeText(NoteActivity.this, msg, Toast.LENGTH_LONG).show();
            return true;
        } catch (IOException e) {
            Log.e("save file failed", e.getMessage(), e);
            Toast.makeText(NoteActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private String getContentToSave() {
        String content = contentView.getText().toString();
        return content;
    }

    private File getFileToSave() {
        File targetDir = getFolderToSave();

        String targetFileName = convertToValidFileName(titleView.getText().toString());
        return new File(targetDir, targetFileName + ".txt");
    }

    @NonNull
    private File getFolderToSave() {
        if (openedFile != null) {
            return openedFile.getParentFile();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = df.format(Calendar.getInstance().getTime());

        File extStorageDir = Environment.getExternalStorageDirectory();
        return new File(extStorageDir, "clipboard2file/" + datePath);
    }

    private boolean hasTitle() {
        return !titleView.getText().toString().trim().isEmpty();
    }

    /**
     * valid file name see: https://en.wikipedia.org/wiki/Filename
     */
    private String convertToValidFileName(String str) {
        return str.trim().replaceAll(PATTERN_FOR_FILE_NAME_RESERVED_CHAR, "_");
    }

    /**
     * @return null when clipboard is empty
     */
    @Nullable
    private String getPasteContent() {
        // Gets a handle to the clipboard service.
        ClipboardManager mClipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (!mClipboard.hasPrimaryClip()) {
            Toast.makeText(this,
                    "Clipboard is empty", Toast.LENGTH_SHORT).show();
            return null;
        }
        ClipData clipData = mClipboard.getPrimaryClip();
        int count = clipData.getItemCount();
        if (count > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            CharSequence str = item.coerceToText(this);
            return str.toString();
        } else {
            return "";
        }

//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < count; ++i) {
//            ClipData.Item item = clipData.getItemAt(i);
//            CharSequence str = item.coerceToText(this);
//            Log.i("pasteToResult", "item : " + i + ": " + str);
//            sb.append(str);
//        }
//        return sb.toString();
    }

    private void handlePasteAppendToContext() {
        View btn = findViewById(R.id.pasteToAppendContentBtn);
        btn.setOnClickListener(view -> {
            String clipText = getPasteContent();
            if (clipText != null) {
                contentView.append(clipText.trim());
                //TODO: scroll to end
                Toast.makeText(NoteActivity.this,
                        "Clipboard text has appended to text.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
