package com.walhalla.android.webview.adapter;

import android.app.Activity;

import com.walhalla.android.R;
import com.walhalla.android.webview.LStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ChangesObj {
    public static final Integer REMOVED = -2;

    public static Map<String, Integer> handleChanges(Activity activity) {
        Map<String, Integer> arr = new HashMap<>();
        Set<String> stringSet = LStorage.getInstance(activity).whiteListLabels();
//        <item>HTML</item>
//        <item>XHR</item>
//        <item>Fonts</item>
//        <item>WS</item>
//        <item>Other</item>

        if (stringSet.contains("JS")) {
            arr.put(".js", R.color.u_js);
        } else {
            arr.put(".js", REMOVED);
        }
        if (stringSet.contains("CSS")) {
            arr.put(".css", R.color.u_css);
        } else {
            arr.put(".css", REMOVED);
        }

        if (stringSet.contains("Images")) {
            arr.put(".jpg", R.color.u_jpg);
            arr.put(".gif", R.color.u_gif);
            arr.put(".svg", R.color.u_svg);
            arr.put(".png", R.color.u_svg);
            arr.put("favicon.ico", R.color.u_svg);
        } else {
            arr.put(".jpg", REMOVED);
            arr.put(".gif", REMOVED);
            arr.put(".svg", REMOVED);
            arr.put(".png", REMOVED);
            arr.put("favicon.ico", REMOVED);
        }

        if (stringSet.contains("Media")) {
            arr.put(".m3u8", R.color.u_m3u8);
            arr.put(".mp3", R.color.u_mp3);
            arr.put(".mp4", R.color.u_mp4);
            arr.put(".3gp", R.color.u_3gp);
            arr.put(".avi", R.color.u_avi);
            arr.put("stream", R.color.u_stream);
        }
        return arr;
    }
}
