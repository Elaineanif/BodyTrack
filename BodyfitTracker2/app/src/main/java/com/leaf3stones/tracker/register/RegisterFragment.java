package com.leaf3stones.tracker.register;

import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.leaf3stones.tracker.R;
import com.leaf3stones.tracker.data.UserIdSharedPreferenceKt;
import com.leaf3stones.tracker.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;
    private FragmentRegisterBinding binding;


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.adminCheckbox.setVisibility(View.GONE); // disable make admin checkBox
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.adminCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                viewModel.updateAdmin(b);
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
        });

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (viewModel.registerUser()) {
                    UserIdSharedPreferenceKt.setUserId(viewModel.getId());
                    Navigation.findNavController(view).navigate(R.id.fillDetailBodyInfo);
                } else {
                    if (viewModel.checkFields()) {
                        // username taken
                        Toast.makeText(requireContext(), "username taken", Toast.LENGTH_SHORT).show();
                    } else {
                        // no enough info
                        Toast.makeText(requireContext(), "please fill all info", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}