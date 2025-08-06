package com.walhalla.android.webview.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerController;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.Toast;

import com.walhalla.android.BuildConfig;

import com.walhalla.android.webview.UIWebView.Const;
import com.walhalla.android.webview.UIWebView.CustomWebChromeClient;
import com.walhalla.android.R;
import com.walhalla.android.webview.WebUtils;
import com.walhalla.android.databinding.FragmentBrowserBinding;
import com.walhalla.android.webview.UIWebView.CustomWebViewClient;
import com.walhalla.android.webview.utils.WebViewUtils;
import com.walhalla.utils.DLog;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.ReceivedError;
import com.walhalla.webview.WVTools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class F_WebView extends Fragment implements View.OnClickListener, ChromeView {

    public FragmentBrowserBinding binding;
    private long time;

    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private Outputable mOutputable;

    private final String TAG = "@@@";
    private final boolean isConnected = true;

    private final java.lang.String errorPage = "file:///android_asset/www/index.html";
    public String url;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mOutputable = (Outputable) context;
    }

    private static final String ARG_URL = "section_number";

    public F_WebView() {
    }

    /**
     * #никогда не буду бедный
     * Returns a new instance of this fragment for the given section
     * number.
     *
     * @param url
     */
    public static F_WebView newInstance(String url) {
        F_WebView fragment = new F_WebView();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.url = getArguments().getString(ARG_URL);
        }
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        RelativeLayout.LayoutParams ww = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        ww.addRule(RelativeLayout.CENTER_IN_PARENT);
//        progressBar = new ProgressBar(getContext());
//
//        mBinding.swipe = new SwipeRefreshLayout(getContext());
//        mBinding.swipe.setId(8);
//
////        getWebView() = new ExtendedWebView(getContext());
////        getWebView().setId(333);
//
//        LinearLayout linearLayout = new LinearLayout(getContext());
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.addView(new EditText(getContext()));
//        linearLayout.addView(new Button(getContext()));
//
//
//        RelativeLayout rootView = new RelativeLayout(getContext());
//        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        //relativeParams.addRule(RelativeLayout.ABOVE, 333);
//
//
//        mBinding.swipe.addView(getWebView(), relativeParams);
//        RelativeLayout.LayoutParams hhhhh = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        hhhhh.addRule(RelativeLayout.ABOVE, 8);
//        linearLayout.setLayoutParams(hhhhh);
//
//        rootView.addView(linearLayout, hhhhh);
//        rootView.addView(mBinding.swipe, relativeParams);
//        rootView.addView(progressBar, ww);
//
//        //View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
//

//        rootView.setOnClickListener(this);
//
////        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
////        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
////        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
//
//        //progressBar = rootView.findViewById(R.id.progressBar);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browser, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ok.setOnClickListener(v -> {
            String tmp = binding.etInput.getText().toString().trim();
            if (isValidUrl(tmp)) {
                mOutputable.clearLogRequest();
            }
            tryNavigate(getActivity(), tmp);
        });

        final String offlineMessageHtml = "DEFINE THIS";
        final String timeoutMessageHtml = "DEFINE THIS";


//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                /*c.*/getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null)
//
//        {
//            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
//            if (ni.getState() != NetworkInfo.State.CONNECTED) {
//                // record the fact that there is not connection
//                isConnected = false;
//            }
//        }


        //WebView.VisualStateCallback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.webview.postVisualStateCallback(100, new WebView.VisualStateCallback() {
                @Override
                public void onComplete(long l) {

                }
            });
        }


        WebUtils.makeWebView(binding.webview);


//        if (Build.VERSION.SDK_INT < 8) {
////            getWebView().getSettings().setPluginsEnabled(true);
//        } else {


        //Disable flash player
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
        //getWebView().getSettings().setPluginsEnabled(true);
        //} else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {


        //}

//        }//flash

        CustomWebChromeClient cls = new CustomWebChromeClient(getActivity(),
                (CustomWebChromeClient.Callback) getActivity());
        binding.webview.setWebChromeClient(cls);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ServiceWorkerController swController = ServiceWorkerController.getInstance();
            swController.setServiceWorkerClient(new ServiceWorkerClient() {
                @Override
                public WebResourceResponse shouldInterceptRequest(WebResourceRequest request) {
                    // Capture request here and generate response or allow pass-through
                    // by returning null.
                    return null;
                }
            });
        }
        //!@
        binding.webview.setWebViewClient(
                new CustomWebViewClient(binding.webview, this, getActivity()) {

                    @Override
                    public void onLoadResource(WebView view, String url) {
                        super.onLoadResource(view, url);
                        mOutputable.printLogLine(url);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                        String newValue = request.getUrl().toString();
                        if (isBlocked(newValue)) {
                            // Если запрос к заблокированному домену, возвращаем пустой ответ
                            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
                        }

                        if (newValue.contains(".js")) {
                            try {
//                            WebResourceResponse originalResponse = super.shouldInterceptRequest(view, request);
//                            InputStream originalStream = originalResponse.getData();
//                            byte[] buffer = new byte[originalStream.available()];
//                            originalStream.read(buffer);
//                            DLog.d(Arrays.toString(buffer));
//                            // Создание нового потока данных с измененным содержимым
//                            ByteArrayInputStream modifiedStream = new ByteArrayInputStream("body { background-color: lightblue; }".getBytes());
//
//                            // Создание WebResourceResponse с измененным содержимым
//                            WebResourceResponse modifiedResponse = new WebResourceResponse(originalResponse.getMimeType(), originalResponse.getEncoding(), modifiedStream);
//
//                            // Возвращение измененного WebResourceResponse
//                            return modifiedResponse;

                                HttpURLConnection connection = (HttpURLConnection) new URL(newValue).openConnection();
                                connection.setRequestProperty("User-Agent", view.getSettings().getUserAgentString());
                                connection.connect();

                                // Получаем поток для чтения содержимого файла
                                InputStream inputStream = connection.getInputStream();
                                String mm = WebViewUtils.toConnectionString(inputStream);
                                DLog.d(mm);

                                // Возвращаем содержимое файла как WebResourceResponse
                                return new WebResourceResponse("application/javascript", null, inputStream);
                            } catch (Exception e) {
                                DLog.handleException(e);
                                DLog.e(newValue);
                            }
                        }

                        // В противном случае, пропускаем запрос
                        return super.shouldInterceptRequest(view, request);
                    }
                }


        );

        //mWebView.addJavascriptInterface(new MyJavascriptInterface(MainActivity.this, mWebView), "Client");


//        StringBuffer sb = new StringBuffer();
//        sb.append("&app=").append(getActivity().getPackageName());
//        sb.toString();//sig

        if (BuildConfig.DEBUG) {
            tryNavigate(getActivity(), this.url);
        }

        //mBinding.swipe = view.findViewById(R.id.refresh);
        binding.swipe.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                () -> {
                    binding.swipe.setEnabled(getWebView().getScrollY() == 0);

                });

        binding.swipe.setOnRefreshListener(() -> {
            getWebView().reload();
            binding.swipe.setRefreshing(false);
        });

        binding.etInput.setText(
                //"https://rt.pornhub.com/view_video.php?viewkey=ph5f419ef34ae2b"
                //"https://www.camsoda.com/sweetreya"
                //"https://www.livejasmin.com/ru/girls/?psid=yourfreepornatus#!chat/AllisonKeys"
                //"https://www.blackcamz.com/cam/pussycatboobies"
                //"https://rt.bongacams.com/paraizmsk"
                //"chrome://flags",
                //"https://www.xvcams.com/?model=katelyn-hendry"
                //"https://www.flirt4free.com/?model=lune-noir"
                "https://www.facebook.com/share/r/KPxYSmjqrziudsgq/"
        );
        binding.etInput.setText("https://tango.me/stream/unw26cnwu8rkPpo2Ky4kqw");

        //https://www.flirt4free.com/
        //
    }

    private boolean isValidUrl(String tmp) {
        return !TextUtils.isEmpty(tmp);
    }


    private void tryNavigate(Activity activity, String rawUrl) {
        if (isValidUrl(rawUrl)) {
            WVTools.hideKeyboardFrom(activity, activity.getWindow().getDecorView()
            );
            binding.etInput.disMissOrUpdatePopupWindow();

            if (rawUrl.startsWith("http://")
                    || rawUrl.startsWith("https://")
                    || rawUrl.startsWith("http//")
                    || rawUrl.startsWith("https//")) {
            } else {
                rawUrl = "https://" + rawUrl;
            }
            DLog.d("@@@" + rawUrl);
            onPageStarted();
            getWebView().loadUrl(rawUrl);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.hint_tv_answer, Toast.LENGTH_SHORT).show();
            DLog.d("[x]" + rawUrl);
        }
    }

    public WebView getWebView() {
        return (null == binding) ? null : binding.webview;
    }


    @Override
    public void onStop() {
        this.binding.swipe.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

//    public void loadErrorPage(WebView webview) {
//        if (webview != null) {
//
//            String htmlData = "<html><body><div align=\"center\" >This is the description for the load fail : "
//                    //+ description + "\nThe failed url is : " + failingUrl
//                    + "\n </div ></body > ";
//
//            webview.loadUrl("about:blank");
//            webview.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
//            webview.invalidate();
//
//        }
//    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            //((MainActivity) getActivity()).pressed(sectionNumber);
        }
    }

    public boolean canGoBack() {
        return (getWebView() != null) && getWebView().canGoBack();
    }

    private void onPageStarted() {
        if (null != binding.pb) {
            binding.pb.setIndeterminate(true);
        }
    }

    public void onPageFinished() {
        if (null != binding.pb) {
            binding.pb.setIndeterminate(false);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_webview, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            binding.webview.reload();
            Toast.makeText(getContext(), "Page updated", Toast.LENGTH_SHORT).show();
            return true;
        }
//        else if (id == R.id.action_share) {
//            WVTools.shareText(getActivity(), "@@@");
//            return false;
//        }

//        else if (id == R.id.action_copy) {
//            WVTools.copyToClipboard(getActivity(), "@@@");
//            return false;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageStarted(String newUrl) {
        onPageStarted();
        time = System.currentTimeMillis();

//        fragment.url = newUrl;
//        fragment.binding.etInput.setText(newUrl);

        //handleRedirect...
//        String oldValue = binding.webview.getUrl();
//        boolean notRedirect = oldValue.equals(newUrl);
//        if (notRedirect) {
//            mOutputable.clearLogRequest();
//        }
    }

    @Override
    public void onPageFinished(String url) {
        onPageFinished();
        long delta = System.currentTimeMillis() - time;
        if (Const.DEBUG) {
            DLog.d("onPageFinished: " + url + " " + delta + "ms");
        }
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