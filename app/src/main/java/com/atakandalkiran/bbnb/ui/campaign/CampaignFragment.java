package com.atakandalkiran.bbnb.ui.campaign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentCampaignBinding;

public class CampaignFragment extends BaseFragment {

    FragmentCampaignBinding binding;

    @Override
    protected void setupUI() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);

        Button joinButton = view.findViewById(R.id.joinButton);
        Button joinButton1 = view.findViewById(R.id.joinButton1);
        Button joinButton2 = view.findViewById(R.id.joinButton2);
        Button joinButton3 = view.findViewById(R.id.joinButton3);

        final String buttonText = "Kampanyaya Katıldınız!";

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(buttonText);
                ((Button) v).setText("Kampanyaya Katıldınız");
                ((Button) v).setEnabled(false);
            }
        };

        joinButton.setOnClickListener(clickListener);
        joinButton1.setOnClickListener(clickListener);
        joinButton2.setOnClickListener(clickListener);
        joinButton3.setOnClickListener(clickListener);

        return view;
    }
    @Override
    protected int getLayoutResId() {
        return 0;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
