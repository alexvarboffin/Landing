package com.walhalla.android.webview.fragments;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.android.R;

import com.walhalla.android.webview.adapter.NetAdapter;
import com.walhalla.android.webview.ui.activities.ManifestExplorer;
import com.walhalla.android.webview.adapter.ChangesObj;
import com.walhalla.android.webview.adapter.EmptyViewModel;
import com.walhalla.android.databinding.FragmentConsoleBinding;
import com.walhalla.android.webview.UResource;
import com.walhalla.android.webview.adapter.RowViewHolder;
import com.walhalla.webview.WVTools;
import com.yandex.metrica.YandexMetrica;

import java.util.Map;
import java.util.regex.Pattern;

public class F_Network extends Fragment implements RowViewHolder.RowViewHolderCallback {

    private FragmentConsoleBinding mBinding;
    private Pattern mPattern = Pattern.compile("stream|m3u8");
    private NetAdapter adapter;

    private SharedPreferences var0;
    private Map<String, Integer> arr;

    private final SharedPreferences.OnSharedPreferenceChangeListener aaa = (sharedPreferences, key) -> {
        if (SettingsPreferenceFragment.PREF_LABEL.equals(key)) {
            arr = ChangesObj.handleChanges(getActivity());
            if (getActivity() != null && isAdded()) {
                adapter.rebuild(arr);
            }
        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var0 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        var0.registerOnSharedPreferenceChangeListener(aaa);
    }

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
        arr = ChangesObj.handleChanges(getActivity());
        adapter = new NetAdapter(getContext(),arr, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        mBinding.recyclerView.setAdapter(adapter);
        adapter.swap(new EmptyViewModel("Network Log Empty"));
    }

    @Override
    public void onResume() {
        super.onResume();
        //DLog.d("@@@@@@@@");
    }

    public F_Network() {
    }

    public void println(String data) {
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

    @Override
    public void copyItem(UResource obj) {
        WVTools.copyToClipboard0(getActivity(), obj.data);
    }

    @Override
    public void shareText(UResource obj) {
        WVTools.shareText(getActivity(), obj.data);
    }

    @Override
    public void openHtmlViewer(String resource) {
        Intent i = ManifestExplorer.newIntent(getActivity(), resource);
        startActivity(i);
        String eventParameters = "{\"resource\":\"" + resource + "\"}";
        YandexMetrica.reportEvent("resource", eventParameters);
    }

    @Override
    public void requestViewIntent(String url) {
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(resource));
//        startActivity(i);
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getActivity().startActivity(intent);

        //String eventParameters = "{\"resource\":\"" + url + "\"}";
        //YandexMetrica.reportEvent("resource", eventParameters);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_network, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            String aa = adapter.getText();
            WVTools.shareText(getActivity(), aa);
            return false;
        } else if (id == R.id.action_copy) {
            String aa = adapter.getText();
            WVTools.copyToClipboard0(getActivity(), aa);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        var0.unregisterOnSharedPreferenceChangeListener(aaa);
    }
}
