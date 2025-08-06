package com.j0bar.client;


import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

public class JobarApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(BuildConfig.ONESIGNAL_APP_ID)) {
            // OneSignal Initialization
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    .unsubscribeWhenNotificationsAreDisabled(true)
//                    .autoPromptLocation(true)
//                    .init();

            // Enable verbose OneSignal logging to debug issues if needed.
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

            // OneSignal Initialization
            //OneSignal.initWithContext(this);
            OneSignal.initWithContext(this, BuildConfig.ONESIGNAL_APP_ID);
//            DLog.d("OneSignal Initialization");
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    //--.unsubscribeWhenNotificationsAreDisabled(true)
//                    //.autoPromptLocation(true)
//                    .init();

//            OneSignal.unsubscribeWhenNotificationsAreDisabled(false);
//            OSDeviceState device = OneSignal.getDeviceState();
//            if (device != null) {
//                String email = device.getEmailAddress();
//                String emailId = device.getEmailUserId();
//                String pushToken = device.getPushToken();
//                String userId = device.getUserId();
//
//                boolean enabled = device.areNotificationsEnabled();
//                boolean subscribed = device.isSubscribed();
//                boolean pushDisabled = device.isPushDisabled();
//
//                DLog.d("[" + enabled + "] " + subscribed + " " + pushDisabled);
//                DLog.d(String.valueOf(device.toJSONObject()));
        }
    }
}
