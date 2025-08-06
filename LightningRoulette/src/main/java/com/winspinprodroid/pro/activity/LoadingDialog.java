package com.winspinprodroid.pro.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;

import com.winspinprodroid.pro.R;

//public class LoadingDialog {
//
//    private final Dialog dialog;
//
//    public LoadingDialog(Context context) {
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.loading_dialog);
//
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setBackgroundDrawableResource(android.R.color.transparent);
////            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        }
//    }
//
//    public void show() {
//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//        }
//    }
//
//    public void dismiss() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
//}