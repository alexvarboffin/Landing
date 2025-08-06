package org.chromium.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDexApplication;
import androidx.webkit.ProxyConfig;

import com.example.android.myapplicationbrowser.ProxySettings;
import com.example.android.myapplicationbrowser.activity.CoolClazz;
import com.example.android.myapplicationbrowser.model.MProxy;
import com.walhalla.ui.DLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class CoreApplication extends MultiDexApplication {

    public static ProxySettings getPpa() {
        return ppa;
    }

    private static ProxySettings ppa;

    //    private String aa = "aaaa";
//    public ProxyReceiver aa1 = new ProxyReceiver();
//
    public static class ProxyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent.getAction().equals(Proxy.PROXY_CHANGE_ACTION)) {
//                ProxyConfig saa = extractNewProxy(intent);
//                DLog.d("@@@" + intent.toString()+saa);
                //proxySettingsChanged(saa);
            }
        }
    }

//    public static class ProxyConfig {
//        public ProxyConfig(String host, int port, String pacUrl, String[] exclusionList) {
//            mHost = host;
//            mPort = port;
//            mPacUrl = pacUrl;
//            mExclusionList = exclusionList;
//        }
//
//        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//        public static ProxyConfig fromProxyInfo(ProxyInfo proxyInfo) {
//            if (proxyInfo == null) {
//                return null;
//            }
//            final Uri pacFileUrl = proxyInfo.getPacFileUrl();
//            return new ProxyConfig(proxyInfo.getHost(), proxyInfo.getPort(),
//                    Uri.EMPTY.equals(pacFileUrl) ? null : pacFileUrl.toString(),
//                    proxyInfo.getExclusionList());
//        }
//
//        public final String mHost;
//        public final int mPort;
//        public final String mPacUrl;
//        public final String[] mExclusionList;
//
//        public static final ProxyConfig DIRECT = new ProxyConfig("", 0, "", new String[0]);
//
//        @Override
//        public String toString() {
//            return "ProxyConfig{" +
//                    "mHost='" + mHost + '\'' +
//                    ", mPort=" + mPort +
//                    ", mPacUrl='" + mPacUrl + '\'' +
//                    ", mExclusionList=" + Arrays.toString(mExclusionList) +
//                    '}';
//        }
//    }

//    static ProxyConfig extractNewProxy(Intent intent) {
//        Bundle extras = intent.getExtras();
//        if (extras == null) {
//            return null;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            return ProxyConfig.fromProxyInfo(
//                    (ProxyInfo) extras.get("android.intent.extra.PROXY_INFO"));
//        }
//
//        try {
//            final String getHostName = "getHost";
//            final String getPortName = "getPort";
//            final String getPacFileUrl = "getPacFileUrl";
//            final String getExclusionList = "getExclusionList";
//            final String className = "android.net.ProxyProperties";
//
//            Object props = extras.get("proxy");
//            if (props == null) {
//                return null;
//            }
//
//            Class<?> cls = Class.forName(className);
//            Method getHostMethod = cls.getDeclaredMethod(getHostName);
//            Method getPortMethod = cls.getDeclaredMethod(getPortName);
//            Method getExclusionListMethod = cls.getDeclaredMethod(getExclusionList);
//
//            String host = (String) getHostMethod.invoke(props);
//            int port = (Integer) getPortMethod.invoke(props);
//
//            String[] exclusionList;
//            String s = (String) getExclusionListMethod.invoke(props);
//            exclusionList = s.split(",");
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                Method getPacFileUrlMethod = cls.getDeclaredMethod(getPacFileUrl);
//                String pacFileUrl = (String) getPacFileUrlMethod.invoke(props);
//                if (!TextUtils.isEmpty(pacFileUrl)) {
//                    return new ProxyConfig(host, port, pacFileUrl, exclusionList);
//                }
//            }
//            return new ProxyConfig(host, port, null, exclusionList);
//        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
//                | InvocationTargetException | NullPointerException ex) {
//            Log.e("@@@", "Using no proxy configuration due to exception:" + ex);
//            return null;
//        }
//    }


    //api 21, 24 work

    public static final boolean FB_ENABLED = false;
    public static MProxy proxy = new MProxy(
            "185.170.166.24",
            80,
            "", ""
    );
    //public static MProxy proxy = MProxy.parse("95.216.42.172:53748@ec5wvq5q8ssje5bmoxdg:aDKYpxbzkp4rcg7wR738");

    @Override
    public void onCreate() {
        super.onCreate();
        if (FB_ENABLED) {
            com.google.firebase.FirebaseApp.initializeApp(this);
        }

        String[] aa = new String[]{
                "android.app.Application",
                "androidx.multidex.MultiDexApplication",
                "org.chromium.net.CoreApplication"
        };
        for (String s : aa) {
            try {
                Class applictionCls = Class.forName(s);
            } catch (ClassNotFoundException e) {
                DLog.d("@@@" + e.getMessage() + " " + s);
            }
        }

        ppa = ProxySettings.getInstance();

//        System.setProperty("http.proxyHost", "");
//        System.setProperty("http.proxyPort", "0:");
//        System.setProperty("https.proxyHost", "");
//        System.setProperty("https.proxyPort", "0:");

        ppa.systemsetProperty(proxy);


        //DLog.d("!@@@"+getProxyConfig().mPort);
        //new ProxyChangeListener().ProxyReceiver().proxySettingsChanged(null);

    }

//    @SuppressLint("NewApi")
//    private ProxyChangeListener.ProxyConfig getProxyConfig() {
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//         ProxyInfo proxyInfo = connectivityManager.getDefaultProxy();
//        return proxyInfo == null ? ProxyChangeListener.ProxyConfig.DIRECT : ProxyChangeListener.ProxyConfig.fromProxyInfo(proxyInfo);
//    }
}
