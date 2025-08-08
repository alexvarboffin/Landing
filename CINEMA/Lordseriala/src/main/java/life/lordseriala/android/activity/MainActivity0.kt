package life.lordseriala.android.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.UWView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView

import life.lordseriala.android.Cfg9


import com.walhalla.landing.Const
import com.walhalla.landing.activity.AboutActivity
import com.walhalla.landing.activity.WebActivity
import com.walhalla.landing.base.ActivityConfig
import com.walhalla.landing.base.WViewPresenter
import com.walhalla.ui.DLog.getAppVersion

import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U.feedback
import com.walhalla.ui.plugins.Module_U.isFromGooglePlay
import com.walhalla.ui.plugins.Module_U.moreApp
import com.walhalla.ui.plugins.Module_U.shareThisApp
import com.walhalla.webview.ChromeView
import com.walhalla.webview.ReceivedError
import life.lordseriala.android.BuildConfig
import life.lordseriala.android.R
import life.lordseriala.android.databinding.ActivityMainBinding
import java.util.Calendar
import java.util.concurrent.TimeUnit

//import BaseSimpleActivity;
//import BuildConfig;
//import ChromeView;
//import Const;
//import Custobinding.webviewClient;
//import MultiplePagination;


class MainActivity0 : WebActivity(), ChromeView, NavigationView.OnNavigationItemSelectedListener {


    //private progressBar progressBar;
    //private AdView mAdView;
    private var mHandler: Handler? = null
    private var mThread: Thread? = null


    private var menu: Menu? = null
    private var toggle: ActionBarDrawerToggle? = null

    private val url = "https://lordseriala.life/"


    //    private final GConfig aaa = new GConfig(
    //            false, false, UrlSaver.FIRST,
    //            true, false
    //    );

    override fun onResume() {
        super.onResume()
        binding.swipe.isRefreshing = false
    }


    //private Handler mHandler;
    protected lateinit var binding: ActivityMainBinding
    protected var presenter: WViewPresenter? = null
    private var activityConfig: ActivityConfig? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mHandler = new Handler();
        //Handler handler = new Handler(Looper.getMainLooper());
        //presenter = new WPresenterImpl(handler, this);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //toolbar.setVisibility(View.GONE);
        setSupportActionBar(binding.toolbar)
        activityConfig = getActivityConfig()
        if (presenter == null) {
            presenter = WViewPresenter(this, activityConfig!!)
        }
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
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()


//        if (savedInstanceState != null) {
//            return;
//        }


        //progressBar = findViewById(R.id.//progressBar);


        // Восстанавливаем состояние WebView, если оно есть
        if (savedInstanceState != null) {
            if (binding.webview == null) {
                if (BuildConfig.DEBUG) {
                    Toast.makeText(this, "@@@@", Toast.LENGTH_SHORT).show()
                }
                generateViews(this, binding.contentMain)
            }
            binding.webview?.restoreState(savedInstanceState)
        } else {
            if (BuildConfig.DEBUG) {
                Toast.makeText(
                    this,
                    "Инициализация WebView, если savedInstanceState == null",
                    Toast.LENGTH_SHORT
                ).show()
            }
            generateViews(this, binding.contentMain)
            if (!TextUtils.isEmpty(url)) {
                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show()
                switchViews(false)
                binding.webview?.loadUrl(url)
            }
        }

        setUpNavigationView()

        //@@@ switchViews(true);
        switchViews(false)


        setDrawerEnabled(false)
        swipeWebViewRef(binding.swipe, binding.webview)

        //if (savedInstanceState == null) {
        //presenter = new ResPresenter(this, this);


        //}

//        //@if (!rotated()) {
//            presenter.init0(this);
//        //@}
    }

    fun getActivityConfig(): ActivityConfig {
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
        menu = binding.navView.menu
        binding.navView.setNavigationItemSelectedListener(this)


        //        MenuItem item = menu.getItem(0);
//        if (item.hasSubMenu()) {
//            pagination.setupDrawer(this, item.getSubMenu());
//        } else {
//            pagination.setupDrawer(this, menu);
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
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
            //p0!!.onNavigationItemSelected("" + item.title.toString())
        }
        if (intent != null) {
            loadScreen(intent)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    var doubleBackToExitPressedOnce: Boolean = false


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    //@@@@
    override fun onPageStarted(url: String?) {
        presenter!!.hideSwipeRefreshing()
        if (Cfg9.cfg.isSwipeEnabled) {
            binding.swipe.isRefreshing = false //modify if needed
        }
        showProgressbar()
    }

    private fun showProgressbar() {
        if (Cfg9.cfg.isProgressEnabled) {
            binding.mpb.visibility = View.VISIBLE
            binding.mpb.isIndeterminate = true
        }
    }


    override fun removeErrorPage() {
    }

    override fun setErrorPage(receivedError: ReceivedError) {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем состояние WebView
        binding.webview?.saveState(outState)
    }

    private fun generateViews(context: Context, view: ViewGroup) {

        presenter0.a123(this, binding.webview)
    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
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
            }
        }

        backPressedToast()
        this.doubleBackToExitPressedOnce = true
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1800)
    }

    fun USE_HISTORY(): Boolean {
        return false
    }


//    override fun setupNavigationMenus(
//        navMenuItems: List<CatItem>,
//        bottomNavMenuItems: List<CatItem>
//    ) {
//        menu!!.clear()
//        for (category in navMenuItems) {
//            menu!!.add(R.id.menu_container, category._id, Menu.NONE, category.title)
//                .setCheckable(true)
//                .setIcon(category.icon)
//        }
//
//        setDrawerEnabled((menu!!.size() > 1))
//
//        val navigationView = findViewById<NavigationView>(R.id.navView)
//        val menu = navigationView.menu //При старте приложения, автоматом выбираем 1 пункт меню
//        val m = menu.getItem(0)
//        if (m.hasSubMenu()) {
//            val s = m.subMenu!!.getItem(0)
//            onNavigationItemSelected(s)
//            switchViews(false)
//        } else {
//            onNavigationItemSelected(m)
//        }
//        //MenuItem m = menu.getItem(0);//promo
//        //MenuItem m = menu.getItem(1);
//        //onNavigationItemSelected(m);
//    }

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
        } else if (itemId == R.id.action_refresh) {
            presenter!!.refreshWV()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPageFinished( /*WebView view, */
                                 url: String?
    ) {
        binding.swipe.isRefreshing = false

        //        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (Cfg9.cfg.isSwipeEnabled) {
            binding.swipe.isRefreshing = false
        }

        //        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
        if (Cfg9.cfg.isProgressEnabled) {
            binding.mpb.visibility = View.GONE
            binding.mpb.isIndeterminate = false
        }
    }


    fun switchViews(b: Boolean) {
        if (b) {
            binding.contentFake.root.visibility = View.VISIBLE
            binding.contentMain.visibility = View.GONE
            //getSupportActionBar().setTitle("...");
        } else {
            binding.contentFake.root.visibility = View.GONE
            binding.contentMain.visibility = View.VISIBLE
            //getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    fun setDrawerEnabled(enabled: Boolean) {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val lockMode =
            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        binding.drawerLayout.setDrawerLockMode(lockMode)
        toggle.isDrawerIndicatorEnabled = enabled
    } //bpt
    //    View coordinatorLayout = findViewById(R.id.coordinatorLayout);
    //        Snackbar.make(coordinatorLayout, getString(R.string.press_again_to_exit), Snackbar.LENGTH_LONG)
    //            .setAction("Action", null).show();


    companion object {
        fun aboutDialog(context: Context) {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            //&#169; - html
            val title = "\u00a9 " + year + " " + context.getString(R.string.play_google_pub)
            val mView = LayoutInflater.from(context).inflate(R.layout.about, null)
            val dialog = AlertDialog.Builder(context)
                .setTitle(null)
                .setCancelable(true)
                .setIcon(null) //.setNegativeButton(R.string.action_discover_more_app, (dialog1, which) -> moreApp(context))
                .setPositiveButton(android.R.string.ok, null)
                .setView(mView)
                .create()
            mView.setOnClickListener { v: View? -> dialog.dismiss() }
            val textView = mView.findViewById<TextView>(R.id.about_version)
            textView.text = getAppVersion(context)
            val _c = mView.findViewById<TextView>(R.id.about_copyright)
            _c.text = title
            val logo = mView.findViewById<ImageView>(R.id.aboutLogo)
            logo.setOnLongClickListener { v: View? ->
                var _o = "[+]gp->" + isFromGooglePlay(mView.context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    _o = _o + ", category->" + mView.context.applicationInfo.category
                }
                _c.text = _o
                false
            }
            //dialog.setButton();
            dialog.show()
        }
    }
}
