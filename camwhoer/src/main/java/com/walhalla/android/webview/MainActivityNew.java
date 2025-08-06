package com.walhalla.android.webview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.walhalla.android.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivityNew extends AppCompatActivity {

    //    Intent intent;
    WebView webView;
    WebSettings webSettings;
    static NetworkInfo netInfo;
    Intent intent;
    HttpURLConnectionExample httpURLConnection;
    int otvetservera;

    String GET_URL = "https://2ip.ru";

    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "myurl";
    public static final String APP_PREFERENCES_URL = "url";
    SharedPreferences.Editor editor;

    public ValueCallback<Uri[]> uploadMessage;
    private ValueCallback<Uri> mUploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_browser);
        VRabote();
    }

    public void VRabote() {

        httpURLConnection = new HttpURLConnectionExample();
        httpURLConnection.start();
        try {
            httpURLConnection.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_URL)) {
            GET_URL = mSettings.getString(APP_PREFERENCES_URL, "");
        }

        otvetservera = httpURLConnection.otvetservera();
        if (otvetservera != 404) {
            Log.d("@@@", "VRabote: ");
            webView = (WebView) findViewById(R.id.webview);
            webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(false);
            webSettings.setDisplayZoomControls(false);
            webView.setInitialScale(1);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setWebChromeClient(new MyWebChromeClient());
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.loadUrl(GET_URL);
            webView.addJavascriptInterface(new JavaClassEtap(), "HTMLOUT");
            webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mailto:")) {
                    //Handle mail Urls
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
                } else if (url.startsWith("tel:")) {
                    //Handle telephony Urls
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                if (uri.toString().startsWith("mailto:")) {
                    //Handle mail Urls
                    startActivity(new Intent(Intent.ACTION_SENDTO, uri));
                } else if (uri.toString().startsWith("tel:")) {
                    //Handle telephony Urls
                    startActivity(new Intent(Intent.ACTION_DIAL, uri));
                }
                else if (uri.toString().startsWith("tg:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                else if (uri.toString().startsWith("whatsapp:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                else if (uri.toString().startsWith("viber:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                } else if (uri.toString().startsWith("intent:")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Приложение не установлено на ваш телефон", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else {
                    //Handle Web Urls
                    view.loadUrl(uri.toString());
                }
                return true;
            }

                public void onPageFinished(WebView view, String url) {
                    view.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    CookieSyncManager.getInstance().sync();
                    GET_URL = webView.getUrl();
                    editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_URL, GET_URL);
                    editor.clear();

                }

            });
        } else {
            EtoBot();
        }

    }
    class JavaClassEtap {
        @JavascriptInterface
        public void showHTML(String html_data) {
            if (html_data.compareToIgnoreCase("<html><head><style>body{margin:0}</style></head><body><!--welcome_ooo_pulsar--></body></html>") == 0) {
                EtoBot();
            }
        }
    }

    private void EtoBot() {
    }

    class MyWebChromeClient extends WebChromeClient {
        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }


        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                Toast.makeText(MainActivityNew.this, "Файл невозможно открыть", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

        //For Android 4.1 only 19
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Выбор файла"), FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            startActivityForResult(Intent.createChooser(i, "Выбор файла"), FILECHOOSER_RESULTCODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else
            Toast.makeText(this, "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }

    private class HttpURLConnectionExample extends HttpURLConnection {
        protected HttpURLConnectionExample(URL u) {
            super(u);
        }

        public HttpURLConnectionExample() {
            super(null);
        }

        @Override
        public void disconnect() {

        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {

        }

        public void start() {
//            URL obj = new URL(GET_URL);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("User-Agent", USER_AGENT);
//            int responseCode = con.getResponseCode();
//            System.out.println("GET Response Code :: " + responseCode);
//            if (responseCode == HttpURLConnection.HTTP_OK) { // success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // print result
//                System.out.println(response.toString());
//            } else {
//                System.out.println("GET request not worked");
//            }
        }

        public void join() throws InterruptedException{
        }

        public int otvetservera() {
            return 200;
        }
    }
}