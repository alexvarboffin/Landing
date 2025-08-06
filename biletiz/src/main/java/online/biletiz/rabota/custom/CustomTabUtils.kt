package online.biletiz.rabota.custom

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import com.android.lib.CustomTabsBroadcastReceiver
import com.walhalla.ui.DLog.d

object CustomTabUtils {
    //Android 12
    val flag: Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT

    fun customWeb(context: Activity): CustomTabsIntent {
        //Configure the color of the address bar
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor( //context.getResources().getColor(R.color.colorPrimaryDark)
                //@@@ContextCompat.getColor(context, android.R.color.white)
                //ContextCompat.getColor(context, android.R.color.transparent)
                0
            ) //.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark))
            .setNavigationBarDividerColor(Color.BLACK)
            .setSecondaryToolbarColor( //Color.BLACK
                //@@@ContextCompat.getColor(context, android.R.color.transparent)
                0
            ).build()

        val builder0 = CustomTabsIntent.Builder()
        builder0.setShowTitle(false) // Скрыть заголовок
        builder0.setDefaultColorSchemeParams(defaultColors)
        builder0.setInstantAppsEnabled(true) // Включить возможность мгновенного запуска приложений


        //Configure a custom action button
        //24dp
        //@PandingIntent pandingIntent = new PendingI
        //@builder.setActionButton(icon, description, pendingIntent, tint);

        //Configure a custom menu
        //builder.addMenuItem(menuItemTitle, menuItemPendingIntent);
        attachMenuItem0(context, builder0)
        attachMenuItem1(context, builder0)
        builder0.setUrlBarHidingEnabled(true)

        val customTabsIntent = builder0.setUrlBarHidingEnabled(true).build()


        val list = getCustomTabsPackages(context)
        if (!list.isEmpty()) {
            for (info in list) {
//            24 ResolveInfo{5f59c04 com.android.chrome/com.google.android.apps.chrome.Main m=0x208000}
//               ResolveInfo{a8a75dc com.android.chrome/com.google.android.apps.chrome.IntentDispatcher m=0x208000}
                d("@" + info.toString() + " " + info.activityInfo.packageName)
                if ("com.android.chrome" == info.activityInfo.packageName) {
                    customTabsIntent.intent.setPackage("com.android.chrome") // Указываем пакет Chrome
                }
            }
        }

        return customTabsIntent
    }

    private fun attachMenuItem0(context: Activity?, builder0: CustomTabsIntent.Builder) {
        val intent: Intent = Intent(context, CustomTabsBroadcastReceiver::class.java)
        intent.setAction(CustomTabsBroadcastReceiver.ACTION_COPY_URL)
        var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, flag)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            //PendingIntent.FLAG_UPDATE_CURRENT
            pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        builder0.addMenuItem("Copy link...", pendingIntent).build()
    }

    private fun attachMenuItem1(context: Activity?, builder0: CustomTabsIntent.Builder) {
        val intent: Intent = Intent(context, CustomTabsBroadcastReceiver::class.java)
        intent.setAction(CustomTabsBroadcastReceiver.ACTION_ITEM_2)
        var pendingIntent = PendingIntent.getBroadcast(context, 0, intent, flag)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            //PendingIntent.FLAG_UPDATE_CURRENT
            pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        builder0.addMenuItem("Кастомное меню", pendingIntent).build()
    }


    fun getCustomTabsPackages(context: Context): ArrayList<ResolveInfo> {
        val pm = context.packageManager
        // Get default VIEW intent handler.
        val activityIntent: Intent? = Intent().setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(Uri.fromParts("http", "", null))

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = pm.queryIntentActivities(activityIntent!!, 0)
        val packagesSupportingCustomTabs = ArrayList<ResolveInfo>()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.setPackage(info.activityInfo.packageName)
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info)
            }
        }
        return packagesSupportingCustomTabs
    }
}
