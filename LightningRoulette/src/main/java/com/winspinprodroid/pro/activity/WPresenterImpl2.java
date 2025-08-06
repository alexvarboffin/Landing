package com.winspinprodroid.pro.activity;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.UWView;
import androidx.appcompat.app.AlertDialog;

import com.walhalla.landing.BuildConfig;
import com.walhalla.landing.activity.WPresenter;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.CustomWebViewClient;
import com.walhalla.webview.MyWebChromeClient;

import java.io.File;

public class WPresenterImpl2 implements WPresenter, MyWebChromeClient.Callback {

    private final Activity activity;

    //protected ActivityResultLauncher<Intent> requestSelectFileLauncher0;
    //protected ActivityResultLauncher<Intent> requestFileChooser;
    
    protected ValueCallback<Uri[]> uploadMessage;
    private CustomWebViewClient var0;

    public WPresenterImpl2(Handler handler, Activity compatActivity) {
        this.activity = compatActivity;

//        requestSelectFileLauncher0 = compatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            final int resultCode = result.getResultCode();
////                    if (resultCode == Activity.RESULT_OK) {
////                        Intent data = result.getData();
////                        defaultMarketRateCallback(data, code);
////                    } else if (code == Activity.RESULT_CANCELED) {
////                        Intent data = result.getData();
////                        defaultMarketRateCallback(data, code);
////                    } else {
////                        Intent data = result.getData();
////                        defaultMarketRateCallback(data, code);
////                    }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (uploadMessage == null) return;
//                if (result.getResultCode() == RESULT_OK) {
//                }// Файлы были успешно выбраны
//                Intent data = result.getData();
//                Uri[] selectedFiles = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
//                alert(result, selectedFiles, activity);
//                uploadMessage.onReceiveValue(selectedFiles);
//                uploadMessage = null;
//            }
//        });


//        requestFileChooser = compatActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            final int resultCode = result.getResultCode();
//            if (null == mUploadMessage) return;
//            Intent intent = result.getData();
//            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
//            // Use RESULT_OK only if you're implementing WebView inside an Activity
//            Uri result0 = intent == null || resultCode != RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result0);
//            mUploadMessage = null;
//        });
    }

    
    
    

    private void makeFileSelector21_x(UWView view) {

//        view.setWebChromeClient(@@new WebChromeClient() {
//            // For 3.0+ Devices (Start)
//            // onActivityResult attached before constructor
//            private void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                openImageChooser(uploadMsg, acceptType);
//            }
//
//            // For Lollipop 5.0+ Devices
//            @Override
//            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                openImageChooser(webView, filePathCallback, fileChooserParams);
//                return true;
//            }
//
//            // openFileChooser for Android < 3.0
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooser(uploadMsg, "");
//            }
//
//            //openFileChooser for other Android versions
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                openFileChooser(uploadMsg, acceptType);
//            }
//        });

        view.setWebChromeClient(new MyWebChromeClient(this));
    }

//    public void openImageChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//        if (uploadMessage != null) {
//            uploadMessage.onReceiveValue(null);
//            uploadMessage = null;
//        }
//        uploadMessage = filePathCallback;
//        Intent intent = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            intent = fileChooserParams.createIntent();
//            if (fileChooserParams.getAcceptTypes() != null) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, fileChooserParams.getAcceptTypes());
//            }
//        }
//        try {
//            requestSelectFileLauncher0.launch(intent);
//            // activity.startActivityForResult(intent, REQUEST_SELECT_FILE);
//        } catch (ActivityNotFoundException e) {
//            uploadMessage = null;
//            //return false;
//        }
//    }

//    public void openImageChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//        mUploadMessage = uploadMsg;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("*/*"); //intent.setType("image/*");//"image/*"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        //activity.startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
//        requestFileChooser.launch(Intent.createChooser(intent, "File Browser"));
//    }


    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    public void a123(ChromeView chromeView, UWView mView) {
        mView.getSettings().setSupportZoom(false);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
        mView.getSettings().setLoadWithOverviewMode(true);
        //mView.getSettings().setUseWideViewPort(true);

        //ERR_TOO_MANY_REDIRECTS
        //mView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mView.getSettings().setAppCacheMaxSize( 100 * 1024 * 1024 ); // 100MB

        mView.getSettings().getLoadsImagesAutomatically();
        mView.getSettings().setGeolocationEnabled(true);
        mView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mView.getSettings().setDomStorageEnabled(true);
        mView.getSettings().setBuiltInZoomControls(false);//!@@@@@@@@@@
        mView.getSettings().setJavaScriptEnabled(true);
        mView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mView.getSettings().setAllowFileAccess(true);
        mView.getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView.getSettings().setAllowFileAccessFromFileURLs(true);
            mView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        //
        //System.getProperty("http.agent")
        String tmp = mView.getSettings().getUserAgentString();
        mView.getSettings().setUserAgentString(tmp.replace("; wv)", ")"));
        //mView.getSettings().setUserAgentString(...);
        if (BuildConfig.DEBUG) {
            mView.setBackgroundColor(Color.parseColor("#80770000"));
        }
        var0 = new CustomWebViewClient(mView, chromeView, activity);
        mView.setWebViewClient(var0);
        makeFileSelector21_x(mView);

        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mView, true);
        }

//        __mView.addJavascriptInterface(
//                new MyJavascriptInterface(CompatActivity.this, __mView), "JSInterface");
//@@        mView.addJavascriptInterface(new MyJavascriptInterface(mView.getContext(), mView), "Client");
    }

    //public static final int REQUEST_SELECT_FILE = 100;


//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (requestCode == REQUEST_SELECT_FILE) {
//                if (uploadMessage == null) return;
//                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
//                uploadMessage = null;
//            }
//        } else if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage) return;
//            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        }
//    }









    public void loadUrl(UWView mView, String s) {
        mView.stopLoading();
        mView.clearHistory();
        mView.loadUrl("about:blank");
        //mView.loadUrl("file:///android_asset/infAppPaused.html");
        var0.resetAllErrors();
        var0.setHomeUrl(s);
        mView.loadUrl(s);
    }

    private void alert(ActivityResult result, Uri[] selectedFiles, Context context) {
        Intent intent = result.getData();
        StringBuilder sb = new StringBuilder();
        if (selectedFiles != null) {
            for (Uri uri : selectedFiles) {
                sb.append(uri.toString()).append("\n");
            }
        }
        if (intent != null) {
            sb.append(intent);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("debug" + ((selectedFiles == null) ? selectedFiles : selectedFiles.length))

                .setMessage(sb.toString()).setPositiveButton(context.getString(android.R.string.ok), (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.setCancelable(false).create();
        alert.show();
    }


    @Override
    public void onProgressChanged(int progress) {

    }





    
}
