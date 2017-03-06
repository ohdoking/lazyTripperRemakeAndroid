package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

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

        TextView emailTv = (TextView) findViewById(R.id.textView2);
        String email = getIntent().getStringExtra(ConstantIntent.EMAIL);
        if(email == null || email.equals("")){
            email = "유리";
        }
        emailTv.setText(email + "님,\n떠나고 싶은\n여행을 만나보세요");


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
