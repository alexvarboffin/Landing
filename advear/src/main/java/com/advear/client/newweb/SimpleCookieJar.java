package com.advear.client.newweb;

import android.os.Build;

import com.walhalla.ui.DLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SimpleCookieJar implements CookieJar {

    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        DLog.d("Сохраняем куки, привязанные к " + url + " " + cookies);
        cookieStore.put(url.host(), cookies);
    }

    @Override
    public @NotNull List<Cookie> loadForRequest(@NotNull HttpUrl url) {

        List<Cookie> storage = cookieStore.get(url.host());
        DLog.d("restore, привязанные к URL " + storage);

        // Remove expired Cookies
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (storage != null) {
                storage.removeIf(cookie -> cookie.expiresAt() < System.currentTimeMillis());
            }
            // Only return matching Cookies
            return storage != null
                    ? storage.stream().filter(cookie -> cookie.matches(url)).collect(Collectors.toList())
                    : new ArrayList<>();

        }
        return storage != null ? storage : new ArrayList<>();
    }
}