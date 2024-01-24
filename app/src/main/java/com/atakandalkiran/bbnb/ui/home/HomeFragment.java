package com.atakandalkiran.bbnb.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atakandalkiran.bbnb.EntranceActivity;
import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentHomeBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.CardDetailsModel;
import com.atakandalkiran.bbnb.db.UserDao;
import com.atakandalkiran.bbnb.util.CardClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends BaseFragment implements CardClickListener {

    FragmentHomeBinding binding;
    RecyclerView recyclerView;
    ImageButton delete;

    private HomeAdapter adapter;

    private HomeViewModel viewModel;
    FloatingActionButton cardAdderButton;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        cardAdderButton = binding.addingCardButton;

        binding.setLifecycleOwner(getViewLifecycleOwner());

        adapter = new HomeAdapter(this);
        binding.creditCardsRecyclerView.setAdapter(adapter);
        binding.creditCardsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            int userId = getArguments().getInt("userId", 0);
            UserDao userDao = AppDatabase.getDbInstance(requireContext()).userdao();
            viewModel = new ViewModelProvider(this, new HomeViewModelFactory(requireContext(), userId, userDao))
                    .get(HomeViewModel.class);
        }
        adapter.setDeleteButtonClickListener(new CardClickListener() {
            @Override
            public void onCardClick(View view, CardDetailsModel cardDetails) {}

            @Override
            public void onDeleteButtonClicked(CardDetailsModel cardDetails) {
                viewModel.deleteSavedCard(cardDetails);
            }

            @Override
            public void onCurrentChanged() {}
        });

        if (viewModel != null && viewModel.getCardPropertiesLiveData() != null) {
            viewModel.getCardPropertiesLiveData().observe(getViewLifecycleOwner(), cardDetailsList -> {
                if (cardDetailsList.isEmpty()) {
                    binding.cardViewRV.setVisibility(View.INVISIBLE);
                    binding.thereIsNoCardText.setVisibility(View.VISIBLE);
                } else {
                    binding.cardViewRV.setVisibility(View.VISIBLE);
                    binding.thereIsNoCardText.setVisibility(View.INVISIBLE);
                    adapter.submitList(cardDetailsList);
                }
            });
        }

        cardAdderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments() != null) {
                    int userId = getArguments().getInt("userId", 0);
                    Bundle args = new Bundle();
                    args.putInt("userId", userId);
                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_addingCardFragment, args);
                }
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                logoutProcess(context);
            }
        });

        return binding.getRoot();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    @Override
    protected int getLayoutResId() {
        return  R.layout.fragment_home;
    }

    @Override
    public void onCardClick(View view, CardDetailsModel cardDetails) {
        if(getArguments() != null) {
            int userId = getArguments().getInt("userId", 0);
            Bundle args = new Bundle();
            args.putInt("userId", userId);
            args.putParcelable("cardDetails", cardDetails);
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_detailFragment, args);
        }
    }

    @Override
    public void onDeleteButtonClicked(CardDetailsModel cardDetails) {}

    public void logoutProcess(Context context) {
        if(getArguments() != null) {
            int userId = getArguments().getInt("userId", 0);
            Bundle args = new Bundle();
            args.putInt("userId", userId);
            Intent intent = new Intent(context, EntranceActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    public void onCurrentChanged() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}