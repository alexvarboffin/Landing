package com.microchecker.app.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.snackbar.Snackbar
import com.microchecker.app.Cfg9
import com.microchecker.app.R
import com.microchecker.app.databinding.MainBinding
import com.walhalla.landing.base.ActivityConfig
import com.walhalla.landing.base.WViewPresenter
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U.feedback
import com.walhalla.ui.plugins.Module_U.moreApp
import com.walhalla.ui.plugins.Module_U.shareThisApp
import com.walhalla.webview.ReceivedError
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private var mHandler: Handler? = null
    private var mThread: Thread? = null

    private var binding: MainBinding? = null
    private var selectedListener: MyOnNavigationItemSelectedListener? = null
    private var presenter1: WViewPresenter? = null


    //private MainBinding bind;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        mHandler = Handler()
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        selectedListener = MyOnNavigationItemSelectedListener(this)
        val activityConfig: ActivityConfig = object : ActivityConfig {
            override fun isProgressEnabled(): Boolean {
                return this@MainActivity.isProgressEnabled
            }

            override fun isSwipeEnabled(): Boolean {
                return this@MainActivity.isSwipeEnabled
            }
        }
        presenter1 = WViewPresenter(this, activityConfig)
        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding!!.toolbar)
        //        Handler handler = new Handler(Looper.getMainLooper());
//        // Запланируйте выполнение закрытия приложения через 4 минуты (240000 миллисекунд)
//        // Закроет активити и завершит приложение
//        handler.postDelayed(this::finish, 360000); // 4 минуты в миллисекундах
//        if(Validator.validate0()){
//            finish();
//        }
        //bind = MainBinding.inflate(getLayoutInflater());
        mHandler = Handler()
        //setContentView(bind.getRoot());
        binding!!.toolbar.visibility = View.GONE


        //        mAdView = 222(R.id.b1);
//        com.google.android.gms.ads.AdRequest adRequest = new AdRequest.Builder().build();
// Start loading the ad in the background.
//        mAdView.loadAd(adRequest);
//        if (!Cfg9.AD_MOB_ENABLED) {
//            mAdView.setVisibility(View.GONE);
//        }


//        if (savedInstanceState != null) {
//            return;
//        }
        setUpNavigationView()


        //@@@ switchViews(true);
        switchViews(false)

        generateViews(this, binding!!.contentMain)

        //setDrawerEnabled((menu.size()>1));
        setDrawerEnabled(true)
        if (savedInstanceState == null) {
            val menu = binding!!.navView.menu
            //            MenuItem m = menu.getItem(0);
//            if (m.hasSubMenu()) {
//                MenuItem s = m.getSubMenu().getItem(0);
//                onNavigationItemSelected(s);
//                switchViews(false);
//            } else {
//                onNavigationItemSelected(m);
//            }
            val m = menu.getItem(0) //promo
            //MenuItem m = menu.getItem(1);
            switchViews(false)
            selectedListener!!.onNavigationItemSelected(m)
        }
    }


    private fun setUpNavigationView() {
        val menu = binding!!.navView.menu
        binding!!.navView.setNavigationItemSelectedListener(selectedListener)
        val item = menu.getItem(0)
        if (item.hasSubMenu()) {
            selectedListener!!.setupDrawer(this, item.subMenu)
        } else {
            selectedListener!!.setupDrawer(this, menu)
        }
    }

    fun loadScreen(data: Intent) {
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


    fun switchViews(b: Boolean) {
        if (b) {
            binding!!.contentFake.root.visibility = View.VISIBLE
            binding!!.contentMain.visibility = View.INVISIBLE
            //getSupportActionBar().setTitle("...");
        } else {
            binding!!.contentFake.root.visibility = View.INVISIBLE
            binding!!.contentMain.visibility = View.VISIBLE
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    //base
    override fun onPageStarted(url: String?) {
        super.onPageStarted(url)
        if (isProgressEnabled) {
            binding!!.progressBar.visibility = View.VISIBLE
            binding!!.progressBar.isIndeterminate = true
        }
    }

    override fun onPageFinished( /*WebView view, */
                                 url: String?
    ) {
        super.onPageFinished(url)
        if (isProgressEnabled) {
            binding!!.progressBar.visibility = View.GONE
            binding!!.progressBar.isIndeterminate = false
        }
    }

    override fun removeErrorPage() {
        switchViews(false)
    }

    override fun setErrorPage(receivedError: ReceivedError) {
        switchViews(true)
    }

    override fun USE_HISTORY(): Boolean {
        return true
    }

    override fun isProgressEnabled(): Boolean {
        return Cfg9.cfg.isProgressEnabled
    }

    override fun isSwipeEnabled(): Boolean {
        return Cfg9.cfg.isSwipeEnabled
    }


    override fun onBackPressed() {
        if (binding!!.drawerLayout != null && binding!!.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding!!.drawerLayout.closeDrawer(GravityCompat.START)
            return
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            if (USE_HISTORY()) {
                if (dynamicWebView.webView.canGoBack()) {
                    //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                    //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                    dynamicWebView.webView.goBack()
                    return
                }
            } else {
                super.onBackPressed()
            }
        }
        this.doubleBackToExitPressedOnce = true
        backPressedToast()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }


    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_about) {
            presenter1!!.aboutDialog(this)
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

    override fun backPressedToast() {
        Snackbar.make(
            binding!!.coordinatorLayout,
            R.string.press_again_to_exit,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null).show()
        //        View view = findViewById(android.R.id.content);
//        if (view == null) {
//            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
//        } else {
//            Snackbar.make(view, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("Action", null).show();
//        }
    }

    fun hideDrawer() {
        binding!!.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun setDrawerEnabled(enabled: Boolean) {
        val toggle = ActionBarDrawerToggle(
            this,
            binding!!.drawerLayout,
            binding!!.toolbar,
            com.walhalla.landing.R.string.navigation_drawer_open,
            com.walhalla.landing.R.string.navigation_drawer_close
        )
        binding!!.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val lockMode =
            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        binding!!.drawerLayout.setDrawerLockMode(lockMode)
        toggle.isDrawerIndicatorEnabled = enabled
    }

    override fun onResume() {
        super.onResume()

        //        int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.GET_ACCOUNTS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.GET_ACCOUNTS)) {
//
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.GET_ACCOUNTS},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//        }
//        String possibleEmail="";
//        try{
//            possibleEmail += "************* Get Registered Gmail Account *************\n\n";
//            AccountManager accountManager = AccountManager.get(this);
//            //Account[] accounts = accountManager.getAccountsByType("com.google");
//            Account[] accounts = accountManager.getAccounts();
//            //Account[] accounts = accountManager.getAccountsByType(accountType);
//            for (Account account : accounts) {
//
//                possibleEmail += " --> "+account.name+" : "+account.type+" , \n";
//                possibleEmail += " \n\n";
//
//            }
//            DLog.d("@@@@@" + Arrays.toString(accounts));
//        }catch (Exception e){
//            Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        DLog.d("mails: " + possibleEmail);
    }
}