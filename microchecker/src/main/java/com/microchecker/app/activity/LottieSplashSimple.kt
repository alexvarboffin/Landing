package com.microchecker.app.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationSet
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.microchecker.app.Cfg9
import com.microchecker.app.R
import com.walhalla.landing.utility.NetUtils
import com.walhalla.ui.plugins.Module_U.actionWirelessSettings

@SuppressLint("CustomSplashScreen")
class LottieSplashSimple : AppCompatActivity() {
    //private PulsatorLayout pulsator;
    private val viewProgress: View? = null

    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */
    private var secondsRemaining: Long = 0
    private var COUNTER_TIME = 0

    private var countDownTimer: CountDownTimer? = null


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashlottiesimple)
        val textView = findViewById<TextView>(R.id.textVer)
        textView.text = ""
        //textView.setText(DLog.getAppVersion(this));
        val textName = findViewById<TextView>(R.id.textName)
        textName.setText(R.string.app_name)

        //SharedPref pref = new SharedPref(this);
        //mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        int dism = 0;//mSharedPreferences.getLong(KEY_DISM_COUNT, 0);
//        boolean isRated = pref.appRated();
//        int cc = pref.appResumeCount(); //onResume
//        int count = pref.startCount();
//        if (count == 0) {
//            DLog.d("@aaa@ :: First launch " + isRated + " " + cc + " " + dism);
//            //if (isRated) {
//                pref.startCount(1);//level2
//            //}
//            REQURST_RUN_ADS = true;
//            COUNTER_TIME = 5;
//        } else if (count == 1) {//level2
//            pref.startCount(2);
//            COUNTER_TIME = 1;
//            REQURST_RUN_ADS = false;
//            DLog.d("@aaa@ second launch " + isRated + " " + cc + " " + dism);
//        } else {
//            COUNTER_TIME = 2;
//            REQURST_RUN_ADS = false;
//            DLog.d("@aaa@ >2 launch " + isRated + " " + cc + " " + dism);
//        }
        //show
        //skip
        //skip
        COUNTER_TIME = 200


        //        viewProgress = findViewById(R.id.view_progress);
//        int viewWidth = viewProgress.getWidth();
//
//        TranslateAnimation move = new TranslateAnimation(-(getScreenWidth() / 2) + viewWidth / 2, (getScreenWidth() / 2) + viewWidth / 2 + viewWidth, 0, 0);
//        move.setDuration(1000);
//        TranslateAnimation move1 = new TranslateAnimation(-viewWidth, 0, 0, 0);
//        move1.setDuration(500);
//        ScaleAnimation laftOut = new ScaleAnimation(0, 1, 1, 1);
//        laftOut.setDuration(500);
//
//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(move);
//        animationSet.addAnimation(move1);
//        animationSet.addAnimation(laftOut);
//        animationSet.addAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideout));
//        startAnimation(animationSet);

        //@@@ Loading task = new Loading(this);
        //@@@ if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        //@@@     task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //@@@ else
        //@@@     task.execute();
    }

    override fun onResume() {
        super.onResume()

        if (COUNTER_TIME > 1) {
//            this.pulsator = findViewById(R.id.pulsator);
//            this.pulsator.setCount(3);
//            this.pulsator.setDuration(2_200);//single pulse duration
//            this.pulsator.start();
        }

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

                    createTimer(COUNTER_TIME.toLong())
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.network_error))
                        .setMessage(getString(R.string.network_error_message))
                        .setPositiveButton(
                            getString(R.string.ok)
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
                createTimer(COUNTER_TIME.toLong())
            }
        } else {
            startMainActivity()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= 19 && hasFocus) {
            window.decorView.systemUiVisibility = 5126
        }
    }

    private fun startAnimation(animationSet: AnimationSet) {
        viewProgress!!.startAnimation(animationSet)
        Handler().postDelayed({ startAnimation(animationSet) }, 10)
    }

    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     *
     * @param seconds the number of seconds that the timer counts down from
     */
    private fun createTimer(seconds: Long) {
        //final TextView counterTextView = findViewById(R.id.timer);

        countDownTimer =
            object : CountDownTimer(seconds * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsRemaining = ((millisUntilFinished / 1000) + 1)
                    //counterTextView.setText("" + secondsRemaining);
                }

                override fun onFinish() {
                    timerFinish()
                }
            }
        countDownTimer?.start()
    }

    private fun timerFinish() {
        secondsRemaining = 0
        //counterTextView.setText("Done.");
        startMainActivity()
    }


    /**
     * Start the MainActivity.
     */
    private fun startMainActivity() {
        try {
            val intent = Intent(
                this.applicationContext,
                MainActivity::class.java
            ).setFlags(COOL_FLAGH)
            this.startActivity(intent)
            this.overridePendingTransition(R.anim.open_next, R.anim.close_main)
            this.finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val COOL_FLAGH: Int =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_RECEIVER_FOREGROUND

        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }
}