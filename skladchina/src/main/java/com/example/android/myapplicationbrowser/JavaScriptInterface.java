package com.example.android.myapplicationbrowser;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.walhalla.ui.DLog;


public class JavaScriptInterface {
    private Activity activity;

    public JavaScriptInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void openBrowser(String data) {
        //
//        try {
//            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.PRIVACY_POLICY)));
//        } catch (ActivityNotFoundException e) {
//            //browser not found
//        }
    }


    @JavascriptInterface
    public void log(String data) {
        DLog.d("log: " + data);
    }
}
