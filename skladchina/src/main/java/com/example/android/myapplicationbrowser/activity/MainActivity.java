package com.example.android.myapplicationbrowser.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android.R;
import com.example.android.myapplicationbrowser.JavaScriptInterface;
import com.example.android.myapplicationbrowser.repository.MainControllerHttp;
import com.example.android.myapplicationbrowser.MyJavascriptInterface;
import com.example.android.myapplicationbrowser.ProxyAuthWebViewClient;
import com.example.android.myapplicationbrowser.ProxySettings;
import com.example.android.myapplicationbrowser.model.MProxy;
import com.walhalla.ui.DLog;
import org.apache.http.Header;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.chromium.net.CoreApplication;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends QBaseActivity implements MainControllerHttp.MainView {


    private final Looper mLooper;
    private final Handler mHandler;
    private ProxyReceiver mProxyReceiver;


    public MainActivity() {
        mLooper = Looper.myLooper();
        mHandler = new Handler(mLooper);
    }


    private static class ProxyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent.getAction().equals(Proxy.PROXY_CHANGE_ACTION)) {
                //ActionBar m = MainActivity.this.getSupportActionBar();

            }
        }
    }

    private void runOnThread(Runnable r) {
        if (onThread()) {
            r.run();
        } else {
            mHandler.post(r);
        }
    }

    private boolean onThread() {
        return mLooper == Looper.myLooper();
    }

    private static final String PLAIN_TEXT_TYPE = "*/*";

    private static class IncomingHandler extends Handler {

        private final WeakReference<MainActivity> var0;

        IncomingHandler(MainActivity service) {
            var0 = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity service = var0.get();
            if (service != null) {
                service.heandlerMessage(msg);
            }
        }
    }

    private IncomingHandler handler;

    @SuppressLint("SetJavaScriptEnabled")
    private void heandlerMessage(Message m) {

        DLog.d("aa" + controller.dataset.toString());

        if (m.what == 0) {
            findViewById(R.id.preload_bar).setVisibility(View.INVISIBLE);
            TextView preloader_txt = findViewById(R.id.preload_txt);
            preloader_txt.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append("ERR#");
            sb.append(controller.code_preload);
            sb.append("\n");
            sb.append(controller.txt_preload);
            preloader_txt.setText(sb.toString());
        }
        if (m.what == 1) {


            findViewById(R.id.preloader).setVisibility(View.INVISIBLE);
            MProxy proxy = controller.dataset.proxies.get(0);

            if (!controller.dataset.openWithoutProxy) {

                //Log.d("@@@@@@@@@@@", "heandlerMessage: @@@@@@@@@");
                String applicationClassName = "android.app.Application";
                //String applicationClassName="androidx.multidex.MultiDexApplication";
                //String applicationClassName = "org.chromium.net.CoreApplication";
                //String applicationClassName = "org.chromium.net.CoreApplication";


                ProxySettings aaa = CoreApplication.getPpa();
                aaa.setProxy(mWebView, applicationClassName, proxy);

                mWebView.setWebViewClient(new ProxyAuthWebViewClient(proxy.user, proxy.password));
            } else {
                String none = "";
                mWebView.setWebViewClient(new ProxyAuthWebViewClient(none, none));
            }

            WebSettings webset = mWebView.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
            }
            webset.setJavaScriptEnabled(true);
            webset.setBuiltInZoomControls(true);
            webset.setDisplayZoomControls(false);
            webset.setUserAgentString("SKLADCHINA_APP");



            if (!controller.dataset.openWithoutProxy) {
                loadUrl(
                        mWebView,
                        controller.dataset.urlForOpen, "", "");
            } else {
                loadUrl(
                        mWebView,
                        controller.dataset.urlForOpen, proxy.user, proxy.password);
            }
        }
    }

    private void loadUrl(WebView view, String url, String user, String password) {

        if (user.isEmpty() && password.isEmpty()) {
            view.loadUrl(url);
        } else {
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, password);
            Header credHeader = BasicScheme.authenticate(creds, "UTF-8", true);
            Map<String, String> additionalHttpHeaders = new HashMap<>();
            additionalHttpHeaders.put(credHeader.getName(), credHeader.getValue());
            view.loadUrl(url, additionalHttpHeaders);
        }

        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        //this.mWebView.setBackgroundColor(Color.RED);

        //webView.loadUrl("javascript:alert();");
    }

    EditText inputUrl;


    ProgressBar progressBar;
    ImageButton refreshButton;
    ImageButton sendButton;

    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    public SwipeRefreshLayout swipeRefreshLayout;
    private WebView mWebView;

    /*
     *  http://m.skladchina.biz/
     * */
    private ImageButton copButton;
    private MainControllerHttp controller;

    @Override
    protected void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        TextView textView = findViewById(R.id.textView);
        textView.setText(DLog.getAppVersion(this));

        handler = new IncomingHandler(this);
        controller = new MainControllerHttp(this, handler);
        controller.asyncload_params(); //load data

        this.progressBar = findViewById(R.id.progressBar);
        this.inputUrl = findViewById(R.id.autoCompleteTextView);

        //@@@ this.mWebView = findViewById(R.id.webView);
        ViewGroup clazz1 = findViewById(R.id.clazz2);

        onViewCreated(clazz1, this);

        ImageButton forwardButton = findViewById(R.id.forwardButton);
        ImageButton backButton = findViewById(R.id.backButton);
        this.refreshButton = findViewById(R.id.refreshButton);
        this.copButton = findViewById(R.id.сopButton);
        forwardButton.setOnClickListener(v -> {
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            }
        });
        backButton.setOnClickListener(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        });
        this.copButton.setOnClickListener(v -> {
            String webUrl = mWebView.getUrl();
            try {
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType(PLAIN_TEXT_TYPE);
                shareIntent.putExtra("android.intent.extra.SUBJECT", R.string.title_share);
                StringBuilder sb = new StringBuilder();
                //sb.append("Рекомендую посмотреть на складчине:");
                sb.append("\n\n");
                sb.append(webUrl);
                shareIntent.putExtra("android.intent.extra.TEXT", sb.toString());
                startActivity(Intent.createChooser(shareIntent, getString(R.string.title_share_2)));
            } catch (Exception e) {
                DLog.handleException(e);
            }
        });
        this.refreshButton.setOnClickListener(v -> mWebView.reload());
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    protected void onViewCreated(ViewGroup view, Context context) {

        //mWebView = privacy.findViewById(R.id.web_view);
        //swipeRefreshLayout = privacy.findViewById(R.id.refresh);

        swipeRefreshLayout = new SwipeRefreshLayout(context);
        mWebView = new WebView(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        swipeRefreshLayout.setLayoutParams(lp);
        mWebView.setLayoutParams(lp);
        view.addView(swipeRefreshLayout);
        swipeRefreshLayout.addView(mWebView);
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                () -> {
                    if (mWebView.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true);
                    else
                        swipeRefreshLayout.setEnabled(false);

                });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mWebView.reload();
            swipeRefreshLayout.setRefreshing(false);
        });

        mWebView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        // включаем поддержку JavaScript
        //@@     mWebView.setVisibility(View.INVISIBLE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        //webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        settings.setBuiltInZoomControls(false);


        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(jsInterface, "JSInterface");
        //mWebView.setBackgroundColor(Color.BLUE);

        settings.setUserAgentString(
                //
                System.getProperty("http.agent")
        );

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress > 60) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.addJavascriptInterface(new MyJavascriptInterface(this, mWebView), "Client");


    }

    @Override
    protected void onResume() {
        super.onResume();
//        d = new Dataset();
//        d.openWithoutProxy = false;
//        d.proxies = null;
//        d.urlForOpen = "https://2ip.ru";
    }

    @Override
    public void sendEmptyMessage(int i) {
        handler.sendEmptyMessage(i);
    }

}
