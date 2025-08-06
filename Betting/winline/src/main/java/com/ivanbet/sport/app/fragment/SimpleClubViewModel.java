package com.ivanbet.sport.app.fragment;

import com.ivanbet.sport.app.model.ClubViewModel;
import com.ivanbet.sport.app.model.ViewModel;
import com.ivanbet.sport.app.FileUtils;

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
