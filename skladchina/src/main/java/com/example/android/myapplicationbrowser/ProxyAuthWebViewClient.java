package com.example.android.myapplicationbrowser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.walhalla.ui.DLog;

/**
 * myWebClient
 */

public class ProxyAuthWebViewClient extends WebViewClient {

    private final String proxyUserName;
    private final String proxyPassword;

    public ProxyAuthWebViewClient(String proxy_login, String proxy_pass) {
        this.proxyUserName = proxy_login;
        this.proxyPassword = proxy_pass;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        loadUrl(view, Uri.parse(url));
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        loadUrl(view, request.getUrl());
        return true;
    }

    private void loadUrl(WebView view, Uri uri) {

//        UsernamePasswordCredentials creds= new UsernamePasswordCredentials(proxyUserName, proxyPassword);
//        Header credHeader = BasicScheme.authenticate(creds, "UTF-8", true);
//        Map<String, String> header = new HashMap<String, String>();
//        header.put(credHeader.getName(), credHeader.getValue());
//        view.loadUrl(url, header);

        DLog.d("HANDLE-> " + uri);

        String path = uri.getPath();
        String host = uri.getHost();
        String scheme = uri.getScheme();
        String url = String.valueOf(uri);

        StringBuilder sb = new StringBuilder();
        sb.append("@@ url: ");
        sb.append(url);
        DLog.d(sb.toString());

        if (path.contains("redirect")) {
            try {
                String redirect_url = uri.getQueryParameter("url");
                if (redirect_url != null) {
                    view.getContext()
                            .startActivity(new Intent("android.intent.action.VIEW",
                                    Uri.parse(redirect_url)));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        view.loadUrl(url);
        return;
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // ignore ssl error
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if (handler != null) {
            handler.proceed();

        } else {
            super.onReceivedSslError(view, null, error);
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        //String url = view.getUrl();
        //view.loadUrl(url); //reload the url.
        DLog.d("@@@@@@@@@@@@@@@@@@@@@@@@@@");
        handler.proceed(this.proxyUserName, this.proxyPassword);
    }
}
