package com.prm4u.app;

import static com.prm4u.app.BuildConfig.ONESIGNAL_APP_ID;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class Prm4uApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
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
