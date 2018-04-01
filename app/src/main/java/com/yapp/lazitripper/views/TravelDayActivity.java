package com.yapp.lazitripper.views;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.facebook.all.All;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.views.adapters.DayItemAdapter;
import com.yapp.lazitripper.views.adapters.DayItemDetailAdapter;
import com.yapp.lazitripper.views.adapters.PlaceInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class TravelDayActivity extends TravelBaseActivity {

    private List<PlaceInfoDto> list;

    public static final String DELIVER_ITEM = "travelInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_day);

        TravelInfo travelInfo = (TravelInfo) getIntent().getSerializableExtra(DELIVER_ITEM);
        list = travelInfo.getPlaceInfoDtoList();

        init();
    }

    private void init() {

        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map_summary);
        mapFragment.getMapAsync(mapReadyCallback);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_travel_list);

        DayItemDetailAdapter adapter = new DayItemDetailAdapter(getApplicationContext(), list, DayItemAdapter.DETAIL);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap Map) {

            googleMap = Map;

            PlaceInfoDto firstPlace = list.get(0);

            LatLng latLng = new LatLng(firstPlace.getMapy(), firstPlace.getMapx());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            int i = 1;
            for (PlaceInfoDto item : list) { // Day1의 리스트 마커로
                googleMap.addMarker(getMarker(i++, item));
            }
        }
    };
}
