package com.advear.client.activity

import com.walhalla.landing.config.Cfg
import com.walhalla.ui.BuildConfig


object Cfg {
    const val COUNTER_TIME: Int = 2

    @JvmField
    var cfg: Cfg = Cfg.Builder()
        .setToolbarEnabled(true)
        .setCounterTimeMs(2400)
        .setSplashScreenEnabled(true)
        .setCheckConnection(!BuildConfig.DEBUG)
        .setSwipeEnabled(true)
        .setProgressEnabled(true)
        .build()

    // https://advear.net
    @JvmField
    var v0m: IntArray = intArrayOf(
        116,
        101,
        110,
        46,
        114,
        97,
        101,
        118,
        100,
        97,
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
