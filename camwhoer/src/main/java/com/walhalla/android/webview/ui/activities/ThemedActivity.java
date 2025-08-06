package com.walhalla.android.webview.ui.activities;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.walhalla.android.webview.utils.Theme;

@SuppressLint("Registered") //This is only a base class for other activities
public abstract class ThemedActivity extends AppCompatActivity {

    private com.walhalla.android.webview.utils.Theme.ThemeDescriptor mAppliedTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mAppliedTheme = Theme.apply(this);
        Theme.observe(this, this, theme -> {
            if (!theme.equals(mAppliedTheme))
                recreate();
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
