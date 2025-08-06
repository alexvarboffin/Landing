package com.wsms.client;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PermissionResolver {

    private static final int REQUEST_PERMISSIONS_CODE = 1001;

    private final Activity activity;
    private final MainView view;

    public PermissionResolver(MainView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void onSplashTimeout() {
        view.showMainContent();
    }


    String[] perms = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
            "android.permission.MODIFY_AUDIO_SETTINGS"
    };


    public void checkPermissions() {

        List<String> unresolved = new ArrayList<>();

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                unresolved.add(perm);
            }
        }
        if (!unresolved.isEmpty()) {
            view.showPermissionRequest();
            String[] p = unresolved.toArray(new String[0]);
            ActivityCompat.requestPermissions(activity, p, REQUEST_PERMISSIONS_CODE);
            DLog.d(Arrays.toString(p));

        } else {
            view.showPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            boolean granted = true;
            for (int i = 0; i < grantResults.length; i++) {
                boolean tmp = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                if (!tmp) {
                    granted = false;
                }
                DLog.d("granted?: " + permissions[i] + " " + tmp);
            }
            if (granted){
                view.showPermissionGranted();
            } else{
                view.showPermissionDenied();
            }
        } else {
            Toast.makeText(activity, "wwwwwww", Toast.LENGTH_SHORT).show();
        }
    }

    public interface MainView {
        void showMainContent();


        //perm
        void showPermissionRequest();

        void showPermissionDenied();

        void showPermissionGranted();
    }
}

