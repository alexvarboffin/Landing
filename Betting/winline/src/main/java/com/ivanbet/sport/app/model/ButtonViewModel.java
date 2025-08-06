package com.ivanbet.sport.app.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class ButtonViewModel implements ViewModel, Serializable {

    public String title;
    public String url;


    public ButtonViewModel(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
