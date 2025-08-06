package com.advear.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.advear.client.newweb.SessionConfig;
import com.advear.client.newweb.WebViewRepository;

public class WebViewFragmentOld extends BaseWebViwFragment {

    private static final String ARG_SESSION_CONFIG = "session_config";

    private SessionConfig sessionConfig;

    // Static factory method to create a new instance of the fragment with a SessionConfig
    public static WebViewFragmentOld newInstance(SessionConfig sessionConfig) {
        WebViewFragmentOld fragment = new WebViewFragmentOld();
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
        binding = WebViewRepository.createWebView1(requireContext());
        return binding;
    }

    @Override
    protected SessionConfig getSessionConfig() {
        return sessionConfig;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Handle cookies if necessary
    }
}
