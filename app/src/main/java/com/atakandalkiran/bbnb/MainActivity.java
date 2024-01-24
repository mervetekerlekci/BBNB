package com.atakandalkiran.bbnb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import com.atakandalkiran.bbnb.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavigation();
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navHostFragment);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        int userId = getIntent().getIntExtra("userId", 0);

        Bundle args = new Bundle();
        args.putInt("userId", userId);

        navController.navigate(R.id.homeFragment, args);

        NavigationUI.setupWithNavController(binding.bottomNavigationBar, navController);
    }
}