package com.atakandalkiran.bbnb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.atakandalkiran.bbnb.databinding.ActivityEntranceBinding;


public class EntranceActivity extends AppCompatActivity {
    
    private ActivityEntranceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEntranceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}