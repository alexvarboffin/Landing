package com.walhalla.android.webview.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.walhalla.android.R;

public class EmptyViewHolder extends RecyclerView.ViewHolder {

    //private final TextView response;
    private final TextView error_msg;

    public EmptyViewHolder(View v2) {
        super(v2);
        //response = v2.findViewById(R.id.response);
        error_msg = v2.findViewById(R.id.tv_error_msg);
    }

    public void bind(Object o) {
        EmptyViewModel error = (EmptyViewModel) o;
        if (error != null) {
            error_msg.setText(error.error);
        }
    }
}
