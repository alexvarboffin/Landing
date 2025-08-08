package life.lordseriala.android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import life.lordseriala.android.Cfg9.cfg
import life.lordseriala.android.R

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
        progressBar.indeterminateDrawable
            .setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimaryLight),
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
        if (cfg.isSplashScreenEnabled) {
            okStart()
        } else {
            startMainActivity()
        }
    }

    private fun okStart() {
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
        welcomeThread!!.start()
    }

    private fun startMainActivity() {
        val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
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
