package com.advear.client.fragment;

import static com.advear.client.newweb.MultiWebActivity.URLS;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.CookieManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.UWView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.advear.client.custom.WPresenterAAImpl2;
import com.advear.client.databinding.FragmentSessionBinding;
import com.advear.client.newweb.ProxyServer;
import com.advear.client.newweb.SessionConfig;
import com.google.common.base.Charsets;
import com.walhalla.ui.DLog;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.ReceivedError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class BaseWebViwFragment extends Fragment implements ChromeView {

    protected @NonNull UWView binding;

    private WPresenterAAImpl2 presenter;
    public CookieManager cookieManager;
    private ProxyServer server;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cookieManager = CookieManager.getInstance();
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfCores);


        executor.execute(() -> {
            try {
                server = new ProxyServer(getSessionConfig(), cookieManager, getActivity());
            } catch (Exception e) {
                DLog.handleException(e);
                Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler = new Handler(Looper.getMainLooper());
        this.presenter = new WPresenterAAImpl2(handler, (AppCompatActivity) getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.newVariant(getSessionConfig().getSessionName(), this, binding, null, null);

        String cookies = cookieManager.getCookie("https://your-domain.com");
        DLog.d("Cookies:" + cookies);

        for (String url : URLS) {
            String k2 = cookieManager.getCookie(url);
            DLog.d(zzz() + "=Cookies:" + k2);
        }

        getUrlTrowsProxy();

        for (String url : URLS) {
            String k2 = cookieManager.getCookie(url);
            DLog.d(zzz() + "=Cookies:" + k2);
        }
    }

    public void getUrlTrowsProxy() {
        String mm = getSessionConfig().getUrl();
        if (getSessionConfig() != null) {
            try {
                mm = "http://localhost:" + getSessionConfig().getPort()
                        + "?encode=" + URLEncoder.encode(getSessionConfig().getUrl(), Charsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                DLog.handleException(e);
            }

            //mm = "http://localhost:" + getSessionConfig().getPort() + "?encode=" + getSessionConfig().getUrl();

        } else {

        }
        binding.loadUrl(mm);
    }

    private String zzz() {
        return getClass().getSimpleName() + "[" + getSessionConfig().getSessionName() + "]";
    }


    protected abstract SessionConfig getSessionConfig();

    @Override
    public void onPageStarted(String url) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void webClientError(ReceivedError failure) {

    }

    @Override
    public void removeErrorPage() {

    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {

    }

    @Override
    public void openBrowser(Activity context, String url) {

    }


}