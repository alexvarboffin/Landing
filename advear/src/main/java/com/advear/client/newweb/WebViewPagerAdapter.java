package com.advear.client.newweb;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.advear.client.fragment.WebViewFragment1;

import java.util.List;

public class WebViewPagerAdapter extends FragmentStateAdapter {

    private final List<SessionConfig> fragmentConfigs;

    public WebViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<SessionConfig> fragmentConfigs) {
        super(fragmentActivity);
        this.fragmentConfigs = fragmentConfigs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position >= 0 && position < fragmentConfigs.size()) {
            SessionConfig config = fragmentConfigs.get(position);
            return WebViewFragment1.newInstance(config);
        }
        // Handle default case if position is out of bounds
        return WebViewFragment1.newInstance(new SessionConfig("default_url", "default_session", 0));
    }

    @Override
    public int getItemCount() {
        return fragmentConfigs.size();
    }
}