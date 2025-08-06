package com.topzaymnakartu.online24.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.walhalla.landing.activity.WPresenter;
import com.walhalla.landing.activity.WPresenterImpl;
import com.walhalla.landing.activity.WebActivity;
import com.walhalla.landing.base.ActivityConfig;
import com.walhalla.landing.base.DynamicWebViewOld;
import com.topzaymnakartu.online24.android.R;

public abstract class BaseActivity extends WebActivity implements ActivityConfig {


    protected boolean doubleBackToExitPressedOnce;
    private Handler mHandler;
    protected DynamicWebViewOld dynamicWebView;

    private SwipeRefreshLayout swipe;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    

    @Override
    public void onPageStarted(String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);//modify if needed
        }
    }

    @Override
    public void onPageFinished(/*WebView view, */String url) {
        if (isSwipeEnabled()) {
            swipe.setRefreshing(false);
        }

//        if (getSupportActionBar() != null) {
//            String m = view.getTitle();
//            if(TextUtils.isEmpty(m)){
//                getSupportActionBar().setSubtitle(m);
//            }
//        }
    }

    protected void generateViews(Context context, ViewGroup view) {
        // Создайте экземпляр DynamicWebView и получите его WebView
        dynamicWebView = new DynamicWebViewOld(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dynamicWebView.getWebView().setLayoutParams(lp);
        if (isSwipeEnabled()) {
            swipe = new SwipeRefreshLayout(context);
            swipe.setLayoutParams(lp);
            view.addView(swipe);
            swipe.addView(dynamicWebView.getWebView());
            swipe.setRefreshing(false);
            swipeWebViewRef(swipe, dynamicWebView.getWebView());
        } else {
            view.addView(dynamicWebView.getWebView());
        }

        //mWebView.setBackgroundColor(Color.BLACK);
        // register class containing methods to be exposed to JavaScript
        presenter.a123(this, dynamicWebView.getWebView());
    }

    protected void loadUrl(String url) {
        dynamicWebView.getWebView().loadUrl(url);
    }


    protected abstract boolean USE_HISTORY();

    protected WPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        presenter = new WPresenterImpl(handler, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }


}
