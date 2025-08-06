package com.example.android.myapplicationbrowser.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.R;
import com.example.android.myapplicationbrowser.ProxyAuthWebViewClient;
import com.example.android.myapplicationbrowser.ProxySettings;
import com.example.android.myapplicationbrowser.model.MProxy;
import com.walhalla.ui.DLog;

import org.apache.http.Header;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.chromium.net.CoreApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;

public class MainActivity0 extends AppCompatActivity {


    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(webview = new WebView(this));

        web0();
        //CoolClazz.test0(this);
    }

    private void web0() {
        //Toolbar toolBar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolBar);

        //Set Webview instance


        WebSettings webSettings = webview.getSettings();
        CookieManager.getInstance().setAcceptCookie(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);


        String applicationClassName = "android.app.Application";
        //String applicationClassName = "org.chromium.net.CoreApplication";
        //String applicationClassName = "androidx.multidex.MultiDexApplication";
        MProxy proxy = CoreApplication.proxy;
        //ProxySettings.setProxy(webview, applicationClassName, proxy);
        //webview.setWebViewClient(new ProxyAuthWebViewClient(proxy.user, proxy.password));
        String url = "https://2ip.ru";
        loadUrl(webview, url, proxy);
    }


    public void loadUrl(WebView view, String url, MProxy proxy) {

        Toast.makeText(this, url + proxy.user + ", " + proxy.password, Toast.LENGTH_SHORT).show();

        if (proxy.user.isEmpty() && proxy.password.isEmpty()) {
            view.loadUrl(url);
        } else {
            UsernamePasswordCredentials creds
                    = new UsernamePasswordCredentials(proxy.user, proxy.password);
            Header credHeader = BasicScheme.authenticate(creds, "UTF-8", true);
            Map<String, String> header = new HashMap<>();
            header.put(credHeader.getName(), credHeader.getValue());
            view.loadUrl(url, header);
        }

    }
}