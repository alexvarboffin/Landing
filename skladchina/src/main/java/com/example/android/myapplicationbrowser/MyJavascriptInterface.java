package com.example.android.myapplicationbrowser;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


public class MyJavascriptInterface {

    private final WebView webView;
    private Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    public MyJavascriptInterface(Context c, WebView webView) {
        this.webView = webView;
        mContext = c;
    }

    @JavascriptInterface   // must be added for API 17 or higher
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void finish() {
//            PackageHelper.removePackage(str);
//            InjectionActivity.this.finish();
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    public void showConfirmationDialog(final String callbackFunction, final String data, String title,
                                       String message) {

        Dialog.OnClickListener positiveListener = new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                webView.loadUrl("javascript:" + callbackFunction + "('" + data + "', true)");
            }
        };
        Dialog.OnClickListener negativeListener = new Dialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                webView.loadUrl("javascript:" + callbackFunction + "('" + data + "', false)");
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(message).setPositiveButton("Ok", positiveListener)
                .setNegativeButton("Cancel", negativeListener).setCancelable(false);
        builder.create().show();
    }
}
