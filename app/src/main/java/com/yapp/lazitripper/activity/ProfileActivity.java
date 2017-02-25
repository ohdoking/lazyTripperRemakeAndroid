package com.yapp.lazitripper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yapp.lazitripper.R;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

public class ProfileActivity extends AppCompatActivity {
    private ArrayList<String> tagList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);

        mTagGroup.setTags(new String[]{"박물관", "면세점","뮤지컬", "미술관", "산", "이색체험", "게스트 하우스", "카페"});



    }
}
