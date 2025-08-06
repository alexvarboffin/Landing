package com.sport.matches.winline.model;

import java.io.Serializable;

public class ButtonViewModel implements ViewModel, Serializable {

    public String title;
    public String url;


    public ButtonViewModel(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
