package online.biletiz.rabota

import androidx.multidex.MultiDexApplication
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import io.appmetrica.analytics.ValidationException
import io.appmetrica.analytics.push.AppMetricaPush

class BetApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //io.appmetrica.analytics.ValidationException: Invalid ApiKey
        try {
            // Creating an extended library configuration.
            val config: AppMetricaConfig =
                AppMetricaConfig.newConfigBuilder(API_APPMETRICA_KEY).build()
            // Initializing the AppMetrica SDK.
            AppMetrica.activate(this, config)

            AppMetricaPush.activate(this)
//        AppMetricaPush.activate(
//                getApplicationContext(),
//                new FirebasePushServiceControllerProvider(this),
//                new HmsPushServiceControllerProvider(this)
//        );
        } catch (e: ValidationException) {
            //DLog.handleException(e);
        } catch (e: java.lang.IllegalStateException) {
        }
    }

    companion object {
        private const val API_APPMETRICA_KEY = "6cb8d743-5912-42dc-a4fd-028e664df4e3"
    }
}
