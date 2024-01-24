package com.atakandalkiran.bbnb.ui.adding_card;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentAddingCardBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.CardDetailsModel;
import com.atakandalkiran.bbnb.ui.email_validation.EmailValidationFragment;

public class AddingCardFragment extends BaseFragment {

    private AddingCardViewModel mViewModel;
    private FragmentAddingCardBinding binding;
    EditText titleName, cardNo;
    Button cancel, save;

    public static AddingCardFragment newInstance() {
        return new AddingCardFragment();
    }

    @Override
    protected void setupUI() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        titleName = binding.cardTitle;
        cardNo = binding.cardNo;
        save = binding.buttonSave;
        cancel = binding.cancelButton;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardNo.getError() != null) {
                    Toast.makeText(getContext(), "Lütfen talimatlarımızı dikkate alınız.", Toast.LENGTH_SHORT).show();
                } else {
                    saveCardDetails();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancellationOfCardAddingProcess();
            }
        });

        cardNo.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        cardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});
        cardNo.addTextChangedListener(new TextWatcher() {
            private boolean formatting;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!formatting) {
                    formatting = true;

                    String cardNumber = editable.toString().replaceAll("\\s", "");

                    if (cardNumber.length() > 0 && cardNumber.length() != 16) {
                        StringBuilder formattedCardNumber = new StringBuilder(cardNumber);
                        for (int i = 4; i < cardNumber.length(); i += 5) {
                            formattedCardNumber.insert(i, " ");
                        }
                        cardNo.setError("Lütfen 16 haneli kart numaranızı giriniz.");

                        if (!TextUtils.equals(cardNumber, formattedCardNumber.toString())) {
                            cardNo.setText(formattedCardNumber.toString());
                            cardNo.setSelection(formattedCardNumber.length());
                        }
                    } else {
                        cardNo.setError(null);
                    }
                    formatting = false;
                }
            }});

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_adding_card;
    }

    private void saveCardDetails() {
        Context context = getContext();
        if(context != null && getArguments() != null) {
            int userId = getArguments().getInt("userId");
            String titleTxt = titleName.getText().toString().trim();
            String cardNoTxt = cardNo.getText().toString().trim();

            CardDetailsModel model = new CardDetailsModel();
            model.setCardTitle(titleTxt);
            model.setUserId(userId);
            if (!TextUtils.isEmpty(cardNoTxt)) {
                try {

                    model.setCardNo(cardNoTxt);
                    int usableLimit = model.getUsableLimit();
                    model.setUsableLimit(usableLimit);
                    int debt = model.getDebt();
                    model.setDebt(debt);
                    int balance = model.getBalance();
                    model.setBalance(balance);
                    if (!checkCardExistence(cardNoTxt)) {
                        Bundle args = new Bundle();
                        args.putInt("userId", userId);
                        args.putString("cardTitle", titleTxt);
                        args.putString("cardNo", model.getCardNo());
                        args.putInt("usableLimit", usableLimit);
                        args.putInt("debt", debt);
                        args.putInt("balance", balance);
                        AppDatabase.getDbInstance(getContext()).userdao().InsertCardInformations(model);
                        Toast.makeText(getContext(), "Kart kaydetme işleminiz başarıyla gerçekleşmiştir.", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(AddingCardFragment.this)
                                .navigate(R.id.action_addingCardFragment_to_homeFragment, args);
                    } else if (titleTxt.isEmpty() || cardNoTxt.isEmpty()) {
                        Toast.makeText(getContext(), "Kart kaydetme işlemi sırasında hiçbir alanı boş bırakmamalısınız.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Aynı kart ile tekrar kayıt yaptıramazsınız.", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Geçersiz kart numarası formatı.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Kart numarası boş olamaz.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkCardExistence(String cardNo) {
        CardDetailsModel cardExistence = AppDatabase.getDbInstance(getContext()).userdao().validateCardExistence(cardNo);
        return cardExistence != null;
    }

    private void cancellationOfCardAddingProcess() {
        if(getArguments() != null) {
            int userId = getArguments().getInt("userId");
            Bundle args = new Bundle();
            args.putInt("userId", userId);
            Toast.makeText(getContext(), "Kart ekleme işleminiz iptal edilmiştir.", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(AddingCardFragment.this).navigate(R.id.action_addingCardFragment_to_homeFragment, args);
        }
    }
}