package com.example.android.myapplicationbrowser.activity;

import static org.chromium.net.CoreApplication.proxy;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.util.ArrayMap;

import com.walhalla.ui.DLog;

import org.chromium.net.CoreApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CoolClazz {





    @SuppressLint("PrivateApi")
    public static void test0(Context context) {
        try {
            Context appContext = context.getApplicationContext();
            // Get the application and APK classes.  Suppress the lint warning that reflection may not always work in the future and on all devices.

//            Class applicationClass = Class.forName(
//                    org.chromium.net.CoreApplication.class.getCanonicalName()
//            );

            Class abba = CoreApplication.ProxyReceiver.class;
            Class applicationClass = CoreApplication.class;

            // Get the declared fields.  Suppress the lint warning that 'mLoadedApk' cannot be resolved.
            for (Field field : applicationClass.getFields()) {
                if(field.getName().startsWith("aa")){
                    DLog.d("@@@" + field);
                }
            }
            @SuppressWarnings("JavaReflectionMemberAccess")
            Field mLoadedApkField = applicationClass.getDeclaredField("aa1");

            // Allow the values to be changed.
            mLoadedApkField.setAccessible(true);
            DLog.d("@@@" + mLoadedApkField.getName()+" "+mLoadedApkField.getDeclaringClass());

            Method onReceiveMethod = abba.getDeclaredMethod("onReceive", Context.class, Intent.class);

            Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
            //intent.setAction(Proxy.PROXY_CHANGE_ACTION);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ProxyInfo info = ProxyInfo.buildDirectProxy(
                        proxy.host,
                        proxy.port);
                intent.putExtra("android.intent.extra.PROXY_INFO", info);
            }
            //intent.putExtra("proxy", info);


            Class<?> o = mLoadedApkField.getClass();
            onReceiveMethod.invoke(new CoreApplication.ProxyReceiver(), appContext, intent);


            //@
            String className = "android.net.ProxyProperties";
            Class<?> cls = Class.forName(className);


        } catch (Exception e) {
            //DLog.handleException(e);
            DLog.d(" EEEEEEEEEEEEEEEEEEE "+e.getClass().getSimpleName()+" " + e.getLocalizedMessage());
        }
    }

//    @SuppressLint("PrivateApi")
//    public static void test0(Context context) {
//        try {
//            Context appContext = context.getApplicationContext();
//            // Get the application and APK classes.  Suppress the lint warning that reflection may not always work in the future and on all devices.
//
////            Class applicationClass = Class.forName(
////                    org.chromium.net.CoreApplication.class.getCanonicalName()
////            );
//
//            //Class applicationClass = org.chromium.net.CoreApplication.class;
//            //Class applicationClass = android.app.Application.class;
//
//            //DLog.d("@@"+applicationClass+"@@"+org.chromium.net.CoreApplication.class.getCanonicalName());
//
//            //Class applicationClass = Class.forName("androidx.multidex.MultiDexApplication");
//            Class applicationClass = Class.forName("android.app.Application");
//
//            Class loadedApkClass = Class.forName("android.app.LoadedApk");
//
//            // Get the declared fields.  Suppress the lint warning that 'mLoadedApk' cannot be resolved.
//            for (Field field : applicationClass.getFields()) {
//                DLog.d("@@" + field);
//            }
//            @SuppressWarnings("JavaReflectionMemberAccess")
//            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
//            Field mReceiversField = loadedApkClass.getDeclaredField("mReceivers");
//
//            // Allow the values to be changed.
//            mLoadedApkField.setAccessible(true);
//            mReceiversField.setAccessible(true);
//
//            // Get the APK object.
//            Object mLoadedApkObject = mLoadedApkField.get(appContext);
//
//            // Get an array map of the receivers.
//            ArrayMap receivers = (ArrayMap) mReceiversField.get(mLoadedApkObject);
//
//            // Set the proxy.
//            for (Object receiverMap : receivers.values()) {
//                for (Object receiver : ((ArrayMap) receiverMap).keySet()) {
//                    // 'Class<?>', which is an 'unbounded wildcard parameterized type', must be used instead of 'Class', which is a 'raw type'.  Otherwise, 'receiveClass.getDeclaredMethod' is unhappy.
//                    Class<?> receiverClass = receiver.getClass();
//                    // Get the declared fields.
//                    final Field[] declaredFieldArray = receiverClass.getDeclaredFields();
//                    if (receiverClass.getName().contains("ProxyReceiver")) {
//                        // Set the proxy for each field that is a 'ProxyChangeListener'.
//                        for (Field field : declaredFieldArray) {
//                            if (field.getType().getName().contains("ProxyChangeListener")) {
//
//                                DLog.d("@@" + field.getType().getName() + " " + receiverClass);
//                                Method onReceiveMethod = receiverClass.getDeclaredMethod("onReceive", Context.class, Intent.class);
//
//                                Intent intent = new Intent(Proxy.PROXY_CHANGE_ACTION);
//                                //intent.setAction(Proxy.PROXY_CHANGE_ACTION);
//
//                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                                    ProxyInfo info = ProxyInfo.buildDirectProxy(
//                                            proxy.host,
//                                            proxy.port);
//                                    intent.putExtra("android.intent.extra.PROXY_INFO", info);
//                                }
//                                //intent.putExtra("proxy", info);
//                                onReceiveMethod.invoke(receiver, appContext, intent);
//                            }
//                        }
//                        break;
//                    }
//                }
//            }
//
////            Class applictionCls = Class.forName(applicationClassName);
////            DLog.d("==>" + " " + applicationClassName);
////
////            Field loadedApkField = applictionCls.getField("mLoadedApk");
////            Field loadedApkField0 = applictionCls.getDeclaredField("mLoadedApk");
////
////            DLog.d("@"+(loadedApkField!=null));
////            DLog.d("@"+(loadedApkField0!=null));
////
////
////            loadedApkField.setAccessible(true);
////            Object loadedApk = loadedApkField.get(appContext);
////            Class loadedApkCls = Class.forName("android.app.LoadedApk");
////            Field receiversField = loadedApkCls.getDeclaredField("mReceivers");
////            receiversField.setAccessible(true);
////
////            ArrayMap receivers = (ArrayMap) receiversField.get(loadedApk);
////
////            for (Object receiverMap : receivers.values()) {
////                for (Object rec : ((ArrayMap) receiverMap).keySet()) {
////                    Class clazz = rec.getClass();
////
////                    if (clazz.getName().contains("ProxyChangeListener")) {
////                        DLog.d("@"+(clazz.getName()));
////                        Method onReceiveMethod = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
////                        Intent intent = new Intent("android.intent.action.PROXY_CHANGE");
////
////                        onReceiveMethod.invoke(rec, appContext, intent);
////                    }
////                }
////            }
//
////            for (ArrayMap keySet : ((ArrayMap) kkk).values()) {
////                for (Object rec : keySet.keySet()) {
////                    Class clazz = rec.getClass();
////                    if (clazz.getName().contains("ProxyChangeListener")) {
////                        applictionCls = applictionCls2;
////                        clazz.getDeclaredMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(rec, new Object[]{appContext, new Intent("android.intent.action.PROXY_CHANGE")});
////                    } else {
////                        applictionCls = applictionCls2;
////                    }
////                    applictionCls2 = applictionCls;
////                }
////            }
//
//        } catch (Exception e) {
//            //DLog.handleException(e);
//            DLog.d(" EEEEEEEEEEEEEEEEEEE " + e.getMessage());
//        }
//    }
}
