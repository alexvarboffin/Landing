package com.advear.client.newweb;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.advear.client.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.common.base.Charsets;
import com.walhalla.ui.DLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class MultiWebActivity extends AppCompatActivity {

    public static final String[] URLS;

    static {
        try {
            URLS = new String[]{
//"http://www.http2demo.io/",

                    //"https://lh4.googleusercontent.com/-LLKrWt4w0zc/AAAAAAAAAAI/AAAAAAAABso/tGn3mbiPRq4/photo.jpg",

                    //"https://spaces.im/i/dark/b/sprites/ico_logo_default.png",
                    //"https://spaces.im/gcaptcha/QoQYrxySIYpfRfCdiQZM/?1724782076",
                    "https://spaces.im",
                    "https://spaces.im",
                    "https://spaces.im",

                    //            "http://localhost:1234?proxy=https://spaces.im",
                    //            "http://localhost:1235?proxy=https://spaces.im",

//                    "http://localhost:1235?encode=" + URLEncoder.encode("https://spaces.im", Charsets.UTF_8.name()),
//                    "http://localhost:1236?encode=" + URLEncoder.encode("https://spaces.im", Charsets.UTF_8.name()),


                    //"https://setcookie.net",
                    //"https://setcookie.net",
                    //            "https://setcookie.net"
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        CookieSyncManager.createInstance(this);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);


        List<SessionConfig> configs = Arrays.asList(
                new SessionConfig(URLS[0], "session-1", 1234),
                new SessionConfig(URLS[1], "session-2", 1235),
                new SessionConfig(URLS[2], "session-3", 1236)
        );


        // Initialize the adapter with the configurations
        WebViewPagerAdapter adapter = new WebViewPagerAdapter(this, configs);
        viewPager.setOffscreenPageLimit(configs.size());
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        // Link TabLayout and ViewPager2 together
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(String.valueOf(position));
        }).attach();
    }

    @Override
    protected void onResume() {
        super.onResume();

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        //cookieManager.setAcceptThirdPartyCookies(true)
        cookieManager.setAcceptFileSchemeCookies(true);
        CookieSyncManager.getInstance().startSync();
        // Установка куки
        //cookieManager.setCookie("https://your-domain.com", "key=value");

        // Получение куки
        String cookies = cookieManager.getCookie("https://your-domain.com");
        DLog.d("Cookies:" + cookies);

        for (String url : URLS) {
            String k2 = cookieManager.getCookie(url);
            DLog.d("Cookies:" + k2);
        }

    }
}
