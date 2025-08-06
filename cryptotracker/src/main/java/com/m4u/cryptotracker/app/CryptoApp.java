package com.m4u.cryptotracker.app;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import io.appmetrica.analytics.AppMetrica;
import io.appmetrica.analytics.AppMetricaConfig;
import io.appmetrica.analytics.push.AppMetricaPush;

public class CryptoApp extends MultiDexApplication {


    private static final String API_KEY = "a58a17cb-56bb-4080-981a-403e92e603f4";

    @Override
    public void onCreate() {
        super.onCreate();


        // Creating an extended library configuration.
        AppMetricaConfig config = AppMetricaConfig.newConfigBuilder(API_KEY).build();
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config);

        AppMetricaPush.activate(this);
//        AppMetricaPush.activate(
//                getApplicationContext(),
//                new FirebasePushServiceControllerProvider(this),
//                new HmsPushServiceControllerProvider(this)
//        );


        if (BuildConfig.DEBUG) {
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
        }
        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);
        OneSignal.initWithContext(this, BuildConfig.ONESIGNAL_APP_ID);
//            DLog.d("OneSignal Initialization");
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    //--.unsubscribeWhenNotificationsAreDisabled(true)
//                    //.autoPromptLocation(true)
//                    .init();

        //OneSignal.unsubscribeWhenNotificationsAreDisabled(false);
    }
}
