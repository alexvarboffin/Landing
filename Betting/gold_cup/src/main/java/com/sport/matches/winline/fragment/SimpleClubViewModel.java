package com.sport.matches.winline.fragment;

import com.sport.matches.winline.model.ClubViewModel;
import com.sport.matches.winline.model.ViewModel;
import com.sport.matches.winline.app.FileUtils;

import java.io.Serializable;

public class SimpleClubViewModel implements ViewModel, Serializable {

    public final String logoClub;
    public final String total_win;

    public ClubViewModel clubViewModel;

    public SimpleClubViewModel(ClubViewModel model) {
        clubViewModel = model;
        this.logoClub = FileUtils.getLocalThumbnails(model.logoClub);
        this.total_win = "Количество побед: " + model.total_win;
    }

}
