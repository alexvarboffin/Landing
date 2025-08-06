package com.ivanbet.sport.app.model;

import androidx.annotation.Keep;

@Keep
public class SimpleCardViewModel implements ViewModel {

    public String total_win;
    public String img;

    public SimpleCardViewModel(String total_win, String img) {
        this.total_win = total_win;
        this.img = img;
    }
}
