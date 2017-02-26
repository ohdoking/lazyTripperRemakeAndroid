package com.yapp.lazitripper.activity;

import android.graphics.Color;
import android.widget.ListView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

public class TravelSummaryActivity extends BaseFragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    //리스트뷰
    ListView list;
    PlaceInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);

        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        Intent intent = getIntent();
        ArrayList<PlaceInfoDto> list2 =
                (ArrayList<PlaceInfoDto>)intent.getSerializableExtra(ConstantIntent.PLACELIST);

        //리스트뷰
        list = (ListView) findViewById(R.id.listview);

        adapter = new PlaceInfoAdapter();
        adapter.addAllItem(list2);

        list.setAdapter(adapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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




        // animateCamera() 는 근거리에선 부드럽게 변경합니다

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.
        /*MarkerOptions marker = new MarkerOptions();
        marker .position(new LatLng(37.555744, 126.970431))
                .title("서울역")
                .snippet("Seoul Station");
        googleMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

        MarkerOptions first = new MarkerOptions();
        first.position(new LatLng(37.521280, 127.041268)).title("몰라").snippet("idonknow");
        googleMap.addMarker(first).showInfoWindow();

        MarkerOptions second = new MarkerOptions();
        second.position(new LatLng(37.517180, 127.041268)).title("몰라").snippet("idonknow");
        googleMap.addMarker(second).showInfoWindow();
        */

        //makeMarker(37.1f,127.0f,"as","Esf");
        //makeMarker(37.2f,127.1f,"aersdf","gEsfsf");
        //makeMarker(37.3f,127.2f,"awesfsag","qEsf");

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(adapter.getItem(0).getMapy(), adapter.getItem(0).getMapx())   // 위도, 경도
        ));
//        View v = getSupportFragmentManager().findFragmentById(R.id.map).getView();
//        v.setAlpha(0.5f);
        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
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


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
            Log.i("ohdoking-lat","!!!");
//            makeMarker(curItem.getMapx(), curItem.getMapy(), curItem.getAddr1(), null);


            return view;
        }
    }



}
