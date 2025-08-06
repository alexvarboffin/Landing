package com.example.android.myapplicationbrowser.repository;

import android.os.Handler;

public abstract class BaseController {

    private final MainView m;
    protected final android.os.Handler handler;

    protected BaseController(MainView m, Handler handler) {
        this.m = m;
        this.handler = handler;
    }

    public interface MainView {
        void sendEmptyMessage(int i);
    }

    public abstract void load_proxies();

}
