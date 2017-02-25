package com.yapp.lazitripper.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PlaceInfoDto;

import java.util.ArrayList;

public class TravelSummaryActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;



    //리스트뷰
    ListView list;
    PlaceInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);



        //리스트뷰
        list = (ListView) findViewById(R.id.listview);

        adapter = new PlaceInfoAdapter();

        PlaceInfoDto a = new PlaceInfoDto();
        PlaceInfoDto b = new PlaceInfoDto();
        PlaceInfoDto c = new PlaceInfoDto();
        a.setAddr1("???");a.setMapx(37.555744f);a.setMapy(126.970431f);
        b.setAddr1("adf");b.setMapx(37.755744f);b.setMapy(126.970431f);
        c.setAddr1("sefes");c.setMapx(37.855744f);c.setMapy(126.970431f);


        adapter.addItem(a);
        adapter.addItem(b);
        adapter.addItem(c);

        list.setAdapter(adapter);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





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
                new LatLng(37.555744, 126.970431)   // 위도, 경도
        ));

        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
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

        for(int i=0;i<adapter.getCount();i++){
            PlaceInfoDto temp = (PlaceInfoDto)adapter.getItem(i);
            makeMarker(temp.getMapx(), temp.getMapy(), temp.getAddr1(), null);

        }
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


    private void makeMarker(float lat,float lng,String title,String sni){

        MarkerOptions temp = new MarkerOptions();
        temp.position(new LatLng(lat, lng)).title(title).snippet(sni);
        mMap.addMarker(temp).showInfoWindow();

    }


    class PlaceInfoAdapter extends BaseAdapter{

        ArrayList<PlaceInfoDto> items = new ArrayList<>();


        public void addItem(PlaceInfoDto dto){
            items.add(dto);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
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

            view.setName(curItem.getAddr1());
            view.setLat(curItem.getMapx().toString());
            view.setLng(curItem.getMapy().toString());


            return view;
        }
    }



}
