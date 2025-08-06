package com.ivanbet.sport.app.fragment;

import android.content.Context;
import android.os.Bundle;

import com.ivanbet.sport.app.fragment.base.CoreListFragment;
import com.ivanbet.sport.app.model.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class ClubDescriptionFragment extends CoreListFragment {


    private static final String ARG_PARAM1 = "param1";
    private SimpleClubViewModel mParam1;

    public ClubDescriptionFragment() {}

    public static ClubDescriptionFragment newInstance(SimpleClubViewModel param1) {
        ClubDescriptionFragment fragment = new ClubDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SimpleClubViewModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    @Override
    protected List<ViewModel> generateList(Context context) {
        List<ViewModel> data = new ArrayList<>();
        data.add(mParam1.clubViewModel);
        return data;
    }

}