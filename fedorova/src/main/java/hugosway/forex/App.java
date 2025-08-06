package hugosway.forex;

import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();

//        if (!TextUtils.isEmpty(BuildConfig.ONESIGNAL_APP_ID)) {
//        }
    }
}
