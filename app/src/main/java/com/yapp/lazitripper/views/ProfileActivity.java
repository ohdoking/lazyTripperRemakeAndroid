package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.FirebaseService;
import com.yapp.lazitripper.views.adapters.RecentTravelAdapter;
import com.yapp.lazitripper.views.adapters.RecyclerItemClickListener;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class ProfileActivity extends BaseAppCompatActivity {
    private static String TAG = "dongs";
    private List<AllTravelInfo> travelList = new ArrayList<>();
    private RecentTravelAdapter adapter;
    private RecyclerView recyclerTavel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ((ImageView) findViewById(R.id.iv_profile_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        ((ImageView) findViewById(R.id.iv_profile_logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });
        ((TextView) findViewById(R.id.my_travel_key_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, KeywordActivity.class);
                i.putExtra("init", false);//KeywordActivity Flag 추가
                startActivity(i);
            }
        });
        recyclerTavel = (RecyclerView) findViewById(R.id.recycler_profile_travel_list);
        String userId = Profile.getCurrentProfile().getId();
        String profileURL = "http://graph.facebook.com/" + userId + "/picture?type=large";
        ImageView profileImageView = (ImageView) findViewById(R.id.user_profile_photo);
        Glide.with(this).load(profileURL).into(profileImageView);
        TextView nameText = (TextView) findViewById(R.id.user_profile_name);
        nameText.setText(Profile.getCurrentProfile().getName());
        recyclerTavel.addOnItemTouchListener(new RecyclerItemClickListener(ProfileActivity.this, recyclerTavel, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MyTravelActivity.class);
                intent.putExtra(MyTravelActivity.DELIVER_ITEM, travelList.get(position));
                startActivity(intent);
            }
        }));

        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        //태그 그룹에 # 추가
        int index = 0;
        if (tagList.length != 0)
            for (String tag : tagList) {
                tagList[index] = "#" + tag;
                index++;
            }
        else {
            tagList = new String[1];
            tagList[0] = "#태그를 선택해주세요";
        }
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);

    }


    @Override
    public void onResume() {
        super.onResume();

        travelList = FirebaseService.getInstance().getTravelList();
        recentTravelListSetting();
    }

    private void recentTravelListSetting() {

        adapter = new RecentTravelAdapter(getApplicationContext(), travelList, R.layout.item_route);
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setHasFixedSize(true);
        recyclerTavel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }
}
