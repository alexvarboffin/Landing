package com.sport.matches.winline.model;


public class SimpleCardViewModel implements ViewModel {

    public String total_win;
    public String img;

    public SimpleCardViewModel(String total_win, String img) {
        this.total_win = total_win;
        this.img = img;
    }
}
