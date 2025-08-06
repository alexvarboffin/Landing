package com.mostapkbet.client.activity;


import com.mostapkbet.client.BuildConfig;
import com.walhalla.landing.loader.Config;

public class Cfg {

    //no splash
    public static Config cfg = new Config();

    static {
        Cfg.cfg.swipe_enabled = true;
        Cfg.cfg.progress_enabled = true;
        if (BuildConfig.DEBUG) {
            Cfg.cfg.check_connection = false;
        } else {
            Cfg.cfg.check_connection = true;
        }
    }


    // https://betcity-link.ru/mostbet-rus/
    public static int[] v0 = new int[]{47, 115, 117, 114, 45, 116, 101, 98, 116, 115, 111, 109, 47, 117, 114, 46, 107, 110, 105, 108, 45, 121, 116, 105, 99, 116, 101, 98, 47, 47, 58, 115, 112, 116, 116, 104};
}
