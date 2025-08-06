package com.stalpro.dashboard.admin;

import android.util.SparseIntArray;
import com.stalpro.dashboard.admin.R;


public class Config {
    public static int[] v0 = new int[] {109, 111, 99, 46, 111, 114, 112, 108, 97, 116, 115, 46, 117, 47, 47, 58, 115, 112, 116, 116, 104};

    //public static final boolean AD_MOB_ENABLED = false;
    public static final boolean SHOW_TOOLBAR = false;
    public static final boolean SPLASH_SCREEN_ENABLED = true;
    public static final boolean SWIPE_ENABLED = true;
    public static final boolean PROGRESS_ENABLED = true;


    public static final SparseIntArray contacts = new SparseIntArray();

    private static final int DEMOVALUE = R.string.app_name;

    static {
//        contacts.append(R.id.contact_call, R.string.app_phone);
//        contacts.append(R.id.contact_email, R.string.app_email);
//        contacts.append(R.id.contact_address, R.string.app_address);
//        contacts.append(R.id.contact_facebook, R.string.app_facebook);
//        contacts.append(R.id.contact_instagram, R.string.app_instagram);
//        contacts.append(R.id.contact_ok, R.string.app_ok);
//        contacts.append(R.id.contact_vk, R.string.app_vk);
//        contacts.append(R.id.contact_youtube, R.string.app_youtube);
//        contacts.append(R.id.contact_twitter, R.string.app_twitter);


        contacts.append(R.id.contact_call, DEMOVALUE);
        contacts.append(R.id.contact_email, DEMOVALUE);
        contacts.append(R.id.contact_address, DEMOVALUE);
        contacts.append(R.id.contact_facebook, DEMOVALUE);
        contacts.append(R.id.contact_instagram, DEMOVALUE);
        contacts.append(R.id.contact_ok, DEMOVALUE);
        contacts.append(R.id.contact_vk, DEMOVALUE);
        contacts.append(R.id.contact_youtube, DEMOVALUE);
        contacts.append(R.id.contact_twitter, DEMOVALUE);
    }
}
