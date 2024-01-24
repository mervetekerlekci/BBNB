package com.atakandalkiran.bbnb.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;

import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentLoginBinding;
import com.atakandalkiran.bbnb.db.AppDatabase;
import com.atakandalkiran.bbnb.db.User;

public class LoginFragment extends BaseFragment {

    FragmentLoginBinding binding;
    private EditText editTextTCKN, editTextPassword;
    private LoginViewModel loginViewModel;
    private static final String PREFS_NAME = "MyPrefsFile";
    SwitchCompat rememberSwitch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getLayoutResId(), container, false);

        editTextTCKN = binding.editTextTCKN;
        editTextPassword = binding.editTextPassword;
        TextView registerText = binding.uyeText;
        TextView forgottenPasswordText = binding.forgottenPasswordText;

        Button loginButton = binding.loginButton;
        rememberSwitch = binding.rememberButton;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to RegisterFragment using NavController
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        forgottenPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_emailValidationFragment);
            }
        });

        if (getRememberStatus()) {
            String savedTCKN = getSavedTCKN();
            rememberSwitch.setChecked(true);
            editTextTCKN.setText(savedTCKN);
        }

        rememberSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    saveTCKN();
                } else {
                    clearSavedTCKN();
                }
                // Move saveRememberStatus here
                saveRememberStatus(isChecked);
            }
        });

        return binding.getRoot();
    }

    private void login() {
        String citizenshipNo = editTextTCKN.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(citizenshipNo) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Lütfen T.C. Kimlik Numaranızı ve Şifrenizi giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = AppDatabase.getDbInstance(getContext()).userdao().getUserLoginInformations(citizenshipNo, password);


        if (user != null) {
            Toast.makeText(getContext(), "Giriş başarılı!", Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putInt("userId", user.userId);
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_mainActivity, args);
        } else {
            Toast.makeText(getContext(), "Geçersiz T.C. Kimlik No veya Şifre.", Toast.LENGTH_SHORT).show();
        }
    }

    public LoginFragment() {
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
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    private boolean getRememberStatus() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean("rememberMe", false);
    }

    private void saveRememberStatus(boolean isChecked) {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberMe", isChecked);
        editor.apply();
    }

    private void saveTCKN() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberMe", true);

        // Check if the switch is checked before saving the TCKN
        if (rememberSwitch.isChecked()) {
            editor.putString("savedTCKN", editTextTCKN.getText().toString());
        } else {
            editor.remove("savedTCKN");
        }

        editor.apply();
    }

    private String getSavedTCKN() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString("savedTCKN", "");
    }

    private void clearSavedTCKN() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("rememberMe");
        editor.remove("savedTCKN");
        editor.apply();
    }
}