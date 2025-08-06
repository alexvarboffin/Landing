package com.sport.matches.winline.fragment;

import android.content.Context;
import android.os.Bundle;

import com.sport.matches.winline.fragment.base.CoreListFragment;
import com.sport.matches.winline.model.ClubViewModel;
import com.sport.matches.winline.model.ViewModel;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sport.matches.winline.app.FileUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ClubsListFragment extends CoreListFragment {


    private static final String ARG_PARAM1 = "param1";
    private int mParam1;

    public ClubsListFragment() {
        // Required empty public constructor
    }

    public static ClubsListFragment newInstance(int param1) {
        ClubsListFragment fragment = new ClubsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    protected List<ViewModel> generateList(Context context) {
        List<ViewModel> data = new ArrayList<>();

        List<ClubViewModel> dataset;
        Type type = new TypeToken<List<ClubViewModel>>() {
        }.getType();
        dataset = new GsonBuilder().create().fromJson(
                FileUtils.loadJSONFromAsset(context, "csvjson.json"), type);

        if (dataset != null) {
            for (int i = 0; i < dataset.size(); i++) {
                ClubViewModel model = dataset.get(i);
                data.add(new SimpleClubViewModel(model));
                //data.add(model);
            }
        }
        return data;
    }

}