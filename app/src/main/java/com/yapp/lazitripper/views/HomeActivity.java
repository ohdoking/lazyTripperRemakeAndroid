package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

// 메인 화면

public class HomeActivity extends BaseAppCompatActivity {

    private final String TAG = "HomeActivity";
    private SharedPreferenceStore sharedPreferenceStore;
    private FirebaseUser user;
    private List<AllTravelInfo> travelList = new ArrayList<>();
    private String userName;
    private RecyclerView recyclerTavel;
    private RecentTravelAdapter adapter;
    private TextView tvHomeTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getDisplayName();

        sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        sharedPreferenceStore.savePreferences(ConstantStore.USERNAME, user.getDisplayName());

        viewSetting();

        travelList = FirebaseService.getInstance().getTravelList();
        recentTravelListSetting();


    }

    public void viewSetting() {

        recyclerTavel = (RecyclerView) findViewById(R.id.recycler_home_travel_list);
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.icon_profile);
        tvHomeTravel = (TextView)findViewById(R.id.tv_home_travel);
        TextView tvEmail = (TextView) findViewById(R.id.text_email_home);

        tvEmail.setText(userName + "님,\n편하게 여행을\n만들어보세요:-)");
        //오른쪽 마이페이지 버튼
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        Button btnMakeRoot = (Button) findViewById(R.id.btnMakeRoot); //여행 만들기
        btnMakeRoot.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeActivity.this, DatePickActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void recentTravelListSetting() {

        if(travelList==null || travelList.size()==0)
            tvHomeTravel.setVisibility(View.GONE);
        adapter = new RecentTravelAdapter(getApplicationContext(), travelList, R.layout.item_route);
        recyclerTavel.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerTavel, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MyTravelActivity.class);
                intent.putExtra(MyTravelActivity.DELIVER_ITEM, travelList.get(position));
                startActivity(intent);
            }
        }));
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setHasFixedSize(true);
        recyclerTavel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

}
