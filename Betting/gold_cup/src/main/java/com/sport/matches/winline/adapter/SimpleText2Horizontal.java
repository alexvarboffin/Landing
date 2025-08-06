package com.sport.matches.winline.adapter;

import com.sport.matches.winline.model.ViewModel;

public class SimpleText2Horizontal implements ViewModel {

    public SimpleText2Horizontal(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;

    }

    public String text1;
    public String text2;

}