package com.atakandalkiran.bbnb.ui.register;

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
import com.atakandalkiran.bbnb.databinding.FragmentRegisterBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.User;
import com.atakandalkiran.bbnb.ui.home.HomeFragment;
import com.atakandalkiran.bbnb.ui.login.LoginFragment;

public class RegisterFragment extends BaseFragment{

    FragmentRegisterBinding binding;
    private EditText name, surname, phone, email, citizenshipNo,password, confirmPassword;
    Button save, cancel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setupUI() {
        // Initialize UI components here
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getLayoutResId(), container, false);
        name = binding.editTextName;
        surname = binding.editTextSurname;
        email = binding.editTextEmail;
        citizenshipNo = binding.editTextCitizenShipNo;
        phone =binding.editTextPhone;
        password = binding.editTextPassword;
        confirmPassword = binding.editTextConfirmPassword;
        save = binding.buttonRegister;
        cancel = binding.cancelButton;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getError() != null || surname.getError() != null || email.getError() != null || citizenshipNo.getError() !=null ||phone.getError() != null  || password.getError() != null || confirmPassword.getError() != null) {
                    Toast.makeText(getContext(), "Lütfen girdiğiniz bilgilerin doğruluğunu kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Giriş ekranına yönlendiriliyorsunuz.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        String namePattern = "^[a-zA-ZıİğĞüÜşŞöÖçÇ]+(?:\\s[a-zA-ZıİğĞüÜşŞöÖçÇ]+)?$";
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String nameValidation = name.getText().toString().trim();
                if(nameValidation.matches(namePattern)) {
                    name.setError(null);
                } else if (editable.length() == 0) {
                    name.setError(null);
                } else {
                    name.setError("Lütfen kimlikteki adınızı giriniz.");
                }
            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String surnameValidation = surname.getText().toString().trim();
                if(surnameValidation.matches(namePattern)) {
                    surname.setError(null);
                } else if (editable.length() == 0) {
                    surname.setError(null);
                } else {
                    surname.setError("Lütfen kimlikteki soyadınızı giriniz.");
                }
            }
        });
        citizenshipNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0 && editable.length() != 11) {
                    citizenshipNo.setError("Lütfen 11 haneli T.C. Kimlik Numaranızı giriniz.");
                } else {
                    citizenshipNo.setError(null);
                }
            }
        });

        String emailPattern = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String emailValidation = email.getText().toString().trim();
                if(emailValidation.matches(emailPattern) && editable.length() > 0) {
                    email.setError(null);
                } else if(editable.length() == 0) {
                    email.setError(null);
                }  else {
                    email.setError("Lütfen geçerli bir e-mail adresi giriniz.");
                }
            }
        });

        String phoneNumberPattern = "^[+]?[0-9]{10,13}$";
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String phoneNumberValidation = phone.getText().toString().trim();
                if(phoneNumberValidation.matches(phoneNumberPattern) && editable.length() > 0) {
                    phone.setError(null);
                } else if (editable.length() == 0) {
                    phone.setError(null);
                } else {
                    phone.setError("Lütfen geçerli bir telefon numarası giriniz."); }
            }
        });

        String passwordPattern = "^(?!.*(.)\\1)\\d+$";
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordValidation = password.getText().toString().trim();
                if(passwordValidation.matches(passwordPattern) && editable.length() == 6) {
                    password.setError(null);
                } else if(editable.length() == 0) {
                    password.setError(null);
                } else {
                    password.setError("Lütfen sayı tekrarı içermeyen 6 haneli şifrenizi giriniz");
                }
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String passwordString = password.getText().toString().trim();
                String confirmPasswordString = confirmPassword.getText().toString().trim();

                if (!passwordString.equals(confirmPasswordString) && editable.length() > 0) {
                    confirmPassword.setError("Şifreleriniz eşleşmemektedir.");
                } else {
                    confirmPassword.setError(null);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register;
    }
    private void saveData() {

        String name_txt = name.getText().toString().trim();
        String surName_txt = surname.getText().toString().trim();
        String phone_txt = phone.getText().toString().trim();
        String email_txt = email.getText().toString().trim();
        String citizenshipNo_txt = citizenshipNo.getText().toString().trim();
        String password_txt = password.getText().toString().trim();
        String passwordConfirmation_txt = confirmPassword.getText().toString().trim();

        User model = new User();
        model.setFirstName(name_txt);
        model.setLastName(surName_txt);
        model.setPhoneNumber(phone_txt);
        model.setEmail(email_txt);
        model.setCitizenshipNo(citizenshipNo_txt);
        model.setPassword(password_txt);

        User existingUser = AppDatabase.getDbInstance(getContext()).userdao().getUserByEmail(email_txt);
        if (existingUser != null) {
            Toast.makeText(getContext(), "Tekrar kayıt yaptıramazsınız, şifremi unuttum ekranına geçin lütfen.", Toast.LENGTH_LONG).show();
        } else if (name_txt.isEmpty() || surName_txt.isEmpty() || phone_txt.isEmpty() || email_txt.isEmpty() || citizenshipNo_txt.isEmpty() || password_txt.isEmpty() || passwordConfirmation_txt.isEmpty()) {
            Toast.makeText(getContext(), "Hiçbir alanı boş bırakmamalısınız.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Kayıt işleminiz başarıyla tamamlanmıştır.", Toast.LENGTH_LONG).show();
            AppDatabase.getDbInstance(getContext()).userdao().InsertUser(model);
            NavHostFragment.findNavController(RegisterFragment.this)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
        }
    }
}