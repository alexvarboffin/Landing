package com.mostapkbet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PackageRemovedReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
//            String packageName = intent.getData().getSchemeSpecificPart();
//            if (packageName != null && packageName.equals(context.getPackageName())) {
                Toast.makeText(context, "@@@ Приложение было удалено", Toast.LENGTH_LONG).show();
                // Можете выполнить здесь необходимые действия

//            }
        }
    }
}

