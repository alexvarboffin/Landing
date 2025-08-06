package com.example.android.myapplicationbrowser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.WebView;
import android.net.http.UrlRequest;

import androidx.annotation.NonNull;

import com.example.android.myapplicationbrowser.model.MProxy;
import com.walhalla.ui.DLog;

//import org.apache.http.HttpHost;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.PasswordAuthentication;


public class ProxySettings {

    static ProxySettings instance;

    private ProxySettings() {
    }

    public static ProxySettings getInstance() {
        if (instance == null) {
            instance = new ProxySettings();
        }
        return instance;
    }

    @SuppressLint("ObsoleteSdkInt")
    public boolean setProxy(@NonNull WebView webview, String applicationClassName, MProxy proxy) {

        if (VERSION.SDK_INT <= 13) {
            return setProxyUpToHC(webview, proxy.host, proxy.port);
        }
        if (VERSION.SDK_INT <= 15) {
            return setProxyICS(webview, proxy.host, proxy.port);
        }
        if (VERSION.SDK_INT <= 18) {
            return setProxyJB(webview, proxy.host, proxy.port);
        }
        return setProxyKKPlus(webview, applicationClassName, proxy);
    }


    private boolean setProxyUpToHC(WebView webview, String host, int port) {
//        DLog.d("Setting proxy with <= 3.2 API.");
//        HttpHost proxyServer = new HttpHost(host, port);
//        // Getting network
//        Class networkClass = null;
//        try {
//            networkClass = Class.forName("android.webkit.Network");
//            if (networkClass == null) {
//                DLog.d("failed to get class for android.webkit.Network");
//                return false;
//            }
//            Method getInstanceMethod = networkClass.getMethod("getInstance", new Class[]{Context.class});
//            if (getInstanceMethod == null) {
//                DLog.d("failed to get getInstance method");
//            }
//            Object network = getInstanceMethod.invoke(networkClass, new Object[]{webview.getContext()});
//            if (network == null) {
//                DLog.d("error getting network: network is null");
//                return false;
//            }
//            try {
//                Object requestQueue = getFieldValueSafely(networkClass.getDeclaredField("mRequestQueue"), network);
//                if (requestQueue == null) {
//                    DLog.d("Request queue is null");
//                    return false;
//                }
//                try {
//                    Field proxyHostField = Class.forName("android.net.http.RequestQueue").getDeclaredField("mProxyHost");
//                    boolean temp = proxyHostField.isAccessible();
//                    try {
//                        proxyHostField.setAccessible(true);
//                        proxyHostField.set(requestQueue, proxyServer);
//                    } catch (Exception e) {
//                        DLog.d("error setting proxy host");
//                    } catch (Throwable th) {
//                        proxyHostField.setAccessible(temp);
//                        throw th;
//                    }
//                    proxyHostField.setAccessible(temp);
//                    DLog.d("Setting proxy with <= 3.2 API successful!");
//                    return true;
//                } catch (Exception e2) {
//                    DLog.d("error getting proxy host field");
//                    return false;
//                }
//            } catch (Exception e3) {
//                DLog.d("error getting field value");
//                return false;
//            }
//        } catch (Exception ex) {
//            String str = "@@@";
//            StringBuilder sb = new StringBuilder();
//            sb.append("error getting network: ");
//            sb.append(ex);
//            Log.e(str, sb.toString());
//            return false;
//        }
        return false;
    }

    private boolean setProxyICS(WebView webview, String host, int port) {
        String str = "android.net.ProxyProperties";
        try {
            DLog.d("Setting proxy with 4.0 API.");
            Method updateProxyInstance = Class.forName("android.webkit.JWebCoreJavaBridge").getDeclaredMethod("updateProxy", new Class[]{Class.forName(str)});
            try {
                Object sJavaBridge = getFieldValueSafely(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), getFieldValueSafely(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), getFieldValueSafely(Class.forName("android.webkit.WebView").getDeclaredField("mWebViewCore"), webview)));
                Class ppclass = Class.forName(str);
                Class[] pparams = {String.class, Integer.TYPE, String.class};
                Class cls = ppclass;
                Class[] clsArr = pparams;
                updateProxyInstance.invoke(sJavaBridge, new Object[]{ppclass.getConstructor(pparams).newInstance(new Object[]{host, Integer.valueOf(port), null})});
                DLog.d("Setting proxy with 4.0 API successful!");
                return true;
            } catch (Exception e) {
//                StringBuilder sb = new StringBuilder();
//                sb.append("failed to set HTTP proxy: ");
//                sb.append(e);
                DLog.handleException(e);
                return false;
            }
        } catch (Exception e2) {
//            WebView webView = webview;
//            String str22 = LOG_TAG;
//            StringBuilder sb2 = new StringBuilder();
//            sb2.append("failed to set HTTP proxy: ");
//            sb2.append(e2);
            DLog.handleException(e2);
            return false;
        }
    }

    private boolean setProxyJB(WebView webview, String host, int port) {
        String str = "android.net.ProxyProperties";
        String str2 = "android.webkit.WebViewClassic";
        DLog.d("Setting proxy with 4.1 - 4.3 API.");
        try {
            Class wvcClass = Class.forName(str2);
            Object webViewClassic = wvcClass.getDeclaredMethod("fromWebView", new Class[]{Class.forName("android.webkit.WebView")}).invoke(null, new Object[]{webview});
            Class wv = Class.forName(str2);
            Object sJavaBridge = getFieldValueSafely(Class.forName("android.webkit.BrowserFrame").getDeclaredField("sJavaBridge"), getFieldValueSafely(Class.forName("android.webkit.WebViewCore").getDeclaredField("mBrowserFrame"), getFieldValueSafely(wv.getDeclaredField("mWebViewCore"), webViewClassic)));
            Constructor ppcont = Class.forName(str).getConstructor(new Class[]{String.class, Integer.TYPE, String.class});
            Class cls = wv;
            Class[] params = {Class.forName(str)};
            Class jwcjb = Class.forName("android.webkit.JWebCoreJavaBridge");
            Class[] clsArr = params;
            Class cls2 = jwcjb;
            Class cls3 = wvcClass;
            jwcjb.getDeclaredMethod("updateProxy", params).invoke(sJavaBridge, new Object[]{ppcont.newInstance(new Object[]{host, Integer.valueOf(port), null})});
            DLog.d("Setting proxy with 4.1 - 4.3 API successful!");
            return true;
        } catch (Exception ex) {
//            String str3 = LOG_TAG;
//            StringBuilder sb = new StringBuilder();
//            sb.append("Setting proxy with >= 4.1 API failed with error: ");
//            sb.append(ex.getMessage());
            DLog.handleException(ex);
            return false;
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("all")
    private boolean setProxyKKPlus(WebView webView, String applicationClassName, MProxy proxy) {

        DLog.d("Setting proxy with >= 4.4 API. " + proxy.toString());

        Context appContext = webView.getContext().getApplicationContext();
        systemsetProperty(proxy);

//        Authenticator.setDefault(
//                new Authenticator() {
//                    @Override
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(
//                                proxy.user, proxy.password.toCharArray());
//                    }
//                }
//        );

        if (proxy.user != null) {

        }


        try {


            // Get the application and APK classes.  Suppress the lint warning that reflection may not always work in the future and on all devices.
            Class applicationClass = Class.forName("android.app.Application");
            @SuppressLint("PrivateApi") Class loadedApkClass = Class.forName("android.app.LoadedApk");

            // Get the declared fields.  Suppress the lint warning that 'mLoadedApk' cannot be resolved.
            @SuppressWarnings("JavaReflectionMemberAccess") Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            Field mReceiversField = loadedApkClass.getDeclaredField("mReceivers");

            // Allow the values to be changed.
            mLoadedApkField.setAccessible(true);
            mReceiversField.setAccessible(true);

            // Get the APK object.
            Object mLoadedApkObject = mLoadedApkField.get(appContext);

            // Get an array map of the receivers.
            ArrayMap receivers = (ArrayMap) mReceiversField.get(mLoadedApkObject);

            // Set the proxy.
            for (Object receiverMap : receivers.values()) {
                for (Object receiver : ((ArrayMap) receiverMap).keySet()) {
                    // 'Class<?>', which is an 'unbounded wildcard parameterized type', must be used instead of 'Class', which is a 'raw type'.  Otherwise, 'receiveClass.getDeclaredMethod' is unhappy.
                    Class<?> receiverClass = receiver.getClass();

                    // Get the declared fields.
                    final Field[] declaredFieldArray = receiverClass.getDeclaredFields();
                    String clazName = receiverClass.getName();


                    if (validate(clazName)) {
                        DLog.d("[][]" + clazName);
                        // Set the proxy for each field that is a 'ProxyChangeListener'.
                        for (Field field : declaredFieldArray) {

                            DLog.d("\t\t" + field.getName() + " " + field.getType().getName());
                            DLog.d("\t\t" + field.getType().getName() + " " + receiverClass);

                            if (validateField(field)) {

                                Method onReceiveMethod = receiverClass.getDeclaredMethod("onReceive", Context.class, Intent.class);

                                ProxyInfo info0 = ProxyInfo.buildDirectProxy(proxy.host, proxy.port);
                                //ProxyChangeListener.ProxyConfig info = org.chromium.net.ProxyChangeListener.ProxyConfig.DIRECT;
//
                                Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
//                                //intent.setAction(Proxy.PROXY_CHANGE_ACTION);

                                Class<?> proxyClazz = receiverClass;
                                if (proxyClazz != null) {
                                    DLog.d("[RECEIVER-->]" + proxyClazz.getSimpleName() + " " + (onReceiveMethod != null));
                                } else {
                                    DLog.d("@@@@@@@@@@@@@@@@@@");
                                }

                                //>21

                                //intent.putExtra("proxy", info0);


                                /*********** optional, may be need in future *************/

                                try {
                                    final String CLASS_NAME = "android.net.ProxyProperties";
                                    Class cls = Class.forName(CLASS_NAME);
                                    Constructor constructor = cls.getConstructor(String.class, Integer.TYPE, String.class);
                                    constructor.setAccessible(true);
                                    Object proxyProperties = constructor.newInstance(proxy.host, proxy.port, null);
                                    intent.putExtra("proxy", (Parcelable) proxyProperties);
                                } catch (Exception e) {
                                    //DLog.handleException(e);
                                }

                                intent.putExtra("android.intent.extra.PROXY_INFO", info0);
                                /*********** optional, may be need in future *************/

                                try {
                                    onReceiveMethod.invoke(receiver, appContext, intent);
                                    DLog.d("@@@@@" + clazName + " OK!");
                                } catch (InvocationTargetException e) {
                                    DLog.d(receiver.getClass().getName() + " Oops, couldn't invoke this receiver");
                                }
                            }
                        }
                        //break;
                    }
                }
            }

//            Class applictionCls = Class.forName(applicationClassName);
//            DLog.d("==>" + " " + applicationClassName);
//
//            Field loadedApkField = applictionCls.getField("mLoadedApk");
//            Field loadedApkField0 = applictionCls.getDeclaredField("mLoadedApk");
//
//            DLog.d("@"+(loadedApkField!=null));
//            DLog.d("@"+(loadedApkField0!=null));
//
//
//            loadedApkField.setAccessible(true);
//            Object loadedApk = loadedApkField.get(appContext);
//            Class loadedApkCls = Class.forName("android.app.LoadedApk");
//            Field receiversField = loadedApkCls.getDeclaredField("mReceivers");
//            receiversField.setAccessible(true);
//
//            ArrayMap receivers = (ArrayMap) receiversField.get(loadedApk);
//
//            for (Object receiverMap : receivers.values()) {
//                for (Object rec : ((ArrayMap) receiverMap).keySet()) {
//                    Class clazz = rec.getClass();
//
//                    if (clazz.getName().contains("ProxyChangeListener")) {
//                        DLog.d("@"+(clazz.getName()));
//                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
//                        Intent intent = new Intent("android.intent.action.PROXY_CHANGE");
//
//                        onReceiveMethod.invoke(rec, appContext, intent);
//                    }
//                }
//            }

//            for (ArrayMap keySet : ((ArrayMap) kkk).values()) {
//                for (Object rec : keySet.keySet()) {
//                    Class clazz = rec.getClass();
//                    if (clazz.getName().contains("ProxyChangeListener")) {
//                        applictionCls = applictionCls2;
//                        clazz.getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(rec, new Object[]{appContext, new Intent("android.intent.action.PROXY_CHANGE")});
//                    } else {
//                        applictionCls = applictionCls2;
//                    }
//                    applictionCls2 = applictionCls;
//                }
//            }
            DLog.d("Setting proxy with >= 4.4 API successful!");
            return true;
        } catch (ClassNotFoundException e) {
            //DLog.handleException(e);
            DLog.d(" EEEEEEEEEEEEEEEEEEE " + applicationClassName);
            return false;
        } catch (NoSuchFieldException e2) {
            StringWriter sw2 = new StringWriter();
            e2.printStackTrace(new PrintWriter(sw2));
            String exceptionAsString2 = sw2.toString();
            DLog.handleException(e2);
            return false;
        } catch (IllegalAccessException e3) {
            StringWriter sw3 = new StringWriter();
            e3.printStackTrace(new PrintWriter(sw3));
            String exceptionAsString3 = sw3.toString();
            DLog.handleException(e3);
            return false;
        } catch (IllegalArgumentException e4) {
            StringWriter sw4 = new StringWriter();
            e4.printStackTrace(new PrintWriter(sw4));
            String exceptionAsString4 = sw4.toString();
            DLog.handleException(e4);
            return false;
        } catch (NoSuchMethodException e5) {
            StringWriter sw5 = new StringWriter();
            e5.printStackTrace(new PrintWriter(sw5));
            String exceptionAsString5 = sw5.toString();
            DLog.handleException(e5);
            return false;
        }
    }

    public void systemsetProperty(MProxy proxy) {
        DLog.d("~~ SYSTEM SET ~~");
        System.setProperty("http.proxyHost", proxy.host);
        System.setProperty("http.proxyPort", proxy.port + "");
        System.setProperty("https.proxyHost", proxy.host);
        System.setProperty("https.proxyPort", proxy.port + "");

        //err_connection_timeout
//        System.setProperty("https.proxyUser", proxy.user);
//        System.setProperty("https.proxyPassword", proxy.password);
//        System.setProperty("http.proxyUser", proxy.user);
//        System.setProperty("http.proxyPassword", proxy.password);

    }

    private boolean validateField(Field field) {
        return Build.VERSION.SDK_INT >= 24 ? true : field.getType().getName().contains("ProxyChangeListener");
    }

    private boolean validate(String clazName) {
        return Build.VERSION.SDK_INT >= 24 ? true : clazName.contains("ProxyReceiver");
    }

    private Object getFieldValueSafely(Field field, Object classInstance) throws IllegalArgumentException, IllegalAccessException {
        boolean oldAccessibleValue = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(classInstance);
        field.setAccessible(oldAccessibleValue);
        return result;
    }
}
