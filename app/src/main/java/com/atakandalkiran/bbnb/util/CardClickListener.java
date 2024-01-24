package com.atakandalkiran.bbnb.util;

import android.view.View;

import com.atakandalkiran.bbnb.db.CardDetailsModel;

public interface CardClickListener {
    void onCardClick(View view, CardDetailsModel cardDetails);

    void onDeleteButtonClicked(CardDetailsModel cardDetails);

    void onCurrentChanged();
}
