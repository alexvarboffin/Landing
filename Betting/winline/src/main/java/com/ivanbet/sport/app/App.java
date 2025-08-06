package com.ivanbet.sport.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Base64;

import androidx.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;

//import com.yandex.metrica.YandexMetrica;
//import com.yandex.metrica.YandexMetricaConfig;


import org.apache.cordova.base.AppConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class App extends MultiDexApplication {


    private final AppConfig config = new AppConfig(
            null,
            false,//true
            "",
            "",
            ""
    );

    @Override
    public void onCreate() {
        super.onCreate();

        try {
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setApplicationId("1087151334766") //APP ID Required for Analytics.
//                    .setProjectId("1087151334766") //PROJECT ID Required for Firebase Installations.
//                    .setApiKey(getString(R.string.api_key)) // Required for Auth.
//                    .build();
//            com.google.firebase.FirebaseApp.initializeApp(this, options, getString(R.string.app_name));
            com.google.firebase.FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            DLog.handleException(e);
        }

        if (!TextUtils.isEmpty(config.ONESIGNAL_APP_ID)) {

            // Enable verbose OneSignal logging to debug issues if needed.
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            // OneSignal Initialization
//            OneSignal.initWithContext(this);
//            OneSignal.setAppId(config.ONESIGNAL_APP_ID);

            OneSignal.initWithContext(this, config.ONESIGNAL_APP_ID);

//            // OneSignal Initialization
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    .unsubscribeWhenNotificationsAreDisabled(true)
//                    .autoPromptLocation(true)
//                    .init();
        }

//        if (config.FACEBOOK_ENABLED) {
//            try {
//                FacebookSdk.sdkInitialize(getApplicationContext());
//                AppEventsLogger.activateApp(this);
//                printHashKey(this);
//            } catch (Exception e) {
//                DLog.handleException(e);
//            }
//        }

        if (config.APP_METRICA_KEY != null) {
// Creating an extended library configuration.
//            YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(var0.API_key).build();
//            // Initializing the AppMetrica SDK.
//            YandexMetrica.activate(getApplicationContext(), config);
//            // Automatic tracking of user activity.
//            YandexMetrica.enableActivityAutoTracking(this);
        }


//        Dataset dataset = new Dataset(true,"https", false,
//                "11");
//        Gson gson = new GsonBuilder().create();
//        String rxr = gson.toJson(dataset);
//        System.out.println("221::" + rxr);

    }


    public static void printHashKey(Context pContext) {
        if (BuildConfig.DEBUG) {
            try {
                PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String hashKey = new String(Base64.encode(md.digest(), 0));
                    DLog.d("printHashKey() Hash Key: " + hashKey);
                }
            } catch (NoSuchAlgorithmException e) {
                DLog.handleException(e);
            } catch (Exception e) {
                DLog.handleException(e);
            }
        }
    }
}
