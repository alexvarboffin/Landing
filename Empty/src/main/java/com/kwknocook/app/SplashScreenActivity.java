package com.kwknocook.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;


@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {


    private Thread welcomeThread;
    private boolean stopped = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

//        final TextView textView = findViewById(R.id.appName);
//        final TextView appVersion = findViewById(R.id.appVersion);
//        appVersion.setText(DLog.getAppVersion(this));

        ProgressBar progressBar = findViewById(R.id.intro_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryLight),
                        android.graphics.PorterDuff.Mode.SRC_IN);

//        progressBar.setIndeterminate(false);
//        progressBar.setProgress(55);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
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
        if(Config.SPLASH_SCREEN_ENABLED){
            okStart();
        }else {
            startMainActivity();
        }
    }

    private void okStart() {
        welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(400);
                } catch (Exception ignored) {

                } finally {
                    if (!stopped) {
                        startMainActivity();
                    }
                }
            }
        };
        welcomeThread.start();
    }

    private void startMainActivity() {
        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeThread != null && welcomeThread.isAlive()) {
            setNull(true);
            welcomeThread = null;
        }
        super.onDestroy();
    }

    private void setNull(boolean b) {
        stopped = b;
        welcomeThread.interrupt();
    }
}
