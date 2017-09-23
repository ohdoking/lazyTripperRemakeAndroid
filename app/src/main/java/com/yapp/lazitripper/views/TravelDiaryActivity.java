package com.yapp.lazitripper.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.TravelRouteDto;
import com.yapp.lazitripper.views.adapters.DayItemAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TravelDiaryActivity extends BaseAppCompatActivity {

    private GoogleMap googleMap;
    private RecyclerView recyclerView;
    private List<TravelRouteDto> itemList;
    private DayItemAdapter recycler_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_diary);

        setHeader();
        init();
    }

    void init() {

        com.google.android.gms.maps.MapFragment mapFragment = (com.google.android.gms.maps.MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_day_list);
        recycler_adapter = new DayItemAdapter(this, getData(), R.layout.item_travel_route);
        recyclerView.setAdapter(recycler_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    List<TravelRouteDto> getData() {

        itemList = new ArrayList<>(); // todo 파이어베이스에서 받아오기
        itemList.add(new TravelRouteDto("hihi 11111", "http://goos.wiki/images/thumb/1/13/%ED%95%98%EB%8A%98.JPG/800px-%ED%95%98%EB%8A%98.JPG"));
        itemList.add(new TravelRouteDto("hihi 22222", "http://blog.fursys.com/wp-content/uploads/2016/04/160401-cherryblossom-01.png"));
        itemList.add(new TravelRouteDto("hihi 33333", "http://goos.wiki/images/thumb/1/13/%ED%95%98%EB%8A%98.JPG/800px-%ED%95%98%EB%8A%98.JPG"));
        itemList.add(new TravelRouteDto("hihi 444444", "http://post.phinf.naver.net/MjAxNzAyMjdfMjUy/MDAxNDg4MTgzMTQ4MDY1.FigGe-J9X8gDsx0C2M6k-rsHg2X1QjTfmAub8du46Oog.EdloZuaV3W5c6m8iSEgFpoMSsHKK-S3j6BHiNiGShycg.JPEG/2017%EB%85%84%EB%B2%9A%EA%BD%83%EA%B0%9C%ED%99%94%EC%8B%9C%EA%B8%B06.jpg?type=w1200"));
        itemList.add(new TravelRouteDto("hihi 555555", "http://goos.wiki/images/thumb/1/13/%ED%95%98%EB%8A%98.JPG/800px-%ED%95%98%EB%8A%98.JPG"));
        itemList.add(new TravelRouteDto("hihi 666666", "http://post.phinf.naver.net/MjAxNzAyMjdfMjUy/MDAxNDg4MTgzMTQ4MDY1.FigGe-J9X8gDsx0C2M6k-rsHg2X1QjTfmAub8du46Oog.EdloZuaV3W5c6m8iSEgFpoMSsHKK-S3j6BHiNiGShycg.JPEG/2017%EB%85%84%EB%B2%9A%EA%BD%83%EA%B0%9C%ED%99%94%EC%8B%9C%EA%B8%B06.jpg?type=w1200"));

        return itemList;
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap Map) {

            googleMap = Map;

            PolylineOptions polylineOptions;
            LatLng latLng = new LatLng(37.5665350, 126.9779690);
            LatLng latLng2 = new LatLng(37.5752428, 127.1317776);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

            ArrayList<LatLng> arrayPoints = new ArrayList<>();
            arrayPoints.add(latLng);
            arrayPoints.add(latLng2);

            //add marker

            TravelRouteDto td = new TravelRouteDto();
            td.setLatLng(latLng);
            getMarker(1, td);
            td.setLatLng(latLng2);
            getMarker(2, td);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

        }
    };

    Marker getMarker(int index, TravelRouteDto TravelRouteDto){

        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_custom_marker, null);

        TextView markerText = (TextView) marker.findViewById(R.id.text_marker);

        markerText.setText(index+"");

        Marker markerTemp = googleMap.addMarker(new MarkerOptions().position(TravelRouteDto.getLatLng()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));

        return markerTemp;

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
        return bitmap; }

}