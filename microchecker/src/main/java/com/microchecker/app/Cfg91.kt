package com.microchecker.app

import com.walhalla.landing.config.Cfg


object Cfg91 {
    @JvmField
    var cfg: Cfg = Cfg.Builder()
        .setToolbarEnabled(true)
        .setCounterTimeMs(24000000) //2.2s
        .setSplashScreenEnabled(true)
        .setCheckConnection(false)
        .setSwipeEnabled(false)
        .setProgressEnabled(true)
        .build()


    // https://microchecker.net/
    //public static int[] v0 = new int[] {47, 116, 101, 110, 46, 114, 101, 107, 99, 101, 104, 99, 111, 114, 99, 105, 109, 47, 47, 58, 115, 112, 116, 116, 104};
    // https://freeproxy.io
    //public static int[] v0 = new int[] {111, 105, 46, 121, 120, 111, 114, 112, 101, 101, 114, 102, 47, 47, 58, 115, 112, 116, 116, 104};
    // http://porden.com/open2/
    //public static int[] v0 = new int[] {47, 50, 110, 101, 112, 111, 47, 109, 111, 99, 46, 110, 101, 100, 114, 111, 112, 47, 47, 58, 112, 116, 116, 104};
    // https://kwork.ru/seller
    // public static int[] v0 = new int[]{114, 101, 108, 108, 101, 115, 47, 117, 114, 46, 107, 114, 111, 119, 107, 47, 47, 58, 115, 112, 116, 116, 104};
    //public static final boolean AD_MOB_ENABLED = false;
    // https://ekibastuz.net/
    @JvmField
    var v0: IntArray = intArrayOf(
        47,
        116,
        101,
        110,
        46,
        122,
        117,
        116,
        115,
        97,
        98,
        105,
        107,
        101,
        47,
        47,
        58,
        115,
        112,
        116,
        116,
        104
    )
}
