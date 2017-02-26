package com.yapp.lazitripper.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yapp.lazitripper.R;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

public class ProfileActivity extends BaseAppCompatActivity {
    private ArrayList<String> tagList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setHeader();

        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.arrow);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.logout_icon);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });


        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);

        mTagGroup.setTags(new String[]{"박물관", "면세점","뮤지컬", "미술관", "산", "이색체험", "게스트 하우스", "카페"});



    }
}
