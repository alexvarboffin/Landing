package ai;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.UWView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.walhalla.landing.activity.WebActivity;
import com.walhalla.webview.ReceivedError;

public class a extends WebActivity {


    private UWView mWebView;
    private SwipeRefreshLayout swipe;

    private boolean isSwipeEnabled = true;

//    @Override
//    public void setContentView(View view) {
//        super.setContentView(view);
//    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new UWView(this);
        mWebView.setLayoutParams(lp);
        if (isSwipeEnabled) {
            swipe = new SwipeRefreshLayout(this);
            swipe.setLayoutParams(lp);
            setContentView(swipe);
            swipe.addView(mWebView);
            swipe.setRefreshing(false);
            swipe.setOnRefreshListener(() -> {
                swipe.setRefreshing(false);
                mWebView.reload();
            });
        } else {
            setContentView(mWebView);
        }
        getPresenter0().a123(this, mWebView);
        SinglePagination v0 = new SinglePagination();
        mWebView.loadUrl(v0.getUrl0());
    }

    @Override
    public void onPageStarted(String url) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void setErrorPage(ReceivedError receivedError) {
        switchViews(true);
    }

    @Override
    public void removeErrorPage() {
        switchViews(false);
    }

    public void switchViews(boolean b) {
//        if (b) {
//            binding.contentFake.setVisibility(View.VISIBLE);
//            binding.contentMain.setVisibility(View.GONE);
//            //getSupportActionBar().setTitle("...");
//        } else {
//            binding.contentFake.setVisibility(View.GONE);
//            binding.contentMain.setVisibility(View.VISIBLE);
//            //getSupportActionBar().setTitle(R.string.app_name);
//        }
    }
}
