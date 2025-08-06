package io.zerocard.pro.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import io.zerocard.pro.Cfg;
import io.zerocard.pro.R;
import io.zerocard.pro.databinding.ActivitySplashLottieBinding;

import com.walhalla.landing.utility.NetUtils;
import com.walhalla.ui.plugins.Module_U;

import java.util.Calendar;


@SuppressLint("CustomSplashScreen")
public class LottieSplash extends AppCompatActivity {

    public static final int COOL_FLAGH = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_RECEIVER_FOREGROUND;
    //private PulsatorLayout pulsator;

    private View viewProgress;
    /**
     * Number of seconds to count down before showing the app open ad. This simulates the time needed
     * to load the app.
     */

    ActivitySplashLottieBinding binding;

    private long secondsRemaining;


    private CountDownTimer countDownTimer;


    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = ActivitySplashLottieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //textView.setText("");DLog.getAppVersion(this)

        String m = firstStringText();
        //textView.setText("");DLog.getAppVersion(this)
        //binding.textVer.setText("Real time predictor!\nMade by: winspin.bet");
        binding.textVer.setPaintFlags(binding.textVer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.textVer.setText(m);

        final TextView textName = findViewById(R.id.textName);
        textName.setText("");

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

        animateProgressCpi0(true);
    }

    private String firstStringText() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String m = String.format(getString(R.string.launcher_progress_title_first), year);
        return m;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Cfg.COUNTER_TIME > 1) {
//            this.pulsator = findViewById(R.id.pulsator);
//            this.pulsator.setCount(3);
//            this.pulsator.setDuration(2_200);//single pulse duration
//            this.pulsator.start();
        }

        if (Cfg.cfg.splash_screen_enabled) {
            if (Cfg.cfg.check_connection) {
                if (NetUtils.isOnline(this)) {
                    //if (loadStatus) {
//            Intent intent = new Intent(this, CordovaApp.class).setFlags(COOL_FLAGH);
//            startActivity(intent);
//            finish();
                    //} else {
                    //    loadStatus = true;
                    //}

                    // Create a timer so the SplashActivity will be displayed for a fixed amount of time.
                    createTimer();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.network_error))
                            .setMessage(getString(R.string.network_error_message))
                            .setPositiveButton(getString(android.R.string.ok),
                                    (dialog, id) -> {
                                        dialog.cancel();
                                        finish();
                                    })
                            .setNegativeButton(getString(R.string.action_open_connection_settings),
                                    (dialog, id) -> {
                                        dialog.cancel();
                                        Module_U.actionWirelessSettings(this);
                                    });
                    AlertDialog alert = builder
                            .setCancelable(false).create();
                    alert.show();
                }
            } else {
                createTimer();
            }
        } else {
            startMainActivity();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= 19 && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5126);
        }
    }

    private void startAnimation(AnimationSet animationSet) {
        viewProgress.startAnimation(animationSet);
        new android.os.Handler().postDelayed(() -> startAnimation(animationSet), 10);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }


    /**
     * Create the countdown timer, which counts down to zero and show the app open ad.
     */
    private void createTimer() {

        long seconds = Cfg.COUNTER_TIME;

        //final TextView counterTextView = findViewById(R.id.timer);

        countDownTimer =
                new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                        //counterTextView.setText("" + secondsRemaining);
                    }

                    @Override
                    public void onFinish() {
                        timerFinish();
                    }
                };
        countDownTimer.start();
    }

    private void timerFinish() {
        secondsRemaining = 0;
        //counterTextView.setText("Done.");
        startMainActivity();
    }


    /**
     * Start the MainActivity.
     */
    private void startMainActivity() {
        try {
            Intent intent = new Intent(this.getApplicationContext(), MainActivity.class).setFlags(COOL_FLAGH);
            this.startActivity(intent);
            this.overridePendingTransition(R.anim.open_next, R.anim.close_main);
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateProgressCpi0(boolean b) {
        CircularProgressIndicator cpiProgress = binding.cpiProgress;
        cpiProgress.setMax(100);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (b) {
                    cpiProgress.setIndicatorColor(Color.GREEN);
                    //cpiProgress.setVisibility(View.GONE);
                    animateProgressCpi1(binding.cpiBackground, false);
                }
            }
        });
        animator.setDuration(1400); // Например, 5000 миллисекунд (5 секунд)
        animator.addUpdateListener(animation -> {
            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
            float progressValue = (float) animation.getAnimatedValue();

            // Устанавливаем прогресс для CircularProgressIndicator
            cpiProgress.setProgress((int) (progressValue * cpiProgress.getMax()));
        });
        animator.start();
    }

    private void animateProgressCpi1(CircularProgressIndicator cpiProgress, boolean b) {
        cpiProgress.setMax(100);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (b) {
                    cpiProgress.setIndicatorColor(Color.GREEN);
                    //cpiProgress.setVisibility(View.GONE);
                    animateProgressCpi1(binding.cpiBackground, false);
                }
            }
        });
        animator.setDuration(800); // Например, 5000 миллисекунд (5 секунд)
        animator.addUpdateListener(animation -> {
            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
            float progressValue = (float) animation.getAnimatedValue();

            // Устанавливаем прогресс для CircularProgressIndicator
            cpiProgress.setProgress((int) (progressValue * cpiProgress.getMax()));
        });
        animator.start();
    }
}