package com.walhalla.android.webview;


import com.walhalla.android.webview.adapter.ViewModel;

public class UResource implements ViewModel {

    public final int type;
    public final String data;

    public UResource(int type, String url) {
        this.type = type;
        this.data = url;
    }
}
