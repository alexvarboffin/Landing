//package io.zerocard.pro.activity;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.animation.ValueAnimator;
//import android.annotation.SuppressLint;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.view.View;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.progressindicator.CircularProgressIndicator;
//import io.zerocard.pro.Cfg;
//import io.zerocard.pro.R;
//import io.zerocard.pro.databinding.ActivityLauncherBinding;
//import com.walhalla.landing.utility.NetUtils;
//
//
//@SuppressLint("CustomSplashScreen")
//public class SplashActivity extends AppCompatActivity {
//
//    public static final int COOL_FLAG = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_RECEIVER_FOREGROUND;
//
//
//    private Thread welcomeThread;
//    private boolean stopped = false;
//
//    ActivityLauncherBinding binding;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (Cfg.SPLASH_SCREEN_ENABLED) {
//            if (Cfg.CHECK_CONNECTION) {
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
//                                        try {
//                                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                            // Обработка исключения, если активность не найдена
//                                            e.printStackTrace();
//                                        }
//                                    });
//                    AlertDialog alert = builder
//                            .setCancelable(false).create();
//                    alert.show();
//                }
//            } else {
//                createTimer();
//            }
//        } else {
//            startMainActivity();
//        }
//
//        binding.cpiProgress.show();
//        binding.cpiProgress.setIndicatorSize(320);
//        binding.cpiBackground.show();
//        binding.cpiBackground.setIndicatorSize(320);
//
////        FullScreenDialogFragment dialogFragment = new FullScreenDialogFragment();
////        dialogFragment.show(getSupportFragmentManager(), "FullScreenDialogFragment");
//
////        RegGetBonusDialogFragment dialogFragment = new RegGetBonusDialogFragment();
////        dialogFragment.show(getSupportFragmentManager(), "FullScreenDialogFragment");
//
//        logoAnimation();
//
//        String launcher_progress_title_first = getString(R.string.launcher_progress_title_first);//DLog.getAppVersion(this);
//        float dim = getResources().getDimension(R.dimen.tvProgress_translation_distance);
//        float startY = binding.tvProgress.getY() + dim;
//        tvProgressAnimation(startY, dim, binding.tvProgress, launcher_progress_title_first, true);
//
//        animateStarsAppearance(binding.ivStars);
//
//        animateProgressCpi(binding.cpiProgress, true);
//    }
//
//    private void tvProgressAnimation(float startY, float dim, TextView tvProgress, String text, boolean b) {
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
//        //fadeIn.setInterpolator(new AccelerateInterpolator());
//        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
//        fadeIn.setDuration(800); // Длительность анимации в миллисекундах
//
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(fadeIn, translationY, /*scaleX,*/ scaleY);
//        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animatorSet.setDuration(1200); // Длительность анимации в миллисекундах
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(@NonNull Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(@NonNull Animator animator) {
//                if (b) {
//                    tvProgressAnimation(startY, dim, binding.tvProgress, getString(R.string.launcher_progress_title_last), false);
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
//    private void logoAnimation() {
//        // Начальные значения для анимации
//        float startY = binding.ivLogo.getY();
//        float endY = startY - getResources().getDimension(R.dimen.logo_translation_distance); // Расстояние подъема
//        float startScale = 1.0f;
//        float endScale = 0.8f; // Уменьшение в 20%
//
//        // Создаем и запускаем анимацию
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(binding.ivLogo, "translationY", startY, endY);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(binding.ivLogo, "scaleX", startScale, endScale);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(binding.ivLogo, "scaleY", startScale, endScale);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(translationY, scaleX, scaleY);
//        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animatorSet.setDuration(1000); // Длительность анимации в миллисекундах
//        animatorSet.start();
//    }
//
//    private void animateProgressCpi(CircularProgressIndicator cpiProgress, boolean b) {
//        cpiProgress.setMax(100);
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (b) {
//                    cpiProgress.setIndicatorColor(Color.GREEN);
//                    //cpiProgress.setVisibility(View.GONE);
//                    animateProgressCpi(binding.cpiBackground, false);
//                }
//            }
//        });
//        animator.setDuration(5000); // Например, 5000 миллисекунд (5 секунд)
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
//    private void animateStarsAppearance(View ivStars) {
////        ivStars.setAlpha(0.0f);
////        ivStars.setScaleX(0.0f);
////        ivStars.setScaleY(0.0f);
//
//        // Анимация появления
//        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(ivStars, "alpha", 0.0f, 1.0f);
//        //fadeIn.setInterpolator(new AccelerateInterpolator());
//        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
//        fadeIn.setDuration(1200); // Длительность анимации в миллисекундах
//
//
//        float dim = getResources().getDimension(R.dimen.tvProgress_translation_distance);
//        float startY = ivStars.getY() + dim; // Расстояние подъема
//        float endY = startY - dim;
//
//
//        ObjectAnimator translationY = ObjectAnimator.ofFloat(ivStars, "translationY", startY, endY);
//
//        // Анимация изменения размера
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivStars, "scaleX", 0.0f, 1.0f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivStars, "scaleY", 0.0f, 1.0f);
//
//        // Запуск анимаций
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(translationY, fadeIn, scaleX, scaleY);
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(@NonNull Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(@NonNull Animator animator) {
//
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
//    private void createTimer() {
//        welcomeThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    super.run();
//                    sleep(800_0000);
//                } catch (Exception ignored) {
//
//                } finally {
//                    if (!stopped) {
//                        startMainActivity();
//                    }
//                }
//            }
//        };
//        welcomeThread.start();
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (welcomeThread != null && welcomeThread.isAlive()) {
//            setNull(true);
//            welcomeThread = null;
//        }
//        super.onDestroy();
//    }
//
//    private void startMainActivity() {
//        Intent i = new Intent(SplashActivity.this, MainActivity.class).setFlags(COOL_FLAG);
//        startActivity(i);
//        this.finish();
//    }
//
//    private void setNull(boolean b) {
//        stopped = b;
//        welcomeThread.interrupt();
//    }
//}
