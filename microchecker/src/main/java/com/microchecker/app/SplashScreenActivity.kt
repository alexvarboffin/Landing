package com.microchecker.app

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.microchecker.app.activity.MainActivity
import com.walhalla.landing.utility.NetUtils
import com.walhalla.ui.plugins.Module_U.actionWirelessSettings

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var welcomeThread: Thread? = null
    private var stopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        //        final TextView textView = findViewById(R.id.appName);
//        final TextView appVersion = findViewById(R.id.appVersion);
//        appVersion.setText(DLog.getAppVersion(this));
        val progressBar = findViewById<ProgressBar>(R.id.intro_progress)
        progressBar.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(
                this, R.color.colorPrimaryLight
            ),
            PorterDuff.Mode.SRC_IN
        )

        //        progressBar.setIndeterminate(false);
//        progressBar.setProgress(55);
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        //        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // If you want to modify a view in your Activity
//                SplashScreenActivity.this.runOnUiThread(() -> {
//                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//                    SplashScreenActivity.this.finish();
//
//                });
//            }
//        }, 1200);
        if (Cfg9.cfg.isSplashScreenEnabled) {
            if (Cfg9.cfg.isCheckConnection) {
                if (NetUtils.isOnline(this)) {
                    //if (loadStatus) {
//            Intent intent = new Intent(this, CordovaApp.class).setFlags(COOL_FLAGH);
//            startActivity(intent);
//            finish();
                    //} else {
                    //    loadStatus = true;
                    //}

                    // Create a timer so the SplashActivity will be displayed for a fixed amount of time.

                    createTimer()
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.network_error))
                        .setMessage(getString(R.string.network_error_message))
                        .setPositiveButton(
                            getString(android.R.string.ok)
                        ) { dialog: DialogInterface, id: Int ->
                            dialog.cancel()
                            finish()
                        }
                        .setNegativeButton(
                            getString(R.string.action_open_connection_settings)
                        ) { dialog: DialogInterface, id: Int ->
                            dialog.cancel()
                            actionWirelessSettings(this)
                        }
                    val alert = builder
                        .setCancelable(false).create()
                    alert.show()
                }
            } else {
                createTimer()
            }
        } else {
            startMainActivity()
        }
    }

    private fun createTimer() {
        welcomeThread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(400)
                } catch (ignored: Exception) {
                } finally {
                    if (!stopped) {
                        startMainActivity()
                    }
                }
            }
        }
        welcomeThread?.start()
    }

    private fun startMainActivity() {
        val i = Intent(
            this@SplashScreenActivity,
            MainActivity::class.java
        )
        startActivity(i)
        this.finish()
    }

    override fun onDestroy() {
        if (welcomeThread != null && welcomeThread!!.isAlive) {
            setNull(true)
            welcomeThread = null
        }
        super.onDestroy()
    }

    private fun setNull(b: Boolean) {
        stopped = b
        welcomeThread!!.interrupt()
    }
}
