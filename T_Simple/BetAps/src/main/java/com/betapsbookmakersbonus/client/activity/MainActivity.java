package com.betapsbookmakersbonus.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.betapsbookmakersbonus.client.BetApsCfg9;
import com.betapsbookmakersbonus.client.MyOnNavigationItemSelectedListener;
import com.betapsbookmakersbonus.client.R;
import com.betapsbookmakersbonus.client.databinding.MainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.landing.activity.WebActivity;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.WViewPresenter;
import com.walhalla.landing.utility.NetUtils;
import com.walhalla.ui.DLog;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ui.plugins.Module_U;
import com.walhalla.webview.ReceivedError;

import java.util.concurrent.TimeUnit;

public class MainActivity extends WebActivity {

    protected boolean doubleBackToExitPressedOnce;
    private Handler mHandler;


    private WViewPresenter presenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private Thread mThread = null;

    private MainBinding binding;
    //private ActivityCollapsingToolbarBinding binding;
    //private ActivityGlassCollapsinToolbarBinding binding;

    //private ActivityGlassToolbarBinding binding;

    private MyOnNavigationItemSelectedListener mm;

    //private MainBinding bind;


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        presenter.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);

        //binding = ActivityCollapsingToolbarBinding.inflate(getLayoutInflater());
        //binding = ActivityGlassCollapsinToolbarBinding.inflate(getLayoutInflater());

        //binding = ActivityGlassToolbarBinding.inflate(getLayoutInflater());
        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mHandler = new Handler();
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        mm = new MyOnNavigationItemSelectedListener(this);

        ActivityConfig activityConfig = new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return BetApsCfg9.cfg.isProgressEnabled();
            }

            @Override
            public boolean isSwipeEnabled() {
                return BetApsCfg9.cfg.isSwipeEnabled();
            }
        };

        presenter = new WViewPresenter(this, activityConfig);
        presenter.getDynamicWebView().getParent().getButtonMenu().setVisibility(View.GONE);

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
        if (BetApsCfg9.cfg.isToolbarEnabled()) {
            binding.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.toolbar);
            if (getSupportActionBar() != null) {
                //getSupportActionBar().setTitle(R.string.app_name);
                getSupportActionBar().setTitle("");
                //binding.toolbar.setTitleCentered(true);
                //getSupportActionBar().setSubtitle(R.string.app_subtitle);
            }
        } else {
            binding.toolbar.setVisibility(View.GONE);
        }
//        mAdView = 222(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!BetApsCfg9.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView(binding.navView);


        //@@@ switchViews(true);
        switchViews(false);
        presenter.generateViews(this, binding.contentMain, getPresenter0());


        //setDrawerEnabled((menu.size()>1));
        setDrawerEnabled(false);

        //При старте приложения, автоматом выбираем 1 пункт меню
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
            mm.onNavigationItemSelected(m);
            switchViews(false);
        }

//        binding.@@@@@@(v -> {
//            final Menu menu = binding.navView.getMenu();
////            MenuItem m = menu.getItem(0);
////            if (m.hasSubMenu()) {
////                MenuItem s = m.getSubMenu().getItem(0);
////                onNavigationItemSelected(s);
////                switchViews(false);
////            } else {
////                onNavigationItemSelected(m);
////            }
//            MenuItem m = menu.getItem(0);//promo
//            //MenuItem m = menu.getItem(1);
//            mm.onNavigationItemSelected(m);
////            loadUrl(url)
////        Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
//        });


//        binding.fab.setOnClickListener(v -> {
//            Toast.makeText(this, "@@", Toast.LENGTH_SHORT).show();
//        });
    }


    private void setUpNavigationView(NavigationView navView) {
        final Menu menu = navView.getMenu();
        navView.setNavigationItemSelectedListener(mm);
        MenuItem item = menu.getItem(0);
        if (item.hasSubMenu()) {
            mm.setupDrawer(this, item.getSubMenu());
        } else {
            mm.setupDrawer(this, menu);
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
            binding.contentFake.setVisibility(View.VISIBLE);
            binding.contentMain.setVisibility(View.GONE);
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.setVisibility(View.GONE);
            binding.contentMain.setVisibility(View.VISIBLE);
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    //base

    @Override
    public void onPageStarted(String url) {
        presenter.hideSwipeRefreshing();
        if (BetApsCfg9.cfg.isProgressEnabled()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.progressBar.setIndeterminate(true);
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

        if (BetApsCfg9.cfg.isProgressEnabled()) {
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


    protected boolean USE_HISTORY() {
        return true;
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
                if (presenter.canGoBack()) {
                    //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                    //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                    presenter.goBack();
                    return;
                }
//                else {
//                    Toast.makeText(this, "@@", Toast.LENGTH_SHORT).show();
//                    return;
//                }
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
            presenter.aboutDialog(this);
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


    @Override
    protected void onResume() {
        super.onResume();
        //NetUtils.isOnlineFire(this, binding.coordinatorLayout);
        //NetUtils.isOnlinePendulum(this);
        NetUtils.isOnlineSignal(this);
        //TooltipUtils.tapTargetView(this);
    }


    public void loadUrl(String url) {
        presenter.loadUrl(url);
    }

    public void setDrawerEnabled(boolean enabled) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, com.walhalla.landing.R.string.navigation_drawer_open, com.walhalla.landing.R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        binding.drawerLayout.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }
}