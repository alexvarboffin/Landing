package com.betapsbookmakersbonus.client.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.betapsbookmakersbonus.client.BetApsCfg9
import com.betapsbookmakersbonus.client.MyOnNavigationItemSelectedListener
import com.betapsbookmakersbonus.client.R
import com.betapsbookmakersbonus.client.databinding.MainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.walhalla.landing.activity.WebActivity
import com.walhalla.landing.base.ActivityConfig
import com.walhalla.landing.base.WViewPresenter
import com.walhalla.landing.utility.NetUtils
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U.feedback
import com.walhalla.ui.plugins.Module_U.moreApp
import com.walhalla.ui.plugins.Module_U.shareThisApp
import com.walhalla.webview.ReceivedError
import java.util.concurrent.TimeUnit
import androidx.core.view.get

open class MainActivity : WebActivity() {
    private var doubleBackToExitPressedOnce: Boolean = false
    private var mHandler: Handler? = null


    private var presenter: WViewPresenter? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    private var mThread: Thread? = null

    private lateinit var binding: MainBinding

    //private ActivityCollapsingToolbarBinding binding;
    //private ActivityGlassCollapsinToolbarBinding binding;
    //private ActivityGlassToolbarBinding binding;
    private var mm: MyOnNavigationItemSelectedListener? = null


    //private MainBinding bind;
    //    @Override
    //    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        presenter.onActivityResult(requestCode, resultCode, data);
    //    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);

        //binding = ActivityCollapsingToolbarBinding.inflate(getLayoutInflater());
        //binding = ActivityGlassCollapsinToolbarBinding.inflate(getLayoutInflater());

        //binding = ActivityGlassToolbarBinding.inflate(getLayoutInflater());
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mHandler = Handler()
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        mm = MyOnNavigationItemSelectedListener(this)

        val activityConfig: ActivityConfig = object : ActivityConfig {
            override val isProgressEnabled: Boolean
                get() = BetApsCfg9.cfg.isProgressEnabled

            override val isSwipeEnabled: Boolean
                get() = BetApsCfg9.cfg.isSwipeEnabled
        }

        presenter = WViewPresenter(this, activityConfig)
        presenter!!.dynamicWebView.getParent().buttonMenu.visibility = View.GONE

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
        if (BetApsCfg9.cfg.isToolbarEnabled) {
            binding!!.toolbar.visibility = View.VISIBLE
            setSupportActionBar(binding!!.toolbar)
            if (supportActionBar != null) {
                //getSupportActionBar().setTitle(R.string.app_name);
                supportActionBar!!.setTitle("")
                //binding.toolbar.setTitleCentered(true);
                //getSupportActionBar().setSubtitle(R.string.app_subtitle);
            }
        } else {
            binding!!.toolbar.visibility = View.GONE
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
        setUpNavigationView(binding.navView)


        //@@@ switchViews(true);
        switchViews(false)


            //if (savedInstanceState == null) {
            presenter!!.generateViews(this, binding.contentMain, presenter0)
        //}


        //setDrawerEnabled((menu.size()>1));
        setDrawerEnabled(false)

        //При старте приложения, автоматом выбираем 1 пункт меню
        if (savedInstanceState == null) {
            val menu = binding.navView.menu
            //            MenuItem m = menu.getItem(0);
//            if (m.hasSubMenu()) {
//                MenuItem s = m.getSubMenu().getItem(0);
//                onNavigationItemSelected(s);
//                switchViews(false);
//            } else {
//                onNavigationItemSelected(m);
//            }
            val m = menu[0] //promo
            //MenuItem m = menu.getItem(1);
            mm!!.onNavigationItemSelected(m)
            switchViews(false)
        }


        //        binding.@@@@@@(v -> {
//            final Menu menu = binding.navView.getMenu();
        /*            MenuItem m = menu . getItem (0);
                    if (m.hasSubMenu()) {
            * /                MenuItem s = m.getSubMenu().getItem(0);
            * /                onNavigationItemSelected(s);
            * /                switchViews(false);
            * /
        } else {
            * /                onNavigationItemSelected(m);
            * /
        } * /
//            MenuItem m = menu.getItem(0);//promo
//            //MenuItem m = menu.getItem(1);
//            mm.onNavigationItemSelected(m);
        /**/            loadUrl(url)
        * /        Toast.makeText(this, ""+url, Toast.LENGTH_SHORT).show(); */
//        });


//        binding.fab.setOnClickListener(v -> {
//            Toast.makeText(this, "@@", Toast.LENGTH_SHORT).show();
//        });
    }


    private fun setUpNavigationView(navView: NavigationView) {
        val menu = navView.menu
        navView.setNavigationItemSelectedListener(mm)
        val item = menu[0]
        if (item.hasSubMenu()) {
            mm!!.setupDrawer(this, item.subMenu)
        } else {
            mm!!.setupDrawer(this, menu)
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
            binding.contentFake.visibility = View.VISIBLE
            binding.contentMain.visibility = View.GONE
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.visibility = View.GONE
            binding.contentMain.visibility = View.VISIBLE
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    //base
    override fun onPageStarted(url: String?) {
        presenter!!.hideSwipeRefreshing()
        if (BetApsCfg9.cfg.isProgressEnabled) {
            binding!!.progressBar.visibility = View.VISIBLE
            binding!!.progressBar.isIndeterminate = true
        }
    }

    override fun onPageFinished( /*WebView view, */
                                 url: String?
    ) {
        presenter!!.hideSwipeRefreshing()

        //        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (BetApsCfg9.cfg.isProgressEnabled) {
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


    protected fun USE_HISTORY(): Boolean {
        return true
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
                if (presenter!!.canGoBack()) {
                    //Toast.makeText(this, "##" + (mWebBackForwardList.getCurrentIndex()), Toast.LENGTH_SHORT).show();
                    //historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
                    presenter!!.goBack()
                    return
                }
                //                else {
//                    Toast.makeText(this, "@@", Toast.LENGTH_SHORT).show();
//                    return;
//                }
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
            presenter!!.aboutDialog(this)
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


    override fun onResume() {
        super.onResume()
        //NetUtils.isOnlineFire(this, binding.coordinatorLayout);
        //NetUtils.isOnlinePendulum(this);
        NetUtils.isOnlineSignal(this)
        //TooltipUtils.tapTargetView(this);
    }


    fun loadUrl(url: String) {
        presenter!!.loadUrl(url)
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
}