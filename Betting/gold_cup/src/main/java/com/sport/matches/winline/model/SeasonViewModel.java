package com.sport.matches.winline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SeasonViewModel implements ViewModel, Serializable {

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("URL")
    @Expose
    public String url;

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("winner")
    @Expose
    public String winner;

    @SerializedName("score")
    @Expose
    public String score;
    @SerializedName("finalist")
    @Expose
    public String finalist;
    @SerializedName("top_scorer")
    @Expose
    public String top_scorer;

    public SeasonViewModel(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
