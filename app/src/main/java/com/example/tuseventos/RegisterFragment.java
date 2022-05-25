package com.example.tuseventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tuseventos.requests.UserRequests;

public class RegisterFragment extends Fragment {

    final int incorrectFieldBackground = R.drawable.custom_edit_text_rounded_incorrect;
    final int correctFieldBackground = R.drawable.custom_edit_text_rounded;

    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    Button btLogin;
    TextView tvLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        initViews(root);

        return root;
    }

    public void initViews(View root) {
        etUsername = root.findViewById(R.id.et_username);
        etEmail = root.findViewById(R.id.et_email);
        etPassword = root.findViewById(R.id.et_password);
        etPasswordConfirm = root.findViewById(R.id.et_password_confirm);
        btLogin = root.findViewById(R.id.bt_login);
        tvLogin = root.findViewById(R.id.tv_login);

        btLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String passwordConfirm = etPasswordConfirm.getText().toString();

            boolean allFieldsCorrect = true;
            String error = "";

            if (username.length() < 4) {
                etUsername.setBackgroundResource(incorrectFieldBackground);
                error = error.concat(getString(R.string.username_short) + "\n");
                allFieldsCorrect = false;
            } else {
                etUsername.setBackgroundResource(correctFieldBackground);
            }

            if (!email.matches(Tags.EMAIL_REGEX)) {
                etEmail.setBackgroundResource(incorrectFieldBackground);
                error =  error.concat(getString(R.string.email_invalid) + "\n");
                allFieldsCorrect = false;
            } else {
                etEmail.setBackgroundResource(correctFieldBackground);
            }

            if (!password.equals(passwordConfirm)) {
                etPassword.setBackgroundResource(incorrectFieldBackground);
                etPasswordConfirm.setBackgroundResource(incorrectFieldBackground);
                error = error.concat(getString(R.string.password_different) + "\n");
                allFieldsCorrect = false;
            } else {
                etPassword.setBackgroundResource(correctFieldBackground);
                etPasswordConfirm.setBackgroundResource(correctFieldBackground);
            }

            if (allFieldsCorrect) {
                UserRequests.register(
                        this,
                        username,
                        email,
                        password
                );
            } else {
                Toast.makeText(
                        getContext(),
                        error.substring(0, error.length() - 2),
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        tvLogin.setOnClickListener(v -> {
            NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    public void onRegisterSuccess() {
        getActivity().finish();
    }

    public void onRegisterFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}