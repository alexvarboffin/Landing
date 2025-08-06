package com.sport.matches.winline.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.footballdeluxe.goldсup.R;
import com.sport.matches.winline.adapter.ComplexAdapter;
import com.sport.matches.winline.model.ClubViewModel;
import com.sport.matches.winline.model.ViewModel;

import java.util.List;

public abstract class CoreListFragment extends CoreFragment {
    protected RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recyclerView = view.findViewById(R.id.recyclerView);

        ComplexAdapter adapter = new ComplexAdapter(getContext(), generateList(getContext()), new ComplexAdapter.ChildItemClickListener() {
            @Override
            public void menuNavigation(ViewModel navItem) {
                if (mCallback != null) {
                    mCallback.onClick(navItem);
                }
            }

            @Override
            public void clubSelected(ClubViewModel obj) {
                if (mCallback != null) {
                    mCallback.onClick(obj);
                }
            }
        });

        int span_count = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span_count);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            //            @Override
//            public int getSpanSize(int position) {
//                return (3 - position % 3);
//            }
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {

                    case ComplexAdapter.TYPE_HEADER:
                        return span_count;

                    case ComplexAdapter.TYPE_SIMPLE:
                        return span_count;

                    case ComplexAdapter.TYPE_SIMPLE_CARD:
                        return span_count;

                    case ComplexAdapter.TYPE_SIMPLE_CLUBVIEWMODEL:
                        return span_count;

                    case ComplexAdapter.TYPE_SIMPLE_TEXT_2_H:
                    case ComplexAdapter.TYPE_SIMPLE_TEXT_2_V:
                    case ComplexAdapter.TYPE_SIMPLE_TEXT:
                        return span_count;

                    case ComplexAdapter.T_SIMPLE_BUTTON:
                        return span_count;

                    case ComplexAdapter.TYPE_CLUB:
                        return span_count;

                    case ComplexAdapter.TYPE_SEASON:
                        return span_count;

                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(2, 3));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    protected abstract List<ViewModel> generateList(Context context);

}
