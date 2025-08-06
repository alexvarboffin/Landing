package com.stalpro.dashboard.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stalpro.dashboard.admin.BuildConfig;

public class WebViewActivity extends AppCompatActivity {

    private WebView mView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new WebView(this);
        mView.getSettings().setSupportZoom(true);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
        mView.getSettings().setLoadWithOverviewMode(true);
        mView.getSettings().setUseWideViewPort(true);//!@@@@@@@@@@

        mView.getSettings().getLoadsImagesAutomatically();
        mView.getSettings().setGeolocationEnabled(true);
        mView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mView.getSettings().setDomStorageEnabled(true);
        mView.getSettings().setBuiltInZoomControls(false);//+ - @@@@@@@@@@
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
            //mView.setBackgroundColor(Color.parseColor("#80002200"));
        }

        //makeFileSelector21_x(mView);

        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mView, true);
        }

        //mView.setWebViewClient(new CustomWebViewClient(mView, chromeView, activity));
        mView.setWebViewClient(new WebViewClient());
        mView.loadUrl("........");
        setContentView(mView);
    }

    @Override
    public void onBackPressed() {
        if (mView.canGoBack()) {
            mView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
