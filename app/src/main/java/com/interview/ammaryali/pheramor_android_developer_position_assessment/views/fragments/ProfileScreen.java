package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentProfileScreenBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;

import java.util.Calendar;


public class ProfileScreen extends Fragment {
    private static final String ARG_USER = "user";

    private UserVO user;

    public static ProfileScreen newInstance(UserVO user) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        ProfileScreen fragment = new ProfileScreen();
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

        FragmentProfileScreenBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_screen, container, false);
        View view = binding.getRoot();

        binding.photo.setImageBitmap(user.getProfilePicture());
        binding.tvFullName.setText(user.getFullName());
        binding.tvAge.setText(Integer.toString(getAge()));
        binding.tvHeight.setText((user.getHeight()));
        binding.tvRace.setText(user.getRace());
        binding.tvReligion.setText(user.getReligion());
        binding.tvGender.setText(user.getGender());


        return view;
    }

    private int getAge() {
        Calendar dob = Calendar.getInstance();
        dob.setTime(user.getDateOfBirth());
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}
