package com.microchecker.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.microchecker.app.activity.MainActivity;
import com.walhalla.landing.utility.NetUtils;
import com.walhalla.ui.plugins.Module_U;

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
        if (Cfg9.cfg.isSplashScreenEnabled()) {
            if (Cfg9.cfg.isCheckConnection()) {
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

    private void createTimer() {
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
