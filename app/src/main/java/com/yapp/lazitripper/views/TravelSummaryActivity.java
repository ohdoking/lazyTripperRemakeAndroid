package com.yapp.lazitripper.views;

import android.graphics.Color;
import android.os.Handler;
import android.widget.ListView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.TravelRoute;
import com.yapp.lazitripper.views.bases.BaseFragmentActivity;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

/*
*
* 여행 추천 결과 화면
*
* */
public class TravelSummaryActivity extends BaseFragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String TAG = "TrevelSummaryActivity";
    private int i=0;
    //리스트뷰
    ListView placeListView;
    PlaceInfoAdapter adapter;

    SharedPreferenceStore<PickDate> scheduleDateStore;
    PickDate scheduleDate;


    LoadingDialog loadingDialog;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);

        loadingDialog = new LoadingDialog(TravelSummaryActivity.this);
        loadingDialog.show();
        // SP 에서 저장된 테그들의 정보를 가져옴.
        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);


        //해당 여행 일자
        scheduleDateStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);
        scheduleDate = scheduleDateStore.getPreferences(ConstantStore.SCHEDULE_DATE,PickDate.class);

        // 선택 엑티비티에서 선택한 장소에 대한 정보를 가져옴.
        Intent intent = getIntent();
        ArrayList<PlaceInfoDto> beforeSelectPlaceList =
                (ArrayList<PlaceInfoDto>)intent.getSerializableExtra(ConstantIntent.PLACELIST);

        //요게 일단 최단거리긴한데 수정중
        TravelRoute travelRoute = new TravelRoute(beforeSelectPlaceList);
        ArrayList<PlaceInfoDto> shortRoute = travelRoute.findShortRoute();

        //리스트뷰
        placeListView = (ListView) findViewById(R.id.listview);

        adapter = new PlaceInfoAdapter();
        //adapter.addAllItem(beforeSelectPlaceList);
        adapter.addAllItem(shortRoute);

        placeListView.setAdapter(adapter);

        //경계값 오류 처리해야함.
//        for(i=0; !beforeSelectPlaceList.isEmpty(); i++){
//            Log.e(TAG, beforeSelectPlaceList.get(i).getTitle());
//        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 테그 뷰의 인스턴스를 바인딩 시킨 뒤, 테그뷰에 값을 넣어준다
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(adapter.getItem(0).getMapy(), adapter.getItem(0).getMapx())   // 위도, 경도
        ));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(9);
        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        int index = adapter.getCount();
        LatLng[] latLngList = new LatLng[index];
        for(int i=0;i<adapter.getCount();i++){
            PlaceInfoDto temp = adapter.getItem(i);
            makeMarker(temp.getMapy(), temp.getMapx(), temp.getAddr1(), null);
            latLngList[i] = new LatLng(temp.getMapy(), temp.getMapx());
        }

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(latLngList)
                .width(30)
                .color(Color.parseColor("#FEDF6B")));
        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(),
                        marker.getTitle() + " 클릭했음"
                        , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        loadingDialog.dismiss();

    }


    void makeMarker(float lat,float lng,String title,String sni){

        MarkerOptions temp = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.round_icon);

        temp.position(new LatLng(lat, lng)).title(title).snippet(sni).icon(icon);
        Log.i("ohdoking-lat",lat+" / " + lng);
        mMap.addMarker(temp).showInfoWindow();
    }


    class PlaceInfoAdapter extends BaseAdapter{

        ArrayList<PlaceInfoDto> items = new ArrayList<>();

        public void addItem(PlaceInfoDto dto){
            items.add(dto);
        }

        public void addAllItem(ArrayList<PlaceInfoDto> dto){
            items.addAll(dto);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public PlaceInfoDto getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PlaceInfoItem view = null;
            if(convertView == null) {
                view = new PlaceInfoItem(getApplicationContext());
            }else{
                view = (PlaceInfoItem) convertView;
            }
            PlaceInfoDto curItem = items.get(position);

            view.setImage(curItem.getFirstimage());
            view.setLocatioin(curItem.getAddr1());
            view.setel(curItem.getTel());
            view.setTitle(curItem.getTitle());

            return view;
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, R.string.back_cause,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
