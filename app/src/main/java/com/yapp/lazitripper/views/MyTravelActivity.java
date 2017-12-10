package com.yapp.lazitripper.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.all.All;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.util.FirebaseService;
import com.yapp.lazitripper.views.adapters.DayItemAdapter;
import com.yapp.lazitripper.views.adapters.RecentTravelAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTravelActivity extends AppCompatActivity {

    private RecyclerView recyclerTavel;
    private DayItemAdapter adapter;
    private AllTravelInfo travelList;

    public static final String DELIVER_ITEM = "allTravelInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel);

        travelList = (AllTravelInfo) getIntent().getSerializableExtra(DELIVER_ITEM);

        recyclerTavel = (RecyclerView) findViewById(R.id.recycler_travel_list);
        recentTravelListSetting();
    }

    private void recentTravelListSetting() {

        adapter = new DayItemAdapter(getApplicationContext(), travelList.getAllTraveInfo(), R.layout.item_travel_route);
        recyclerTavel.setAdapter(adapter);
        recyclerTavel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
