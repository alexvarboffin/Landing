package com.chatgpt.app.activity;

import com.walhalla.landing.config.Cfg;

public class Cfg9 {

   public static Cfg cfgBetApp = new Cfg.Builder()
            .setToolbarEnabled(true)
            .setCounterTimeMs(2400)
            .setSplashScreenEnabled(false)
            .setCheckConnection(false)
            .setSwipeEnabled(false)
            .setProgressEnabled(true)
            .build();
}
