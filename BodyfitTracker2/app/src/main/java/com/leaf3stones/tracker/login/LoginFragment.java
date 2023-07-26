package com.leaf3stones.tracker.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leaf3stones.tracker.MainPageActivity;
import com.leaf3stones.tracker.R;
import com.leaf3stones.tracker.data.User;
import com.leaf3stones.tracker.data.UserIdSharedPreferenceKt;
import com.leaf3stones.tracker.databinding.FragmentLoginBinding;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;
    private FragmentLoginBinding binding;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.createAccount);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = viewModel.searchUser();
                String message;
                if (user == null) {
                    message = "check your username and password";
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    // we find user with given username, now check the password
                    if (Objects.equals(user.getPassword(), viewModel.getUser().getPassword())) {
                        // correct password
                        message = "login as " + user.getUsername();
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(requireContext(), MainPageActivity.class);
                        UserIdSharedPreferenceKt.setUserId(user.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(requireContext(), "wrong password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // no need
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.updateUsername(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // no need
            }
        });

        binding.passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // no need
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.updatePassword(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // no need
            }
        }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}