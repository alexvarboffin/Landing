package com.mostapkbet.client.app;

import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.mostapkbet.client.BuildConfig;
import com.my.tracker.MyTracker;
import com.my.tracker.MyTrackerConfig;
import com.my.tracker.MyTrackerParams;
import com.my.tracker.config.AntiFraudConfig;
import com.walhalla.ui.DLog;

import java.util.HashMap;
import java.util.Map;

public class MostbetApp extends MultiDexApplication {


    private static final String SDK_KEY = "09528971785472646354";

    //private AppAdapter presenter;


    @Override
    public void onCreate() {
        super.onCreate();

        // При необходимости, настройте конфигурацию трекера
        MyTrackerParams trackerParams = MyTracker.getTrackerParams();
        MyTrackerConfig trackerConfig = MyTracker.getTrackerConfig();
        // …
        // Настройте параметры трекера
        // …
        // Инициализируйте трекер
        MyTracker.initTracker(SDK_KEY, this);
//        MyTracker.isDebugMode();
//        MyTracker.getTrackerConfig().setAntiFraudConfig(
//                AntiFraudConfig.newBuilder()
//                        .useGyroscopeSensor()
//                        .useLightSensor()
//                        .build()
//        );

        if (BuildConfig.DEBUG) {
            MyTracker.setDebugMode(true);
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put("someParamKey1", "someParamValue1");
            eventParams.put("someParamKey2", "someParamValue2");
            MyTracker.trackEvent("eventName", eventParams);
        }
        Handler handler = new Handler(Looper.getMainLooper());
//        presenter = new AppAdapter(handler, this);
//        presenter.execute();
    }

    public void tst(String ba, String path) {
        Toast.makeText(MostbetApp.this, ba+" "+path, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //presenter.terminate();
    }
}
