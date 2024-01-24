package com.atakandalkiran.bbnb.util;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.atakandalkiran.bbnb.db.CardDetailsModel;
import com.atakandalkiran.bbnb.ui.home.HomeAdapter;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter("listData")
    public static void bindRecyclerView(RecyclerView recyclerView, List<CardDetailsModel> data) {
        HomeAdapter adapter = (HomeAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.submitList(data);
        }
    }
}
