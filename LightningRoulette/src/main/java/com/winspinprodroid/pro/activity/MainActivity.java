package com.winspinprodroid.pro.activity;

import static com.walhalla.landing.base.BaseSimpleActivity.aboutDialog;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.databinding.ActivityMainBinding;
import com.walhalla.landing.utility.NetUtils;

import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.winspinprodroid.UIUtils;
import com.winspinprodroid.pro.Cfg;
import com.winspinprodroid.pro.R;

public class MainActivity extends AppCompatActivity
        //implements MainContract.ViewSimple
{


    //private AlertDialog mmm;
    private UIUtils uiUtils;
    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    private boolean isChecked;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (loadTheme()) {
            setTheme(R.style.Theme_ThemeSwitcher_Dark);
        } else {
            setTheme(R.style.Theme_ThemeSwitcher_Light);
        }
        super.onCreate(savedInstanceState);

        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainPresenter = new MainPresenter(Cfg.v0, /*this,*/ binding, this, new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return false;
            }

            @Override
            public boolean isSwipeEnabled() {
                return false;
            }
        });
        uiUtils = new UIUtils();

        if (Cfg.TOOLBAR_ENABLED) {
            binding.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.app_name);
                //getSupportActionBar().setSubtitle(R.string.app_subtitle);
            }
        } else {
            binding.toolbar.setVisibility(View.GONE);
        }

        //@@@ switchViews(true);
        switchViews(false);
        mainPresenter.generateViews(binding.contentMain);

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


        mainPresenter.fetchRemoteConfig(this);

//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "button1");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "button1");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressLint({"UnknownNullness"})
    private void switchViews(boolean b) {
        if (b) {
            binding.contentFake.getRoot().setVisibility(View.VISIBLE);
            binding.contentMain.setVisibility(View.GONE);
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.getRoot().setVisibility(View.GONE);
            binding.contentMain.setVisibility(View.VISIBLE);
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

//    private void loadPageUrl(String s) {
//        presenter.loadUrl(mWebView, s);
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            aboutDialog(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy) {
            Launcher.openBrowser(this, getString(R.string.url_privacy_policy));
            return true;
        } else if (itemId == R.id.themes) {
            int currentNightMode = AppCompatDelegate.getDefaultNightMode();
            if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                // Текущая тема - дневная
                setSubTitle("Текущая тема - дневная");
            } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                // Текущая тема - ночная
                setSubTitle("Текущая тема - ночная");
            } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                // Текущая тема следует системным настройкам
                setSubTitle("Текущая тема следует системным настройкам");
            } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY) {
                // Текущая тема автоматически следует системным настройкам и зависит от уровня заряда батареи
                setSubTitle("Текущая тема автоматически следует системным настройкам и зависит от уровня заряда батареи");
            } else {
                // Текущая тема не определена
                setSubTitle("Текущая тема не определена");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            if (isChecked) {
                // Включаем ночную тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Включаем дневную тему
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            isChecked = loadTheme();
            if (isChecked) {

                saveTheme(true);
                setTheme(R.style.Theme_ThemeSwitcher_Dark);
            } else {
                saveTheme(false);
                setTheme(R.style.Theme_ThemeSwitcher_Light);

            }
            setSubTitle("@@@" + isChecked);
            recreate();
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

    @SuppressLint({"SoonBlockedPrivateApi"})
    private void setSubTitle(String a) {
        getSupportActionBar().setSubtitle("@" + a);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        WebBackForwardList mWebBackForwardList = mWebView.copyBackForwardList();
//        handleHeader(mWebBackForwardList.getCurrentIndex() > 0);
        //ActivityRecreationHelper.onResume(this);

        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        getSupportActionBar().setSubtitle("@" + currentNightMode + " " + isChecked);

        NetUtils.isOnlineFire(this, binding.coordinatorLayout);
        //NetUtils.isOnlinePendulum(this);
        //NetUtils.isOnlineSignal(this);
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


    @Override
    protected void onDestroy() {
//        if (mmm != null) {
//            mmm.dismiss();
//        }
        super.onDestroy();
    }


    private static class MyUndoListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }


    @SuppressLint({"WrongConstant"})
    private void saveTheme(boolean isDarkTheme) {
        SharedPreferences preferences = getSharedPreferences("ThemeSwitcher", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isDarkTheme", isDarkTheme);
        editor.apply();
    }


    @SuppressLint({"DiscouragedPrivateApi", "SoonBlockedPrivateApi"})
    private boolean loadTheme() {
        SharedPreferences preferences = getSharedPreferences("ThemeSwitcher", MODE_PRIVATE);
        return preferences.getBoolean("isDarkTheme", false);
    }
}