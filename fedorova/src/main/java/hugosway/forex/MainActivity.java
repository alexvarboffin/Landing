package hugosway.forex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;

import com.walhalla.landing.Const;
import com.walhalla.landing.MultiplePagination;
import com.walhalla.landing.activity.AboutActivity;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.BaseSimpleActivity;
import com.walhalla.ui.DLog;
import com.walhalla.webview.ReceivedError;

import java.util.concurrent.TimeUnit;


public class MainActivity extends BaseSimpleActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    

    
    //private progressBar progressBar;
    //private AdView mAdView;

    private Handler mHandler;
    private Thread mThread = null;
    private MultiplePagination pagination;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

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
        switchViews(true);

        presenter.generateViews(this, binding.contentMain, getPresenter0());
        
        setDrawerEnabled(true);

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

            MenuItem m = menu.getItem(1);
            onNavigationItemSelected(m);
            switchViews(false);
        }

    }

    @Override
    protected ActivityConfig getActivityConfig() {
        return new ActivityConfig() {
            @Override
            public boolean isProgressEnabled() {
                return false;
            }

            @Override
            public boolean isSwipeEnabled() {
                return false;
            }
        };
    }

    @Override
    protected boolean USE_HISTORY() {
        return true;
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
        if (id == R.id.nav_promo) {//Toast.makeText(this, "Zaglushka", Toast.LENGTH_SHORT).show();
            switchViews(true);
        } else if (id == R.id.nav_about) {
            intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {
            intent = new Intent(MainActivity.this, ContactActivity.class);
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
            binding.contentFake.setVisibility(View.VISIBLE);
            binding.contentMain.setVisibility(View.GONE);
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.setVisibility(View.GONE);
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
}
