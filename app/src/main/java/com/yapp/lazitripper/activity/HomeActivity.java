package com.yapp.lazitripper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.store.ConstantStore;

import java.util.Date;

public class HomeActivity extends BaseAppCompatActivity {

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader();

        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.map_icon);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView rightImage = getLeftImageView();
        rightImage.setImageResource(R.drawable.more);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.nextPageBtn);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DatePickActivity.class);
                startActivity(i);
            }
        });
    }
}
