package com.slotmachinesonline24.client.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.walhalla.landing.activity.WPresenter;
import com.walhalla.landing.activity.WPresenterImpl;
import com.walhalla.landing.base.UWVlayout;
import com.walhalla.webview.ChromeView;

public abstract class Wzb extends AppCompatActivity
        implements ChromeView, UWVlayout.UWVlayoutCallback {


    private WPresenter var0;

    public WPresenter getPresenter() {
        return var0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        var0 = new WPresenterImpl(handler, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        var0.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void closeApplication() {
        finish();
    }    


    @Override
    public void copyToClipboard(String value) {
        Wzb activity = this;
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("packageName", "" + value);
            clipboard.setPrimaryClip(clip);
        }
        String tmp = String.format(activity.getString(com.walhalla.webview.R.string.data_to_clipboard), value);
//        Toasty.custom(activity, tmp,
//                ComV19.getDrawable(activity, R.drawable.ic_info),
//                ContextCompat.getColor(activity, R.color.colorPrimaryDark),
//                ContextCompat.getColor(activity, R.color.white), Toasty.LENGTH_SHORT, true, true).show();

        Toast.makeText(activity, tmp, Toast.LENGTH_SHORT).show();
    }
}