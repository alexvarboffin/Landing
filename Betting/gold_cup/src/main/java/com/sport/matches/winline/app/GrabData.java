package com.sport.matches.winline.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;

/**
 * Created by combo on 10/31/2017.
 * String iid = InstanceID.getInstance(context).getId()
 * Creating a GUID: String uniqueID = UUID.randomUUID().toString
 */

public class GrabData {



    public GrabData(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE
        );
        /*
         * getDeviceId() returns the unique device ID.
         * For gamestorm,the IMEI for GSM and the MEID or ESN for CDMA phones.
         */
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String deviceId = telephonyManager != null ? telephonyManager.getDeviceId() : null;

        /*
         * getSubscriberId() returns the unique subscriber ID,
         * For gamestorm, the IMSI for a GSM phone.
         */
        if (telephonyManager != null) {
            String subscriberId = telephonyManager.getSubscriberId();
        }
    }
}
