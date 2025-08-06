package com.sport.matches.winline.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.footballdeluxe.goldсup.R;
import com.walhalla.ui.DLog;

import java.lang.ref.WeakReference;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private View viewProgress;
    private Loading loading;

    public static class Loading extends AsyncTask<Void, Void, Void> {
        private final WeakReference<Activity> mWeakReference;

        Loading(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Activity activity = mWeakReference.get();
            if (activity != null) {
                activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                activity.overridePendingTransition(R.anim.open_next, R.anim.close_main);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Thread.setDefaultUncaughtExceptionHandler(CustomExceptionHandler.getInstance(getApplicationContext()));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc_splash);
        final TextView textView = findViewById(R.id.text_ver);
        textView.setText(DLog.getAppVersion(this));

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


        loading = new Loading(this);
        loading.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loading != null && !loading.isCancelled()) {
            try {
                loading.cancel(true);
            }catch (Exception ignored){}
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
}
