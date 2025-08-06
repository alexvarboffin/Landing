package com.stalpro.dashboard.admin.pojo;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stalpro.dashboard.admin.pagination.CatItem;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class Dataset implements Serializable {

    @SerializedName("items")
    @Expose
    public ArrayList<CatItem> items = new ArrayList<>();

    public Dataset() {
    }
}
