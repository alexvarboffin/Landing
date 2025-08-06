package com.ketodieto.app.activity;


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
import com.ketodieto.app.R;
import com.walhalla.landing.activity.AboutActivity;
import com.walhalla.landing.base.BaseSimpleActivity;

import com.walhalla.landing.Const;
import com.walhalla.landing.MultiplePagination;
import com.walhalla.landing.databinding.ActivityMainBinding;
import com.walhalla.ui.DLog;

import java.util.concurrent.TimeUnit;

import com.ketodieto.app.Config;

public class MainActivity extends BaseSimpleActivity
        implements NavigationView.OnNavigationItemSelectedListener {





    private Handler mHandler;
    private Thread mThread = null;
    private MultiplePagination pagination;
    private View promoView;
    private RelativeLayout main;
    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding.toolbar);

//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }



//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();
        

        main = findViewById(R.id.content_main);
        promoView = findViewById(R.id.content_fake);

        //@@@ switchViews(true);
        switchViews(false);

        generateViews(this, main);


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
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.nav_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                break;

            case R.id.nav_contact:
                Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
                //intent = new Intent(MainActivity.this, ContactActivity.class);
                break;

            case R.id.nav_share:
                String shareBody = getString(R.string.app_name) + " " + String.format(Const.url_app_google_play, getPackageName());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                intent = Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name));
                break;

            case R.id.action_exit:
                this.finish();
                return true;

            default:
                String url = pagination.getUrl(id);
                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
                switchViews(false);
                loadUrl(url);
                break;
        }
        if (intent != null) {
            loadScreen(intent);
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchViews(boolean b) {
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

    private boolean doubleBackToExitPressedOnce;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
    protected boolean USE_HISTORY() {
        return true;
    }

    @Override
    public boolean isProgressEnabled() {
        return Config.PROGRESS_ENABLED;
    }

    public boolean isSwipeEnabled() {
        return Config.SWIPE_ENABLED;
    }
}