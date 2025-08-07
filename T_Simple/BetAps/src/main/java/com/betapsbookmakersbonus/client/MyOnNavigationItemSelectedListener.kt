package com.betapsbookmakersbonus.client

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import com.betapsbookmakersbonus.client.activity.MainActivity
import com.google.android.material.navigation.NavigationView
import com.walhalla.landing.Const
import com.walhalla.landing.pagination.SinglePagination

class MyOnNavigationItemSelectedListener(private val a: MainActivity) :

    //BetApsCfg9.cryptUrl
    SinglePagination("https://lordseriala.life/"), NavigationView.OnNavigationItemSelectedListener {

    override fun enc0(input: String): IntArray {
        // 1. Переворачиваем строку обратно
        val reversed = StringBuilder(input).reverse().toString()

        // 2. Преобразуем каждый символ в ASCII-код
        val result = IntArray(reversed.length)
        for (i in reversed.indices) {
            result[i] = reversed[i].code
        }

        return result
    }


    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var intent: Intent? = null
        if (id == R.id.nav_about) { //Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show();
            //intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {
            Toast.makeText(a, "NONE", Toast.LENGTH_SHORT).show()
            //intent = new Intent(MainActivity.this, ContactActivity.class);
        } else if (id == R.id.nav_share) {
            val shareBody = a.getString(R.string.app_name) + " " + String.format(
                Const.url_app_google_play,
                a.packageName
            )
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, a.getString(R.string.app_name))
            intent = Intent.createChooser(sharingIntent, a.resources.getString(R.string.app_name))
        } else if (id == R.id.action_exit) {
            a.finish()
            return true
        } else {
            val url = getUrl(id)
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            a.switchViews(false)
            a.loadUrl(url)
        }
        if (intent != null) {
            a.loadScreen(intent)
        }
        a.hideDrawer()
        return true
    }
}
