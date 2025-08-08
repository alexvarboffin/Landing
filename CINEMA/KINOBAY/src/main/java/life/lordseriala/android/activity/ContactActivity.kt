package life.lordseriala.android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.walhalla.ui.DLog.d
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.webview.CustomWebViewClient
import androidx.core.util.size
import androidx.core.net.toUri
import life.lordseriala.android.Cfg9
import life.lordseriala.android.R

class ContactActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.nav_contact)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        for (i in 0 until Cfg9.contacts.size) {
            val key = Cfg9.contacts.keyAt(i)
            val value = Cfg9.contacts[key]

            val viewGroup = findViewById<ViewGroup>(key)
            if ("@0" == getString(value)) {
                viewGroup.visibility = View.GONE
            } else {
                val viewGroup1 = viewGroup.getChildAt(1) as ViewGroup
                val child = viewGroup1.getChildAt(1)
                if (child is TextView) {
                    val content = SpannableString(getString(value))
                    content.setSpan(UnderlineSpan(), 0, content.length, 0)
                    child.text = content
                }
            }
        }
    }

    fun openEmail(view: View) {
        val textView = view as TextView
        val emailIntent =
            Intent(Intent.ACTION_SENDTO)
        emailIntent.setData(("mailto:" + textView.text.toString()).toUri())

        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
        }
    }

    fun callNumber(view: View) {
        val textView = view as TextView
        var number = textView.text.toString()
        number = number.trim { it <= ' ' }
            .replace(" ", "")
            .replace("-", "")
            .replace(")", "")
            .replace("(", "")

        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.setData("tel:$number".toUri()) //change the number


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                MY_PERMISSIONS_REQUEST_CALL_PHONE
            )

            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            //You already have permission
            try {
                startActivity(callIntent)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) { // If request is cancelled, the result arrays are empty.
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this,
                    "Permission was granted, yay! Do the phone call", Toast.LENGTH_SHORT
                ).show()
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    override fun onClick(v: View) {
        if (v is TextView) {
            openBrowser(this, v.text.toString())
        } else if (v.id == R.id.contact_address) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                ("google.navigation:q=" + getString(R.string.app_address)).toUri()
            )
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.i(CustomWebViewClient.TAG, "onClick: " + e.localizedMessage)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_about_share) {
            val sb = StringBuilder()

            for (i in 0 until Cfg9.contacts.size) {
                val key = Cfg9.contacts.keyAt(i)
                val value = Cfg9.contacts[key]

                val viewGroup = findViewById<ViewGroup>(key)

                if ("@0" == getString(value)) {
                    //viewGroup.setVisibility(View.GONE);
                } else {
                    val viewGroup1 = viewGroup.getChildAt(1) as ViewGroup
                    val count = viewGroup1.childCount
                    sb.append(10.toChar())
                    for (i1 in 0 until count) {
                        val t2 = viewGroup1.getChildAt(i1)
                        if (t2 is TextView) {
                            sb.append(t2.text)
                                .append(10.toChar())
                        }
                    }
                }
            }
            //Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
            d("@@$sb")
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            sharingIntent.putExtra(Intent.EXTRA_TEXT, sb.toString())
            startActivity(Intent.createChooser(sharingIntent, "Share text via"))
            return true
        } else if (itemId == android.R.id.home) { //NavUtils.navigateUpFromSameTask(this);
            this.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_CALL_PHONE = 3400
    }
}