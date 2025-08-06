package com.royspins.app.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.UWView;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;

import com.royspins.app.Cfg9;
import com.royspins.app.ContactActivity;
import com.royspins.app.R;
//import BaseSimpleActivity;
//import BuildConfig;
//import ChromeView;
//import Const;
//import CustomWebViewClient;
//import MultiplePagination;

import com.royspins.app.adapter.FirebaseRepository;
import com.royspins.app.presenter.MainContract;
import com.walhalla.landing.Const;
import com.walhalla.landing.activity.AboutActivity;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.BaseSimpleActivity;
import com.walhalla.landing.pagination.CatItem;
import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.walhalla.webview.ReceivedError;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity0 extends BaseSimpleActivity implements
        NavigationView.OnNavigationItemSelectedListener, MainContract.View//, MainView
{


    protected MainContract.Presenter p0;

    //private progressBar progressBar;
    //private AdView mAdView;


    private Handler mHandler;
    private Thread mThread = null;


    private Menu menu;
    private ActionBarDrawerToggle toggle;


//    private final GConfig aaa = new GConfig(
//            false, false, UrlSaver.FIRST,
//            true, false
//    );


//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.onResume(getIntent());
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        Handler handler = new Handler(Looper.getMainLooper());
//        presenter = new MainPresenter(
//                this, this, aaa, handler, makeTracker());

        //toolbar.setVisibility(View.GONE);
//        setSupportActionBar(toolbar);
        mHandler = new Handler();

//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState != null) {
//            return;
//        }


        //progressBar = findViewById(R.id.//progressBar);

        generateViews(this, binding.contentMain);
        setUpNavigationView();

        //@@@ switchViews(true);
        switchViews(false);


        setDrawerEnabled(false);


        if (savedInstanceState == null) {
            //presenter = new ResPresenter(this, this);
            p0 = new FirebaseRepository(this, this);
            p0.fetchRemoteConfig(this);
        }

//        //@if (!rotated()) {
//            presenter.init0(this);
//        //@}
    }

    @Override
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

//    @Override
//    public Integer orientation404() {
//        return null;
//    }
//
//    @Override
//    public Integer orientationWeb() {
//        return null;
//    }
//
//    @Override
//    public boolean checkUpdate() {
//        return false;
//    }
//
//    @Override
//    public boolean webTitle() {
//        return false;
//    }
//
//    public FirebaseRepository makeTracker() {
//        return new FirebaseRepository(this);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        presenter.onDestroy();
//    }

    private void setUpNavigationView() {
        menu = binding.navView.getMenu();
        binding.navView.setNavigationItemSelectedListener(this);

//        MenuItem item = menu.getItem(0);
//        if (item.hasSubMenu()) {
//            pagination.setupDrawer(this, item.getSubMenu());
//        } else {
//            pagination.setupDrawer(this, menu);
//        }


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
        //            case R.id.nav_promo:
        ////                //Toast.makeText(this, "Zaglushka", Toast.LENGTH_SHORT).show();
        ////                switchViews(true);
        //
        //
        //                String url0 = "https://royspins.com/";
        //                //Toast.makeText(this, "" + url0, Toast.LENGTH_SHORT).show();
        //                //switchViews(false);
        //                mWView.setVisibility(View.VISIBLE);
        //                mWView.loadUrl(url0);
        //                break;
        if (id == R.id.nav_about) {
            intent = new Intent(MainActivity0.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {
            intent = new Intent(MainActivity0.this, ContactActivity.class);
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
        } else {//                String url = pagination.getUrl(id);
//                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
//                switchViews(false);
//                mWView.loadUrl(url);

            showProgressbar();
            p0.onNavigationItemSelected("" + item.getTitle().toString());
        }
        if (intent != null) {
            loadScreen(intent);
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean doubleBackToExitPressedOnce;

    private SwipeRefreshLayout swipe;
    protected UWView mView;


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


    //@@@@


    @Override
    public void onPageStarted(String url) {
        if (Cfg9.cfgBetApp.isSwipeEnabled()) {
            swipe.setRefreshing(false);//modify if needed
        }
        showProgressbar();
    }

    private void showProgressbar() {
        if (Cfg9.cfgBetApp.isProgressEnabled()) {
            binding.mpb.setVisibility(View.VISIBLE);
            binding.mpb.setIndeterminate(true);
        }
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        if (Cfg9.cfgBetApp.isSwipeEnabled()) {
            swipe.setRefreshing(false);
        }

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

    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {

    }

    protected void generateViews(Context context, ViewGroup view) {
        mView = new UWView(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (Cfg9.cfgBetApp.isSwipeEnabled()) {
            swipe = new SwipeRefreshLayout(context);
            swipe.setLayoutParams(lp);
        }
        mView.setLayoutParams(lp);
        if (Cfg9.cfgBetApp.isSwipeEnabled()) {
            view.addView(swipe);
            swipe.addView(mView);
            swipe.setRefreshing(false);
            swipe.setOnRefreshListener(() -> {
                swipe.setRefreshing(false);
                mView.reload();
            });
        } else {
            view.addView(mView);
        }

        //mWebView.setBackgroundColor(Color.BLACK);
        // register class containing methods to be exposed to JavaScript
        getPresenter0().a123(this, mView);
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            if (mView.canGoBack()) {
                //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                mView.goBack();
                return;
            }
            // super.onBackPressed();
        }

        backPressedToast();
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1600);
    }

    @Override
    protected boolean USE_HISTORY() {
        return true;
    }


    @Override
    public void showSideNavigationMenu() {
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void hideSideNavigationMenu() {
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void showBottomNavigationMenu() {

    }

    @Override
    public void hideBottomNavigationMenu() {

    }

    @Override
    public void showToolbar() {

    }

    @Override
    public void hideToolbar() {

    }

    @Override
    public void loadUrlInWebView(String url) {
        if (!TextUtils.isEmpty(url)) {
            Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            switchViews(false);
            mView.loadUrl(url);
        }
    }

    @Override
    public void setupNavigationMenus(List<CatItem> categories, List<CatItem> bottomNavMenuItems) {
        menu.clear();
        for (final CatItem category : categories) {
            menu.add(R.id.menu_container, category._id, Menu.NONE, category.title)
                    .setCheckable(true)
                    .setIcon(category.icon);
        }

        setDrawerEnabled((menu.size() > 1));

        NavigationView navigationView = findViewById(R.id.navView);
        final Menu menu = navigationView.getMenu();//При старте приложения, автоматом выбираем 1 пункт меню
        MenuItem m = menu.getItem(0);
        if (m.hasSubMenu()) {
            MenuItem s = m.getSubMenu().getItem(0);
            onNavigationItemSelected(s);
            switchViews(false);
        } else {
            onNavigationItemSelected(m);
        }
        //MenuItem m = menu.getItem(0);//promo
        //MenuItem m = menu.getItem(1);
        //onNavigationItemSelected(m);
    }


}
