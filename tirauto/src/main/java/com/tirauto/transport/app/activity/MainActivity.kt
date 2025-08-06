//package com.tirauto.transport.app.activity
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
//import androidx.core.view.GravityCompat
//import androidx.drawerlayout.widget.DrawerLayout
//import com.google.android.material.snackbar.Snackbar
//import com.walhalla.landing.base.ActivityConfig
//import com.walhalla.landing.utility.NetUtils
//import com.walhalla.ui.DLog
//import com.walhalla.ui.plugins.Launcher
//import com.walhalla.ui.plugins.Module_U
//import com.walhalla.webview.ReceivedError
//import com.tirauto.transport.app.Cfg9
//import com.tirauto.transport.app.R
//import com.tirauto.transport.app.databinding.MainBinding
//
//import java.util.concurrent.TimeUnit
//
//class MainActivity : WebActivity5(), ActivityConfig {
//    protected var doubleBackToExitPressedOnce: Boolean = false
//    private var mHandler: Handler? = null
//
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//
//    private var mThread: Thread? = null
//
//    private var binding: MainBinding? = null
//    private var selectedListener: MyOnNavigationItemSelectedListener? = null
//
//
//    //private MainBinding bind;
//    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()
//        super.onCreate(savedInstanceState)
//
//        //Handler handler = new Handler(Looper.getMainLooper());
//        binding = MainBinding.inflate(layoutInflater)
//        setContentView(binding!!.root)
//        mHandler = Handler()
//        //Handler handler = new Handler(Looper.getMainLooper());
//        //presenter = new WPresenterImpl(handler, this);
//        selectedListener = MyOnNavigationItemSelectedListener(this)
//        val activityConfig: ActivityConfig = object : ActivityConfig {
//            override fun isProgressEnabled(): Boolean {
//                return Cfg9.cfg.isProgressEnabled
//            }
//
//            override fun isSwipeEnabled(): Boolean {
//                return Cfg9.cfg.isSwipeEnabled
//            }
//        }
//
//
//        //toolbar.setVisibility(View.GONE);
//        setSupportActionBar(binding!!.toolbar)
//        if (isProgressEnabled) {
//            binding!!.progressBar.visibility = View.VISIBLE
//            binding!!.progressBar.isIndeterminate = true
//        }
//        //        if (Validator.validate0()) {
////            finish();
////        }
//        //bind = MainBinding.inflate(getLayoutInflater());
//        mHandler = Handler()
//        //setContentView(bind.getRoot());
//        binding!!.toolbar.visibility = View.GONE
//
//
//        //        mAdView = 222(R.id.b1);
////        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
//// Start loading the ad in the background.
////        mAdView.loadAd(adRequest);
////        if (!Cfg.AD_MOB_ENABLED) {
////            mAdView.setVisibility(View.GONE);
////        }
//
//
////        if (savedInstanceState != null) {
////            return;
////        }
//        val header = binding!!.navView.getHeaderView(0)
//        val textView = header.findViewById<TextView>(R.id.version)
//        textView.text = DLog.getAppVersion(this)
//        setUpNavigationView()
//
//        //@@@ switchViews(true);
//        switchViews(false)
//
//        //        binding.childVW.mainCloseButton.setOnClickListener(v -> {
////            presenter.closeChild();
////        });
//        presenter0!!.newVariant(
//            "session-1", this, binding!!.webView,
//            binding!!.childVW.mainAdChildLayout(), binding!!.childVW.mainBrowserLayout()
//        )
//
//        //setDrawerEnabled((menu.size()>1));
//        setDrawerEnabled(false)
//
//        if (savedInstanceState == null) {
//            val menu = binding!!.navView.menu
//            //            MenuItem m = menu.getItem(0);
////            if (m.hasSubMenu()) {
////                MenuItem s = m.getSubMenu().getItem(0);
////                onNavigationItemSelected(s);
////                switchViews(false);
////            } else {
////                onNavigationItemSelected(m);
////            }
//            val m = menu.getItem(0) //promo
//            //MenuItem m = menu.getItem(1);
//            selectedListener!!.onNavigationItemSelected(m)
//            switchViews(false)
//        }
//
//        binding!!.webViewReloadUrl.setOnClickListener { v: View? ->
//            val menu = binding!!.navView.menu
//            //            MenuItem item = menu.getItem(0);
////            if (item.hasSubMenu()) {
////                MenuItem s = item.getSubMenu().getItem(0);
////                onNavigationItemSelected(s);
////                switchViews(false);
////            } else {
////                onNavigationItemSelected(item);
////            }
//            val item = menu.getItem(0) //promo
//
//            //            //MenuItem item = menu.getItem(1);
////            mm.onNavigationItemSelected(item);
//            selectedListener!!.onlyRefresh(item.itemId)
//        }
//        //swipeWebViewRef(binding.swipe, binding.webView);
//    }
//
//
//    //    RelativeLayout browserLayout = (RelativeLayout) findViewById(R.id.mainBrowserLayout);
//    //    RelativeLayout childLayout = (RelativeLayout) findViewById(R.id.mainAdChildLayout);
//    private fun setUpNavigationView() {
//        val menu = binding!!.navView.menu
//        binding!!.navView.setNavigationItemSelectedListener(selectedListener)
//        val item = menu.getItem(0)
//        if (item.hasSubMenu()) {
//            selectedListener!!.setupDrawer(this, item.subMenu)
//        } else {
//            selectedListener!!.setupDrawer(this, menu)
//        }
//    }
//
//    fun loadScreen(data: Intent) {
//        mThread = Thread({
//            try {
//                TimeUnit.MILLISECONDS.sleep(400)
//                mHandler!!.post {
//                    // update the main content by replacing fragments
//                    //                        Fragment fragment = getHomeFragment(currentTag);
//                    //                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    //                        //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    //                        fragmentTransaction.replace(R.id.container, fragment, CURRENT_TAG);
//                    //                        fragmentTransaction.commitAllowingStateLoss();
//                    startActivity(data)
//                }
//                //mThread.interrupt();
//            } catch (e: InterruptedException) {
////                Toast.makeText(this, e.getLocalizedMessage()
////                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
//            }
//        }, "m-thread")
//        if (!mThread!!.isAlive) {
//            try {
//                mThread!!.start()
//            } catch (r: Exception) {
//                DLog.handleException(r)
//            }
//        }
//    }
//
//
//    fun switchViews(b: Boolean) {
//        if (b) {
//            binding!!.contentFake.visibility = View.VISIBLE
//            binding!!.contentMain.visibility = View.GONE
//            //getSupportActionBar().setTitle("...");
//        } else {
//            binding!!.contentFake.visibility = View.GONE
//            binding!!.contentMain.visibility = View.VISIBLE
//            //getSupportActionBar().setTitle(R.string.app_name);
//        }
//    }
//
//    //base
//    override fun onPageStarted(url: String) {
////        if (isSwipeEnabled()) {
////            binding.swipe.setRefreshing(false);//modify if needed
////        }
//        if (isProgressEnabled) {
//            binding!!.progressBar.visibility = View.VISIBLE
//            binding!!.progressBar.isIndeterminate = true
//        }
//    }
//
//    override fun onPageFinished( /*WebView view, */
//                                 url: String
//    ) {
////        if (isSwipeEnabled()) {
////            binding.swipe.setRefreshing(false);
////        }
//
////        if (getSupportActionBar() != null) {
////            String m = view.getTitle();
////            if(TextUtils.isEmpty(m)){
////                getSupportActionBar().setSubtitle(m);
////            }
////        }
//
//        if (isProgressEnabled) {
//            binding!!.progressBar.visibility = View.GONE
//            binding!!.progressBar.isIndeterminate = false
//        }
//    }
//
//
//    override fun removeErrorPage() {
//        switchViews(false)
//    }
//
//
//    override fun setErrorPage(receivedError: ReceivedError) {
//        switchViews(true)
//    }
//
//
//    protected fun USE_HISTORY(): Boolean {
//        return true
//    }
//
//    override fun isProgressEnabled(): Boolean {
//        return Cfg9.cfg.isProgressEnabled
//    }
//
//    override fun isSwipeEnabled(): Boolean {
//        //Toast.makeText(this, "@@@@@@"+Cfg9.cfg.isSwipeEnabled(), Toast.LENGTH_SHORT).show();
//        return Cfg9.cfg.isSwipeEnabled
//    }
//
//
//    override fun onBackPressed() {
//        if (binding!!.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            binding!!.drawerLayout.closeDrawer(GravityCompat.START)
//            return
//        } else {
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed()
//                return
//            }
//            if (USE_HISTORY()) {
//                if (binding!!.webView.canGoBack()) {
//                    //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
//                    //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
//                    binding!!.webView.goBack()
//                    return
//                }
//            } else {
//                super.onBackPressed()
//            }
//        }
//        this.doubleBackToExitPressedOnce = true
//        backPressedToast()
//        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val itemId = item.itemId
//        if (itemId == R.id.action_about) {
//            //presenter.aboutDialog(this);
//            return true
//        } else if (itemId == R.id.action_privacy_policy) {
//            openBrowser(this, getString(R.string.url_privacy_policy))
//            return true
//        } else if (itemId == R.id.action_rate_app) {
//            Launcher.rateUs(this)
//            return true
//        } else if (itemId == R.id.action_share_app) {
//            Module_U.shareThisApp(this, null)
//            return true
//        } else if (itemId == R.id.action_discover_more_app) {
//            Module_U.moreApp(this)
//            return true
//        } else if (itemId == R.id.action_exit) {
//            this.finish()
//            return true
//        } else if (itemId == R.id.action_feedback) {
//            Module_U.feedback(this)
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun backPressedToast() {
//        Snackbar.make(
//            binding!!.coordinatorLayout,
//            R.string.press_again_to_exit,
//            Snackbar.LENGTH_LONG
//        ).setAction("Action", null).show()
//        //        View view = findViewById(android.R.id.content);
////        if (view == null) {
////            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
////        } else {
////            Snackbar.make(view, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("Action", null).show();
////        }
//    }
//
//    fun hideDrawer() {
//        binding!!.drawerLayout.closeDrawer(GravityCompat.START)
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        //NetUtils.isOnlineFire(this, binding.coordinatorLayout);
//        if (Cfg9.cfg.isCheckConnection) {
//            NetUtils.isOnlinePendulum(this)
//        }
//        //NetUtils.isOnlineSignal(this);
//    }
//
//
//    fun loadUrl(url: String?) {
//        binding!!.webView.loadUrl(url!!)
//    }
//
//
//    fun setDrawerEnabled(enabled: Boolean) {
//        val toggle = ActionBarDrawerToggle(
//            this,
//            binding!!.drawerLayout,
//            binding!!.toolbar,
//            R.string.navigation_drawer_open,
//            com.walhalla.landing.R.string.navigation_drawer_close
//        )
//        binding!!.drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        val lockMode =
//            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
//        binding!!.drawerLayout.setDrawerLockMode(lockMode)
//        toggle.isDrawerIndicatorEnabled = enabled
//    }
//}