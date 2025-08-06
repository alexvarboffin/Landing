//package com.wsms.client.activity;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.drawable.AnimationDrawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.View;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.AccelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.progressindicator.CircularProgressIndicator;
//import com.walhalla.landing.utility.NetUtils;
//import com.walhalla.ui.DLog;
//import com.walhalla.ui.plugins.Module_U;
//import com.wsms.client.Cfg;
//import com.wsms.client.PermissionResolver;
//import com.wsms.client.R;
//import com.wsms.client.databinding.ActivityMain0Binding;
//
//import java.util.Calendar;
//
//
//public class SalebayActivity extends AppCompatActivity implements PermissionResolver.MainView {
//
//
//    private ActivityMain0Binding binding;
//    private PermissionResolver presenter;
//    private long secondsRemaining;
//    private CountDownTimer countDownTimer;
//    private AnimationDrawable animationDrawable;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        binding = ActivityMain0Binding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//
//
////        animationDrawable = (AnimationDrawable) binding.constraintLayout.getBackground();
////        // setting enter fade animation duration to 100mls
////        animationDrawable.setEnterFadeDuration(200);
////        // setting exit fade animation duration to 2 seconds
////        animationDrawable.setExitFadeDuration(2000);
//
//
//        //textView.setText("");DLog.getAppVersion(this)
//        //binding.textVer.setText("Real time predictor!\nMade by: winspin.bet");
//        String m = firstStringText();
//        //textView.setText("");DLog.getAppVersion(this)
//        //binding.textVer.setText("Real time predictor!\nMade by: winspin.bet");
//        binding.splashScreen0.textVer.setPaintFlags(binding.splashScreen0.textVer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        binding.splashScreen0.textVer.setText(m);
//
//        final TextView textName = findViewById(R.id.textName);
//        textName.setText(R.string.app_name);
//
//        float dim = getResources().getDimension(R.dimen.tvProgress_translation_distance);
//        float startY = binding.splashScreen0.textVer.getY() + dim;
//        tvProgressAnimationFirst(startY, dim, m, true);
//        animateProgressCpi0(true);
//
//
//        presenter = new PermissionResolver(this, this);
//
//        //new Handler().postDelayed(() -> presenter.onSplashTimeout(), 2000_000);
//    }
//
//    private void timerFinish() {
//        secondsRemaining = 0;
//        //counterTextView.setText("Done.");
//        showMainContent();
//    }
//
//    @Override
//    public void showMainContent() {
//        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_splash);
//        binding.splashScreen0.getRoot().setVisibility(View.GONE);
//        binding.mainContent0.getRoot().setVisibility(View.VISIBLE);
//        binding.mainContent0.getRoot().startAnimation(slideUp);
//    }
//
//
//
//    //splash
//    private String firstStringText() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        String m = String.format(getString(R.string.launcher_progress_title_first), year);
//        return m;
//    }
//
//    private void tvProgressAnimationFirst(float startY, float dim, String text0, boolean b) {
//        TextView tvProgress = binding.splashScreen0.textVer;
//        tvProgress.setText(text0);
//
//
//        float endY = startY - dim; // Расстояние подъема
//        float startScale = 1.0f;
//        float endScale = 0.8f; // Уменьшение в 20%
//
//        // Создаем и запускаем анимацию
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(tvProgress, "translationY", startY, endY);
//        //ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvProgress, "scaleX", startScale, endScale);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvProgress, "scaleY", startScale, endScale);
//        // Анимация появления
//        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tvProgress, "alpha", 0.8f, 1.0f);
//        fadeIn.setInterpolator(new AccelerateInterpolator());
//        //fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
//        fadeIn.setDuration(800); // Длительность анимации в миллисекундах
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(fadeIn, translationY, /*scaleX,*/ scaleY);
//        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animatorSet.setDuration(800); // Длительность анимации в миллисекундах
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(@NonNull Animator animator) {
//            }
//
//            @Override
//            public void onAnimationEnd(@NonNull Animator animator) {
//                if (b) {
//
//                    tvProgressAnimation(startY, dim, binding.splashScreen0.textVer, secondText(), false);
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(@NonNull Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(@NonNull Animator animator) {
//
//            }
//        });
//        animatorSet.start();
//    }
//
//    private void tvProgressAnimation(float startY, float dim, TextView tvProgress, String text0, boolean b) {
//        tvProgress.setText(text0);
//
//        //launcher_progress_title_second
//        //launcher_progress_title_third
//        //launcher_progress_title_last
//
//        float endY = startY - dim; // Расстояние подъема
//        float startScale = 1.0f;
//        float endScale = 0.8f; // Уменьшение в 20%
//
//        // Создаем и запускаем анимацию
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(tvProgress, "translationY", startY, endY);
//        //ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvProgress, "scaleX", startScale, endScale);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvProgress, "scaleY", startScale, endScale);
//        // Анимация появления
//        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tvProgress, "alpha", 0.0f, 1.0f);
//        fadeIn.setInterpolator(new AccelerateInterpolator());
//        //fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
//        fadeIn.setDuration(1200); // Длительность анимации в миллисекундах
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(fadeIn, translationY, /*scaleX,*/ scaleY);
//        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animatorSet.setDuration(1200); // Длительность анимации в миллисекундах
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(@NonNull Animator animator) {
//            }
//
//            @Override
//            public void onAnimationEnd(@NonNull Animator animator) {
//                if (b) {
//                    tvProgressAnimation(startY, dim, binding.splashScreen0.textVer, thirdText(), false);
//
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(@NonNull Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(@NonNull Animator animator) {
//
//            }
//        });
//        animatorSet.start();
//    }
//
//    private String thirdText() {
//        return "@@@@@@@@@@@"; //R.string.launcher_progress_title_second
//    }
//
//    private String secondText() {
//        return getString(R.string.launcher_progress_title_second);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (animationDrawable != null && animationDrawable.isRunning()) {
//            // stop the animation
//            animationDrawable.stop();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.checkPermissions();
//
//        if (animationDrawable != null && !animationDrawable.isRunning()) {
//            // start the animation
//            animationDrawable.start();
//        }
//        if (Cfg.COUNTER_TIME > 1) {
////            this.pulsator = findViewById(R.id.pulsator);
////            this.pulsator.setCount(3);
////            this.pulsator.setDuration(2_200);//single pulse duration
////            this.pulsator.start();
//        }
//
//        if (Cfg.cfg.isSplashScreenEnabled()) {
//            if (Cfg.cfg.isCheckConnection()) {
//                if (NetUtils.isOnline(this)) {
//                    //if (loadStatus) {
////            Intent intent = new Intent(this, CordovaApp.class).setFlags(COOL_FLAGH);
////            startActivity(intent);
////            finish();
//                    //} else {
//                    //    loadStatus = true;
//                    //}
//
//                    // Create a timer so the SplashActivity will be displayed for a fixed amount of time.
//                    createTimer();
//
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle(getString(R.string.network_error))
//                            .setMessage(getString(R.string.network_error_message))
//                            .setPositiveButton(getString(android.R.string.ok),
//                                    (dialog, id) -> {
//                                        dialog.cancel();
//                                        finish();
//                                    })
//                            .setNegativeButton(getString(R.string.action_open_connection_settings),
//                                    (dialog, id) -> {
//                                        dialog.cancel();
//                                        Module_U.actionWirelessSettings(this);
//                                    });
//                    AlertDialog alert = builder
//                            .setCancelable(false).create();
//                    alert.show();
//                }
//            } else {
//                createTimer();
//            }
//        } else {
//            showMainContent();
//        }
//    }
//
//    @SuppressLint("ObsoleteSdkInt")
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (Build.VERSION.SDK_INT >= 19 && hasFocus) {
//            getWindow().getDecorView().setSystemUiVisibility(5126);
//        }
//    }
//
////    private void startAnimation(AnimationSet animationSet) {
////        viewProgress.startAnimation(animationSet);
////        new android.os.Handler().postDelayed(() -> startAnimation(animationSet), 10);
////    }
//
//    public static int getScreenWidth() {
//        return Resources.getSystem().getDisplayMetrics().widthPixels;
//    }
//
//
//    /**
//     * Create the countdown timer, which counts down to zero and show the app open ad.
//     */
//    private void createTimer() {
//
//        long seconds = Cfg.COUNTER_TIME;
//
//        //final TextView counterTextView = findViewById(R.id.timer);
//
//        countDownTimer =
//                new CountDownTimer(seconds * 1000, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
//                        //counterTextView.setText("" + secondsRemaining);
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        timerFinish();
//                    }
//                };
//        countDownTimer.start();
//    }
//
//
//
//    private void animateProgressCpi0(boolean b) {
//        CircularProgressIndicator cpiProgress = binding.splashScreen0.cpiProgress;
//        cpiProgress.setMax(100);
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (b) {
//                    cpiProgress.setIndicatorColor(Color.GREEN);
//                    //cpiProgress.setVisibility(View.GONE);
//                    animateProgressCpi1(binding.splashScreen0.cpiBackground, false);
//                }
//            }
//        });
//        animator.setDuration(1400); // Например, 5000 миллисекунд (5 секунд)
//        animator.addUpdateListener(animation -> {
//            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
//            float progressValue = (float) animation.getAnimatedValue();
//
//            // Устанавливаем прогресс для CircularProgressIndicator
//            cpiProgress.setProgress((int) (progressValue * cpiProgress.getMax()));
//        });
//        animator.start();
//    }
//
//    private void animateProgressCpi1(CircularProgressIndicator cpiProgress, boolean b) {
//        cpiProgress.setMax(100);
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (b) {
//                    cpiProgress.setIndicatorColor(Color.GREEN);
//                    //cpiProgress.setVisibility(View.GONE);
//                    animateProgressCpi1(binding.splashScreen0.cpiBackground, false);
//                }
//            }
//        });
//        animator.setDuration(800); // Например, 5000 миллисекунд (5 секунд)
//        animator.addUpdateListener(animation -> {
//            // Получаем текущее значение анимации (в диапазоне от 0 до 1)
//            float progressValue = (float) animation.getAnimatedValue();
//
//            // Устанавливаем прогресс для CircularProgressIndicator
//            cpiProgress.setProgress((int) (progressValue * cpiProgress.getMax()));
//        });
//        animator.start();
//    }
//
//
//
//    //permission
//    @Override
//    public void showPermissionRequest() {
//        Toast.makeText(this, "Запрашиваем разрешения", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showPermissionDenied() {
//        Toast.makeText(this, "Разрешения отклонены", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void showPermissionGranted() {
//        DLog.d("@@@@@@@@@@@@@");
//        Toast.makeText(this, "Разрешения предоставлены", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//}