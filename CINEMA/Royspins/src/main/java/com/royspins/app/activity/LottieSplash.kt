package com.royspins.app.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.royspins.app.Cfg9
import com.royspins.app.R
import com.royspins.app.databinding.SplashMicrocheckerBinding

import com.walhalla.landing.utility.NetUtils
import com.walhalla.ui.plugins.Module_U.actionWirelessSettings
import java.util.Calendar

@SuppressLint("CustomSplashScreen")
class LottieSplash : AppCompatActivity() {
    //private PulsatorLayout pulsator;
    private val viewProgress: View? = null

    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */
    var binding: SplashMicrocheckerBinding? = null

    private var secondsRemaining: Long = 0


    private var countDownTimer: CountDownTimer? = null


    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = SplashMicrocheckerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        //textView.setText("");DLog.getAppVersion(this)
        //binding.textVer.setText("Real time predictor!\nMade by: winspin.bet");
        binding!!.textVer.paintFlags =
            binding!!.textVer.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val m = String.format(getString(R.string.launcher_progress_title_first), year)
        binding!!.textVer.text = m

        val textName = findViewById<TextView>(R.id.textName)
        textName.text = ""

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
        val dim = resources.getDimension(R.dimen.tvProgress_translation_distance)
        val startY = binding!!.textVer.y + dim
        tvProgressAnimation0(startY, dim, m, true)

        animateProgressCpi0(true)
    }

    private fun tvProgressAnimation0(startY: Float, dim: Float, text0: String, b: Boolean) {
        val tvProgress = binding!!.textVer
        tvProgress.text = text0

        //launcher_progress_title_second
        //launcher_progress_title_third
        //launcher_progress_title_last
        val endY = startY - dim // Расстояние подъема
        val startScale = 1.0f
        val endScale = 0.8f // Уменьшение в 20%

        // Создаем и запускаем анимацию
        val translationY = ObjectAnimator.ofFloat(tvProgress, "translationY", startY, endY)
        //ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvProgress, "scaleX", startScale, endScale);
        val scaleY = ObjectAnimator.ofFloat(tvProgress, "scaleY", startScale, endScale)
        // Анимация появления
        val fadeIn = ObjectAnimator.ofFloat(tvProgress, "alpha", 0.0f, 1.0f)
        fadeIn.interpolator = AccelerateInterpolator()
        //fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.setDuration(400) // Длительность анимации в миллисекундах

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeIn, translationY,  /*scaleX,*/scaleY)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.setDuration(1700) // Длительность анимации в миллисекундах
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                if (b) {
                    tvProgressAnimation(startY, dim, binding!!.textVer, thirdText(), false)
                }
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
        animatorSet.start()
    }

    private fun thirdText(): String {
        return "@@@@@@@@@@@" //R.string.launcher_progress_title_second
    }

    private fun tvProgressAnimation(
        startY: Float,
        dim: Float,
        tvProgress: TextView,
        text0: String,
        b: Boolean
    ) {
        tvProgress.text = text0

        //launcher_progress_title_second
        //launcher_progress_title_third
        //launcher_progress_title_last
        val endY = startY - dim // Расстояние подъема
        val startScale = 1.0f
        val endScale = 0.8f // Уменьшение в 20%

        // Создаем и запускаем анимацию
        val translationY = ObjectAnimator.ofFloat(tvProgress, "translationY", startY, endY)
        //ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvProgress, "scaleX", startScale, endScale);
        val scaleY = ObjectAnimator.ofFloat(tvProgress, "scaleY", startScale, endScale)
        // Анимация появления
        val fadeIn = ObjectAnimator.ofFloat(tvProgress, "alpha", 0.0f, 1.0f)
        fadeIn.interpolator = AccelerateInterpolator()
        //fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.setDuration(1200) // Длительность анимации в миллисекундах

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeIn, translationY,  /*scaleX,*/scaleY)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.setDuration(1800) // Длительность анимации в миллисекундах
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
            }

            override fun onAnimationEnd(animator: Animator) {
                if (b) {
                    tvProgressAnimation(startY, dim, binding!!.textVer, thirdText(), false)
                }
            }

            override fun onAnimationCancel(animator: Animator) {
            }

            override fun onAnimationRepeat(animator: Animator) {
            }
        })
        animatorSet.start()
    }

    override fun onResume() {
        super.onResume()

        //        if (Cfg9.COUNTER_TIME > 1) {
////            this.pulsator = findViewById(R.id.pulsator);
////            this.pulsator.setCount(3);
////            this.pulsator.setDuration(2_200);//single pulse duration
////            this.pulsator.start();
//        }

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
     */
    private fun createTimer() {
        //final TextView counterTextView = findViewById(R.id.timer);
        countDownTimer = object : CountDownTimer(Cfg9.cfg.counterTimeMs, 1000) {
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
                this.applicationContext, MainActivity0::class.java
            ).setFlags(COOL_FLAGH)
            this.startActivity(intent)
            this.overridePendingTransition(R.anim.open_next, R.anim.close_main)
            this.finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun animateProgressCpi0(b: Boolean) {
        val cpiProgress = binding!!.cpiProgress
        cpiProgress.max = 100
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (b) {
                    cpiProgress.setIndicatorColor(Color.GREEN)
                    //cpiProgress.setVisibility(View.GONE);
                    animateProgressCpi1(binding!!.cpiBackground, false)
                }
            }
        })
        animator.setDuration(1400) // Например, 5000 миллисекунд (5 секунд)
        animator.addUpdateListener { animation: ValueAnimator ->
            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
            val progressValue = animation.animatedValue as Float

            // Устанавливаем прогресс для CircularProgressIndicator
            cpiProgress.progress = (progressValue * cpiProgress.max).toInt()
        }
        animator.start()
    }

    private fun animateProgressCpi1(cpiProgress: CircularProgressIndicator, b: Boolean) {
        cpiProgress.max = 100
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (b) {
                    cpiProgress.setIndicatorColor(Color.GREEN)
                    //cpiProgress.setVisibility(View.GONE);
                    animateProgressCpi1(binding!!.cpiBackground, false)
                }
            }
        })
        animator.setDuration(800) // Например, 5000 миллисекунд (5 секунд)
        animator.addUpdateListener { animation: ValueAnimator ->
            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
            val progressValue = animation.animatedValue as Float

            // Устанавливаем прогресс для CircularProgressIndicator
            cpiProgress.progress = (progressValue * cpiProgress.max).toInt()
        }
        animator.start()
    }

    companion object {
        const val COOL_FLAGH: Int =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_RECEIVER_FOREGROUND

        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels
    }
}