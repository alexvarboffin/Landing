package com.walhalla.android.webview.ui.activities;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;


import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.walhalla.android.R;

import com.walhalla.android.databinding.ActivityMainV0Binding;

import com.walhalla.android.webview.ViewPagerAdapter;
import com.walhalla.android.webview.fragments.F_WebView;
import com.walhalla.android.webview.fragments.F_Console;
import com.walhalla.android.webview.fragments.F_Network;
import com.walhalla.android.webview.fragments.Outputable;
import com.walhalla.android.webview.UIWebView.CustomWebChromeClient;
import com.walhalla.android.webview.fragments.SettingsPreferenceFragment;
import com.walhalla.android.webview.viewmodel.ConMsg;
import com.walhalla.android.webview.widget.RememberEditText;

import com.walhalla.ui.plugins.Launcher;
import com.walhalla.utils.Module_U;
import com.walhalla.webview.WVTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainAct extends ThemedActivity
        implements Outputable, CustomWebChromeClient.Callback, NavigationBarView.OnItemSelectedListener {

    private static final String TAG = "@@@";

    private boolean doubleBackToExitPressedOnce;

    private ActivityMainV0Binding mBinding;
    private ViewPagerAdapter mSectionsPagerAdapter;

    private F_WebView mBrowserFragment;

    private final String APP_PREFERENCES = "my.settings";
    private static final String APP_PREFERENCES_AGREE = "key.agree";

    private int current;


//    private SharedPreferences mySharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("Crash").setOnMenuItemClickListener(
//                new MenuItem.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        throw new RuntimeException("Test Crash"); // Force a crash
//                    }
//                }
//        );
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        //            case R.id.action_refresh:
//                //requestData();
//                return false;
//
//            case R.id.action_share:
//                //WVTools.shareString(WVTools.getDataText(this.mDatabaseManager, dataLocal, this), this);
//                return false;
//
//            case R.id.action_copy:
//                //WVTools.copyToClipboard(WVTools.getDataText(this.mDatabaseManager, dataLocal, this), this);
//                return false;
        if (itemId == R.id.action_about) {
            Module_U.aboutDialog(this);
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

//            case R.id.action_exit:
//                mNotificationManager.cancel(NOTIFICATION_ID);
//                this.finish();
//                return true;

//            case R.id.action_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                return true;
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_PROGRESS);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_v0);
        setSupportActionBar(mBinding.toolbar);
        mBinding.bottomnavMain.setOnItemSelectedListener(this);
        getSupportActionBar().setTitle(R.string.app_name);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        //Disable JavaScript

        int pos = 0;

        mSectionsPagerAdapter = new ViewPagerAdapter(this);


//        mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        int i = (Const.DEBUG) ? 0 : mySharedPreferences.getInt(APP_PREFERENCES_AGREE, 0);


        mBrowserFragment = F_WebView.newInstance("");
        mSectionsPagerAdapter.addFragment(mBrowserFragment);
        mSectionsPagerAdapter.addFragment(new F_Network());
        mSectionsPagerAdapter.addFragment(new F_Console());
        mSectionsPagerAdapter.addFragment(new SettingsPreferenceFragment());
//        if (i == 0) {
//            if (Const.DEBUG) {
//                pos = 1;
//            }
//            //mSectionsPagerAdapter.addFragment(FirstFragment.newInstance(0), "s1");
//            mSectionsPagerAdapter.addFragment(SecondFragment.newInstance(1), "out");
//        } else if (i == 1) {
//            //mSectionsPagerAdapter.addFragment(new Fragment(), "s1");
//            mSectionsPagerAdapter.addFragment(new Fragment(), "s2");
//            pos = 1;
//        }

        List<String> titles = new ArrayList<>();
        titles.add("WebView");
        titles.add("Network");
        titles.add("Console");
        titles.add("Settings");

        // Set up the ViewPager with the sections adapter.
        //mBinding.tabs.setupWithViewPager(mBinding.viewPager2);

//        mBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                current = tab.getPosition();
//                invalidateFragmentMenus(MainAct.this, current); //api v2
//                //DLog.d("" + tab.getText() + " " + mSelected);
//
//                //if (getActivity() != null) {
//                WVTools.hideKeyboardFrom(MainAct.this,
//                        //getActivity().findViewById(R.id.et_user_input)
//                        getWindow().getDecorView()
//                );
//                RememberEditText view = findViewById(R.id.input);
//                if (view != null) {
//                    view.disMissOrUpdatePopupWindow();
//                }
//                //}
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        mBinding.viewPager2.setAdapter(mSectionsPagerAdapter);
//
//        new TabLayoutMediator(mBinding.tabs, mBinding.container,
//                (tab, position) -> tab.setText(titles.get(position))).attach();
//        mBinding.viewPager2.setOffscreenPageLimit(
//                (mBinding.tabs.getTabCount() > 0) ? mBinding.tabs.getTabCount() : ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
//        );

        mBinding.viewPager2.setOffscreenPageLimit(titles.size());
        mBinding.viewPager2.setUserInputEnabled(false);

//        mBinding.container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //Log.i(TAG, "onPageSelected: " + position);
//                current = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view
//                -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

//        nextScreen(i);

    }

    private void invalidateFragmentMenus(Activity activity, int position) {
        //DLog.d("@invalidateFragmentMenus " + " " + position);
//        v1
//        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
//            mPagerAdapter.getItem(i).setHasOptionsMenu(i == position);
//        }
//        if (getActivity() != null) {
//            getActivity().invalidateOptionsMenu(); //or respectively its support method.
//        }
        for (int i = 0; i < mSectionsPagerAdapter.getItemCount(); i++) {
            //int item = mBinding.viewpager.getCurrentItem();
            mSectionsPagerAdapter.getItem(i).setHasOptionsMenu(/*i == item && */i == position);
            //DLog.d("{000000} " + i + " " + position);
        }
        if (activity != null) {
            activity.invalidateOptionsMenu(); //or respectively its support method.
        }
    }

    private int randomize() {
        int min = 0;
        int max = 9;

        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    public void nextScreen(int sectionNumber) {
//        if (mBinding.container.getCurrentItem() != sectionNumber) {
//            mBinding.container.setCurrentItem(sectionNumber);
//        }
//        if (!Const.DEBUG) {
//            if (sectionNumber <= 2) {
//                SharedPreferences.Editor editor = mySharedPreferences.edit();
//                editor.putInt(APP_PREFERENCES_AGREE, sectionNumber);
//                editor.apply();
//            }
//        }
//
//        YandexMetrica.reportEvent("next-screen-" + sectionNumber);
//    }


    //Browser Listener
    @Override
    public void onBackPressed() {

//        if (fullLayout.isDrawerOpen(GravityCompat.START)) {
//            fullLayout.closeDrawer(GravityCompat.START);
//        } else {
        if (mBrowserFragment != null && mBrowserFragment.canGoBack()) {
            mBrowserFragment.getWebView().goBack();
        } else {


            //Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
            //Pressed back => return to home screen
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(count > 0);
            }
            if (count > 0) {
                getSupportFragmentManager()
                        .popBackStack(getSupportFragmentManager()
                                        .getBackStackEntryAt(0).getId(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {//count == 0


//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                //super.onBackPressed();


                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                backPressedToast();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1500);

            }


            /*
            //Next/Prev Navigation
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Leaving this App?")
                        .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else
            {
            super.onBackPressed();
            }
            */

        }
    }

    @Override
    public void printLogLine(String data) {
        if (current != 1) {
            final List<Fragment> fragments = getSupportFragmentManager().getFragments();
            Log.i(TAG, "{LogLine} " + data + " " + fragments.size());
            if (fragments.size() > 1) {
                F_Network fragment = (F_Network) fragments.get(1);
                fragment.println(data);
            }
        }
    }

    @Override
    public void clearLogRequest() {
        if (current != 1) {
            final List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments.size() > 1) {
                Fragment fragment = fragments.get(1);
                if (fragment instanceof F_Network) {
                    ((F_Network) fragment).clearLog();
                }
            }
            if (fragments.size() > 2) {
                Fragment fragment = fragments.get(2);
                if (fragment instanceof F_Console) {
                    ((F_Console) fragment).clearLog();
                }
            }
        }
    }

    @Override
    public void onConsoleMessage(ConsoleMessage m) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 2) {
            Fragment fragment = fragments.get(2);
            if (fragment instanceof F_Console) {
                ConMsg m1 = ConMsg.wrap(m);
                ((F_Console) fragment).println(m1);
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_installer) {
            switchTo(0);
        } else if (itemId == R.id.menu_backup) {
            switchTo(1);
        } else if (itemId == R.id.menu_console) {
            switchTo(2);
        } else if (itemId == R.id.menu_settings) {
            switchTo(3);
        }
        return true;
    }

    private void switchTo(int position) {
        current = position;
        invalidateFragmentMenus(MainAct.this, current); //api v2
        //DLog.d("" + tab.getText() + " " + mSelected);

        //if (getActivity() != null) {
        WVTools.hideKeyboardFrom(MainAct.this,
                //getActivity().findViewById(R.id.et_user_input)
                getWindow().getDecorView()
        );
        RememberEditText view = findViewById(R.id.etInput);
        if (view != null) {
            view.disMissOrUpdatePopupWindow();
        }
        //}
        mBinding.viewPager2.setCurrentItem(position);
    }

    private static class ZZ extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume: ");
//        if (getSupportActionBar() != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            // Hide the status bar
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            // Hide the action bar
//            getSupportActionBar().hide();
//        } else {
//            // Hide the status bar
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            );
//            // Hide the action bar
//            if (getActionBar() != null) {
//                getActionBar().hide();
//            }
//        }
    }
}
