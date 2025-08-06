package com.sport.matches.winline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClubViewModel implements ViewModel, Serializable {

    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("president")
    @Expose
    public String president;
    @SerializedName("coach")
    @Expose
    public String coach;
    @SerializedName("site")
    @Expose
    public String site;
    @SerializedName("mail")
    @Expose
    public String mail;
    @SerializedName("stadium")
    @Expose
    public String stadium;
    @SerializedName("URL")
    @Expose
    public String url;
    @SerializedName("name_club")
    @Expose
    public String nameClub;

    @SerializedName("logo_club")
    @Expose
    public String logoClub;

   @SerializedName("total_win")
    @Expose
    public String total_win;

}
