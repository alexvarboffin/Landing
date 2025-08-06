package com.walhalla.android.webview.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.walhalla.android.R;

import com.walhalla.android.webview.adapter.ConMsgViewHolder;
import com.walhalla.android.webview.adapter.EmptyViewModel;
import com.walhalla.android.webview.adapter.ConsAdapter;
import com.walhalla.android.databinding.FragmentConsoleBinding;
import com.walhalla.android.webview.viewmodel.ConMsg;
import com.walhalla.webview.WVTools;

import java.util.HashMap;
import java.util.Map;


public class F_Console extends Fragment {
    private FragmentConsoleBinding mBinding;
    private ConsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_console, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mBinding.out.setMovementMethod(new ScrollingMovementMethod());
//        mBinding.out.setTextIsSelectable(true);
//        mBinding.out.setCursorVisible(true);
//        mBinding.out.setVerticalScrollBarEnabled(true);

        Map<String, Integer> arr = new HashMap<>();
        adapter = new ConsAdapter(getContext(), arr, new ConMsgViewHolder.ConMsgViewHolderCallback()
        {

            @Override
            public void copyItem(ConMsg obj) {
                WVTools.copyToClipboard0(getContext(), obj.mMessage);
            }

            @Override
            public void shareText(ConMsg obj) {
                WVTools.shareText(getContext(), obj.mMessage);
            }

            @Override
            public void openHtmlViewer(String url) {
                Toast.makeText(getContext(), "@@", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestViewIntent(String data) {
                Toast.makeText(getContext(), "@@", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        mBinding.recyclerView.setAdapter(adapter);
        adapter.swap(new EmptyViewModel("Console Log Empty"));
    }

//        DLog.d("{Console} " + message.message()
//                + " -- From line " + message.lineNumber()
//                + " of " + message.sourceId());

    public void println(ConsoleMessage message) {
        if (getActivity() != null) {
            adapter.swap(ConMsg.wrap(message));
        }
    }

    public void println(ConMsg data) {
        if (getActivity() != null && isAdded()) {
//            String tmp = (char) 10 + data;
//            if (mPattern.matcher(tmp).find()) {
//                SpannableString highlightString = new SpannableString(tmp);
//                highlightString.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark))
//                        , 0, tmp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//            }
//            mBinding.out.append(tmp);
            adapter.swap(data);
        }
    }

    public void clearLog() {
        if (getActivity() != null && adapter != null) {
//            String tmp = (char) 10 + data;
//            if (mPattern.matcher(tmp).find()) {
//                SpannableString highlightString = new SpannableString(tmp);
//                highlightString.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark))
//                        , 0, tmp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//            }
//            mBinding.out.append(tmp);
            adapter.clear();
        }
    }
}
