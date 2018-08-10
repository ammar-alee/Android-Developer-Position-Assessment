package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.ActivityLoginBinding;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void signup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
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
}
