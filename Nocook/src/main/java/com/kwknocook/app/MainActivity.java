package com.kwknocook.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import com.walhalla.landing.base.BaseActivity;

import com.walhalla.landing.Const;

import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.landing.utility.NetUtils;
import com.walhalla.ui.DLog;
import com.walhalla.plugins.Module_U;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {




    private Handler mHandler;
    private Thread mThread = null;
    private SinglePagination pagination;

    private View promoView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        binding.toolbar.setVisibility(View.GONE);

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


        promoView = findViewById(R.id.content_fake);

        //@@@ switchViews(true);
        switchViews(false);

        generateViews(this);


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
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        Intent intent = null;
        switch (id) {

            case R.id.nav_about:
                Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
                //intent = new Intent(MainActivity.this, AboutActivity.class);
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
            binding.contentMain.setVisibility(View.INVISIBLE);
            //getSupportActionBar().setTitle("...");
        } else {
            promoView.setVisibility(View.INVISIBLE);
            binding.contentMain.setVisibility(View.VISIBLE);
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }


    //base

    public static void aboutDialog(Context context) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //&#169; - html
        String title = "\u00a9 " + year + " " + context.getString(com.walhalla.ui.R.string.play_google_pub);
        View mView = LayoutInflater.from(context).inflate(R.layout.about, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(null)
                .setCancelable(true)
                .setIcon(null)
                //.setNegativeButton(com.walhalla.ui.R.string.action_discover_more_app, (dialog1, which) -> moreApp(context))
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
        return Config.PROGRESS_ENABLED;
    }

    public boolean isSwipeEnabled() {
        return Config.SWIPE_ENABLED;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //NetUtils.isOnlineFire(this, binding.coordinatorLayout);
        NetUtils.isOnlinePendulum(this);
        //NetUtils.isOnlineSignal(this);
    }
}