package com.chatgpt.app.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chatgpt.app.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.landing.Const;
import com.walhalla.landing.MultiplePagination;
import com.walhalla.landing.activity.WebActivity;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.WViewPresenter;

import com.walhalla.landing.databinding.ActivityMainBinding;
import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.walhalla.webview.ReceivedError;


import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity
        //extends BaseSimpleActivity
        //extends BaseCollapsingActivity
        extends WebActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Handler mHandler;
    private Thread mThread = null;

    //private SinglePagination pagination;
    private MultiplePagination pagination;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mHandler = new Handler();
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding.toolbar);
        activityConfig = getActivityConfig();
        presenter = new WViewPresenter(this, activityConfig);

//        Handler handler = new Handler(Looper.getMainLooper());
//        // Запланируйте выполнение закрытия приложения через 4 минуты (240000 миллисекунд)
//        // Закроет активити и завершит приложение
//        handler.postDelayed(this::finish, 360000); // 4 минуты в миллисекундах
//        if(Validator.validate0()){
//            finish();
//        }
        //bind = MainBinding.inflate(getLayoutInflater());
        mHandler = new Handler();
        //setContentView(bind.getRoot());

        //binding.toolbar.setVisibility(View.GONE);

//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Cfg.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();


        switchViews(false); //@@@ switchViews(true);
        presenter.generateViews(this, binding.contentMain, getPresenter0());

        if (savedInstanceState == null) {//При старте приложения, автоматом выбираем 1 пункт меню
            final Menu menu = binding.navView.getMenu();
//            MenuItem m = menu.getItem(0);
//            if (m.hasSubMenu()) {
//                MenuItem s = m.getSubMenu().getItem(0);
//                onNavigationItemSelected(s);
//                switchViews(false);
//            } else {
//                onNavigationItemSelected(m);
//            }
            MenuItem m = menu.getItem(0);//promo
            setDrawerEnabled((menu.size() > 1));

            //setDrawerEnabled(false);
            //MenuItem m = menu.getItem(1);
            onNavigationItemSelected(m);
            switchViews(false);
        }

        binding.contentFake.webViewReloadUrl.setOnClickListener(v -> {
            final Menu menu = binding.navView.getMenu();
//            MenuItem m = menu.getItem(0);
//            if (m.hasSubMenu()) {
//                MenuItem s = m.getSubMenu().getItem(0);
//                onNavigationItemSelected(s);
//                switchViews(false);
//            } else {
//                onNavigationItemSelected(m);
//            }
            MenuItem m = menu.getItem(0);//promo
            //MenuItem m = menu.getItem(1);
            onNavigationItemSelected(m);
//            loadUrl(url)
//        Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout != null && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return;
                } else {
                    if (doubleBackToExitPressedOnce) {
                        MainActivity.super.onBackPressed();
                        return;
                    }
                    if (USE_HISTORY()) {
                        if (presenter.canGoBack()) {
                            //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                            //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                            presenter.goBack();
                            return;
                        }
                    } else {
                        MainActivity.super.onBackPressed();
                    }
                }

                View coordinatorLayout = findViewById(R.id.coordinatorLayout);
                Snackbar.make(coordinatorLayout, getString(R.string.press_again_to_exit), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                MainActivity.this.doubleBackToExitPressedOnce = true;
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);
            }
        });
    }


    protected ActivityConfig getActivityConfig() {
        return new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return Cfg9.cfgBetApp.isProgressEnabled();
            }

            @Override
            public boolean isSwipeEnabled() {
                return Cfg9.cfgBetApp.isSwipeEnabled();
            }
        };
    }


    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navView);
        final Menu menu = navigationView.getMenu();
        pagination = new MultiplePagination();
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem item = menu.getItem(0);
        if (item.hasSubMenu()) {
            pagination.setupDrawer(this, item.getSubMenu());
        } else {
            pagination.setupDrawer(this, menu);
        }
    }

    private void loadScreen(@NonNull final Intent data) {
        mThread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(400);
                mHandler.post(() -> {
                    // update the main content by replacing fragments
//                        Fragment fragment = getHomeFragment(currentTag);
//                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                        fragmentTransaction.replace(R.id.container, fragment, CURRENT_TAG);
//                        fragmentTransaction.commitAllowingStateLoss();
                    startActivity(data);
                });
                //mThread.interrupt();
            } catch (InterruptedException e) {
//                Toast.makeText(this, e.getLocalizedMessage()
//                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
            }
        }, "m-thread");
        if (!mThread.isAlive()) {
            try {
                mThread.start();
            } catch (Exception r) {
                DLog.handleException(r);
            }
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_about) {
            Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {//intent = new Intent(MainActivity.this, ContactActivity.class);
            copyTextToClipboard("f720b61f3bd21a17003cf6f0e5501d276e6733b2d995754a6471b2752a568e02");
        } else if (id == R.id.nav_share) {
            String shareBody = getString(R.string.app_name) + " " + String.format(Const.url_app_google_play, getPackageName());
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent = Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name));
        } else if (id == R.id.action_exit) {
            this.finish();
            return true;
        } else {
            String url = pagination.getUrl(id);
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            switchViews(false);
            presenter.loadUrl(url);
        }
        if (intent != null) {
            loadScreen(intent);
        }
        DrawerLayout drw = findViewById(R.id.drawerLayout);
        drw.closeDrawer(GravityCompat.START);
        return true;
    }

    private void copyTextToClipboard(String text) {
        // Получаем системный сервис ClipboardManager
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // Создаем объект ClipData для хранения текста
        ClipData clipData = ClipData.newPlainText("label", text);

        // Устанавливаем объект ClipData в буфер обмена
        clipboardManager.setPrimaryClip(clipData);

        // Выводим сообщение о сохранении текста в буфер обмена
        Toast.makeText(this, "Текст скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
    }

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

    //base

    @Override
    public void onPageStarted(String url) {
        presenter.hideSwipeRefreshing();
        if (Cfg9.cfgBetApp.isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        presenter.hideSwipeRefreshing();
//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (Cfg9.cfgBetApp.isProgressEnabled()) {
            binding.mpb.setVisibility(View.GONE);
            binding.mpb.setIndeterminate(false);
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


    public boolean USE_HISTORY() {
        return true;
    }

    protected boolean doubleBackToExitPressedOnce;
    //private Handler mHandler;

    protected ActivityMainBinding binding;
    protected WViewPresenter presenter;
    private ActivityConfig activityConfig;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


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
        } else if (itemId == R.id.action_refresh) {
            presenter.refreshWV();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    


    public static void aboutDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //&#169; - html
        String title = "\u00a9 " + year + " " + context.getString(R.string.play_google_pub);
        View mView = LayoutInflater.from(context).inflate(R.layout.about, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(null)
                .setCancelable(true)
                .setIcon(null)
                //.setNegativeButton(R.string.action_discover_more_app, (dialog1, which) -> moreApp(context))
                .setPositiveButton(android.R.string.ok, null)
                .setView(mView)
                .create();
        mView.setOnClickListener(v -> dialog.dismiss());
        TextView textView = mView.findViewById(R.id.about_version);
        textView.setText(DLog.getAppVersion(context));
        TextView _c = mView.findViewById(R.id.about_copyright);
        _c.setText(title);
        ImageView logo = mView.findViewById(R.id.aboutLogo);
        logo.setOnLongClickListener(v -> {
            String _o = "[+]gp->" + Module_U.isFromGooglePlay(mView.getContext());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                _o = _o + ", category->" + mView.getContext().getApplicationInfo().category;
            }
            _c.setText(_o);
            return false;
        });
        //dialog.setButton();
        dialog.show();
    }

    public void setDrawerEnabled(boolean enabled) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        binding.drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isUsbConnected(this);


        mmm();
    }

    private void mmm() {


//        int[] fragmentIds = {
//                graphs.get("deferred_navigation"),
//                fragments.get("private_deferred")};
//
//// Создание пакета с данными
//        Bundle b1 = new Bundle();
//
//// Создание интента для запуска активности
//        Intent intent = new Intent();
//        intent.setClassName("ru.ptsecurity.navigation_example", "ru.ptsecurity.navigation_example.MainActivity");
//        intent.putExtra("android-support-nav:controller:deepLinkExtras", b1);
//        intent.putExtra("android-support-nav:controller:deepLinkIds", fragmentIds);
//
//// Запуск активности
//        startActivity(intent);
    }

    //    Power mode
//    Enabled usb cabel
//    Enabled usb mode
    public boolean isUsbConnected(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (usbManager != null) {
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            getSupportActionBar().setSubtitle(deviceList.toString());
            return !deviceList.isEmpty();
        }
        return false;
    }
}