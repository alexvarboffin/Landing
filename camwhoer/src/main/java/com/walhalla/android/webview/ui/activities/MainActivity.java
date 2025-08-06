package com.walhalla.android.webview.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.walhalla.android.R;
import com.walhalla.android.webview.UIWebView.CustomWebChromeClient;
import com.walhalla.android.webview.fragments.F_Console;
import com.walhalla.android.webview.fragments.F_Network;
import com.walhalla.android.webview.fragments.F_WebView;
import com.walhalla.android.webview.fragments.Outputable;
import com.walhalla.android.webview.fragments.SettingsPreferenceFragment;
import com.walhalla.android.webview.utils.FragmentNavigator;
import com.walhalla.android.webview.utils.MiuiUtils;
import com.walhalla.android.webview.utils.PreferencesKeys;
import com.walhalla.utils.DLog;

import java.util.List;

public class MainActivity extends ThemedActivity
        implements
        FragmentNavigator.FragmentFactory,
        Outputable, CustomWebChromeClient.Callback,
        NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private FragmentNavigator mFragmentNavigator;

    private F_WebView mInstallerFragment;

    private boolean mIsNavigationEnabled = true;
    private static final String TAG = "@@@";
    private int current;

    //private BillingManager mBillingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mBillingManager = DefaultBillingManager.getInstance(this);

        //TODO is this ok?
        //DefaultBackupManager.getInstance(this);

        showMiuiWarning();


        mBottomNavigationView = findViewById(R.id.bottomnav_main);
        //mBottomNavigationView.pageLimit
        mBottomNavigationView.setOnItemSelectedListener(this);

        mFragmentNavigator = new FragmentNavigator(savedInstanceState, getSupportFragmentManager(), R.id.container_main, this);
        mInstallerFragment = mFragmentNavigator.findFragmentByTag("installer");
        if (savedInstanceState == null)
            mFragmentNavigator.switchTo("installer");

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getData() != null) {
            deliverActionViewUri(intent.getData());
            getIntent().setData(null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_VIEW.equals(intent.getAction()) && intent.getData() != null) {
            deliverActionViewUri(intent.getData());
        }
    }

    private void deliverActionViewUri(Uri uri) {
        if (!mIsNavigationEnabled) {
            Toast.makeText(this, R.string.main_navigation_disabled, Toast.LENGTH_SHORT).show();
            return;
        }

        String[] aaa = new String[]{
                "installer", "backup", "console", "settings"
        };
        for (int i = 0; i < aaa.length; i++) {
            mBottomNavigationView.getMenu().getItem(i).setChecked(true);
            mFragmentNavigator.switchTo(aaa[i]);
        }

        //getInstallerFragment().handleActionView(uri);
    }

    private void showMiuiWarning() {
        if (MiuiUtils.isMiui() && !PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PreferencesKeys.MIUI_WARNING_SHOWN, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void setNavigationEnabled(boolean enabled) {
        mIsNavigationEnabled = enabled;

        for (int i = 0; i < mBottomNavigationView.getMenu().size(); i++) {
            mBottomNavigationView.getMenu().getItem(i).setEnabled(enabled);
        }
        mBottomNavigationView.animate()
                .alpha(enabled ? 1f : 0.4f)
                .setDuration(300)
                .start();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_installer) {
            mFragmentNavigator.switchTo("installer");
        } else if (itemId == R.id.menu_backup) {
            mFragmentNavigator.switchTo("backup");
        } else if (itemId == R.id.menu_console) {
            mFragmentNavigator.switchTo("console");
        } else if (itemId == R.id.menu_settings) {
            mFragmentNavigator.switchTo("settings");
        }
        return true;
    }

    @Override
    public Fragment createFragment(String tag) {
        switch (tag) {
            case "installer":
                return getInstallerFragment();
            case "backup":
                return new F_Network();
            case "console":
                return new F_Console();
            case "settings":
                return new SettingsPreferenceFragment();
        }

        throw new IllegalArgumentException("Unknown fragment tag: " + tag);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentNavigator.writeStateToBundle(outState);
    }

    private F_WebView getInstallerFragment() {
        if (mInstallerFragment == null)
            mInstallerFragment = new F_WebView();
        return mInstallerFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mBillingManager.refresh();
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
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments.size() > 1) {
                F_Network fragment = (F_Network) fragments.get(1);
                fragment.clearLog();
            }
        }
    }

    @Override
    public void onConsoleMessage(ConsoleMessage message) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 2) {
            DLog.d("{Console} " + message.message()
                    + " -- From line " + message.lineNumber()
                    + " of " + message.sourceId());
            Fragment fragment = fragments.get(2);
            if (fragment instanceof F_Console) {
                ((F_Console) fragment).println(message);
            }
        }
    }
}
