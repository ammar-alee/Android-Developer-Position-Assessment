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

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentSignupPageOneBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities.SignupActivity;

import br.com.ilhasoft.support.validation.Validator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupPageOne extends Fragment {

    private static final String ARG_USER = "user";
    private static final String ARG_USER_PARAM = "user_param";

    private FragmentSignupPageOneBinding binding;
    private UserVO user;

    public static SignupPageOne newInstance(UserVO user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        SignupPageOne fragment = new SignupPageOne();
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
                inflater, R.layout.fragment_signup_page_one, container, false);
        final Validator validator = new Validator(binding);

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.validate()) {
                    user.setEmail(binding.etEmailAddress.getText().toString());
                    user.setFullName(binding.etFullName.getText().toString());
                    user.setPassword(binding.etPassword.getText().toString());
                    updateUser(user);
                    Fragment fragment = SignupPageTwo.newInstance(user);
                    SignupActivity.fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }

            }
        });
        return binding.getRoot();
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
}
