package com.wsms.client;

import android.util.SparseIntArray;


public class Cfg9 {

    public static com.walhalla.landing.config.Cfg cfg;

    public static final int COUNTER_TIME = 2;



    public static int[] v0 = new int[]{
            //47, 112, 112, 97, 47, 117, 114, 46, 115, 109, 115, 119, 47, 47, 58, 115, 112, 116, 116, 104


            //https://iframe.coomeet.com
            109, 111, 99, 46, 116, 101, 101, 109, 111, 111, 99, 46, 101, 109, 97, 114, 102, 105, 47, 47, 58, 115, 112, 116, 116, 104
            //testcam
            //47, 109, 111, 99, 46, 115, 116, 115, 101, 116, 99, 105, 109, 47, 47, 58, 115, 112, 116, 116, 104

    };

    //public static final boolean AD_MOB_ENABLED = false;
    //public static final String ONESIGNAL_APP_ID = "";



    public static final SparseIntArray contacts = new SparseIntArray();


    static {
        contacts.append(R.id.contact_call, R.string.app_phone);
        contacts.append(R.id.contact_email, R.string.app_email);
        contacts.append(R.id.contact_address, R.string.app_address);
        contacts.append(R.id.contact_facebook, R.string.app_facebook);
        contacts.append(R.id.contact_instagram, R.string.app_instagram);
        contacts.append(R.id.contact_ok, R.string.app_ok);
        contacts.append(R.id.contact_vk, R.string.app_vk);
        contacts.append(R.id.contact_youtube, R.string.app_youtube);
        contacts.append(R.id.contact_twitter, R.string.app_twitter);
    }
}
