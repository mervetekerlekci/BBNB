package com.atakandalkiran.bbnb.ui.home;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.atakandalkiran.bbnb.db.CardDetailsModel;
import com.atakandalkiran.bbnb.util.CardClickListener;
import com.atakandalkiran.bbnb.util.CardItemViewHolder;

import java.util.List;

public class HomeAdapter extends ListAdapter<CardDetailsModel, CardItemViewHolder> {

    private final CardClickListener clickListener;
    private CardClickListener deleteButtonClickListener;

    public HomeAdapter(CardClickListener clickListener) {
        super(new CardItemDiffCallback());
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CardItemViewHolder.from(parent);
    }

    @Override
    public void onCurrentListChanged(@NonNull List<CardDetailsModel> previousList, @NonNull List<CardDetailsModel> currentList) {
        super.onCurrentListChanged(previousList, currentList);
        clickListener.onCurrentChanged();
    }

    @Override
    public void onBindViewHolder(CardItemViewHolder holder, int position) {
        holder.bind(clickListener, deleteButtonClickListener, getItem(position));
    }

    public void setDeleteButtonClickListener(CardClickListener listener) {
        this.deleteButtonClickListener = listener;
    }
}

class CardItemDiffCallback extends DiffUtil.ItemCallback<CardDetailsModel> {
    @Override
    public boolean areItemsTheSame(CardDetailsModel oldItem, CardDetailsModel newItem) {
        return oldItem.getCardTitle().equals(newItem.getCardTitle());
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(CardDetailsModel oldItem, @NonNull CardDetailsModel newItem) {
        return oldItem.equals(newItem);
    }
}
