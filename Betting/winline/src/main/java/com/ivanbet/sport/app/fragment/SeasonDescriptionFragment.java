package com.ivanbet.sport.app.fragment;

import android.content.Context;
import android.os.Bundle;

import com.ivanbet.sport.app.adapter.SimpleText2Horizontal;
import com.ivanbet.sport.app.adapter.SimpleText2Verical;
import com.ivanbet.sport.app.fragment.base.CoreListFragment;
import com.ivanbet.sport.app.model.ButtonViewModel;
import com.ivanbet.sport.app.model.SeasonViewModel;
import com.ivanbet.sport.app.model.SimpleText;
import com.ivanbet.sport.app.model.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class SeasonDescriptionFragment extends CoreListFragment {

    private static final String ARG_PARAM1 = "key_model";
    private SeasonViewModel mParam1;

    public SeasonDescriptionFragment() {}

    public static SeasonDescriptionFragment newInstance(SeasonViewModel param1) {
        SeasonDescriptionFragment fragment = new SeasonDescriptionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (SeasonViewModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    @Override
    protected List<ViewModel> generateList(Context context) {
        List<ViewModel> _a = new ArrayList<>();

        _a.add(new SimpleText("Сезон " + mParam1.title));
        _a.add(new SimpleText2Horizontal("Финалист: ", mParam1.finalist));


        _a.add(new ButtonViewModel("Детальнее на вики про финалиста", mParam1.url));//-
        _a.add(new ButtonViewModel("Детальнее на вики", mParam1.url));

        _a.add(new SimpleText2Verical("Место проведения игры: ", mParam1.location));
        _a.add(new SimpleText2Verical("Дата проведения игры: ", mParam1.location));
        _a.add(new SimpleText2Verical("Победитель: ", mParam1.winner));
        _a.add(new SimpleText2Verical("Лучший бомбардир: ", mParam1.top_scorer));
        return _a;
    }
}