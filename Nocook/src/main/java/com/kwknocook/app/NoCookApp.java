package com.kwknocook.app;

import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.walhalla.ui.DLog;

import java.util.Arrays;
import java.util.Map;

public class NoCookApp extends MultiDexApplication {
    public static String af_status = "";
    public static String idfa = "";
    
    public static String subid2 = "zaimodobren";

    public static String subid4 = "4";
    public static String adset;

    private static final String APPSFLYER_DEV_KEY = "FYA2AtdKQx4feejKo8s7E5";

    @Override
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(APPSFLYER_DEV_KEY)) {
            ////////////////////////////////////////////////////////////////////////////////////
            final AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
                @Override
                public void onConversionDataSuccess(Map<String, Object> conversionData) {
                    if (conversionData != null) {
                        for (Map.Entry<String, Object> entry : conversionData.entrySet()) {
                            DLog.d("conversion_attribute: " + entry.getKey() + " = " + entry.getValue());
                        }

//                af_status = Organic █
//                af_message = organic install █
//                is_first_launch = false

                        Object tmp = conversionData.get("af_status");
                        if (tmp != null) {
                            af_status = tmp.toString();
                        }
                        if (af_status.equalsIgnoreCase("Non-organic")) {
                            tmp = conversionData.get("campaign");
                            if (tmp != null) {
                                String campaign = tmp.toString();
                                String[] campaignParts = campaign.split("_");
                                Log.e("campaign", Arrays.toString(campaignParts));
                                try {
                                    subid2 = campaignParts[0];
                                } catch (Exception e) {
                                    subid2 = "";
                                }
                                try {
                                    subid4 = campaignParts[2];
                                } catch (Exception e) {
                                    subid4 = "";
                                }
                            }

                            tmp = conversionData.get("adset");
                            if (tmp != null) {
                                adset = tmp.toString();
                            }
                        }
                    }
                }

                @Override
                public void onConversionDataFail(String errorMessage) {
                    DLog.d("FAIL: " + errorMessage);
                }

                @Override
                public void onAppOpenAttribution(Map<String, String> attributionData) {
                    DLog.d("");
                }

                @Override
                public void onAttributionFailure(String errorMessage) {
                    DLog.d("");
                }
            };
            AppsFlyerLib.getInstance().init(APPSFLYER_DEV_KEY, conversionListener, this);
            //AppsFlyerLib.getInstance().startTracking(this);
            ////////////////////////////////////////////////////////////////////////////////////
        }
    }
}
