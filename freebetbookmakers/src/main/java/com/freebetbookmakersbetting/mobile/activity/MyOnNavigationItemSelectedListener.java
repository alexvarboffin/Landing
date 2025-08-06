package com.freebetbookmakersbetting.mobile.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import com.walhalla.landing.Const;
import com.walhalla.landing.pagination.SinglePagination;

import com.freebetbookmakersbetting.mobile.R;

public class MyOnNavigationItemSelectedListener extends SinglePagination implements NavigationView.OnNavigationItemSelectedListener {

    private final MainActivity a;

    public MyOnNavigationItemSelectedListener(MainActivity activity) {
        super(Cfg9.v0);
        this.a = activity;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.nav_about:
                Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
                //intent = new Intent(MainActivity.this, AboutActivity.class);
                break;
            case R.id.nav_contact:
                Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
                //intent = new Intent(MainActivity.this, ContactActivity.class);
                break;
            case R.id.nav_share:
                String shareBody = a.getString(R.string.app_name) + " " + String.format(Const.url_app_google_play, a.getPackageName());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, a.getString(R.string.app_name));
                intent = Intent.createChooser(sharingIntent, a.getResources().getString(R.string.app_name));
                break;
            case R.id.action_exit:
                a.finish();
                return true;
            default:
                String url = getUrl(id);
                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
                a.switchViews(false);
                a.loadUrl(url);
                break;
        }
        if (intent != null) {
            a.loadScreen(intent);
        }
        a.hideDrawer();
        return true;
    }
}
