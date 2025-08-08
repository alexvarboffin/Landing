package com.royspins.app.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.UWView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.royspins.app.Cfg9
import com.royspins.app.ContactActivity
import com.royspins.app.R
import com.royspins.app.adapter.SpreadsheetsRepository

import com.royspins.app.presenter.MainContract
import com.walhalla.landing.Const
import com.walhalla.landing.activity.AboutActivity
import com.walhalla.landing.base.ActivityConfig
import com.walhalla.landing.base.BaseSimpleActivity
import com.walhalla.landing.pagination.CatItem
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U
import com.walhalla.ui.plugins.Module_U.feedback
import com.walhalla.ui.plugins.Module_U.moreApp
import com.walhalla.ui.plugins.Module_U.shareThisApp
import com.walhalla.webview.ReceivedError
import java.util.concurrent.TimeUnit

//import BaseSimpleActivity;
//import BuildConfig;
//import ChromeView;
//import Const;
//import CustomWebViewClient;
//import MultiplePagination;
class MainActivity0 : BaseSimpleActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainContract.View //, MainView
{


    //private progressBar progressBar;
    //private AdView mAdView;
    private var mHandler: Handler? = null
    private var mThread: Thread? = null


    private var menu: Menu? = null
    private var toggle: ActionBarDrawerToggle? = null


    val url =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vQKFNb6bCwxzY0SQ5l_fENfzo82WQ9K85yz8pg-98AmN5CX3gT8M7H8XcMVvpcuduTkKjlOwfUzX6MZ/pub?gid=0&single=true&output=csv"


    protected var p0: MainContract.Presenter? = SpreadsheetsRepository(url, handler, this, this)

    //    private final GConfig aaa = new GConfig(
    //            false, false, UrlSaver.FIRST,
    //            true, false
    //    );
    //    @Override
    //    protected void onResume() {
    //        super.onResume();
    //        presenter.onResume(getIntent());
    //    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler()

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        Handler handler = new Handler(Looper.getMainLooper());
//        presenter = new MainPresenter(
//                this, this, aaa, handler, makeTracker());

        binding?.toolbar?.visibility = View.GONE

//        setSupportActionBar(toolbar);


//        mAdView = findViewById(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Config.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


        toggle = ActionBarDrawerToggle(
            this,
            binding!!.drawerLayout,
            binding!!.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding!!.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()


        //        if (savedInstanceState != null) {
//            return;
//        }


        //progressBar = findViewById(R.id.//progressBar);


        // Восстанавливаем состояние WebView, если оно есть
        if (savedInstanceState != null) {
            if (mWebView == null) {
                generateViews(this, binding!!.contentMain)
            }
            mWebView?.restoreState(savedInstanceState)
        } else {
            // Инициализация WebView, если savedInstanceState == null
            generateViews(this, binding!!.contentMain)
        }

        setUpNavigationView()

        //@@@ switchViews(true);
        switchViews(false)


        setDrawerEnabled(false)


        //if (savedInstanceState == null) {
        //presenter = new ResPresenter(this, this);


        p0?.fetchRemoteConfig(this)
        //}

//        //@if (!rotated()) {
//            presenter.init0(this);
//        //@}
    }

    override fun getActivityConfig(): ActivityConfig {
        return object : ActivityConfig {
            override val isProgressEnabled: Boolean
                get() = Cfg9.cfg.isProgressEnabled

            override val isSwipeEnabled: Boolean
                get() = Cfg9.cfg.isSwipeEnabled
        }
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
    private fun setUpNavigationView() {
        menu = binding!!.navView.menu
        binding!!.navView.setNavigationItemSelectedListener(this)


        //        MenuItem item = menu.getItem(0);
//        if (item.hasSubMenu()) {
//            pagination.setupDrawer(this, item.getSubMenu());
//        } else {
//            pagination.setupDrawer(this, menu);
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        p0?.destroy()
    }

    private fun loadScreen(data: Intent) {
        mThread = Thread({
            try {
                TimeUnit.MILLISECONDS.sleep(400)
                mHandler!!.post {
                    // update the main content by replacing fragments
                    //                        Fragment fragment = getHomeFragment(currentTag);
                    //                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    //                        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    //                        fragmentTransaction.replace(R.id.container, fragment, CURRENT_TAG);
                    //                        fragmentTransaction.commitAllowingStateLoss();
                    startActivity(data)
                }
                //mThread.interrupt();
            } catch (e: InterruptedException) {
//                Toast.makeText(this, e.getLocalizedMessage()
//                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
            }
        }, "m-thread")

        if (!mThread!!.isAlive) {
            try {
                mThread!!.start()
            } catch (r: Exception) {
                handleException(r)
            }
        }
    }


    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        var intent: Intent? = null
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
            intent = Intent(this@MainActivity0, AboutActivity::class.java)
        } else if (id == R.id.nav_contact) {
            intent = Intent(this@MainActivity0, ContactActivity::class.java)
        } else if (id == R.id.nav_share) {
            val shareBody = getString(R.string.app_name) + " " + String.format(
                Const.url_app_google_play,
                packageName
            )
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            intent = Intent.createChooser(sharingIntent, resources.getString(R.string.app_name))
        } else if (id == R.id.action_exit) {
            this.finish()
            return true
        } else { //                String url = pagination.getUrl(id);
//                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
//                switchViews(false);
//                mWView.loadUrl(url);

            showProgressbar()
            p0!!.onNavigationItemSelected("" + item.title.toString())
        }
        if (intent != null) {
            loadScreen(intent)
        }
        binding!!.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override var doubleBackToExitPressedOnce: Boolean = false

    private var swipe: SwipeRefreshLayout? = null
    protected var mWebView: UWView?=null


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_about) {
            aboutDialog(this)
            return true
        } else if (itemId == R.id.action_privacy_policy) {
            openBrowser(this, getString(R.string.url_privacy_policy))
            return true
        } else if (itemId == R.id.action_rate_app) {
            rateUs(this)
            return true
        } else if (itemId == R.id.action_share_app) {
            shareThisApp(this)
            return true
        } else if (itemId == R.id.action_discover_more_app) {
            moreApp(this)
            return true
        } else if (itemId == R.id.action_exit) {
            this.finish()
            return true
        } else if (itemId == R.id.action_feedback) {
            feedback(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    //@@@@
    override fun onPageStarted(url: String?) {
        if (Cfg9.cfg.isSwipeEnabled) {
            swipe!!.isRefreshing = false //modify if needed
        }
        showProgressbar()
    }

    private fun showProgressbar() {
        if (Cfg9.cfg.isProgressEnabled) {
            binding!!.mpb.visibility = View.VISIBLE
            binding!!.mpb.isIndeterminate = true
        }
    }

    override fun onPageFinished( /*WebView view, */
                                 url: String?
    ) {
        if (Cfg9.cfg.isSwipeEnabled) {
            swipe!!.isRefreshing = false
        }

        //        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (Cfg9.cfg.isProgressEnabled) {
            binding!!.mpb.visibility = View.GONE
            binding!!.mpb.isIndeterminate = false
        }
    }

    override fun removeErrorPage() {
    }

    override fun setErrorPage(receivedError: ReceivedError) {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем состояние WebView
        mWebView?.saveState(outState)
    }

    private fun generateViews(context: Context, view: ViewGroup) {
        mWebView = UWView(context)
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        if (Cfg9.cfg.isSwipeEnabled) {
            swipe = SwipeRefreshLayout(context)
            swipe!!.layoutParams = lp
        }
        mWebView!!.layoutParams = lp
        if (Cfg9.cfg.isSwipeEnabled) {
            view.addView(swipe)
            swipe!!.addView(mWebView)
            swipe!!.isRefreshing = false
            swipe!!.setOnRefreshListener {
                swipe!!.isRefreshing = false
                mWebView!!.reload()
            }
        } else {
            view.addView(mWebView)
        }

        //mWebView.setBackgroundColor(Color.BLACK);
        // register class containing methods to be exposed to JavaScript
        presenter0.a123(this,  mWebView!!)
    }


    override fun onBackPressed() {
        if (binding!!.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            return
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            if (mWebView!!.canGoBack()) {
                //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                mWebView!!.goBack()
                return
            }
            // super.onBackPressed();
        }

        backPressedToast()
        this.doubleBackToExitPressedOnce = true

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1600)
    }

    override fun USE_HISTORY(): Boolean {
        return true
    }


    override fun showSideNavigationMenu() {
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    override fun hideSideNavigationMenu() {
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    override fun showBottomNavigationMenu() {
    }

    override fun hideBottomNavigationMenu() {
    }

    override fun showToolbar() {
    }

    override fun hideToolbar() {
    }

    override fun loadUrlInWebView(url: String) {
        if (!TextUtils.isEmpty(url)) {
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show()
            switchViews(false)
            mWebView?.loadUrl(url)
        }
    }

    override fun setupNavigationMenus(
        navMenuItems: List<CatItem>,
        bottomNavMenuItems: List<CatItem>
    ) {
        menu!!.clear()
        for (category in navMenuItems) {
            menu!!.add(R.id.menu_container, category._id, Menu.NONE, category.title)
                .setCheckable(true)
                .setIcon(category.icon)
        }

        setDrawerEnabled((menu!!.size() > 1))

        val navigationView = findViewById<NavigationView>(R.id.navView)
        val menu = navigationView.menu //При старте приложения, автоматом выбираем 1 пункт меню
        val m = menu.getItem(0)
        if (m.hasSubMenu()) {
            val s = m.subMenu!!.getItem(0)
            onNavigationItemSelected(s)
            switchViews(false)
        } else {
            onNavigationItemSelected(m)
        }
        //MenuItem m = menu.getItem(0);//promo
        //MenuItem m = menu.getItem(1);
        //onNavigationItemSelected(m);
    }
}
