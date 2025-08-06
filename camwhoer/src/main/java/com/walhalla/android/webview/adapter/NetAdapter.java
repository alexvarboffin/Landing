package com.walhalla.android.webview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.android.R;
import com.walhalla.android.webview.UResource;
import com.walhalla.utils.DLog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NetAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final boolean SKIP = false;


    private final RowViewHolder.RowViewHolderCallback callback;

    private final Context context;
    private final List<ViewModel> items = new ArrayList<>();
    private final Map<String, Integer> arr;

    public static final int VIEW_TYPE_EMPTY = 101;
    private static final int VIEW_ITEM_STATUS = 102;


    public NetAdapter(Context context, Map<String, Integer> arr, RowViewHolder.RowViewHolderCallback callback) {
        this.callback = callback;
        this.context = context;
        this.arr = arr;
    }


    private int wrap(String data) {

        int res = -1;
        try {
            data = java.net.URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }

        for (Map.Entry<String, Integer> entry : arr.entrySet()) {
            if (data.contains(entry.getKey())) {
                res = entry.getValue();
                break;
            }
        }
        return res;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == VIEW_ITEM_STATUS) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1, parent, false);
            return new RowViewHolder(callback, mView);
        }
        else {
            //VIEW_TYPE_EMPTY:
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_empty, parent, false);
            return new EmptyViewHolder(view1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = holder.getItemViewType();
        if (viewType == VIEW_ITEM_STATUS) {
            ((RowViewHolder) holder).bind((UResource) items.get(position));
        } else if (viewType == VIEW_TYPE_EMPTY) {
            EmptyViewHolder vh2 = (EmptyViewHolder) holder;
            vh2.bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof UResource) {
            return VIEW_ITEM_STATUS;
        } else if (items.get(position) instanceof EmptyViewModel) {
            return VIEW_TYPE_EMPTY;
        }
        return -1;
    }

    public void swap(EmptyViewModel emptyViewModel) {
        //Log.i(TAG, "swap: " + message.toString());
        this.items.clear();
        this.items.addAll(Collections.singleton(emptyViewModel));
        this.notifyDataSetChanged();
    }

    public void swap(String raw) {
        int aa = wrap(raw);
        if (aa > -1) {
            UResource o = new UResource(aa, raw);
            this.items.add(o);
            this.notifyDataSetChanged();
            DLog.d("00" + raw);
        } else if (aa == ChangesObj.REMOVED) {

        } else if (!SKIP) {
            UResource o = new UResource(R.color.u_default, raw);
            this.items.add(o);
            this.notifyDataSetChanged();
            DLog.d("00");
        }
    }

    public void clear() {
        this.items.clear();
        this.notifyDataSetChanged();
    }


    public String getText() {
        if (items.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ViewModel datum : items) {
            if (datum instanceof UResource) {
                sb.append(((UResource) datum).data).append((char) 10);
            }

        }
        return sb.toString();
    }

    public void rebuild(Map<String, Integer> arr) {
        this.arr.clear();
        this.arr.putAll(arr);

    }
}