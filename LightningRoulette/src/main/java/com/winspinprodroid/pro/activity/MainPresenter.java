package com.winspinprodroid.pro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.UWView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.DynamicWebView;

import com.walhalla.landing.databinding.ActivityMainBinding;
import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.landing.presenter.MainContract;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.ReceivedError;
import com.winspinprodroid.pro.Cfg;
import com.winspinprodroid.pro.R;

public class MainPresenter extends SinglePagination
        implements MainContract.Presenter, ActivityConfig, MainContract.ViewSimple, ChromeView {

    public static int[] watermark = new int[]{1103, 1080, 1089, 1088, 1077, 1042, 32, 1086, 1084, 1077, 1044, 32, 1103, 1072, 1085, 1085, 1077, 1095, 1072, 1083, 1087, 1086, 1077, 1053};

    private final CoordinatorLayout coordinatorLayout;
    private final ActivityMainBinding binding;
    private final Activity activity;


    public DynamicWebView dynamicWebView;

    private SwipeRefreshLayout swipe;

    private final MainContract.ViewSimple view;
    private final SharedPreferences preferences;
    private boolean is_first_launch;

    WPresenterImpl2 presenter;

    public MainPresenter(int[] v0, /*MainContract.ViewSimple view,*/
                         ActivityMainBinding binding, Activity activity, ActivityConfig config) {
        super(v0);
        this.activity = activity;
        this.view = this;//view;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        this.dynamicWebView = new DynamicWebView(activity, config);
        this.binding = binding;
        this.coordinatorLayout = activity.findViewById(R.id.coordinatorLayout);
        Handler handler = new Handler(Looper.getMainLooper());
        presenter = new WPresenterImpl2(handler, activity);
        if (isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @Override
    public void onNavigationItemSelected(String itemId) {

    }

    @Override
    public void onBottomNavigationItemSelected(String itemId) {

    }

    @Override
    public void fetchRemoteConfig(Context context) {
        // Здесь вы можете реализовать логику получения URL, например, из сети или другого источника.
        // В данном примере мы просто извлекаем сохраненный URL из SharedPreferences.

        String savedUrl = getSavedUrl();
        is_first_launch = TextUtils.isEmpty(savedUrl);
        if (!is_first_launch) {
            displayUrl(savedUrl);//view.
        } else {
            displayUrl(getUrl0());//view.  // Замените на вашу логику получения URL
        }
    }


    @Override
    public void showLoading() {
        Toast.makeText(activity, "@@", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(activity, "ww", Toast.LENGTH_SHORT).show();
    }

    public void displayUrl(String url) {
        loadUrl(url);
    }

    protected void loadUrl(String url) {
        dynamicWebView.getWebView().loadUrl(url);
    }

    public String getSavedUrl() {
        return preferences.getString(Cfg.KEY_SAVED_URL, "");
    }


    public boolean isSwipeEnabled() {
        return Cfg.SWIPE_ENABLED;
    }

    public void generateViews(RelativeLayout contentMain) {
        dynamicWebView.generateViews(this, contentMain, presenter);
    }


//    public void generateViews(Context context, ViewGroup view) {
//        if (isSwipeEnabled()) {
//            FrameLayout.LayoutParams lp0 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            dynamicWebView.getWebView().setLayoutParams(lp0);
//            swipe = new SwipeRefreshLayout(context);
//            swipe.setLayoutParams(lp0);
//            view.addView(swipe);
//            swipe.addView(dynamicWebView.getWebView());
//            swipe.setRefreshing(false);
//            swipeWebViewRef(swipe, dynamicWebView.getWebView());
//        } else {
//            FrameLayout.LayoutParams lp0 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            dynamicWebView.getWebView().setLayoutParams(lp0);
//            view.addView(dynamicWebView.getWebView());
//        }
//        //mWebView.setBackgroundColor(Color.BLACK);
//        // register class containing methods to be exposed to JavaScript
//        presenter.a123(this, dynamicWebView.getWebView());
//
////        TextView textView = new TextView(context);
////        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(
////                CoordinatorLayout.LayoutParams.MATCH_PARENT,
////                CoordinatorLayout.LayoutParams.WRAP_CONTENT
////        );
////        params.gravity = Gravity.CENTER;
////        textView.setLayoutParams(params);
////        textView.setGravity(Gravity.CENTER);
////        textView.setText(dec0(watermark));
////        textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
////        textView.setTextSize(22);
//
//        // Добавляем TextView в CoordinatorLayout
//        //coordinatorLayout.addView(textView);
//
//    }



    

    @Override
    public void removeErrorPage() {
        switchViews(false);
    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {
        switchViews(true);
    }

    @Override
    public void openBrowser(Activity context, String url) {

    }

    private void switchViews(boolean b) {
        if (b) {
            binding.contentFake.getRoot().setVisibility(View.VISIBLE);
            binding.contentMain.setVisibility(View.INVISIBLE);
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.getRoot().setVisibility(View.INVISIBLE);
            binding.contentMain.setVisibility(View.VISIBLE);
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    @Override
    public void onPageStarted(String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);//modify if needed
            //dynamicWebView
        }

//        if (url.endsWith("/signup?code=signup")) {
////            mmm = new AlertDialog.Builder(this)
////                    .setCancelable(false)
////                    .setIcon(android.R.drawable.ic_dialog_alert)
////                    .setTitle("Leaving this App?")
////                    .setMessage("БЛОКИРУЕМ СТРАНИЦУ ОТ РЕДАКТИРОВАНИЯ ДО ПОЛНОГО ИНЖЕКТА JS").create();
////            //.setPositiveButton("Yes", (dialog, which) -> {})
////            //.setNegativeButton("No", null)
////            mmm.show();
//            mmm = new LoadingDialog(this);
//            mmm.show();
//        }

//        WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
//        boolean visible = mWebBackForwardList.getCurrentIndex() > 0;
//        handleHeader(visible);
        //Toast.makeText(this, "@@@@"+mWebBackForwardList.getCurrentIndex(), Toast.LENGTH_SHORT).show();

        if (isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @Override
    public boolean isProgressEnabled() {
        return Cfg.PROGRESS_ENABLED;
    }

    @Override
    public void onPageFinished(String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);

        }
//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }




//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (isProgressEnabled()) {
            binding.mpb.setVisibility(View.GONE);
            binding.mpb.setIndeterminate(false);
        }
    }

    @Override
    public void webClientError(ReceivedError failure) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}

