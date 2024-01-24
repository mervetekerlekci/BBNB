package com.atakandalkiran.bbnb.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.atakandalkiran.bbnb.db.User;

public class RegisterViewModel extends ViewModel {
    LiveData<User> navigateToRegister;
}
