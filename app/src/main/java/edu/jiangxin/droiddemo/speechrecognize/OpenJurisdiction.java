package edu.jiangxin.droiddemo.speechrecognize;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class OpenJurisdiction {

    private static String[] PERMISSIONS_STORAGE;

    static {
        PERMISSIONS_STORAGE = new String[]{
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };
    }

    public void addPER(String string,Activity activity){
        String [] ret = new String[PERMISSIONS_STORAGE.length+1];
        System.arraycopy(PERMISSIONS_STORAGE, 0, ret, 0, PERMISSIONS_STORAGE.length);
        ret [PERMISSIONS_STORAGE.length] = string;
        PERMISSIONS_STORAGE = ret;
        open(activity);
    }
    void open(Activity obj){
        for (String aPERMISSIONS_STORAGE : PERMISSIONS_STORAGE) {
            if (ActivityCompat.checkSelfPermission(obj,aPERMISSIONS_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                int REQUEST_PERMISSION_CODE = 2;
                ActivityCompat.requestPermissions(obj, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
    }
}
