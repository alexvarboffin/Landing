package com.advear.client.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.UWView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.advear.client.databinding.FragmentSessionBinding;
import com.walhalla.landing.BuildConfig;
import com.walhalla.landing.activity.BaseWPresenter;

import com.walhalla.ui.DLog;
import com.walhalla.webview.ChromeView;

public class WPresenterAAImpl2 extends BaseWPresenter {

    private final CookieManager cm;


    //protected ActivityResultLauncher<Intent> requestSelectFileLauncher0;
    //protected ActivityResultLauncher<Intent> requestFileChooser;

    protected ValueCallback<Uri[]> uploadMessage;
    private CustomWebViewClient0 var0;
    private MWebChromeClient123 myWebChromeClient;

    public WPresenterAAImpl2(Handler handler, AppCompatActivity compatActivity) {
        super(compatActivity);
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
        cm = CookieManager.getInstance();
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
    public void newVariant(String sessionName, ChromeView chromeView, @NonNull UWView mView,

                           RelativeLayout childLayout, RelativeLayout browserLayout) {
        mView.getSettings().setSupportZoom(false);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
        mView.getSettings().setLoadWithOverviewMode(true);
        //mView.getSettings().setUseWideViewPort(true);

        //ERR_TOO_MANY_REDIRECTS
        //mView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mView.getSettings().setAppCacheMaxSize( 100 * 1024 * 1024 ); // 100MB

        mView.getSettings().getLoadsImagesAutomatically();
        mView.getSettings().setGeolocationEnabled(true);

        mView.getSettings().setDomStorageEnabled(true);
        mView.getSettings().setBuiltInZoomControls(false);//!@@@@@@@@@@

        //@@@@@@@@@@@@@@@@@@@@@@
        mView.getSettings().setSupportMultipleWindows(true);

        mView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

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
        String agent = tmp.replace("; wv)", ")");
        DLog.d("@Agent-0@" + agent);
        mView.getSettings().setUserAgentString(agent);
        //mView.getSettings().setUserAgentString(...);
        if (BuildConfig.DEBUG) {
            mView.setBackgroundColor(Color.parseColor("#80770000"));
        }
        var0 = new CustomWebViewClient0(sessionName, mView, chromeView, activity);
        mView.setWebViewClient(var0);


        ///@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

        myWebChromeClient = new MWebChromeClient123(childLayout, browserLayout, activity, this);
        mView.setWebChromeClient(myWebChromeClient);

        ///@@@@@@@@@@@@@@@@@@@@@@@@@@@


        // Ensure cookies are shared between WebViews
        cm.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cm.setAcceptThirdPartyCookies(mView, true);
        }
        cm.setAcceptFileSchemeCookies(true);
        cm.setAcceptCookie(true);
        //cookieManager.setCookie("your-domain.com", "your-cookie-data");

//        __mView.addJavascriptInterface(
//                new MyJavascriptInterface(CompatActivity.this, __mView), "JSInterface");

        mView.addJavascriptInterface(new MyJavaScriptInterface(mView.getContext(), mView), "Client");
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


    @Override
    public void a123(ChromeView chromeView, UWView webView) {

    }

    @Override
    public void onConfirmation__(boolean allowed, String[] resources) {

    }


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


    public void closeChild() {
        if (myWebChromeClient != null) {
            myWebChromeClient.closeChild();
        }
    }
}
