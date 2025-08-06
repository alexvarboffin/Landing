package com.example.android.myapplicationbrowser.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;
import com.walhalla.plugins.Module_U;
import com.walhalla.ui.observer.RateAppModule;


public abstract class QBaseActivity extends AppCompatActivity {

    private RateAppModule var0;

    public QBaseActivity() {
    }

//    protected static void mess(Object o) {
//        Log.d(TAG, "mess: " + o.toString());
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var0 = new RateAppModule(this);
        getLifecycle().addObserver(var0);        //WhiteScree
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.string.start_test_again:
//                return false;

            case R.id.action_about:
                Module_U.aboutDialog(this);
                return true;

            case R.id.action_privacy_policy:
                Launcher.openBrowser(this, getString(R.string.url_privacy_policy));
                return true;

            case R.id.action_rate_app:
                Launcher.rateUs(this);
                return true;

            case R.id.action_share_app:
                Module_U.shareThisApp(this);
                return true;

            case R.id.action_discover_more_app:
                Module_U.moreApp(this);
                return true;

//            case R.id.action_exit:
//                this.finish();
//                return true;

            case R.id.action_feedback:
                Module_U.feedback(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


        //action_how_to_use_app
        //action_support_developer

        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (var0 != null) {
            var0.appReloadedHandler();
        }
        super.onSaveInstanceState(outState);
    }
}
