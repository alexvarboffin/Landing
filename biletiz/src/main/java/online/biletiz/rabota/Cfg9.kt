package online.biletiz.rabota

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
        107,
        108,
        47,
        101,
        110,
        105,
        108,
        110,
        111,
        46,
        122,
        105,
        116,
        101,
        108,
        105,
        98,
        46,
        97,
        116,
        111,
        98,
        97,
        114,
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
