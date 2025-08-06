package com.sport.matches.winline.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.footballdeluxe.goldсup.R;
import com.sport.matches.winline.model.ClubViewModel;

public class ClubViewHolder extends RecyclerView.ViewHolder {

    // 1. Declare your Views here
    private final TextView tvDate;
    private final TextView tvSite;
    private final TextView tvMail;
    private final TextView tvStadium;
    private final TextView tvName_club;
    //private TextView tvLogo_club;
    private final TextView tvCoach;
    private final TextView tvURL;
    private final TextView tvPresident;


    public ClubViewHolder(View v) {
        super(v);

        // 2. Define your Views here

        tvDate = v.findViewById(R.id.tv_date);
        tvSite = v.findViewById(R.id.tv_site);
        tvMail = v.findViewById(R.id.tv_mail);
        tvStadium = v.findViewById(R.id.tv_stadium);
        tvName_club = v.findViewById(R.id.tv_name_club);
        //tvLogo_club = v.findViewById(R.id.tv_logo_club);
        tvCoach = v.findViewById(R.id.tv_coach);
        tvURL = v.findViewById(R.id.tv_URL);
        tvPresident = v.findViewById(R.id.tv_president);
    }

    @SuppressLint("SetTextI18n")
    public void bind(ClubViewModel model, ComplexAdapter.ChildItemClickListener mCallback) {
        tvDate.setText("" + model.date);
        tvSite.setText(""+model.site);
        tvMail.setText("" + model.mail);
        tvStadium.setText("" + model.stadium);
        tvName_club.setText("" + model.nameClub);
        //tvLogo_club.setText("" + model.logoClub);
        tvCoach.setText("" + model.coach);
        tvURL.setText("" + model.url);
        tvPresident.setText("" + model.president);


        itemView.setOnClickListener(v -> mCallback.clubSelected(model));
    }

}