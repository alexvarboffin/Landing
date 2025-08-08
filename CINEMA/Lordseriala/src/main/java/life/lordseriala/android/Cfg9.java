package life.lordseriala.android;

import android.util.SparseIntArray;

import com.walhalla.landing.config.Cfg;


public class Cfg9 {

    //public static final boolean AD_MOB_ENABLED = false;

    public static Cfg cfg = new Cfg.Builder()
            .setToolbarEnabled(false)
            .setCounterTimeMs(2400)
            .setSplashScreenEnabled(true)
            .setCheckConnection(true)
            .setSwipeEnabled(true)
            .setProgressEnabled(true)
            .build();

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
