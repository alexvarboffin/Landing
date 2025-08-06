package io.zerocard.pro;


import androidx.multidex.MultiDexApplication;

import com.walhalla.landing.loader.Config;
import com.walhalla.landing.loader.ConfigLoader;

//import com.onesignal.OneSignal;

public class App extends MultiDexApplication {




    @Override
    public void onCreate() {
        super.onCreate();
        Cfg.cfg = ConfigLoader.loadConfig(this);
        // OneSignal Initialization
//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();

//        if (!TextUtils.isEmpty(Cfg.ONESIGNAL_APP_ID)) {
//
//            // Enable verbose OneSignal logging to debug issues if needed.
//            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
//            // OneSignal Initialization
//            OneSignal.initWithContext(this);
//            OneSignal.setAppId(Cfg.ONESIGNAL_APP_ID);
////            DLog.d("OneSignal Initialization");
////            OneSignal.startInit(this)
////                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
////                    //.setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
////                    //--.unsubscribeWhenNotificationsAreDisabled(true)
////                    //.autoPromptLocation(true)
////                    .init();
//
//
//            OneSignal.unsubscribeWhenNotificationsAreDisabled(false);
////            OSDeviceState device = OneSignal.getDeviceState();
////            if (device != null) {
////                String email = device.getEmailAddress();
////                String emailId = device.getEmailUserId();
////                String pushToken = device.getPushToken();
////                String userId = device.getUserId();
////
////                boolean enabled = device.areNotificationsEnabled();
////                boolean subscribed = device.isSubscribed();
////                boolean pushDisabled = device.isPushDisabled();
////
////                DLog.d("[" + enabled + "] " + subscribed + " " + pushDisabled);
////                DLog.d(String.valueOf(device.toJSONObject()));
////            }
//        }
    }
}
