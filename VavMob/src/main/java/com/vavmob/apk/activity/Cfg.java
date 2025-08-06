package com.vavmob.apk.activity;


import com.vavmob.apk.BuildConfig;

public class Cfg {
    public static final int COUNTER_TIME = 2;

    static com.walhalla.landing.config.Cfg cfg = new com.walhalla.landing.config.Cfg.Builder()
            .setToolbarEnabled(true)
            .setCounterTimeMs(2400)
            .setSplashScreenEnabled(true)
            .setCheckConnection(!BuildConfig.DEBUG)
            .setSwipeEnabled(true)
            .setProgressEnabled(true)
            .build();

// https://vavmob.com/apk
//    public static int[] v0 = new int[]{
//            107, 112, 97, 47, 109, 111, 99, 46, 98, 111, 109, 118, 97, 118, 47, 47, 58, 115, 112, 116, 116, 104
//    };


    public static String v0 = "https://app.nightaps.com/login/";

}
