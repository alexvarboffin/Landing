package com.sport.matches.winline.fragment;

import android.content.Context;
import android.view.View;


import com.footballdeluxe.goldсup.R;
import com.sport.matches.winline.fragment.base.CoreListFragment;
import com.sport.matches.winline.model.HeaderObject;
import com.sport.matches.winline.model.SeasonViewModel;
import com.sport.matches.winline.model.SimpleModel;
import com.sport.matches.winline.model.ViewModel;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sport.matches.winline.app.FileUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SeasonsListFragment
        extends CoreListFragment implements View.OnClickListener {

    //private SquareImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;


    @Override
    public void onResume() {
        super.onResume();
        mCallback.showToolbar(true);
    }

    public SeasonsListFragment() {}

    @Override
    public void onClick(View view) {
//        TextView text1 = view.findViewById(R.id.textView1); // ссылка на текст
//        if (this.mCallback != null) {
//            this.mCallback.onClick(view);
//        }
    }

    @Override
    protected List<ViewModel> generateList(Context context) {
        
        List<ViewModel> data = new ArrayList<>();
        data.add(new SimpleModel(R.string.header01, R.drawable.ic_promo_0, 0, 44, 0));

        List<SeasonViewModel> dataset;
        Type type = new TypeToken<List<SeasonViewModel>>() {
        }.getType();
        dataset = new GsonBuilder().create().fromJson(
                FileUtils.loadJSONFromAsset(context, "seasons.json"), type);

        if (dataset != null) {
            for (int i = 0; i < dataset.size(); i++) {
                SeasonViewModel model = dataset.get(i);
                data.add(model);//Кубок Украины по футболу
            }
        }


//        data.add(new SimpleModel(R.string.a2021, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2020, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2019, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2018, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2017, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2016, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2015, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2014, R.drawable.ic_promo_0, 0, 44, 0));
//        data.add(new SimpleModel(R.string.a2013, R.drawable.ic_promo_0, 0, 44, 0));


//        data.add(new MenuKey(0, R.drawable.button_blue, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_blue, 0, 44, 0));

//        data.add(new SimpleModel(R.string.title_drum_pad_machine, R.drawable.ic_menu_burppad, 0, 44, 0));
//        data.add(new SimpleModel(R.string.title_activity_woopie, R.drawable.ic_whoopee_cushion_1, 0, 44, 0));
//        data.add(new SimpleModel(R.string.action_rate_app, R.drawable.ic_promo_0, 0, 44, 0));

//        data.add(new MenuKey(R.string.lbl_chat, R.drawable.ic_chat, 0, 44, 0));
//        data.add(new MenuKey(R.string.lbl_send, R.drawable.ic_mail, 0, 44, 0));


        //data.add(new HeaderObject(R.string.a2020, "", "", R.drawable.ic_more_vert_black_24dp));
        ///...
        data.add(new HeaderObject(R.string.a2010, "", "", R.drawable.ic_more_vert_black_24dp));


//        data.add(new GooglePlayViewModel(R.string.app3, "", "com.walhalla.vibro", R.drawable.ic_promo_2));
//        data.add(new GooglePlayViewModel(R.string.app1, "", "com.walhalla.ttloader", R.drawable.ic_promo_0));
//        data.add(new GooglePlayViewModel(R.string.app2, "", "com.walhalla.mtprotolist", R.drawable.ic_promo_1));
//
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_pink, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_pink, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_green, 0, 44, 0));
//        data.add(new MenuKey(0, R.drawable.button_pink, 0, 44, 0));

        return data;
    }
}
