package com.atakandalkiran.bbnb.ui.forgotten_password;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentForgottenPasswordBinding;
import com.atakandalkiran.bbnb.databinding.FragmentRegisterBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.User;
import com.atakandalkiran.bbnb.ui.register.RegisterFragment;

public class ForgottenPasswordFragment extends BaseFragment {
    FragmentForgottenPasswordBinding binding;
    private EditText newPassword, confirmNewPassword;

    Button resetButton, cancel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    public ForgottenPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    protected void setupUI() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getLayoutResId(), container, false);


        newPassword = binding.newPasswordEditText;
        confirmNewPassword = binding.newPasswordValidationEditText;
        resetButton = binding.buton;
        cancel = binding.cancelButton;

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordString = newPassword.getText().toString().trim();
                String confirmPasswordString = confirmNewPassword.getText().toString().trim();
                if (getArguments() != null && !passwordString.isEmpty() && !confirmPasswordString.isEmpty()) {
                    if(newPassword.getError() != null || confirmNewPassword.getError() != null) {
                        Toast.makeText(getContext(), "Lütfen talimatları dikkate alınız.", Toast.LENGTH_SHORT).show();
                    } else {
                        String email = getArguments().getString("email", "");
                        String citizenshipNo = getArguments().getString("citizenshipNo", "");
                        String password = newPassword.getText().toString().trim();
                        AppDatabase.getDbInstance(getContext()).userdao().updatePassword(email, citizenshipNo, password);
                        Toast.makeText(getContext(), "Şifre değiştirme işleminiz başarıyla gerçekleşmiştir. Ana sayfaya yönlendiriliyorsunuz.", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(ForgottenPasswordFragment.this).navigate(R.id.action_forgottenPasswordFragment_to_loginFragment);
                    }
                } else {
                    Toast.makeText(getContext(), "Hiçbir alanı boş bırakmamalısınız.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Şifre değiştirme talebiniz iptal edilmiştir. Ana sayfaya yönlendiriliyorsunuz.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(ForgottenPasswordFragment.this).navigate(R.id.action_forgottenPasswordFragment_to_loginFragment);
            }
        });


        String passwordPattern = "^(?!.*(.)\\1)\\d+$";
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordValidation = newPassword.getText().toString().trim();
                if(passwordValidation.matches(passwordPattern) && editable.length() == 6 || editable.length() == 0) {
                    newPassword.setError(null);
                } else {
                    newPassword.setError("Lütfen sayı tekrarı içermeyen 6 haneli şifrenizi giriniz");
                }
            }
        });
        confirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString = newPassword.getText().toString().trim();
                String confirmPasswordString = confirmNewPassword.getText().toString().trim();

                if (!passwordString.equals(confirmPasswordString) && editable.length() > 0) {
                    confirmNewPassword.setError("Şifreleriniz eşleşmemektedir.");
                } else {
                    confirmNewPassword.setError(null);
                }
            }
        });

        return binding.getRoot();
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_forgotten_password;
    }
}
