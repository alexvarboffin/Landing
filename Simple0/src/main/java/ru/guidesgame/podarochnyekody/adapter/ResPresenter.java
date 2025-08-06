package ru.guidesgame.podarochnyekody.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import ru.guidesgame.podarochnyekody.presenter.AbstractPresenter;
import ru.guidesgame.podarochnyekody.presenter.MainContract;

import com.walhalla.landing.pagination.CatItem;
import com.walhalla.ui.DLog;

import java.util.ArrayList;


public class ResPresenter extends AbstractPresenter {



    private ArrayList<CatItem> categories;


    public ResPresenter(Context context, MainContract.View view) {
        super(view);
    }

    public void fetchRemoteConfig(Context context) {
        String[] urls = context.getResources().getStringArray(com.walhalla.landing.R.array.navigation_url_list);
        String[] titles = context.getResources().getStringArray(com.walhalla.landing.R.array.navigation_title_list);

        // icon list
        TypedArray iconTypedArray = context.getResources().obtainTypedArray(com.walhalla.landing.R.array.navigation_icon_list);
        Integer[] icons = new Integer[iconTypedArray.length()];
        for (int i = 0; i < iconTypedArray.length(); i++) {
            icons[i] = iconTypedArray.getResourceId(i, -1);
        }
        iconTypedArray.recycle();

        categories = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {
            categories.add(new CatItem(i, titles[i], urls[i], icons[i]));
        }

        mView.setupNavigationMenus(categories, null);
    }

    @Override
    public void onNavigationItemSelected(String itemId) {
        String url = getUrlForNavigationItem(itemId);
        DLog.d("" + itemId + " " + url);
        mView.loadUrlInWebView(url);
    }

    public String getUrlForNavigationItem(String title) {
        if (categories != null) {
            for (CatItem item : categories) {
                if (item.title.equals(title)) {
                    return item.url;
                }
            }
        }
        return null;
    }

    @Override
    public void onBottomNavigationItemSelected(String itemId) {
        //none
    }
}