package com.advear.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.advear.client.R;
import com.advear.client.databinding.FragmentSessionBinding;
import com.advear.client.newweb.SessionConfig;
import com.advear.client.newweb.WebViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class WebViewFragment1 extends Fragment {

    private static final String ARG_SESSION_CONFIG = "session_config";

    private SessionConfig sessionConfig;
    private FragmentSessionBinding binding;

    // Static factory method to create a new instance of the fragment with a SessionConfig
    public static WebViewFragment1 newInstance(SessionConfig sessionConfig) {
        WebViewFragment1 fragment = new WebViewFragment1();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SESSION_CONFIG, sessionConfig);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            sessionConfig = getArguments().getParcelable(ARG_SESSION_CONFIG);
            if (sessionConfig == null) {
                throw new IllegalArgumentException("SessionConfig must be provided to WebViewFragment1.");
            }
        } else {
            throw new IllegalArgumentException("Arguments must be provided to WebViewFragment1.");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSessionBinding.inflate(inflater, container, false);


        List<SessionConfig> configs = Arrays.asList(
                new SessionConfig("https://google.com", getSessionConfig().getSessionName(),
                        getSessionConfig().getPort()),
                new SessionConfig("https://google.com", getSessionConfig().getSessionName(),
                        getSessionConfig().getPort()),
                new SessionConfig("https://google.com", getSessionConfig().getSessionName(),
                        getSessionConfig().getPort())
        );


        // Initialize the adapter with the configurations
        WebViewPagerAdapter adapter = new WebViewPagerAdapter(getActivity(), configs);
        binding.viewPager.setOffscreenPageLimit(configs.size());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        // Link TabLayout and ViewPager2 together
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            tab.setText(String.valueOf(position));
        }).attach();
        return binding.getRoot();
    }


    protected SessionConfig getSessionConfig() {
        return sessionConfig;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Handle cookies if necessary
    }



    public static class WebViewPagerAdapter extends FragmentStateAdapter {

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
                return WebViewFragmentOld.newInstance(config);
            }
            // Handle default case if position is out of bounds
            return WebViewFragmentOld.newInstance(new SessionConfig("default_url", "default_session", 0));
        }

        @Override
        public int getItemCount() {
            return fragmentConfigs.size();
        }
    }
}
