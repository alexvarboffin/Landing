package com.royspins.app;

import androidx.multidex.MultiDexApplication;

import com.walhalla.ui.DLog;

//import com.walhalla.stubinjector.AppController;
//import org.apache.cordova.base.AppConfig;

public class App extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
//        AppController app = new AppController();
//        app.init(this, new AppConfig(
//                "",
//                true,
//                BuildConfig.ONESIGNAL_APP_ID,
//                "",
//                ""
//        ));
        try {
            com.google.firebase.FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }
}
