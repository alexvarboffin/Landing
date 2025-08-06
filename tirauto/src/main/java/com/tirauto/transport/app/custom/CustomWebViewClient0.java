package com.tirauto.transport.app.custom;

import static com.walhalla.webview.WVTools.extractDomain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.UWView;
import androidx.core.net.MailTo;
import androidx.core.net.ParseException;

import com.walhalla.webview.BuildConfig;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.ReceivedError;
import com.walhalla.webview.WVTools;
import com.walhalla.webview.WebViewAppConfig;
import com.walhalla.webview.utility.ActivityUtils;
import com.walhalla.webview.utility.DownloadUtility;

import java.io.ByteArrayInputStream;
import java.util.List;

public class CustomWebViewClient0 extends WebViewClient {//RequestInspector


    private final List<String> blockedDomains0;


    private static final String KEY_ERROR_ = "about:blank0error";
    public static final String TAG = "@@@";
    private final String sessionName;

    ReceivedError receivedError = null;


    private final String[] downloadFileTypes;
    private final String[] linksOpenedInExternalBrowser;

    private String _homeUrl_ = null;


    //
    private String homeDomain9 = null;


    private boolean isCheckSameDomainEnabled;
    private boolean feature_same_domain_enabled;


    public void resetAllErrors() {
        receivedError = null;
    }

    public void setHomeUrl(String homeUrl) {
        this._homeUrl_ = homeUrl;
    }

    private static final int STATUS_CODE_UNKNOWN = 9999;
    private static final boolean HANDLE_ERROR_CODE = true;

    private static final boolean isConnected = true;

    static final String offlineMessageHtml = "Offline Connection Error";
    static final String timeoutMessageHtml = "<!DOCTYPE html><html><head><title>Error Page</title></head><body><h1>Network Error</h1><p>There was a problem loading the page. Please check your internet connection and try again.</p></body></html>";

    private final ChromeView chromeView;
    private final Activity context;


    public CustomWebViewClient0(String sessionName, @NonNull UWView webView, ChromeView activity, Activity a) {
        super();
        this.chromeView = activity;
        this.context = a;
        this.downloadFileTypes = context.getResources().getStringArray(com.walhalla.webview.R.array.download_file_types);
        this.linksOpenedInExternalBrowser = context.getResources().getStringArray(com.walhalla.webview.R.array.links_opened_in_external_browser);
        this.blockedDomains0 = WVTools.loadBlockedDomains(context, com.walhalla.webview.R.raw.blockedhost);

        this.sessionName = sessionName;
    }

    public CustomWebViewClient0(String sessionName, ChromeView activity, Activity a) {
        this(sessionName, null, activity, a);
    }

    @Override
    public void onPageStarted(@NonNull WebView view, @NonNull String url, Bitmap favicon) {
        if (_homeUrl_ == null) {
            _homeUrl_ = url;
        }
        if (BuildConfig.DEBUG) {
            printParams("<onPageStarted>", url);
        }
        ChromeView activity = this.chromeView;
        if (activity != null) {
            activity.onPageStarted(url);
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (BuildConfig.DEBUG) {
            printParams("<onPageFinished>", url);
        }
//        if (BuildConfig.DEBUG) {
//            int scale = (int) (100 * view.getScale());
//            Log.d(TAG, "[" + url + "], {Scale}->" + scale + ", " + receivedError);
//        }
        ChromeView activity = this.chromeView;

        //error is fixed
        if (oldValue != null && receivedError == null) {
            activity.removeErrorPage();
        }

        oldValue = receivedError;//set
        receivedError = null;//reset error

        if (KEY_ERROR_.equals(url)) {
            view.clearHistory();
        }

        if (activity != null) {
            activity.onPageFinished(/*view, */url);
        }
        //injectJS(view);

        //String cookies = CookieManager.getInstance().getCookie(url);
        //Log.d(TAG, "All the cookies in a string:" + url + "\n" + cookies);


        //Grab first
        if (feature_same_domain_enabled && TextUtils.isEmpty(homeDomain9)) {
            homeDomain9 = extractDomain(url);
            isCheckSameDomainEnabled = true;
        }
    }

    public void printParams(String s, String url) {
        if (BuildConfig.DEBUG) {
            Uri uri = Uri.parse(url);
            String domain = uri.getHost();
            Log.d(TAG, "[" + sessionName + "] " + s + " " + url + " " + domain);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (isBlocked(request.getUrl().toString())) {
            // Если запрос к заблокированному домену, возвращаем пустой ответ
            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
        }
        // В противном случае, пропускаем запрос
        return super.shouldInterceptRequest(view, request);
    }

    private boolean isBlocked(String url) {

        // Проверяем, находится ли URL в списке заблокированных доменов
        for (String domain : blockedDomains0) {
            if (url.contains(domain)) {
                //Log.d(TAG, "isBlocked: "+url);
                return true;
            }
        }
        return false;
    }

//    @Nullable
//    @Override
//    public WebResourceResponse shouldInterceptRequest(@NonNull WebView view, @NonNull WebViewRequest webViewRequest) {
//        String m = webViewRequest.getMethod();
//        if ("POST".equals(m)) {
//            String body = webViewRequest.getBody();
//            if (!TextUtils.isEmpty(body)) {
//                ChromeView activity = this.activity.get();
//                if (activity != null) {
//                    activity.eventRequest(//https://mc.yandex.ru
//                            new BodyClass(Utils.makeDate(),
//                                    body,
//                                    webViewRequest.getUrl()));
//                }
//            }
//        }
//        return super.shouldInterceptRequest(view, webViewRequest);
//    }

//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//
////        if (url.contains("images/srpr/logo11w.png")){
////            return new WebResourceResponse("text/plain", "utf-8",
////                    new ByteArrayInputStream("".getBytes()));
////        }
//
//        return super.shouldInterceptRequest(view, url);
//    }

//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        try {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                Uri uri = request.getUrl();
//                URL url0 = new URL(uri.toString());
//                //String url01 = view.getUrl();
//                //Content-Type=application/x-www-form-urlencoded
//                Log.d(TAG, uri + " " + url0 + " "+ Thread.currentThread()+" "+request.);
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Log.d("@@", request + "");
//        return super.shouldInterceptRequest(view, request);
//
////        String requestBody = null;
////        Uri uri = request.getUrl();
////        String url = view.getUrl();
////
////        //Determine whether the request is an Ajax request (as long as the link contains ajaxintercept)
////        if (isAjaxRequest(request)) {
////            //Get post request parameters
////            requestBody = getRequestBody(request);
////            //Get original link
////            uri = getOriginalRequestUri(request, MARKER);
////        }
////        //Reconstruct the request and get the response
////        WebResourceResponse webResourceResponse = shouldInterceptRequest(view, new WriteHandlingWebResourceRequest(request, requestBody, uri));
////        if (webResourceResponse == null) {
////            return webResourceResponse;
////        } else {
////            return injectIntercept(webResourceResponse, view.getContext());
////        }
//    }

//    private void injectJS(WebView view) {
//        try {
//            InputStream inputStream = view.getContext().getAssets().open("include.js");
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
//            view.loadUrl("javascript:(function() {" +
//                    "var parent = document.getElementsByTagName('head').item(0);" +
//                    "var script = document.createElement('script');" +
//                    "script.type = 'text/javascript';" +
//                    "script.innerHTML = window.atob('" + encoded + "');" +
//                    "parent.appendChild(script)" +
//                    "})()");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            url = request.getUrl().toString();
            Log.d(TAG, "//1. " + url);
        }
        return handleUrl(view, url);
    }

    @Deprecated
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "@@@. " + url);
        return handleUrl(view, url);
    }

    public boolean isDownloadableFile(String url) {
        int index = url.indexOf("?");
        if (index > -1) {
            url = url.substring(0, index);
        }
        url = url.toLowerCase();
        for (String type : downloadFileTypes) {
            if (url.endsWith(type)) return true;
        }
        return false;
    }

    public boolean isLinkExternal(String url) {
        for (String rule : linksOpenedInExternalBrowser) {
            if (url.contains(rule)) return true;
        }
        return false;
    }

    private boolean handleUrl(WebView view, String url) {

        boolean var0 = isDownloadableFile(url);
        if (var0) {
            Toast.makeText(context, com.walhalla.webview.R.string.fragment_main_downloading, Toast.LENGTH_LONG).show();
            DownloadUtility.downloadFile(context, url, DownloadUtility.getFileName(url));
            return true;
        } else if (url.startsWith("tg:") || url.startsWith("https://t.me/")) {
            ActivityUtils.starttg(context, url);
            return true; //handle itself
        } else if (url.startsWith("file:///android_asset")) {
            Toast.makeText(context, "@@@@@@@@@", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((url.startsWith("http://") || url.startsWith("https://"))) {


//            if (url.startsWith("https://accounts.google.com/o/oauth2") || url.contains("redirect_uri=")) {
//                // Новое значение для параметра redirect_uri
//                String encodedRedirectUri = "";
//                try {
//                    String newRedirectUri = context.getPackageName() + "://app/";
//                    encodedRedirectUri = URLEncoder.encode(newRedirectUri, "UTF-8");
//                } catch (UnsupportedEncodingException ignored) {
//                }
//                // Разбиваем строку по символу '&' для получения параметров
//                String[] parts = url.split("&");
//
//                // Проходим по каждому параметру и заменяем redirect_uri на новое значение
//                StringBuilder modifiedUrlBuilder = new StringBuilder();
//                for (String part : parts) {
//                    if (part.startsWith("redirect_uri=")) {
//                        // Заменяем старое значение на новое
//                        modifiedUrlBuilder.append("redirect_uri=").append(encodedRedirectUri);
//                    } else {
//                        // Сохраняем остальные параметры без изменений
//                        modifiedUrlBuilder.append(part);
//                    }
//                    // Добавляем '&' после каждого параметра, кроме последнего
//                    modifiedUrlBuilder.append("&");
//                }
//
//                // Удаляем лишний '&' в конце строки
//                String modifiedUrl = modifiedUrlBuilder.toString().substring(0, modifiedUrlBuilder.length() - 1);
//                Log.d(TAG, "@" + modifiedUrl);
//                chromeView.openOauth2(context, modifiedUrl);
//                return true;
//            }


            //Log.d(TAG, "@c@");
            // determine for opening the link externally or internally
            boolean openInExternalApp = isLinkExternal(url);//openInExternalApp app
            boolean internal = DownloadUtility.isLinkInternal(url);//internal webView
            if (!openInExternalApp && !internal) {
                openInExternalApp = WebViewAppConfig.OPEN_LINKS_IN_EXTERNAL_BROWSER;
            }
            //My new Code
            if (url.endsWith(".apk")) {
                chromeView.openBrowser(context, url);
                return true;
            }

            // open the link
            if (openInExternalApp) {
                Log.d(TAG, "@@@");
                chromeView.openBrowser(context, url);
                return true;
            } else {
                if (isCheckSameDomainEnabled) {
                    if (isSameDomain(url, homeDomain9)) {
                        Log.d(TAG, "NOT_OVERRIDE:isSameDomain: " + url);
                        return false;
                    } else {
                        Log.d(TAG, "blocked: " + url + ", " + homeDomain9);
                        //url blocked
                        return true;
                    }
                } else {
                    //@@@ showActionBarProgress(true);
                    Log.d(TAG, "NOT_OVERRIDE: " + url);
                    return false;
                }

            }
        } else if (url.startsWith("mailto:")) {
            try {
                MailTo mailTo = MailTo.parse(url);
                ActivityUtils.startEmailActivity(context, mailTo.getTo(), mailTo.getSubject(), mailTo.getBody());
            } catch (ParseException ignored) {
            }
            return true;
        } else if (url.startsWith("tel:")) {
            ActivityUtils.startCallActivity(context, url);
            return true;
        } else if (url.startsWith("sms:")) {
            ActivityUtils.startSmsActivity(context, url);
            return true;
        } else if (url.startsWith("geo:")) {
            ActivityUtils.startMapSearchActivity(context, url);
            return true;
        } else if (url.startsWith("yandexnavi:")) {
            ActivityUtils.startyandexnavi(context, url);
            return true;
        } else if (url.startsWith("intent://")) {
            if (url.startsWith("intent://maps.yandex")) {
                ActivityUtils.startMapYandex(context, url.replace("intent://", "https://"));
                return true;
            }
//bnk            else if (InAppBrowserUtils.isNspb(url)) {
//bnk                return InAppBrowserUtils.handleNspb(view, url);
//bnk            } else if (url.startsWith("intent://pay.mironline.ru")) {
//bnk                InAppBrowserUtils.paymironlineru(context, url);
//bnk                return true;
//bnk            }
            return false;

        }

//bank       else if (url.startsWith("sberpay:") || url.startsWith("http://sberpay:")) {
//bank           InAppBrowserUtils.sberpay(context, url);
//bank           return true;
//bank       } else if (url.startsWith("bank") || url.startsWith("http://bank")) {
//bank           return InAppBrowserUtils.bank(view, url);
//bank       }

        else {
            if (isConnected) {
                // return false to let the WebView handle the URL
                return false;
            } else {
                // show the proper "not connected" message
                view.loadData(offlineMessageHtml, "text/html", "utf-8");
                // return true if the host application wants to leave the current
                // WebView and handle the url itself
                return true;
            }
        }
    }

    private boolean isSameDomain(String url, String baseDomain) {
//        Uri uri = Uri.parse(url);
//        String domain = uri.getHost();
//        if (domain != null && (domain.endsWith("." + baseDomain) || domain.equals(baseDomain))) {
//            return true;
//        } else {
//            return false;
//        }
        //o.php?
        return true;
    }

//
//            @Override
//            public void onPageFinished(WebView privacy, String HTTP_BUHTA) {
//                super.onPageFinished(privacy, HTTP_BUHTA);
//
//                StringBuilder sb = new StringBuilder();
//                sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
//
//
//                sb.append("var objPWD, objAccount;var str = '';");
//                sb.append("var inputs = document.getElementsByTagName('input');");
//                sb.append("for (var i = 0; i < inputs.length; i++) {");
//                sb.append("if (inputs[i].type.toLowerCase() === 'password') {objPWD = inputs[i];}");
//                sb.append("else if (inputs[i].name.toLowerCase() === 'email') {objAccount = inputs[i];}");
//                sb.append("}");
//                sb.append("if (objAccount != null) {str += objAccount.value;}");
//                sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
//                sb.append("window.MYOBJECT.processHTML(str);");
//                sb.append("return true;");
//
//
//                sb.append("};");
//
//                privacy.loadUrl("javascript:" + sb.toString());
//            }*/

    /**
     * API 22 or above ...
     */
    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        final String oldValue = webView.getUrl();
        if (!TextUtils.isEmpty(oldValue)) {
            if (oldValue.equals(failingUrl)) {
                if (errorCode == WebViewClient.ERROR_TIMEOUT || -2 == errorCode) {//-8

                    webView.stopLoading();  // may not be needed
//                    Log.d(TAG, "@@@err1 " + (failingUrl));
//                    //view.loadData(timeoutMessageHtml, "text/html", "utf-8");
//                    //webView.clearHistory();//clear history
//                    Log.d(TAG, "@@@err2 " + failingUrl);
//                    webView.loadUrl("about:blank");
//                    webView.loadDataWithBaseURL(null, timeoutMessageHtml, "text/html", "UTF-8", null);
//                    webView.invalidate();
                } else {
                    //privacy.loadData(errorCode+"", "text/html", "utf-8");
                }

                ReceivedError failure = new ReceivedError(errorCode, description, failingUrl);
                handleErrorCode(webView, failure);
            }
        }
    }


    ReceivedError oldValue;


    private void handleErrorCode(WebView webView, ReceivedError failure) {

        if (HANDLE_ERROR_CODE) {

            //ERR_PROXY_CONNECTION_FAILED, we use Charles
            boolean prError = (failure.errorCode == ERROR_PROXY_AUTHENTICATION);
            if (prError) {
                setErrorPage(failure);
            } else if (failure.errorCode == WebViewClient.ERROR_HOST_LOOKUP /*ERR_INTERNET_DISCONNECTED*/) {//-2 ERR_NAME_NOT_RESOLVED
                if (!theerrorisalreadyshown()) {
                    if (isErrorOnTheSamePage(failure.failingUrl)) {
                        //webView.loadData(timeoutMessageHtml, "text/html", "utf-8");
                        //@@@ webView.loadDataWithBaseURL(KEY_ERROR_, timeoutMessageHtml, "text/html", "UTF-8", null);
                        setErrorPage(failure);
                        //Toast.makeText(context, "@@@", Toast.LENGTH_SHORT).show();
                    }
                }
                webClientError(failure);
            } else if (failure.errorCode == WebViewClient.ERROR_TIMEOUT) {//-8 ERR_CONNECTION_TIMED_OUT
                if (!theerrorisalreadyshown()) {
                    if (isErrorOnTheSamePage(failure.failingUrl)) {
                        //webView.loadData(timeoutMessageHtml, "text/html", "utf-8");
                        //@@@ webView.loadDataWithBaseURL(KEY_ERROR_, timeoutMessageHtml, "text/html", "UTF-8", null);
                        setErrorPage(failure);
                    }
                }
                webClientError(failure);
            } else if (failure.errorCode == WebViewClient.ERROR_CONNECT) {// -6	net::ERR_CONNECTION_REFUSED
                if (!theerrorisalreadyshown()) {
                    if (isErrorOnTheSamePage(failure.failingUrl)) {
                        //webView.loadData(timeoutMessageHtml, "text/html", "utf-8");
                        //@@@ webView.loadDataWithBaseURL(KEY_ERROR_, timeoutMessageHtml, "text/html", "UTF-8", null);
                        setErrorPage(failure);
                    }
                }
                webClientError(failure);
            } else if (failure.errorCode != -14) {// -14 is error for file not found, like 404.
                webClientError(failure);
            }
            //ERR_CONNECTION_REFUSED
            //ERR_CONNECTION_RESET
        }
    }

    private boolean isErrorOnTheSamePage(String failingUrl) {
        return _homeUrl_ != null && _homeUrl_.equals(failingUrl);
    }

    private boolean theerrorisalreadyshown() {
        return receivedError != null && receivedError.errorCode != null;
    }


    /**
     * On API 23 or below
     *
     * @param view
     * @param request
     * @param error
     */

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
//                loadErrorPage(privacy);

//        if (BuildConfig.DEBUG) {
//                Toast.makeText(privacy.getContext(), "Oh no! " + request + " " + error, Toast.LENGTH_SHORT)
//                        .show();
//        }

        String failureUrl = request.getUrl().toString();

        String mainUrl = (view.getUrl() == null) ? "" : view.getUrl();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d(TAG, "!! @@@ >= 23" + error.getErrorCode() + "\t" + error.getDescription());
            Log.d(TAG, "!! @@@: " + mainUrl + " {FailUrl} " + failureUrl);

            if (mainUrl.equals(failureUrl)) {
                Log.d(TAG, "URL: " + mainUrl);
                ReceivedError err0 = new ReceivedError(
                        error.getErrorCode(),
                        error.getDescription().toString(),
                        failureUrl
                );
                handleErrorCode(view, err0);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mainUrl.equals(failureUrl)) {
                Log.d(TAG, "[onReceived--HttpError >= 21 ] " + error + " " + request.getUrl() + " " + view.getUrl());
                //m404();
            }
        } else {
            //REMOVED ... m404();
        }
    }

    private void webClientError(ReceivedError failure) {
        ChromeView view = chromeView;
        if (nonNull(view)) {
            view.webClientError(failure);
        }
    }

    private void setErrorPage(ReceivedError newValue) {
        Log.d(TAG, "@@@@");
        //isErrorPageShown0 = true;
        receivedError = newValue;
        ChromeView view = chromeView;
        if (nonNull(view)) {
            view.setErrorPage(receivedError);
        }
        //isErrorPageShown0 = false;
    }

    private boolean nonNull(Object o) {
        Log.d(TAG, "Nonnull" + ((o != null) ? o.getClass().getCanonicalName() : null));
        return o != null;
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        final int statusCode;
        String cUrl = "";
        // SDK < 21 does not provide statusCode
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            statusCode = STATUS_CODE_UNKNOWN;
        } else {
            statusCode = errorResponse.getStatusCode();
            cUrl = request.getUrl().toString();
        }
        //Log.d(TAG, "Status code: " + statusCode + " " + Build.VERSION.SDK_INT + " " + view.getUrl() + " " + cUrl);
        Log.d(TAG, "[onReceivedHttpError::" + statusCode + "] " + cUrl + "");

//        if (statusCode == 404) {
//            //if (!mainUrl.equals(view.getUrl())) {
//            mainUrl = view.getUrl();
//
//            //Data
//
//            //view.getUrl() - main url wat we nead
//            //request.getUrl() - resource
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (request.getUrl().toString().equals(mainUrl)) {
//                    if (BuildConfig.DEBUG) {
//                        Log.d(TAG, "[onReceivedHttpError] " + statusCode + " " + request.getUrl() + " " + view.getUrl());
//                    }
//                    m404();
//                }
//            }
//
//            //}
//        }

        if (statusCode == 403) {
            if (view.getUrl() != null && view.getUrl().equals(cUrl)) {
                //m404();
            }
        }
    }

//Google play не пропустит!
//    @SuppressLint("WebViewClientOnReceivedSslError")
//    @Override
//    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//
//        handler.proceed();// Пропустить проверку сертификата
//    }


    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        Log.d(TAG, "@@" + oldScale + "@@" + newScale);
    }

    public void setCheckSameDomain() {
        this.feature_same_domain_enabled = true;
    }
}