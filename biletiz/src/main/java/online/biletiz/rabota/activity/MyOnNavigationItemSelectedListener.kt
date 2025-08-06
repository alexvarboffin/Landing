package online.biletiz.rabota.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.walhalla.landing.Const
import com.walhalla.landing.pagination.SinglePagination
import online.biletiz.rabota.Cfg9
import online.biletiz.rabota.R

class MyOnNavigationItemSelectedListener(private val activity: MainActivity) : SinglePagination(
    Cfg9.v0
), NavigationView.OnNavigationItemSelectedListener {
    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var intent: Intent? = null
        if (id == R.id.nav_about) {
            Toast.makeText(activity, "NONE", Toast.LENGTH_SHORT).show()
            //intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.nav_contact) {
            Toast.makeText(activity, "NONE", Toast.LENGTH_SHORT).show()
            //intent = new Intent(MainActivity.this, ContactActivity.class);
        } else if (id == R.id.nav_share) {
            val shareBody = activity.getString(R.string.app_name) + " " + String.format(
                Const.url_app_google_play,
                activity.packageName
            )
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
            intent = Intent.createChooser(
                sharingIntent,
                activity.getResources().getString(R.string.app_name)
            )
        } else if (id == R.id.action_exit) {
            activity.finish()
            return true
        } else {
            val url = getUrl(id)
            //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
            activity.switchViews(false)
            activity.loadUrl(url)
        }
        if (intent != null) {
            activity.loadScreen(intent)
        }
        activity.hideDrawer()
        return true
    }

    private fun openUrlTry(id: Int, switchViews: Boolean) {
        val url = getUrl(id)
        //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();
        activity.switchViews(switchViews)
        activity.loadUrl(url)
    }

    fun onlyRefresh(id: Int) {
        val url = getUrl(id)
        activity.loadUrl(url)
    }
}
