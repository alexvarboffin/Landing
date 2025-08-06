package com.advear.client.newweb;

import android.annotation.SuppressLint;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.appcompat.UWView;

import com.advear.client.databinding.FragmentSessionBinding;


@SuppressLint("SetJavaScriptEnabled")
public class WebViewRepository {

    public static @NonNull UWView createWebView1(Context context) {
        @NonNull UWView webView = new UWView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }

//    public static UWView createWebView2(Context context) {
//        UWView webView = new UWView(context);
//        webView.getSettings().setJavaScriptEnabled(true);
//        return webView;
//    }
}