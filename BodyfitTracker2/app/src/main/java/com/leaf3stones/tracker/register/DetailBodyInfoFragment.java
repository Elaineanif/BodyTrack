package com.leaf3stones.tracker.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.leaf3stones.tracker.MainPageActivity;
import com.leaf3stones.tracker.data.BodyInfo;
import com.leaf3stones.tracker.data.UserIdSharedPreferenceKt;
import com.leaf3stones.tracker.databinding.FragmentDetailBodyInfoBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailBodyInfoFragment extends Fragment implements DatePickerFragment.Callbacks {

    private Boolean isEnabled = null;
    private DetailBodyInfoViewModel viewModel;
    private FragmentDetailBodyInfoBinding binding;

    public static DetailBodyInfoFragment newInstance() {
        return new DetailBodyInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBodyInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailBodyInfoViewModel.class);
        BodyInfo bodyInfo = viewModel.getUserBodyInfo();


        if (bodyInfo != null) {
            binding.nameEdittext.setText(bodyInfo.getName());
            binding.heightEdittext.setText(String.valueOf(bodyInfo.getHeight()));
            binding.weightEdittext.setText(String.valueOf(bodyInfo.getWeight()));
            binding.ageEdittext.setText(String.valueOf(bodyInfo.getAge()));
            setBirthday();
            binding.emailEdittext.setText(bodyInfo.getEmailAddress());
            binding.saveAndEditButton.setText("Change Information");
            isEnabled = false;
            toggleFieldsInteractivity();
            setBMI();
        } else {
            isEnabled = true;
            toggleFieldsInteractivity();
            binding.saveAndEditButton.setText("Save");
        }

        binding.ageEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    viewModel.setAge(Integer.valueOf(charSequence.toString()));
                } catch (Exception e) {
                    viewModel.setAge(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.emailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setEmailAddress(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.nameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.weightEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    viewModel.setWeight(Float.valueOf(charSequence.toString()));
                    setBMI();
                } catch (Exception e) {
                    viewModel.setHeight(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.heightEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    viewModel.setHeight(Float.valueOf(charSequence.toString()));
                    setBMI();
                } catch (Exception e) {
                    viewModel.setHeight(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.saveAndEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEnabled) {
                    // current has full info, now give user chance to update it
                    isEnabled = true;
                    toggleFieldsInteractivity();
                    binding.saveAndEditButton.setText("Save");
                } else {
                    if (viewModel.updateBodyInfo()) {
                        // all fields are filled and perform sql insert successfully
                        if (viewModel.isGetHereFromLoginFragment()) {
                            String message = "login as " + viewModel.getUsername();
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireContext(), MainPageActivity.class);
                            UserIdSharedPreferenceKt.setUserId(viewModel.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(requireContext(), "updated", Toast.LENGTH_SHORT).show();
                            isEnabled = false;
                            toggleFieldsInteractivity();
                            binding.saveAndEditButton.setText("Change Information");
                        }
                    } else {
                        Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show();
                        isEnabled = true;
                        toggleFieldsInteractivity();
                        binding.saveAndEditButton.setText("Save");
                    }
                }

            }
        });

        DetailBodyInfoFragment fragment = this;

        binding.birthdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                if (viewModel.getBirthday() == null) {
                    date = new Date();
                } else {
                    date = viewModel.getBirthday();
                }


                DatePickerFragment pickerFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("date", date);
                pickerFragment.setArguments(bundle);
                pickerFragment.setTargetFragment(fragment, 100);
                pickerFragment.show(fragment.requireFragmentManager(), "Date_Picker");
                viewModel.setBirthday(date);

            }
        });
    }
    @SuppressLint("DefaultLocale")
    private void setBMI() {
        Float res = viewModel.calculateBMI();
        if (res != null) {
            binding.bmiTextview.setText(String.format("%.2f", res));
        } else {
            binding.bmiTextview.setText("0");
        }
    }

    private void toggleFieldsInteractivity() {
        binding.nameEdittext.setEnabled(isEnabled);
        binding.ageEdittext.setEnabled(isEnabled);
        binding.birthdayButton.setEnabled(isEnabled);
        binding.weightEdittext.setEnabled(isEnabled);
        binding.emailEdittext.setEnabled(isEnabled);
        binding.heightEdittext.setEnabled(isEnabled);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onDateSelected(@NonNull Date date) {
        viewModel.setBirthday(date);
        setBirthday();
    }

    private void setBirthday() {
        if (viewModel.getBirthday() != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            binding.birthdayButton.setText(format.format(viewModel.getBirthday()));
        }
    }


}