package com.iqcent.pro;

import static com.iqcent.pro.Config.PROGRESS_ENABLED;
import static com.iqcent.pro.Config.SWIPE_ENABLED;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.UWView;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.landing.Const;
import com.walhalla.landing.MyPagination;
import com.walhalla.ui.DLog;
import com.walhalla.plugins.Module_U;

import java.util.concurrent.TimeUnit;


public class MainActivity extends WebActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private UWView mView;
    private boolean doubleBackToExitPressedOnce;
    private SwipeRefreshLayout swipe;
    //private progressBar progressBar;
    //private AdView mAdView;




    private Handler mHandler;
    private Thread mThread = null;
    private MyPagination pagination;
    private View promoView;
    private RelativeLayout main;
    private Toolbar toolbar;


    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = findViewById(R.id.toolbar);

        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);


        mHandler = new Handler();

//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }

        
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();
        //progressBar = findViewById(R.id.//progressBar);

        main = findViewById(R.id.content_main);
        promoView = findViewById(R.id.content_fake);
        switchViews(true);

        generateViews(this, main);


        setDrawerEnabled(true);

        //При старте приложения, автоматом выбираем 1 пункт меню
        if (savedInstanceState == null) {
            NavigationView navigationView = findViewById(R.id.navView);
            final Menu menu = navigationView.getMenu();
            MenuItem m = menu.getItem(0);
            if (m.hasSubMenu()) {
                MenuItem s = m.getSubMenu().getItem(0);
                onNavigationItemSelected(s);
            } else {
                onNavigationItemSelected(m);
            }
        }
    }

    private void generateViews(Context context, ViewGroup view) {
        mView = new UWView(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isSwipeEnabled()) {
            swipe = new SwipeRefreshLayout(context);
            swipe.setLayoutParams(lp);
        }
        mView.setLayoutParams(lp);
        if (isSwipeEnabled()) {
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
        presenter.a123(this, mView);
    }

    private boolean isProgressEnabled() {
        return PROGRESS_ENABLED;
    }

    private boolean isSwipeEnabled() {
        return SWIPE_ENABLED;
    }

    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navView);
        final Menu menu = navigationView.getMenu();
        pagination = new MyPagination();
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

    @Override
    public void onBackPressed() {

        DrawerLayout 
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            mView.goBack();
            // super.onBackPressed();
        }

        backPressedToast();
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about:
                Module_U.aboutDialog(this);
                return true;

            case R.id.action_privacy_policy:
                Launcher.openBrowser(this, getString(R.string.url_privacy_policy));
                return true;

            case R.id.action_rate_app:
                Launcher.rateUs(this);
                return true;

            case R.id.action_share_app:
                Module_U.shareThisApp(this);
                return true;

            case R.id.action_discover_more_app:
                Module_U.moreApp(this);
                return true;

            case R.id.action_exit:
                this.finish();
                return true;

            case R.id.action_feedback:
                Module_U.feedback(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPageStarted(String url) {
        swipe.setRefreshing(false);//modify if needed
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        swipe.setRefreshing(false);

//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        //progressBar.setVisibility(View.GONE);
    }

    

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.nav_promo:
                //Toast.makeText(this, "Zaglushka", Toast.LENGTH_SHORT).show();
                switchViews(true);
                break;

            case R.id.nav_about:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                break;

            case R.id.nav_contact:
                intent = new Intent(MainActivity.this, ContactActivity.class);
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
                mView.loadUrl(url);
                break;
        }
        if (intent != null) {
            loadScreen(intent);
        }
        DrawerLayout drw = findViewById(R.id.drawer_layout);
        drw.closeDrawer(GravityCompat.START);
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
}
