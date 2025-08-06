package com.wsms.client.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.walhalla.landing.activity.WebActivity;
import com.walhalla.landing.base.DynamicWebViewOld;
import com.walhalla.landing.databinding.ActivityMainBinding;
import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.landing.permissionrequest.ConfirmationDialogFragment;
import com.walhalla.landing.utility.NetUtils;
import com.walhalla.landing.view.ThresholdSwipeRefreshLayout;
import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.walhalla.webview.ReceivedError;
import com.wsms.client.Cfg9;
import com.wsms.client.PermissionResolver;
import com.wsms.client.R;


public class MainActivity extends WebActivity implements PermissionResolver.MainView,
        ConfirmationDialogFragment.Listener {


    private static final int SIZEFIX = 0;//100;
    protected boolean doubleBackToExitPressedOnce;
    private Handler mHandler;

    //protected DynamicWebView dynamicWebView;
    protected DynamicWebViewOld dynamicWebView;

    private ThresholdSwipeRefreshLayout swipe;


    //private SharedPreferences preferences;

    protected SinglePagination pagination; //optional use


    //private LoadingDialog mmm;

    private final String key_saved_url = "key_saved_url09";
    //private boolean is_first_launch;
    private ActivityMainBinding binding;
    private PermissionResolver presenter;


    //private AlertDialog mmm;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new PermissionResolver(this, this);
        presenter.checkPermissions();

        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding.toolbar);
        pagination = new SinglePagination(Cfg9.v0);

        if (!Cfg9.cfg.isToolbarEnabled()) {
            binding.toolbar.setVisibility(View.GONE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            //getSupportActionBar().setSubtitle(R.string.app_subtitle);
        }
        if (Cfg9.cfg.isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }

        //promoView = findViewById(R.id.content_fake);

        //@@@ switchViews(true);
        switchViews(false);
        generateViews(this, binding.contentMain);

        // Получение менеджера куков
//
//        // Включение синхронизации куков (для старых версий Android)
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().startSync();
//
//        String existingCookie = cookieManager.getCookie(pagination.dec0());
//
//        if (existingCookie == null || !existingCookie.contains("download_application_hide=true")) {
//            // Кука не установлена или не содержит нужное значение, установим ее
//            String cookieValue = "download_application_hide=true";
//            // Установка куки для указанного URL
//            cookieManager.setCookie(pagination.dec0(), cookieValue);
//
//            // Сохранение изменений
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                cookieManager.flush(); // Сохранить куки для Android 5.0 и выше
//            } else {
//                CookieSyncManager.getInstance().sync(); // Сохранить куки для более старых версий
//            }
//            //Toast.makeText(this, "Теперь кука установлена", Toast.LENGTH_SHORT).show();
//
//        } else {
//            // Кука уже установлена
//        }

//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String mm = getSavedUrl();
//        is_first_launch = TextUtils.isEmpty(mm);
//        if (is_first_launch) {
//            loadUrl(pagination.getUrl0());
//        } else {
//            loadUrl(mm);
//        }

        loadUrl(pagination.getUrl0());

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "button1");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "button1");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    // Сохранение URL-ссылки
//    public void saveUrl(Context context, String url) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(key_saved_url, url);
//        editor.apply();
//    }
//
//    // Извлечение URL-ссылки
//    public String getSavedUrl() {
//        return preferences.getString(key_saved_url, "");
//    }

//    private void loadPageUrl(String s) {
//        presenter.loadUrl(mWebView, s);
//    }


    public void switchViews(boolean b) {
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
    public void removeErrorPage() {
        switchViews(false);
    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {
        switchViews(true);
    }

    @Override
    public void onPageStarted(String url) {
        if (Cfg9.cfg.isSwipeEnabled()) {
            if (swipe.isRefreshing()) {
                swipe.setRefreshing(false);
            }//modify if needed
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

        if (Cfg9.cfg.isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            Module_U.aboutDialog(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy) {
            Launcher.openBrowser(this, getString(R.string.url_privacy_policy));
            return true;
        } else if (itemId == R.id.action_rate_app) {
            Launcher.rateUs(this);
            return true;
        } else if (itemId == R.id.action_share_app) {
            Module_U.shareThisApp(this);
            return true;
        } else if (itemId == R.id.action_discover_more_app) {
            Module_U.moreApp(this);
            return true;
        } else if (itemId == R.id.action_exit) {
            this.finish();
            return true;
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageFinished(/*WebView view, */String url) {
        if (Cfg9.cfg.isSwipeEnabled()) {
            if (swipe.isRefreshing()) {
                swipe.setRefreshing(false);
            }
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

        if (Cfg9.cfg.isProgressEnabled()) {
            binding.mpb.setVisibility(View.GONE);
            binding.mpb.setIndeterminate(false);
        }
    }


    protected boolean USE_HISTORY() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

//        WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
//        handleHeader(mWebBackForwardList.getCurrentIndex() > 0);
        //ActivityRecreationHelper.onResume(this);

        if (Cfg9.cfg.isCheckConnection()) {
            //NetUtils.isOnlineFire(this, binding.coordinatorLayout);
            //NetUtils.isOnlinePendulum(this);
            NetUtils.isOnlineSignal(this);
        }
    }

//    @Override
//    protected void onDestroy() {
//        //ActivityRecreationHelper.onDestroy(this);
//        super.onDestroy();
//        if (mWebView != null) {
//            mWebView.stopLoading();
//            mWebView.setWebChromeClient(@@null);
//            //mWebView.setWebViewClient(null);
//            mWebView.destroy();
//            mWebView = null;
//        }
//    }


    protected void generateViews(Context context, ViewGroup view) {
        // Создайте экземпляр DynamicWebView и получите его WebView
        dynamicWebView = new DynamicWebViewOld(this/*, Cfg.cfg*/);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dynamicWebView.getWebView().setLayoutParams(lp);
        if (Cfg9.cfg.isSwipeEnabled()) {
            swipe = new ThresholdSwipeRefreshLayout(context);

            swipe.setPadding(
                    swipe.getPaddingLeft(),
                    swipe.getPaddingTop() + SIZEFIX,
                    swipe.getPaddingRight(),
                    swipe.getPaddingBottom()
            );

            lp.setMargins(0, -1 * SIZEFIX, 0, 0); // Устанавливаем отрицательный верхний margin
            swipe.setLayoutParams(lp);


            swipe.setLayoutParams(lp);
            view.addView(swipe);
            swipe.addView(dynamicWebView.getWebView());
            swipe.setRefreshing(false);
            swipeWebViewRef(swipe, dynamicWebView.getWebView());
        } else {
            view.addView(dynamicWebView.getWebView());
        }

        //mWebView.setBackgroundColor(Color.BLACK);
        // register class containing methods to be exposed to JavaScript
        getPresenter0().a123(this, dynamicWebView.getWebView());
    }

    protected void loadUrl(String url) {
        dynamicWebView.getWebView().loadUrl(url);
    }

    @Override
    protected void onDestroy() {
//        if (mmm != null) {
//            mmm.dismiss();
//        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//@@@        if (drawer != null && binding.dr.isDrawerOpen(GravityCompat.START)) {
//@@@            drawer.closeDrawer(GravityCompat.START);
//@@@            return;
//@@@        } else {

        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        if (USE_HISTORY()) {
            if (dynamicWebView.getWebView().canGoBack()) {
                //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
//                    WebBackForwardList mWebBackForwardList = dynamicWebView.getWebView().copyBackForwardList();
//                    String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();

                dynamicWebView.getWebView().goBack();
                return;
            }
        }

        //@@@}

        backPressedToast();
        MainActivity.this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }


    @Override
    public void showMainContent() {

    }

    //permission
    @Override
    public void showPermissionRequest() {
        Toast.makeText(this, "Запрашиваем разрешения", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPermissionDenied() {
        Toast.makeText(this, "Разрешения отклонены", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPermissionGranted() {
        DLog.d("@@@@@@@@@@@@@");
        Toast.makeText(this, "Разрешения предоставлены", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConfirmation(boolean allowed, String[] resources) {
        getPresenter0().onConfirmation__(allowed, resources);
    }
}