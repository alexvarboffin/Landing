package com.walhalla.android.webview.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.walhalla.android.R;
import com.walhalla.android.webview.WebUtils;
import com.walhalla.android.webview.presenter.ManifestPresenter;
import com.walhalla.android.webview.presenter.ManifestPresenterHtml;
import com.walhalla.utils.DLog;
import com.walhalla.webview.WVTools;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class ManifestExplorer extends AppCompatActivity {

    WebView webView = null;
    String pkgName = null;
    //String[] mPkgs = null;

    // current resolution context configured per package, defaults to self
    public static final String KEY_OBJ_NAME = "key_obj_";

    private String meta;

    private ManifestPresenter aa;

    public static Intent newIntent(Context context, String s) {
        Intent intent = new Intent(context, ManifestExplorer.class);
        intent.putExtra(KEY_OBJ_NAME, s);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //ArrayAdapter<String> spinnerArrayAdapter;
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.html_explorer);
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowCustomEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setDisplayShowHomeEnabled(true);
        }

        this.initControls();

        // setup packages list for our spinner
        //mPkgs = this.getPackages();
//        spinnerArrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item, mPkgs);
//        this.spinner.setAdapter(spinnerArrayAdapter);

        handleIntent(getIntent());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            meta = getIntent().getData().toString();
        } else {
            meta = this.getIntent().getStringExtra(KEY_OBJ_NAME);
        }
        if(!TextUtils.isEmpty(meta)){
            this.updateView1();
            //aa = new ManifestPresenterHtml(this);
            //aa.loadDataWithPattern(this.webView, meta);

            //if(meta.startsWith("https"))
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
            webView.setWebViewClient(new WebViewClient() {
                //                    @Override
//                    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                        WebResourceResponse intercepted = assetLoader.shouldInterceptRequest(request.getUrl());
//                        if (request.getUrl().toString().endsWith("js")) {
//                            if (intercepted != null) {
//                                intercepted.setMimeType("text/javascript");
//                            }
//                        }
//                        return intercepted;
//                    }
                @Override
                public void onPageFinished(WebView view, String url) {
//                        webView.loadUrl("javascript:HtmlViewer.showHTML" +
//                                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    webView.loadUrl("javascript:windows.HtmlViewer.showHTML" +
                            "(windows.getAll());");

                }
            });
            webView.loadUrl(meta);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.manifest_explorer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        if (menuItem.getItemId() == R.id.system_info) {
//            if (this.meta == null) {
//                Toast.makeText(this, "@@@@", Toast.LENGTH_SHORT).show();
//                return false;
//            }
////            //LauncherAppsCompat.getInstance(this).showAppDetailsForProfile(this.componentName, UserHandleCompat.myUserHandle());
////            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////            intent.setData(Uri.parse("package:" + meta.packageName));
////            startActivity(intent);
//            return true;
//        } else

        if (menuItem.getItemId() == R.id.menu_copy_manifest_txt) {
            WVTools.copyToClipboard0(this, mOutgetText());
            return true;
        } else if (menuItem.getItemId() == R.id.menu_share_text) {
            WVTools.shareText(this, mOutgetText());
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    protected void updateView1() {

        TextView name = findViewById(R.id.app_name);
        TextView packageName = findViewById(R.id.app_sub_title);
//        try {
//            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(meta.packageName, 0);
//            CharSequence title = applicationInfo.loadLabel(getPackageManager());
//            name.setText(title);
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        name.setText(meta.label);
//        packageName.setText(meta.packageName);

        //this.mOut.setText("");
    }

    private String mOutgetText() {
        return meta;
    }


    protected void initControls() {

        Button xml = this.findViewById(R.id.xml);
        this.webView = this.findViewById(R.id.webView);
        WebUtils.makeWebView(webView);


        xml.setOnClickListener(v -> {
            aa = new ManifestPresenterHtml(this);
            aa.loadDataWithPattern(this.webView, meta);
        });

        //@@@ Web browser content mimetype

        findViewById(R.id.txt).setOnClickListener(v -> {
            webView.loadDataWithBaseURL(null, "" + meta,
                    "text/plain;charset=UTF-8", "UTF-8", null);
//            webView.loadDataWithBaseURL(null, "" + meta,
//                    "text/plain", "UTF-8", null);
//
        });

        findViewById(R.id.text_javascript).setOnClickListener(v -> {
            webView.loadDataWithBaseURL(null, "" + meta,
                    "text/javascript", "UTF-8", null);
        });
        findViewById(R.id.application_json).setOnClickListener(v -> {
            webView.loadDataWithBaseURL(null, "" + meta,
                    "application/json", "UTF-8", null);
        });
        findViewById(R.id.text_html).setOnClickListener(v -> {
            webView.loadDataWithBaseURL(null, "" + meta,
                    "text/html", "UTF-8", null);
        });
        findViewById(R.id.text_csv).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                for (String type : webView.getReceiveContentMimeTypes()) {
                    DLog.d("@@@" + type);
                }
            }
//            WebViewAssetLoader
//            val webViewAssetLoader = new WebViewAssetLoader.Builder()
//                    .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context!!))
//        .build()


            webView.loadDataWithBaseURL(null, "" + meta,
                    "text/csv", "UTF-8", null);


            String baseUrl = "https://www.journaldev.com";
            String data = "Relative Link";
            String mimeType = "text/html";
            String encoding = "UTF-8";
            String historyUrl = "https://www.journaldev.com";

            webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);

        });
    }

    public void showError(CharSequence text, Throwable t) {
        Log.e("ManifestExplorer", text + " : " + ((t != null) ? t.getMessage() : ""));
        Toast.makeText(this, "Error: " + text + " : " + t, Toast.LENGTH_LONG).show();
    }

    //Get the changelog in html code, this will be shown in the dialog's webview
    private String GetHTMLChangelog(XmlResourceParser _xml, Resources aResource) {
        String _Result = "<html><head>" + GetStyle() + "</head><body>";

        try {
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if ((eventType == XmlPullParser.START_TAG) && (_xml.getName().equals("release"))) {
                    _Result = _Result + ParseReleaseTag(_xml);
                }
                eventType = _xml.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("@@@", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("@@@", e.getMessage(), e);
        } finally {
            _xml.close();
        }
        _Result = _Result + "</body></html>";
        return _Result;
    }

    private String ParseReleaseTag(XmlResourceParser xml) {
        return "@@@@@@@@@@@@@@";
    }

    private String GetStyle() {
        return "";
    }

    public String getPkgName() {
        return this.pkgName;
    }



    @Override
    protected void onResume() {
        super.onResume();
//        setPkgName(meta.packageName);
//        configForPackage(getPkgName());
//        updateView();
    }


    static class MyJavaScriptInterface {

        private final Context context;

        MyJavaScriptInterface(Context ctx) {
            this.context = ctx;
        }
        @JavascriptInterface
        public void showHTML(String html) {
            new AlertDialog.Builder(context).setTitle("HTML").setMessage(html)
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }

    }
}