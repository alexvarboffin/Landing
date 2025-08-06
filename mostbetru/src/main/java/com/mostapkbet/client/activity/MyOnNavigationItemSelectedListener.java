package com.mostapkbet.client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;
import com.mostapkbet.client.R;
import com.walhalla.landing.Const;
import com.walhalla.landing.pagination.SinglePagination;

public class MyOnNavigationItemSelectedListener extends SinglePagination
        implements NavigationView.OnNavigationItemSelectedListener {

    private final MainActivity mainActivity;

    public MyOnNavigationItemSelectedListener(MainActivity activity) {
        super(Cfg.v0);
        this.mainActivity = activity;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_about) {//Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {//Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, ContactActivity.class);
        } else if (id == R.id.nav_share) {
            String shareBody = mainActivity.getString(R.string.app_name) + " " + String.format(Const.url_app_google_play, mainActivity.getPackageName());
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, mainActivity.getString(R.string.app_name));
            intent = Intent.createChooser(sharingIntent, mainActivity.getResources().getString(R.string.app_name));
        } else if (id == R.id.action_exit) {
            mainActivity.finish();
            return true;
        } else {
            String url = getUrl(id);
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            mainActivity.switchViews(false);
            mainActivity.loadUrl(url);
        }
        if (intent != null) {
            mainActivity.loadScreen(intent);
        }
        mainActivity.hideDrawer();
        return true;
    }
}
