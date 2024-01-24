package com.atakandalkiran.bbnb.ui.email_validation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentEmailValidationBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.User;
import com.atakandalkiran.bbnb.ui.register.RegisterFragment;

public class EmailValidationFragment extends BaseFragment {
    FragmentEmailValidationBinding binding;

    EditText userEmail, userCitizenshipNo;
    Button button;

    public EmailValidationFragment() {
        // Required empty public constructor
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getLayoutResId(), container, false);

        button = binding.validationButton;
        userEmail = binding.emailValidationEditText;
        userCitizenshipNo = binding.editTextCitizenShipNo;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt = userEmail.getText().toString().trim();
                String citizenshipNo_txt = userCitizenshipNo.getText().toString().trim();
                User existingUser = AppDatabase.getDbInstance(getContext()).userdao().validateUsersExistence(citizenshipNo_txt, email_txt);
                if (existingUser != null) {
                    Bundle args = new Bundle();
                    args.putString("email", email_txt);
                    args.putString("citizenshipNo", citizenshipNo_txt);
                    NavHostFragment.findNavController(EmailValidationFragment.this)
                            .navigate(R.id.action_emailValidationFragment_to_forgottenPasswordFragment, args);

                } else if (email_txt.isEmpty() || citizenshipNo_txt.isEmpty()) {
                    Toast.makeText(getContext(), "Kayıt yaptırdığınızı görebilmemiz için hiçbir alanı boş bırakmamalısınız.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "TC. Kimlik Numaranız veya E-mailiniz tekrar kontrol edip deneyiniz.", Toast.LENGTH_LONG).show();
                }
            }
        });

        String emailPattern = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String emailValidation = userEmail.getText().toString().trim();
                if(emailValidation.matches(emailPattern) && editable.length() > 0) {
                    userEmail.setError(null);
                } else if (editable.length() == 0) {
                    userEmail.setError(null);
                }  else {
                    userEmail.setError("Lütfen geçerli bir e-mail adresi giriniz.");
                }
            }
        });

        userCitizenshipNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0 && editable.length() != 11) {
                    userCitizenshipNo.setError("Lütfen 11 haneli T.C. Kimlik Numaranızı giriniz.");
                } else {
                    userCitizenshipNo.setError(null);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_email_validation;
    }
}