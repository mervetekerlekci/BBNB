package com.atakandalkiran.bbnb.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.atakandalkiran.bbnb.databinding.LayoutRecyclerviewCreditCardsBinding;
import com.atakandalkiran.bbnb.db.CardDetailsModel;

public class CardItemViewHolder extends RecyclerView.ViewHolder {

    private final LayoutRecyclerviewCreditCardsBinding binding;
    private final ImageButton deleteButtonClicker;

    public CardItemViewHolder(LayoutRecyclerviewCreditCardsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.deleteButtonClicker = binding.deleteButton;
    }

    public void bind(CardClickListener cardClickListener, CardClickListener deleteButtonClickListener, CardDetailsModel cardDetails) {
        binding.setCard(cardDetails);
        binding.setClickListener(cardClickListener);
        binding.executePendingBindings();

        deleteButtonClicker.setOnClickListener(view -> {
            if (deleteButtonClickListener != null) {
                deleteButtonClickListener.onDeleteButtonClicked(cardDetails);
            }
        });
    }

    public static CardItemViewHolder from(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutRecyclerviewCreditCardsBinding binding = LayoutRecyclerviewCreditCardsBinding.inflate(layoutInflater, parent, false);
        return new CardItemViewHolder(binding);
    }
}

