package com.winspinprodroid;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.winspinprodroid.pro.R;

public class UIUtils {



    public void toaster(View coordinatorLayout) {
        Snackbar.make(coordinatorLayout, R.string.press_again_to_exit, Snackbar.LENGTH_LONG).setAction("X", null).show();
    }
}
