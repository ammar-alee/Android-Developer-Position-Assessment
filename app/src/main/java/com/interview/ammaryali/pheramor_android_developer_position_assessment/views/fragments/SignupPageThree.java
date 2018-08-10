package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentSignupPageThreeBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities.MainActivity;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities.SignupActivity;


public class SignupPageThree extends Fragment {
    private static final String ARG_USER = "user";
    private static final String ARG_USER_PARAM = "user_param";

    private FragmentSignupPageThreeBinding binding;
    private UserVO user;
    private RadioButton selectedGender;


    public static SignupPageThree newInstance(UserVO user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        SignupPageThree fragment = new SignupPageThree();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserVO) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_signup_page_three, container, false);
        View view = binding.getRoot();
        int genderID = binding.rgGenderInterest.getCheckedRadioButtonId();
        selectedGender = view.findViewById(genderID);

        //race mock data
        String[] raceArray = {"Non Disclosed", "Hispanic/Latino", "American Indian or Alaska Native", "Asian", "Black or African American", "Native Hawaiian or Other Pacific Islander", "White", "Two or more races"};
        ArrayAdapter<String> raceSpinnerArrayAdpater = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, raceArray);
        raceSpinnerArrayAdpater.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.raceSpinner.setAdapter(raceSpinnerArrayAdpater);

        //religion mock data
        String[] religionArray = {"Other", "Atheist", "Buddhism", "Christianity", "Hinduism", "Islam", "Judaism", "Nonreligious", "Secular", "Sikhism"};
        ArrayAdapter<String> religionSpinnerArrayAdpater = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, religionArray);
        religionSpinnerArrayAdpater.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.religionSpinner.setAdapter(religionSpinnerArrayAdpater);

        binding.npAgeMin.setMin(18);
        binding.npAgeMin.setMax(80);
        binding.npAgeMax.setMin(18);
        binding.npAgeMax.setMax(80);

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setInterestGender(selectedGender.getText().toString());
                user.setRace(binding.raceSpinner.getSelectedItem().toString());
                user.setReligion(binding.religionSpinner.getSelectedItem().toString());
                user.setAgeRangeMin(binding.npAgeMin.getSelectedNumber());
                user.setAgeRangeMax(binding.npAgeMax.getSelectedNumber());
                updateUser();
                Fragment fragment = SignupPageFour.newInstance(user);
                SignupActivity.fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    public void updateUser() {
        Intent intent = new Intent();
        intent.putExtra(ARG_USER_PARAM, user);
        if (getActivity() != null)
            getActivity().setResult(Activity.RESULT_OK, intent);
    }

    public static UserVO parseUser(Intent result) {
        return (UserVO) result.getSerializableExtra(ARG_USER_PARAM);
    }
}
