package com.yapp.lazitripper.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;

import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.views.adapters.PlaceInfoAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/*
* 여행 추천 결과 화면
* */

public class TravelSummaryActivity extends BaseAppCompatActivity {

    private AllTravelInfo allTravelInfo = new AllTravelInfo();
    private List<TravelInfo> list;
    private RecyclerView recyclerView;
    private PlaceInfoAdapter adapter;
    private GoogleMap googleMap;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);
        setSummaryHeader();

        allTravelInfo = (AllTravelInfo) getIntent().getSerializableExtra(ConstantIntent.AllTRAVELINFO);
        list = allTravelInfo.getAllTraveInfo();
        saveBtn.setOnClickListener(clickListener); // 상단 저장 버튼 클릭리스너 연결

        init();
        setHeaderContent(); // 지도 위 설명 텍스트 설정

    }

    ImageView.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_arrow_header:
                    saveDatabase();
            }
        }
    };

    void saveDatabase() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String title = editTitle.getText().toString();

            if (title.length() >= 1) {
                allTravelInfo.setTravelTitle(title);
                mDatabase.child("lazitripper").child("user").child(user.getUid()).child("Travel").child(title).setValue(allTravelInfo);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.get_title, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getApplicationContext(), R.string.save_completed, Toast.LENGTH_LONG).show();
        }
    }

    void init() {

        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map_summary);
        mapFragment.getMapAsync(mapReadyCallback);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_travel_list);
        adapter = new PlaceInfoAdapter(this, allTravelInfo);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        adapter.setLayoutManager(manager);
        adapter.shouldShowHeadersForEmptySections(false);
        adapter.shouldShowFooters(true);
        adapter.expandAllSections();
        recyclerView.setAdapter(adapter);
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap Map) {

            googleMap = Map;

            PlaceInfoDto firstPlace = list.get(0).getPlaceInfoDtoList().get(0);

            LatLng latLng = new LatLng(firstPlace.getMapy(), firstPlace.getMapx());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

            int i = 1;
            for (PlaceInfoDto item : list.get(0).getPlaceInfoDtoList()) { // Day1의 리스트 마커로
                getMarker(i++, item);
            }

        }
    };

    Marker getMarker(int index, PlaceInfoDto item) {

        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_custom_marker, null);
        TextView markerText = (TextView) marker.findViewById(R.id.text_marker);
        markerText.setText(index + "");

        LatLng latLng = new LatLng(item.getMapy(), item.getMapx());
        Marker markerTemp = googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));

        return markerTemp;

    }


    void setHeaderContent() {
        TextView textDayDigit = (TextView) findViewById(R.id.text_header_day_digit);
        TextView textDayList = (TextView) findViewById(R.id.text_header_day_list);
        TextView textDate = (TextView) findViewById(R.id.text_header_date);

        String dateDigit = "DAY ";
        String dateList = "";

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                dateDigit += (i + 1);
                dateList += list.get(i).getCityName();
            } else {
                dateDigit += "/" + (i + 1);
                dateList += "/" + list.get(i).getCityName();
            }
        }

        String dateString = new java.text.SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date()) + " 작성";

        textDayDigit.setText(dateDigit);
        textDayList.setText(dateList);
        textDate.setText(dateString);

    }

    // View를 Bitmap으로 변환
    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
