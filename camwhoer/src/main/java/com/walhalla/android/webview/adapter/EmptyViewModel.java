package com.walhalla.android.webview.adapter;

public class EmptyViewModel implements ViewModel
{

    public EmptyViewModel(String error) {
        this.error = error;
    }

    public String error;

}
