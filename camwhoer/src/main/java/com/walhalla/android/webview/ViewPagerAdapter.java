package com.walhalla.android.webview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter
        extends
        //FragmentStatePagerAdapter/*FragmentPagerAdapter */
        FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    private final List<Fragment> mFragmentList = new ArrayList<>();


    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    //@Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //@Override
    public int getCount() {
        return mFragmentList.size();
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return super.getItemPosition(object);
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getItem(position);
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
