package com.atakandalkiran.bbnb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.atakandalkiran.bbnb.db.CardDetailsModel;
import com.atakandalkiran.bbnb.db.UserDao;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<CardDetailsModel>> _cardProperties = new MutableLiveData<>();
    public LiveData<List<CardDetailsModel>> cardProperties = _cardProperties;

    private final UserDao userDao;
    private final Integer userId;



    public HomeViewModel(UserDao userDao, Integer userId) {
        this.userDao = userDao;
        this.userId = userId;
        loadCardDetails();
    }

    public LiveData<List<CardDetailsModel>> getCardPropertiesLiveData() {
        return cardProperties;
    }

    private void loadCardDetails() {
        LiveData<List<CardDetailsModel>> cardDetailsLiveData = userDao.getCardDetailsByUserId(userId);

        cardDetailsLiveData.observeForever(cardDetails -> {
            _cardProperties.setValue(cardDetails);
        });
    }

     void deleteSavedCard(CardDetailsModel cardInformations) {
        userDao.DeleteCardInformations(cardInformations);
    }
}
