package com.yapp.lazitripper.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;

import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.RemainingDay;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.FirebaseService;
import com.yapp.lazitripper.views.adapters.RecentTravelAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;


// 메인 화면

public class HomeActivity extends BaseAppCompatActivity {

    private final String TAG = "HomeActivity";
    private PickDate pickDate;
    private DatabaseReference myRef;
    private SharedPreferenceStore sharedPreferenceStore;
    private List<AllTravelInfo> travelList = new ArrayList<>();
    private String uuid, userName ="";
    private RecyclerView recyclerTavel;
    private RecentTravelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setHeader();

        FirebaseService.getInstance().setFirebase();

        pickDate = new PickDate();

        sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        myRef = FirebaseDatabase.getInstance().getReference("lazitripper");
        recyclerTavel = (RecyclerView)findViewById(R.id.recycler_home_travel_list);
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.ic_person_black_36dp);

        TextView emailTv = (TextView) findViewById(R.id.textView2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        adapter = new RecentTravelAdapter(getApplicationContext(), travelList, R.layout.item_route);
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setLayoutManager(manager);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                travelList = FirebaseService.getInstance().getTravelList();
                recentTravelListSetting();
            }
        }, 2000);

        uuid = user.getUid();

        if (user != null) {
            userName = user.getDisplayName();
            sharedPreferenceStore.savePreferences(ConstantStore.USERNAME, userName);             //shared에 유저명 추가

        }

        emailTv.setText(userName + "님,\n편하게 여행을\n만들어보세요:-)");
        //오른쪽 마이페이지 버튼
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });

        //여행 만들기
        Button btnMakeRoot = (Button) findViewById(R.id.btnMakeRoot);
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
    public void onResume(){
        super.onResume();
        //FirebaseService.getInstance().setFirebase();
        //recentTravelListSetting();

    }

    private void recentTravelListSetting(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new RecentTravelAdapter(getApplicationContext(), travelList, R.layout.item_route);
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setHasFixedSize(true);
        recyclerTavel.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
    }


}
