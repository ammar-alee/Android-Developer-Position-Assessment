package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void getStarted(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}
