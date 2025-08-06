package com.topzaymnakartu.online24.android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import com.topzaymnakartu.online24.android.Cfg;
import com.topzaymnakartu.online24.android.R;
import com.walhalla.landing.Const;
import com.walhalla.landing.pagination.SinglePagination;

public class MyOnNavigationItemSelectedListener extends SinglePagination implements NavigationView.OnNavigationItemSelectedListener {

    private final MainActivity activity;

    public MyOnNavigationItemSelectedListener(MainActivity activity) {
        super(Cfg.v0);
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
            Toast.makeText(activity, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, ContactActivity.class);
        } else if (id == R.id.nav_share) {
            String shareBody = activity.getString(R.string.app_name) + " " + String.format(Const.url_app_google_play, activity.getPackageName());
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            intent = Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.app_name));
        } else if (id == R.id.action_exit) {
            activity.finish();
            return true;
        } else {
            String url = getUrl(id);
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            activity.switchViews(false);
            activity.loadUrl(url);
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
