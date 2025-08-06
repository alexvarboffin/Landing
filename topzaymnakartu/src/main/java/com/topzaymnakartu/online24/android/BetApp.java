package com.topzaymnakartu.online24.android;


import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.walhalla.ui.DLog;

import io.appmetrica.analytics.AppMetrica;
import io.appmetrica.analytics.AppMetricaConfig;
import io.appmetrica.analytics.push.AppMetricaPush;


public class BetApp extends MultiDexApplication {


    private static final String API_ONESIGNAL = "da9010fa-806e-40bb-9aaa-b28314b9ce37";
    private static final String API_APPMETRICA_KEY = "ee0cee5e-0198-4dfb-b770-c189bf988993";

    @Override
    public void onCreate() {
        super.onCreate();
        //io.appmetrica.analytics.ValidationException: Invalid ApiKey
        try {
            // Creating an extended library configuration.
            AppMetricaConfig config = AppMetricaConfig.newConfigBuilder(API_APPMETRICA_KEY).build();
            // Initializing the AppMetrica SDK.
            AppMetrica.activate(this, config);

            AppMetricaPush.activate(this);
//        AppMetricaPush.activate(
//                getApplicationContext(),
//                new FirebasePushServiceControllerProvider(this),
//                new HmsPushServiceControllerProvider(this)
//        );
        }catch (io.appmetrica.analytics.ValidationException e){
            DLog.handleException(e);
        }

        if (!TextUtils.isEmpty(API_ONESIGNAL)) {
            // OneSignal Initialization
//            OneSignal.startInit(this)
//                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
//                    .unsubscribeWhenNotificationsAreDisabled(true)
//                    .autoPromptLocation(true)
//                    .init();

            // OneSignal Initialization
            OneSignal.initWithContext(this, API_ONESIGNAL);
            // Enable verbose OneSignal logging to debug issues if needed.
            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);
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

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getApplicationContext(),
//                            new String[]{POST_NOTIFICATIONS}, 1);
//                }
//            }

            String id = OneSignal.getUser().getOnesignalId();
            DLog.d("@@@@@@@@@@" + id);
        }
    }
}
