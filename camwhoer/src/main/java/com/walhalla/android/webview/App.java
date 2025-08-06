package com.walhalla.android.webview;

import android.app.Application;
import android.os.Build;

import com.walhalla.android.webview.UIWebView.Const;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        YandexMetricaConfig config = YandexMetricaConfig
                .newConfigBuilder("571ed09d-ed3d-4e81-bec9-61415a8580a5")
                .withLogs()
                .build();
        YandexMetrica.activate(this, config);

        //If AppMetrica has received referrer broadcast our own MyTrackerReceiver prints it to log
 //@@@       YandexMetrica.registerReferrerBroadcastReceivers(new MyTrackerReceiver());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            YandexMetrica.enableActivityAutoTracking(this);
        }


        YandexMetrica.reportEvent(Const.TAG_STARTED);
    }
}
