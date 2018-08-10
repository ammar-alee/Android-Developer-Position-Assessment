package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentSignupPageTwoBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities.SignupActivity;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.ilhasoft.support.validation.Validator;

public class SignupPageTwo extends Fragment {

    private static final String ARG_USER = "user";
    private static final String ARG_USER_PARAM = "user_param";
    private static final int REQUEST_DATE = 0;
    private static final String DATE_DIALOG = "date_dialog";

    private FragmentSignupPageTwoBinding binding;
    private UserVO user;

    private RadioButton selectedGender;
    private Date defaultDate;


    public static SignupPageTwo newInstance(UserVO user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        SignupPageTwo fragment = new SignupPageTwo();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (UserVO) (getArguments() != null ? getArguments().getSerializable(ARG_USER) : null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_signup_page_two, container, false);
        View view = binding.getRoot();
        final Validator validator = new Validator(binding);

        defaultDate = new Date();
        int genderID = binding.rgGender.getCheckedRadioButtonId();
        selectedGender = view.findViewById(genderID);
        binding.etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFrag dialog = DatePickerDialogFrag.newInstance(defaultDate);
                FragmentManager fragmentManager = getFragmentManager();
                dialog.setTargetFragment(SignupPageTwo.this, REQUEST_DATE);
                if (fragmentManager != null) {
                    dialog.show(fragmentManager, DATE_DIALOG);
                }
            }
        });
        binding.heightRulerPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                binding.heightValueTv.setText(selectedValue + " cms");
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                binding.heightValueTv.setText(selectedValue + " cms");
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.validate()) {
                    user.setZipCode(Integer.parseInt(binding.etZipCode.getText().toString()));
                    user.setGender(selectedGender.getText().toString());
                    user.setHeight(binding.heightValueTv.getText().toString());

                    updateUser(user);
                    Fragment fragment = SignupPageThree.newInstance(user);
                    SignupActivity.fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });
        return view;

    }

    public void updateUser(UserVO user) {
        Intent data = new Intent();
        data.putExtra(ARG_USER_PARAM, user);
        if (getActivity() != null)
            getActivity().setResult(Activity.RESULT_OK, data);
    }

    public static UserVO parseUser(Intent result) {
        return (UserVO) result.getSerializableExtra(ARG_USER_PARAM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerDialogFrag.EXTRA_DATE);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                    Locale.getDefault());
            String stringDate = dateFormat.format(date);

            user.setDateOfBirth(date);
            binding.etDob.setText(stringDate);
        }
    }
}
