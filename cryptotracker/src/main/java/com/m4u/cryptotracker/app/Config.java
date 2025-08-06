package com.m4u.cryptotracker.app;


import com.walhalla.landing.config.Cfg;

public class Config {

    // https://kwklandingcryptotracker.web.app/
    public static int[] v0 = new int[] {47, 112, 112, 97, 46, 98, 101, 119, 46, 114, 101, 107, 99, 97, 114, 116, 111, 116, 112, 121, 114, 99, 103, 110, 105, 100, 110, 97, 108, 107, 119, 107, 47, 47, 58, 115, 112, 116, 116, 104};
    //public static final boolean AD_MOB_ENABLED = false;

    public static final boolean SPLASH_SCREEN_ENABLED = true;
    public static final boolean CHECK_CONNECTION = false;
    public static final boolean SWIPE_ENABLED = true;
    public static final boolean PROGRESS_ENABLED = true;

    public static Cfg cfgBetApp = new Cfg.Builder()
            .setToolbarEnabled(true)
            .setCounterTimeMs(2400)
            .setSplashScreenEnabled(true)
            .setCheckConnection(false)
            .setSwipeEnabled(true)
            .setProgressEnabled(true)
            .build();
}
