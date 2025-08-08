package online.biletiz.rabota.custom

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.appcompat.UWView
import com.walhalla.ui.BuildConfig
import com.walhalla.ui.DLog.d
import com.walhalla.webview.ChromeView
import com.walhalla.webview.MyWebChromeClient
import com.walhalla.webview.ReceivedError
import online.biletiz.rabota.R
import androidx.core.view.isVisible
import androidx.core.graphics.toColorInt

class MWebChromeClient1234(
    var childLayout: RelativeLayout,
    var browserLayout: RelativeLayout?,
    var myActivity: Activity,var uwView: UWView,
    callback: Callback
) : MyWebChromeClient(myActivity, uwView, callback) {
    private val cm: CookieManager


    private lateinit var mView: UWView


    init {
        cm = CookieManager.getInstance()
        //mm = CustomTabUtils.customWeb(myActivity);
    }


    //+CreateWindow+
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message
    ): Boolean {
        createNewWindow(resultMsg)
        //openChromeCustomTab(view.getContext(), resultMsg);
        return true
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
    private fun createNewWindow(resultMsg: Message) {
        // remove any current child views
        if (browserLayout != null) {
            browserLayout!!.removeAllViews()
        }
        // make the child web view's layout visible
        childLayout.visibility = View.VISIBLE

        // now create a new web view
        mView = UWView(myActivity)
        mView.getSettings().setSupportZoom(false)
        mView!!.getSettings().setDefaultTextEncodingName("utf-8")
        mView!!.getSettings().setLoadWithOverviewMode(true)

        //mView.getSettings().setUseWideViewPort(true);

        //ERR_TOO_MANY_REDIRECTS
        //mView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //mView.getSettings().setAppCacheMaxSize( 100 * 1024 * 1024 ); // 100MB
        mView!!.getSettings().getLoadsImagesAutomatically()
        mView!!.getSettings().setGeolocationEnabled(true)

        mView!!.getSettings().setDomStorageEnabled(true)
        mView!!.getSettings().setBuiltInZoomControls(false) //!@@@@@@@@@@

        mView!!.getSettings().setSupportMultipleWindows(false)

        mView!!.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)

        mView!!.getSettings().setJavaScriptEnabled(true)

        // Ensure cookies are shared between WebViews
        CookieManager.getInstance().setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mView, true)
        }

        cm.setAcceptCookie(true)
        //cookieManager.setCookie("your-domain.com", "your-cookie-data");
//777777777777
        mView!!.getSettings().setPluginState(WebSettings.PluginState.ON)
        mView!!.getSettings().setAllowFileAccess(true)
        mView!!.getSettings().setAllowContentAccess(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView!!.getSettings().setAllowFileAccessFromFileURLs(true)
            mView!!.getSettings().setAllowUniversalAccessFromFileURLs(true)
        }
        val USER_AGENT =
            "Mozilla/5.0 (Linux; Android 10; SM-J105H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.164 Mobile Safari/537.36"
        //
        //System.getProperty("http.agent")
        val tmp = mView!!.getSettings().getUserAgentString()
        val agent = tmp.replace("; wv)", ")")
        d("@Agent-1@" + agent)
        mView!!.getSettings().setUserAgentString(
            agent //System.getProperty("http.agent")
            //USER_AGENT
        )
        //mView.getSettings().setUserAgentString(...);
        if (BuildConfig.DEBUG) {
            mView!!.setBackgroundColor("#80770000".toColorInt())
        }
        val var0: WebViewClient = object : CustomWebViewClient0(
            "@@@@@@@not use@@@@@@@@",
            mView!!,  /*chromeView*/
            object : ChromeView {
                override fun onPageStarted(url: String?) {
                }

                override fun onPageFinished(url: String?) {
                }


                override fun webClientError(failure: ReceivedError) {
                }

                override fun removeErrorPage() {
                    switchViews(false)
                }

                fun switchViews(b: Boolean) {
                }

                override fun setErrorPage(receivedError: ReceivedError) {
                    switchViews(true)
                }

                override fun openBrowser(url: String) {
                }
            },
            myActivity
        ) {
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
        }
        mView.webViewClient = var0


        /**@@@@@@@@@@@@@@@@@@@@@@@@@@@
         */
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
        mView!!.setWebChromeClient(this)


        /**@@@@@@@@@@@@@@@@@@@@@@@@@@@
         */


//        __mView.addJavascriptInterface(
//                new MyJavascriptInterface(CompatActivity.this, __mView), "JSInterface");
        mView!!.addJavascriptInterface(
            MyJavaScriptInterface(mView!!.getContext(), mView!!),
            "Client"
        )

        //mView.getSettings().setUseWideViewPort(false);

        // add the new web view to the layout
        mView!!.setLayoutParams(
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        browserLayout!!.addView(mView)
        // tell the transport about the new view
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = mView
        resultMsg.sendToTarget()

        mView!!.webChromeClient = MWebChromeClient1234(
            childLayout,
            browserLayout,
            myActivity,
            callback = object : Callback {
                override fun onProgressChanged(progress: Int) {
                }

                override fun openFileChooser(uploadMsg: ValueCallback<Uri>, s: String?) {
                }

                override fun openFileChooser(
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams?
                ) {
                }
            },
            uwView = mView!!
        ) //++++++

        // let's be cool and slide the new web view up into view
        val slideUp = AnimationUtils.loadAnimation(myActivity, R.anim.slide_up)
        childLayout.startAnimation(slideUp)
    }

    val isChildOpen: Boolean
        /**
         * Lower the child web view down
         */
        get() = childLayout.isVisible
}