package com.tirauto.transport.app

import com.walhalla.landing.config.Cfg


object Cfg9 {
    @JvmField
    var cfg: Cfg = Cfg.Builder()
        .setToolbarEnabled(true)
        .setCounterTimeMs(2200) //2.2s
        .setSplashScreenEnabled(
            true
        )
        .setCheckConnection(true)
        .setSwipeEnabled(false)
        .setProgressEnabled(true)
        .build()

    // https://rabota.biletiz.online/lk
    @JvmField
    var v0: IntArray = intArrayOf(
        //
    )
}
