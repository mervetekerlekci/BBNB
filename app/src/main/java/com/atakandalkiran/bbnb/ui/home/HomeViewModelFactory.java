package com.atakandalkiran.bbnb.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.atakandalkiran.bbnb.db.UserDao;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final Integer userId;
    private final UserDao userDao;

    public HomeViewModelFactory(Context context, Integer userId, UserDao userDao) {
        this.context = context;
        this.userId = userId;
        this.userDao = userDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(userDao, userId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
