package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.ActivitySignupBinding;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.model.UserVO;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments.SignupPageFour;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments.SignupPageOne;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments.SignupPageThree;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments.SignupPageTwo;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

public class SignupActivity extends AppCompatActivity {
    private static final int PAGE_ONE = 1;
    private static final int PAGE_TWO = 2;
    private static final int PAGE_THREE = 3;
    private static final int PAGE_FOUR = 4;

    private UserVO user;

    public static FragmentManager fragmentManager;

    boolean isBackShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        user = new UserVO();

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = SignupPageOne.newInstance(user);
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).add(R.id.fragment_container, fragment).addToBackStack(null).commit();
            isBackShow = false;
        } else {
            fragmentManager.beginTransaction().setCustomAnimations(R.animator.flip_right_in, R.animator.flip_right_out).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            isBackShow = true;
        }
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case (PAGE_ONE):
                    user = SignupPageOne.parseUser(result);
                case (PAGE_TWO):
                    user = SignupPageTwo.parseUser(result);
                case (PAGE_THREE):
                    user = SignupPageThree.parseUser(result);
                case (PAGE_FOUR):
                    user = SignupPageFour.parseUser(result);
            }

        }
    }
}
