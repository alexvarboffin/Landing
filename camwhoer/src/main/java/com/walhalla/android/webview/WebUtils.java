package com.walhalla.android.webview;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;

import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.walhalla.android.BuildConfig;
import com.walhalla.android.webview.widget.ExtendedWebView;

public class WebUtils {

    static String userAgent1 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/118.0";

    @SuppressLint({"SetJavaScriptEnabled", "ObsoleteSdkInt"})
    public static void makeWebView(WebView w) {
        w.getSettings().setUserAgentString(
                userAgent1
                //System.getProperty("http.agent")
        );
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setDomStorageEnabled(true);//without this option not work videochat

        w.getSettings().setBuiltInZoomControls(true);
        //w.getSettings().setDisplayZoomControls(true);

        w.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        try {
//            DLog.d("Flash plugin -> : " + w.getSettings().getPluginState());
//            w.getSettings().setPluginState(WebSettings.PluginState.OFF);
//            DLog.d( "Flash plugin -> : " + w.getSettings().getPluginState());
//        } catch (Exception e) {
//            DLog.d("Flash plugin -> : " + e.getLocalizedMessage());
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            w.getSettings().setAllowFileAccessFromFileURLs(true);
            w.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        w.getSettings().setAllowFileAccess(true);
        w.getSettings().setAllowContentAccess(true);


//        if (Build.VERSION.SDK_INT > 16) {
//            w.getSettings().setMediaPlaybackRequiresUserGesture(false);
//        }

        w.getSettings().setBlockNetworkImage(false);//#
        w.getSettings().setDefaultTextEncodingName("utf-8");
        w.getSettings().setPluginState(WebSettings.PluginState.ON);
        w.getSettings().setLoadWithOverviewMode(true);
        w.getSettings().setUseWideViewPort(true);

        w.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        w.getSettings().setAllowFileAccess(true);


        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(w, true);
        }

//        if(BuildConfig.DEBUG){
//            w.setBackgroundColor(Color.GREEN);
//        }
    }
}
