package com.advear.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.advear.client.R;
import com.google.android.material.navigation.NavigationView;

import com.walhalla.landing.Const;
import com.walhalla.landing.pagination.SinglePagination;
import com.walhalla.landing.utils.Crypt;
import com.walhalla.ui.plugins.Module_U;


public class MyOnNavigationItemSelectedListener extends SinglePagination
        implements NavigationView.OnNavigationItemSelectedListener {

    private final MainActivity activity;

    public MyOnNavigationItemSelectedListener(MainActivity activity) {
        //super(Cfg.v0m);
        //super(Crypt.enco("https://payup.video/signup/"));
        super(Crypt.enco("https://rudos.ru/newadv/"));

        this.activity = activity;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_about) {
            Toast.makeText(activity, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {
            //Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, ContactActivity.class);

        } else if (id == R.id.action_feedback) {
            Module_U.feedback(activity);
        } else if (id == R.id.nav_share) {
            String shareBody = activity.getString(R.string.share_text_default)
                    + " " + String.format(Const.url_app_google_play, activity.getPackageName());

            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.setType("text/plain");
            intent1.putExtra(Intent.EXTRA_TEXT, shareBody);
            intent1.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            intent = Intent.createChooser(intent1, activity.getResources().getString(R.string.app_name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else if (id == R.id.action_exit) {
            activity.finish();
            return true;
        } else {
            openUrlTry(id, false);
        }
        if (intent != null) {
            activity.loadScreen(intent);
        }
        activity.hideDrawer();
        return true;
    }

    private void openUrlTry(int id, boolean switchViews) {
        String url = getUrl(id);
        //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
        activity.switchViews(switchViews);
        activity.loadUrl(url);
    }

    public void onlyRefresh(int id) {
        String url = getUrl(id);
        activity.loadUrl(url);
    }
}
