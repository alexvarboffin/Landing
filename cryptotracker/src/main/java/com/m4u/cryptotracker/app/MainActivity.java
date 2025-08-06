package com.m4u.cryptotracker.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.walhalla.landing.Const;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.BaseSimpleActivity;
import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.ui.DLog;
import com.walhalla.webview.ReceivedError;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseSimpleActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Handler mHandler;
    private Thread mThread = null;
    private SinglePagination pagination;

    private View promoView;
    private RelativeLayout main;
    private ActionBarDrawerToggle toggle;

    //private MainBinding bind;


    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        binding.drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }

        toggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout,
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();


        main = findViewById(R.id.content_main);
        promoView = findViewById(R.id.content_fake);

        //@@@ switchViews(true);
        switchViews(false);

        presenter.generateViews(this, main, getPresenter0());


        //setDrawerEnabled((menu.size()>1));
        setDrawerEnabled(false);

        //При старте приложения, автоматом выбираем 1 пункт меню
        if (savedInstanceState == null) {
            NavigationView navigationView = findViewById(R.id.navView);
            final Menu menu = navigationView.getMenu();
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
            switchViews(false);
        }
    }

    @Override
    protected ActivityConfig getActivityConfig() {
        return new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return Config.cfgBetApp.isProgressEnabled();
            }

            @Override
            public boolean isSwipeEnabled() {
                return Config.cfgBetApp.isSwipeEnabled();
            }
        };
    }

    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navView);
        final Menu menu = navigationView.getMenu();
        pagination = new SinglePagination(Config.v0);
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
        } else if (id == R.id.nav_contact) {
            Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, ContactActivity.class);
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


    public void switchViews(boolean b) {
        if (b) {
            promoView.setVisibility(View.VISIBLE);
            main.setVisibility(View.INVISIBLE);
            //getSupportActionBar().setTitle("...");
        } else {
            promoView.setVisibility(View.INVISIBLE);
            main.setVisibility(View.VISIBLE);
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    //base

    @Override
    public void onPageStarted(String url) {
        super.onPageStarted(url);
        if (isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        super.onPageFinished(url);
        if (isProgressEnabled()) {
            binding.mpb.setVisibility(View.GONE);
            binding.mpb.setIndeterminate(false);
        }
    }

    @Override
    public void removeErrorPage() {

    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {

    }

    @Override
    protected boolean USE_HISTORY() {
        return true;
    }


    public boolean isProgressEnabled() {
        return Config.PROGRESS_ENABLED;
    }

    public boolean isSwipeEnabled() {
        return Config.SWIPE_ENABLED;
    }
}