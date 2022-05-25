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

public class LoginFragment extends Fragment {

    View root;
    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    TextView tvRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(root);

        return root;
    }

    public void initViews(View root) {
        this.root = root;
        etUsername = root.findViewById(R.id.et_username);
        etPassword = root.findViewById(R.id.et_password);
        btLogin = root.findViewById(R.id.bt_login);
        tvRegister = root.findViewById(R.id.tv_register);

        btLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            UserRequests.login(this, username, password);
        });

        tvRegister.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    public void onLoginSuccess() {
        getActivity().finish();
    }

    public void onLoginFailed(String message) {
        if (message.contains(Tags.USER_NOT_FOUND)) {
            Toast.makeText(getContext(), getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}