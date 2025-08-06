package com.example.android.myapplicationbrowser.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dataset {

    @SerializedName("_url_for_open")
    @Expose
    public String urlForOpen;
    @SerializedName("open_without_proxy")
    @Expose
    public boolean openWithoutProxy;
    @SerializedName("proxies")
    @Expose
    public List<MProxy> proxies = new ArrayList<>();

    public Dataset(String urlForOpen, boolean openWithoutProxy, List<MProxy> proxies) {
        this.urlForOpen = urlForOpen;
        this.openWithoutProxy = openWithoutProxy;
        this.proxies = proxies;
    }

    public Dataset() {}

    @Override
    public String toString() {
        return "{" +
                "urlForOpen='" + urlForOpen + '\'' +
                ", openWithoutProxy=" + openWithoutProxy +
                ", proxies=" + proxies +
                '}';
    }
}
