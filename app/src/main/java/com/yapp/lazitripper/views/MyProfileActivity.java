package com.yapp.lazitripper.views;

import android.os.Bundle;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

public class MyProfileActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setHeader();
    }
}
