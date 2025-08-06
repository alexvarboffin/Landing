package com.betapsbookmakersbonus.client;


import com.walhalla.landing.config.Cfg;

public class BetApsCfg9 {

    public static final Cfg cfg = new Cfg.Builder()
            .setToolbarEnabled(false)
            .setCounterTimeMs(2400_000)
            .setSplashScreenEnabled(true)
            .setCheckConnection(true)
            .setSwipeEnabled(false)
            .setProgressEnabled(true)
            .build();


    // https://betboom-site.ru/
    public static int[] v0 = new int[] {47, 117, 114, 46, 101, 116, 105, 115, 45, 109, 111, 111, 98, 116, 101, 98, 47, 47, 58, 115, 112, 116, 116, 104};
}
