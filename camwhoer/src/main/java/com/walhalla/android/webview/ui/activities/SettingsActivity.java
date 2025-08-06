package com.walhalla.android.webview.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.walhalla.android.R;
import com.walhalla.android.webview.fragments.SettingsPreferenceFragment;

import com.walhalla.ui.plugins.Launcher;
import com.walhalla.utils.Module_U;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            homeScreen();
        }
    }

    public void homeScreen() {
        Fragment fragment = new SettingsPreferenceFragment();

        //Fragment fragment = BlockListFragment.newInstance("");
        getSupportFragmentManager()

                .beginTransaction()
                //.addToBackStack(null)
                .add(R.id.viewPager2, fragment, fragment.getClass().getSimpleName())
                .commit();
//        replaceFragment(fragment);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();//            case R.string.start_test_again:
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
//                this.finish();
//                return true;
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this);
            return true;

//            case R.id.action_settings:
//                //replaceFragment(new SettingsPreferenceFragment());
//                return true;
        }
        return super.onOptionsItemSelected(item);

        //action_how_to_use_app
        //action_support_developer

        //return super.onOptionsItemSelected(item);
    }
}
