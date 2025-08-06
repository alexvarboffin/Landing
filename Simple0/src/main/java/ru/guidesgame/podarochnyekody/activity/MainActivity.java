package ru.guidesgame.podarochnyekody.activity;

//import BaseSimpleActivity;
//import BuildConfig;
//import ChromeView;
//import Const;
//import CustomWebViewClient;
//import MultiplePagination;


import static com.walhalla.ui.Module_U.isFromGooglePlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.UWView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.landing.ChromeView;
import com.walhalla.landing.Const;
import com.walhalla.landing.MultiplePagination;

import com.walhalla.landing.activity.AboutActivity;
import com.walhalla.landing.activity.WebActivity;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import ru.guidesgame.podarochnyekody.Config;
import ru.guidesgame.podarochnyekody.ContactActivity;
import ru.guidesgame.podarochnyekody.R;

public class MainActivity extends WebActivity
        implements ChromeView, NavigationView.OnNavigationItemSelectedListener {


    //private progressBar progressBar;
    //private AdView mAdView;




    private Handler mHandler;
    private Thread mThread = null;
    private MultiplePagination pagination;
    private View promoView;
    private RelativeLayout main;
    private DrawerLayout drawer;


    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerLayout);
//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }

        
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView();
        //progressBar = findViewById(R.id.//progressBar);

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
        DrawerLayout drw = findViewById(R.id.drawerLayout);
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


    private boolean doubleBackToExitPressedOnce;

    private SwipeRefreshLayout swipe;
    protected UWView mView;
    protected Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.walhalla.landing.R.menu.main, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == com.walhalla.landing.R.id.action_about) {
            aboutDialog(this);
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_privacy_policy) {
            Launcher.openBrowser(this, getString(com.walhalla.landing.R.string.url_privacy_policy));
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_rate_app) {
            Launcher.rateUs(this);
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_share_app) {
            Module_U.shareThisApp(this);
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_discover_more_app) {
            Module_U.moreApp(this);
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_exit) {
            this.finish();
            return true;
        } else if (itemId == com.walhalla.landing.R.id.action_feedback) {
            Module_U.feedback(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    @Override
    public void onPageStarted(String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);//modify if needed
        }
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);
        }
//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        //progressBar.setVisibility(View.GONE);
    }


    private boolean isProgressEnabled() {
        return Config.PROGRESS_ENABLED;
    }

    private boolean isSwipeEnabled() {
        return Config.SWIPE_ENABLED;
    }

    protected void generateViews(Context context, ViewGroup view) {

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mView = new UWView(context);
        mView.setLayoutParams(lp);

        if (isSwipeEnabled()) {
            swipe = new SwipeRefreshLayout(context);
            swipe.setLayoutParams(lp);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        Snackbar.make(coordinatorLayout, getString(com.walhalla.landing.R.string.press_again_to_exit), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);
    }

    public static void aboutDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //&#169; - html
        String title = "\u00a9 " + year + " " + context.getString(com.walhalla.ui.R.string.play_google_pub);

        View mView = LayoutInflater.from(context).inflate(com.walhalla.ui.R.layout.about, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(null)
                .setCancelable(true)
                .setIcon(null)

                //.setNegativeButton(com.walhalla.ui.R.string.action_discover_more_app, (dialog1, which) -> moreApp(context))
                .setPositiveButton(android.R.string.ok, null)

                .setView(mView)
                .create();
        mView.setOnClickListener(v -> dialog.dismiss());
        TextView textView = mView.findViewById(com.walhalla.ui.R.id.about_version);
        textView.setText(DLog.getAppVersion(context));
        TextView _c = mView.findViewById(com.walhalla.ui.R.id.about_copyright);
        _c.setText(title);
        ImageView logo = mView.findViewById(com.walhalla.ui.R.id.aboutLogo);
        logo.setOnLongClickListener(v -> {
            String _o = "[+]gp->" + isFromGooglePlay(mView.getContext());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                _o = _o + ", category->" + mView.getContext().getApplicationInfo().category;
            }
            _c.setText(_o);
            return false;
        });
        //dialog.setButton();
        dialog.show();
    }
}