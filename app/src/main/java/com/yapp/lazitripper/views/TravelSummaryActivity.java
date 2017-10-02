package com.yapp.lazitripper.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;

import com.yapp.lazitripper.dto.TravelRouteDto;
import com.yapp.lazitripper.views.adapters.DayItemAdapter;
import com.yapp.lazitripper.views.adapters.PlaceInfoAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/*
* 여행 추천 결과 화면
* */

public class TravelSummaryActivity extends BaseAppCompatActivity  {

    private AllTravelInfo allTravelInfo = new AllTravelInfo();
    private RecyclerView recyclerView;
    private PlaceInfoAdapter recycler_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);
        setHeader();

        allTravelInfo = (AllTravelInfo)getIntent().getSerializableExtra(ConstantIntent.AllTRAVELINFO);

        init();

    }

    void init() {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_day_list);
        recycler_adapter = new PlaceInfoAdapter(this, allTravelInfo);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
