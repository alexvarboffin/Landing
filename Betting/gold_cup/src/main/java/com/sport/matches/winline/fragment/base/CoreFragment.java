package com.sport.matches.winline.fragment.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sport.matches.winline.model.ViewModel;

public abstract class CoreFragment extends Fragment {

    protected Callback mCallback;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            this.mCallback = (Callback) context;
        } else {
            throw new RuntimeException(context + " must implement Callback");
        }
    }

    //Callback
    public interface Callback {
        void onClick(ViewModel navItem);
        void showToolbar(boolean visible);
    }
}
