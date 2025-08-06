package com.topzaymnakartu.online24.android.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.UWView;
import androidx.browser.customtabs.CustomTabsIntent;

import com.topzaymnakartu.online24.android.R;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.DLog;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.MyWebChromeClient;
import com.walhalla.webview.ReceivedError;


public class MWebChromeClient1234 extends MyWebChromeClient {

    private final CookieManager cm;


    RelativeLayout browserLayout;
    Activity myActivity;
    RelativeLayout childLayout;

    private UWView mView;


    public MWebChromeClient1234(RelativeLayout child, RelativeLayout browserLayout, Activity activity, Callback callback) {
        super(callback);
        this.browserLayout = browserLayout;
        this.myActivity = activity;
        this.childLayout = child;
        cm = CookieManager.getInstance();
        //mm = CustomTabUtils.customWeb(myActivity);
    }


    //+CreateWindow+

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        createNewWindow(resultMsg);
        //openChromeCustomTab(view.getContext(), resultMsg);
        return true;
    }

//    private void openChromeCustomTab(Context context, Message resultMsg) {
//        WebView newWebView = new WebView(context);
//        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
//        transport.setWebView(newWebView);
//        resultMsg.sendToTarget();
//
//        // Получаем URL из WebView
//        String url = newWebView.getUrl();
//        if (url != null) {
//            mm.launchUrl(context, Uri.parse(url));
//        }
//    }


    private void createNewWindow(Message resultMsg) {
        // remove any current child views
        if (browserLayout != null) {
            browserLayout.removeAllViews();
        }
        // make the child web view's layout visible
        childLayout.setVisibility(View.VISIBLE);

        // now create a new web view
        mView = new UWView(myActivity);
        mView.getSettings().setSupportZoom(false);
        mView.getSettings().setDefaultTextEncodingName("utf-8");
        mView.getSettings().setLoadWithOverviewMode(true);
        //mView.getSettings().setUseWideViewPort(true);

        //ERR_TOO_MANY_REDIRECTS
        //mView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mView.getSettings().setAppCacheMaxSize( 100 * 1024 * 1024 ); // 100MB

        mView.getSettings().getLoadsImagesAutomatically();
        mView.getSettings().setGeolocationEnabled(true);

        mView.getSettings().setDomStorageEnabled(true);
        mView.getSettings().setBuiltInZoomControls(false);//!@@@@@@@@@@

        mView.getSettings().setSupportMultipleWindows(false);

        mView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mView.getSettings().setJavaScriptEnabled(true);

        // Ensure cookies are shared between WebViews
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mView, true);
        }

        cm.setAcceptCookie(true);
        //cookieManager.setCookie("your-domain.com", "your-cookie-data");
//777777777777
        mView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mView.getSettings().setAllowFileAccess(true);
        mView.getSettings().setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView.getSettings().setAllowFileAccessFromFileURLs(true);
            mView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }
        String USER_AGENT = "Mozilla/5.0 (Linux; Android 10; SM-J105H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Mobile Safari/537.36";
        //
        //System.getProperty("http.agent")
        String tmp = mView.getSettings().getUserAgentString();
        String agent = tmp.replace("; wv)", ")");
        DLog.d("@Agent-1@" + agent);
        mView.getSettings().setUserAgentString(
                agent
                //System.getProperty("http.agent")
                //USER_AGENT
        );
        //mView.getSettings().setUserAgentString(...);
        if (BuildConfig.DEBUG) {
            mView.setBackgroundColor(Color.parseColor("#80770000"));
        }
        WebViewClient var0 = new CustomWebViewClient0("@@@@@@@not use@@@@@@@@", mView, /*chromeView*/new ChromeView() {
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
                switchViews(false);
            }

            private void switchViews(boolean b) {

            }

            @Override
            public void setErrorPage(ReceivedError receivedError) {
                switchViews(true);
            }

            @Override
            public void openBrowser(Activity context, String url) {

            }
        }, myActivity) {
//            /**
//             * Need to grab the title of the web page
//             * @param view - - the web view
//             * @param url - the URL of the page
//             */
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // once the view has loaded, display its title
//                //@@@titleText.setText(view.getTitle());
//            }
        };
        mView.setWebViewClient(var0);


        ///@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //        view.setWebChromeClient(@@new WebChromeClient() {
//            // For 3.0+ Devices (Start)
//            // onActivityResult attached before constructor
//            private void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                openImageChooser(uploadMsg, acceptType);
//            }
//
//            // For Lollipop 5.0+ Devices
//            @Override
//            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                openImageChooser(webView, filePathCallback, fileChooserParams);
//                return true;
//            }
//
//            // openFileChooser for Android < 3.0
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                openFileChooser(uploadMsg, "");
//            }
//
//            //openFileChooser for other Android versions
//
//            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                openFileChooser(uploadMsg, acceptType);
//            }
//        });

        mView.setWebChromeClient(this);

        ///@@@@@@@@@@@@@@@@@@@@@@@@@@@


//        __mView.addJavascriptInterface(
//                new MyJavascriptInterface(CompatActivity.this, __mView), "JSInterface");

        mView.addJavascriptInterface(new MyJavaScriptInterface(mView.getContext(), mView), "Client");
        //mView.getSettings().setUseWideViewPort(false);

        // add the new web view to the layout
        mView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        browserLayout.addView(mView);
        // tell the transport about the new view
        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
        transport.setWebView(mView);
        resultMsg.sendToTarget();

        mView.setWebChromeClient(new MWebChromeClient1234(
                childLayout
                , browserLayout
                , myActivity,
                new Callback() {
                    @Override
                    public void onProgressChanged(int progress) {

                    }

                    @Override
                    public void openFileChooser(ValueCallback<Uri> uploadMsg, String s) {

                    }

                    @Override
                    public void openFileChooser(ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                    }
                }
        ));//++++++

        // let's be cool and slide the new web view up into view
        Animation slideUp = AnimationUtils.loadAnimation(myActivity, R.anim.slide_up);
        childLayout.startAnimation(slideUp);
    }

    /**
     * Lower the child web view down
     */
//    public void closeChild() {
//        Log.v("@@@", "Closing Child WebView");
//        Animation slideDown = AnimationUtils.loadAnimation(myActivity, R.anim.slide_down);
//        childLayout.startAnimation(slideDown);
//        slideDown.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                //@@@titleText.setText("");
//                childLayout.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }
    public boolean isChildOpen() {
        return childLayout.getVisibility() == View.VISIBLE;
    }
}