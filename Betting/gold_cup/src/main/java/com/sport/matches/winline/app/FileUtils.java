package com.sport.matches.winline.app;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String loadJSONFromAsset(Context context, String fileName) {
        String text;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            int xx = is.read(buffer);
            is.close();
            text = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return text;
    }

    public static String getLocalThumbnails(String fileName) {
        return String.format("file:///android_asset/icons/%1$s", fileName);
    }
}
