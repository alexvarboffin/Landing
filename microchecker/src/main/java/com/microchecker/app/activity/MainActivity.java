package com.microchecker.app.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.microchecker.app.Cfg9;
import com.microchecker.app.R;
import com.microchecker.app.databinding.MainBinding;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.WViewPresenter;

import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.walhalla.webview.ReceivedError;


import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {


    private Handler mHandler;
    private Thread mThread = null;

    private MainBinding binding;
    private MyOnNavigationItemSelectedListener selectedListener;
    private WViewPresenter presenter1;


    //private MainBinding bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mHandler = new Handler();
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        selectedListener = new MyOnNavigationItemSelectedListener(this);
        ActivityConfig activityConfig = new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return MainActivity.this.isProgressEnabled();
            }

            @Override
            public boolean isSwipeEnabled() {
                return MainActivity.this.isSwipeEnabled();
            }
        };
        presenter1 = new WViewPresenter(this, activityConfig);
        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding.toolbar);
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
        binding.toolbar.setVisibility(View.GONE);
//        mAdView = 222(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Cfg9.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();


        //@@@ switchViews(true);
        switchViews(false);

        generateViews(this, binding.contentMain);

        //setDrawerEnabled((menu.size()>1));
        setDrawerEnabled(true);
        if (savedInstanceState == null) {
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
            switchViews(false);
            selectedListener.onNavigationItemSelected(m);

        }
    }


    private void setUpNavigationView() {
        final Menu menu = binding.navView.getMenu();
        binding.navView.setNavigationItemSelectedListener(selectedListener);
        MenuItem item = menu.getItem(0);
        if (item.hasSubMenu()) {
            selectedListener.setupDrawer(this, item.getSubMenu());
        } else {
            selectedListener.setupDrawer(this, menu);
        }
    }

    public void loadScreen(@NonNull final Intent data) {
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

    //base

    @Override
    public void onPageStarted(String url) {
        super.onPageStarted(url);
        if (isProgressEnabled()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        super.onPageFinished(url);
        if (isProgressEnabled()) {
            binding.progressBar.setVisibility(View.GONE);
            binding.progressBar.setIndeterminate(false);
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
    protected boolean USE_HISTORY() {
        return true;
    }

    @Override
    public boolean isProgressEnabled() {
        return Cfg9.cfg.isProgressEnabled();
    }

    public boolean isSwipeEnabled() {
        return Cfg9.cfg.isSwipeEnabled();
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout != null && binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            if (USE_HISTORY()) {
                if (dynamicWebView.getWebView().canGoBack()) {
                    //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                    //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                    dynamicWebView.getWebView().goBack();
                    return;
                }
            } else {
                super.onBackPressed();
            }
        }
        this.doubleBackToExitPressedOnce = true;
        backPressedToast();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            presenter1.aboutDialog(this);
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

    public void backPressedToast() {
        Snackbar.make(binding.coordinatorLayout, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        View view = findViewById(android.R.id.content);
//        if (view == null) {
//            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
//        } else {
//            Snackbar.make(view, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        }
    }

    public void hideDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setDrawerEnabled(boolean enabled) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, com.walhalla.landing.R.string.navigation_drawer_open, com.walhalla.landing.R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        binding.drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.GET_ACCOUNTS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.GET_ACCOUNTS)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.GET_ACCOUNTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//        }
//        String possibleEmail="";
//        try{
//            possibleEmail += "************* Get Registered Gmail Account *************\n\n";
//            AccountManager accountManager = AccountManager.get(this);
//            //Account[] accounts = accountManager.getAccountsByType("com.google");
//            Account[] accounts = accountManager.getAccounts();
//            //Account[] accounts = accountManager.getAccountsByType(accountType);
//            for (Account account : accounts) {
//
//                possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
//                possibleEmail += " \n\n";
//
//            }
//            DLog.d("@@@@@" + Arrays.toString(accounts));
//        }catch (Exception e){
//            Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        DLog.d("mails: " + possibleEmail);
    }
}