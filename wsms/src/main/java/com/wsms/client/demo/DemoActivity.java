package com.wsms.client.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.walhalla.landing.utils.CustomTabUtils;
import com.wsms.client.R;
import com.wsms.client.web.CustomTabs;
import com.wsms.client.web.TwaLauncherUtils;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final int REQUEST_AUDIO_PERMISSION = 2;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_webview);

        myWebView = findViewById(R.id.webview);
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        myWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setDomStorageEnabled(true);

        settings.setSupportZoom(false);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadWithOverviewMode(true);
        //settings.setUseWideViewPort(true);

        //ERR_TOO_MANY_REDIRECTS
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //settings.setAppCacheMaxSize( 100 * 1024 * 1024 ); // 100MB

        settings.getLoadsImagesAutomatically();
        settings.setGeolocationEnabled(true);

        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(false);//!@@@@@@@@@@


        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                runOnUiThread(() -> {
                    if (request.getResources().length == 0) {
                        return;
                    }
                    List<String> granted = new ArrayList<>();

                    for (String resource : request.getResources()) {
                        switch (resource) {
                            case PermissionRequest.RESOURCE_VIDEO_CAPTURE:
                                if (ContextCompat.checkSelfPermission(DemoActivity.this, Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(DemoActivity.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            REQUEST_CAMERA_PERMISSION);
                                } else {
                                    granted.add(PermissionRequest.RESOURCE_VIDEO_CAPTURE);
                                }
                                break;

                            case PermissionRequest.RESOURCE_AUDIO_CAPTURE:
                                if (ContextCompat.checkSelfPermission(DemoActivity.this, Manifest.permission.RECORD_AUDIO)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(DemoActivity.this,
                                            new String[]{Manifest.permission.RECORD_AUDIO},
                                            REQUEST_AUDIO_PERMISSION);
                                } else {
                                    granted.add(PermissionRequest.RESOURCE_AUDIO_CAPTURE);
                                }
                                break;

                            default:
                                request.deny();
                                break;
                        }
                    }
                    if (!granted.isEmpty()) {
                        request.grant(granted.toArray(new String[0]));
                    }
                });
            }
        });

        myWebView.loadUrl("https://wsms.ru/app/");


        //new TwaLauncherUtils(this).launchUrl("https://wsms.ru/app/");
        //new CustomTabs(this).launchUrl("https://wsms.ru/app/");


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you may want to reload the WebView or notify the PermissionRequest
            } else {
                // Permission denied
            }
        } else if (requestCode == REQUEST_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you may want to reload the WebView or notify the PermissionRequest
            } else {
                // Permission denied
            }
        }
    }
}
