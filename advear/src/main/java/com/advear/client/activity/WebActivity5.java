package com.advear.client.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.UWView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.advear.client.custom.WPresenterAAImpl2;
import com.google.android.material.snackbar.Snackbar;
import com.walhalla.landing.R;
import com.walhalla.landing.activity.WPresenter;
import com.walhalla.landing.base.UWVlayout;

import com.walhalla.landing.permissionrequest.ConfirmationDialogFragment;
import com.walhalla.ui.BuildConfig;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.webview.ChromeView;
import com.walhalla.webview.ReceivedError;

public abstract class WebActivity5 extends AppCompatActivity
        implements ChromeView, UWVlayout.UWVlayoutCallback, ConfirmationDialogFragment.Listener {

    protected WPresenterAAImpl2 presenter;
    //private CustomTabsIntent customTabsIntent;


    public WPresenterAAImpl2 getPresenter0() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler(Looper.getMainLooper());
        this.presenter = new WPresenterAAImpl2(handler, this);
        //this.customTabsIntent = CustomTabUtils.customWeb(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void openBrowser(Activity context, String url) {
        Launcher.openBrowser(context, url);
    }

    protected void swipeWebViewRef(SwipeRefreshLayout swipeLayout, UWView webView) {
//        ViewTreeObserver vto = swipeLayout.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                // Calculate the trigger distance.
//                final DisplayMetrics metrics = getResources().getDisplayMetrics();
//                Float mDistanceToTriggerSync = Math.min(
//                        ((View) swipeLayout.getParent()).getHeight() * 0.6f, 120 * metrics.density);
//
//                try {
//                    // Set the internal trigger distance using reflection.
//                    Field field = SwipeRefreshLayout.class.getDeclaredField("mDistanceToTriggerSync");
//                    field.setAccessible(true);
//                    field.setFloat(swipeLayout, mDistanceToTriggerSync);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                // Only needs to be done once so remove listener.
//                ViewTreeObserver obs = swipeLayout.getViewTreeObserver();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    obs.removeOnGlobalLayoutListener(this);
//                } else {
//                    obs.removeGlobalOnLayoutListener(this);
//                }
//            }
//        });

//        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
//        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                // Если расстояние по оси Y больше чем расстояние по оси X и достаточное для прокрутки
//                if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) >
//                        viewConfiguration.getScaledTouchSlop()) {
//                    swipeLayout.setRefreshing(false);
//                }else {
//                    if (swipeLayout.isRefreshing()) {
//                        swipeLayout.setRefreshing(false);
//                    }
//                }
//                return super.onScroll(e1, e2, distanceX, distanceY);
//            }
//        });
//
//        swipeLayout.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

//        int distanceToTriggerSync = 800; // расстояние в пикселях
//        swipeLayout.setDistanceToTriggerSync(distanceToTriggerSync);
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            webView.setOnScrollChangeListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (!view.canScrollVertically(-1)) {
                    swipeLayout.setEnabled(true); // Разрешить обновление при прокрутке вверх
                } else {
                    if (swipeLayout.isRefreshing()) {
                        swipeLayout.setRefreshing(false);
                    } // Запретить обновление при прокрутке вниз
                }

//                int scrollY = rootScrollView.getScrollY(); // For ScrollView
//                int scrollX = rootScrollView.getScrollX(); // For HorizontalScrollView
                // DO SOMETHING WITH THE SCROLL COORDINATES


//                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
//                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
//
//                // если diff равен 0 - вы достигли конца скрола
//                if (diff == 0) {
//                    // some code
//                }
            });
        }


        swipeLayout.setOnRefreshListener(() -> {
            if (BuildConfig.DEBUG) {
                Toast.makeText(this, "Swiped", Toast.LENGTH_SHORT).show();
            }
            if (swipeLayout.isRefreshing()) {
                swipeLayout.setRefreshing(false);
            }
            webView.reload();
        });
    }

//    @Override
//    public void openOauth2(Activity context, String url) {
//        try {
////        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
////        Bundle bundle = new Bundle();
////        bundle.putString("goz_ad_click", "1");
////        mFirebaseAnalytics.logEvent("in_offer_open_click", bundle);
//            customTabsIntent.launchUrl(context, Uri.parse(url));
//        } catch (Exception e) {
//            DLog.handleException(e);
//        }
//    }

    public void backPressedToast() {
        View coordinatorLayout = findViewById(R.id.coordinatorLayout);
        if (coordinatorLayout != null) {
            coordinatorLayout = findViewById(android.R.id.content);
        }
        if (coordinatorLayout != null) {
            Snackbar.make(coordinatorLayout, getString(R.string.press_again_to_exit), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    @Override
    public void webClientError(ReceivedError failure) {

    }


    //============================== WEBVIEW ===================

    @Override
    public void closeApplication() {
        finish();
    }

    @Override
    public void copyToClipboard(String value) {
        Activity context = this;
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText("Copied Text", "" + value);
            clipboard.setPrimaryClip(clip);
        }
        String tmp = String.format(context.getString(R.string.data_to_clipboard), value);
//        Toasty.custom(context, tmp,
//                ComV19.getDrawable(context, R.drawable.ic_info),
//                ContextCompat.getColor(context, R.color.colorPrimaryDark),
//                ContextCompat.getColor(context, R.color.white), Toasty.LENGTH_SHORT, true, true).show();

        //Toast.makeText(context, tmp, Toast.LENGTH_SHORT).show();

//        View coordinatorLayout = findViewById(com.walhalla.webview.R.id.coordinatorLayout);
//        if (coordinatorLayout != null) {
        View coordinatorLayout = context.findViewById(android.R.id.content);
//        }
        if (coordinatorLayout != null) {
            Snackbar.make(coordinatorLayout, tmp, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onConfirmation(boolean allowed, String[] resources) {
        presenter.onConfirmation__(allowed, resources);
    }
}