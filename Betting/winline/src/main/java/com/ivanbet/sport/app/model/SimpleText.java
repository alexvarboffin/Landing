package com.ivanbet.sport.app.model;

import androidx.annotation.Keep;

@Keep
public class SimpleText implements ViewModel{

    public SimpleText(String text1) {
        this.text1 = text1;
    }

    public String text1;
}
