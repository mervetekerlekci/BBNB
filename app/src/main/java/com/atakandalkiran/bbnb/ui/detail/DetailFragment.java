package com.atakandalkiran.bbnb.ui.detail;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentDetailBinding;
import com.atakandalkiran.bbnb.db.CardDetailsModel;

public class DetailFragment extends BaseFragment {

    FragmentDetailBinding binding;
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        if (getArguments() != null) {
            CardDetailsModel cardDetails = getArguments().getParcelable("cardDetails");

            // Now you have access to the cardDetails object, you can update the UI accordingly
            if (cardDetails != null) {
                binding.setCard(cardDetails);
            }
        }

        return binding.getRoot();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackgroundRight();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_detail;
    }

    private void setBackgroundRight() {
        int colorTop = ContextCompat.getColor(requireContext(), R.color.dusty_bluish_teal);
        int colorBottom = ContextCompat.getColor(requireContext(), R.color.grayish_dusty_rose);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{colorTop, colorBottom});

        View view = requireView();
        if (view instanceof LinearLayoutCompat) {
            ((LinearLayoutCompat) view).setBackground(gradientDrawable);
        }
    }
}