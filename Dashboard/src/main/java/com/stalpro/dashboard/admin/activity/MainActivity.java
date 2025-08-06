package com.stalpro.dashboard.admin.activity;

import static com.walhalla.plugins.Module_U.isFromGooglePlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.UWView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.stalpro.dashboard.admin.R;

import com.stalpro.dashboard.admin.databinding.ActivityMainBinding;
import com.walhalla.landing.ChromeView;
import com.walhalla.landing.Const;

import com.walhalla.landing.activity.AboutActivity;
import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.ui.DLog;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.stalpro.dashboard.admin.Config;

public class MainActivity extends ApqCompatActivity
        implements ChromeView
        //, NavigationView.OnNavigationItemSelectedListener
{

    public SinglePagination pag0; //optional use

    //private AdView mAdView;

    //none
    //none

    private Handler mHandler;
    private Thread mThread = null;

    private View promoView;
    private RelativeLayout main;

    private PowerManager.WakeLock wakeLock;
    private ActivityMainBinding binding;


    public void setDrawerEnabled(boolean enabled) {
        //none  int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
        //none          DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        //none  drawer.setDrawerLockMode(lockMode);
        //none toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

//        Handler handler = new Handler(Looper.getMainLooper());
//        presenter0 = new WPresenterImpl(handler, this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pag0 = new SinglePagination(Config.v0);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "YourApp:WakeLockTag");
        //wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding.toolbar.setVisibility((Config.SHOW_TOOLBAR) ? View.VISIBLE : View.GONE);
        setSupportActionBar(binding.toolbar);


//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }
        //none drawer = findViewById(R.id.drawerLayout);
        //none toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //none drawer.addDrawerListener(toggle);
        //none toggle.syncState();
//        if (savedInstanceState != null) {
//            return;
//        }


        setUpNavigationView();


        main = findViewById(R.id.content_main);
        promoView = findViewById(R.id.content_fake);

        //@@@ switchViews(true);
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
            //MenuItem m = menu.getItem(0);//promo
            MenuItem m = menu.getItem(3);
            onNavigationItemSelected(m);

//            String url = pagination.getUrl0();
//            mView.loadUrl(url);

            switchViews(false);
        }

    }

    private void setUpNavigationView() {
        //NavigationView navigationView = findViewById(R.id.navView);
        //final Menu menu = navigationView.getMenu();
        //navigationView.setNavigationItemSelectedListener(this);
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
                String url = pag0.getUrl0();
                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
                switchViews(false);


                //String url1 = "https://188.120.231.31/#&ui-state=dialog";
                //String url1 = "https://andr:Gfdtk1981@188.120.231.31/#&ui-state=dialog";
//                String username = "andr";
//                String password = "Gfdtk1981";
//                String auth = Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
//                String authHeader = "Basic " + auth;
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", authHeader);

                //mView.loadUrl(url1, headers);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //base


    @Override
    public void onPageStarted(String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);//modify if needed
        }
        binding.progressBar.setVisibility(View.VISIBLE);
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
        binding.progressBar.setVisibility(View.GONE);
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
//            WebBackForwardList mWebBackForwardList = mView.copyBackForwardList();
//            boolean visible = mWebBackForwardList.getCurrentIndex() > 0;
            //handleHeader(!visible);

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

    @SuppressLint("WakelockTimeout")
    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();// Включаем WakeLock
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release(); // Отключаем WakeLock при приостановке активности
    }
}