package com.yapp.lazitripper.views;

import android.graphics.Color;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.Travel;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.util.TravelRoute;
import com.yapp.lazitripper.views.bases.BaseFragmentActivity;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/*
*
* 여행 추천 결과 화면
*
* */
public class TravelSummaryActivity extends BaseFragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("lazitripper");
    private GoogleMap mMap;
    private String TAG = "TrevelSummaryActivity";
    private int i=0;
    private ArrayList<Travel> travelList = new ArrayList<Travel>();
    private String uuid;

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

        String title;
        int typeid;
        String addr;
        String image;

        loadingDialog = new LoadingDialog(TravelSummaryActivity.this);
        loadingDialog.show();
        // SP 에서 저장된 테그들의 정보를 가져옴.
        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        //UUID를 가져오기 위한 <String> SharedPreferenceStore
        SharedPreferenceStore sharedPreferenceStore1 = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        uuid = (String)sharedPreferenceStore1.getPreferences(ConstantStore.UUID, String.class);


        //해당 여행 일자
        scheduleDateStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);
        scheduleDate = scheduleDateStore.getPreferences(ConstantStore.SCHEDULE_DATE,PickDate.class);

        // 선택 엑티비티에서 선택한 장소에 대한 정보를 가져옴.
        Intent intent = getIntent();
        ArrayList<PlaceInfoDto> beforeSelectPlaceList =
                (ArrayList<PlaceInfoDto>)intent.getSerializableExtra(ConstantIntent.PLACELIST);
        getTravelList();

        //요게 일단 최단거리긴한데 수정중
//        TravelRoute travelRoute = new TravelRoute(beforeSelectPlaceList);
//        ArrayList<PlaceInfoDto> shortRoute = travelRoute.findShortRoute();

        //리스트뷰
        placeListView = (ListView) findViewById(R.id.listview);

        adapter = new PlaceInfoAdapter();
        //adapter.addAllItem(beforeSelectPlaceList);
        adapter.addAllItem(beforeSelectPlaceList);

        placeListView.setAdapter(adapter);

            for(int i=0; i< beforeSelectPlaceList.size(); i++){

            addr = beforeSelectPlaceList.get(i).getAddr1();
            typeid = beforeSelectPlaceList.get(i).getContenttypeid();
            title = beforeSelectPlaceList.get(i).getTitle();
            image = beforeSelectPlaceList.get(i).getFirstimage();

            Travel travel = new Travel(typeid,title,addr,image);
            travelList.add(travel);

            /*Log.e(TAG ,"도시 : " + beforeSelectPlaceList.get(i).getAddr1());
            Log.e(TAG ,"타이틀 : " + beforeSelectPlaceList.get(i).getTitle());
            Log.e(TAG ,"주소 : " + beforeSelectPlaceList.get(i).getAddr2());
            Log.e(TAG ,"전화번호 : " + beforeSelectPlaceList.get(i).getTel());
            Log.e(TAG ,"이미지 : " + beforeSelectPlaceList.get(i).getFirstimage());
            Log.e(TAG ,"카테고리 ID : " + beforeSelectPlaceList.get(i).getContenttypeid());*/
        }
        if(uuid != null) {
            myRef.child("user").child(uuid).child("Travel").push().setValue(travelList);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 테그 뷰의 인스턴스를 바인딩 시킨 뒤, 테그뷰에 값을 넣어준다
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);

    }

    private void addTravelList(Travel travel){
        travelList.add(travel);
    }
    private void getTravelList(){

        myRef.child("user").child(uuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(TAG,"onDataChange");

                 for(DataSnapshot child : dataSnapshot.getChildren()){
                    addTravelList(child.getValue(Travel.class));
                }

                Iterator<Travel> iterator = travelList.iterator();
                while(iterator.hasNext()){
                    Travel travel = (Travel) iterator.next();
                    Log.e(TAG, "save travel title .. : " + travel.getTitle());
                }

               /*GenericTypeIndicator<List<Travel>> t = new GenericTypeIndicator<List<Travel>>() {};

                List<Travel> user_travelList = dataSnapshot.child("Travel").getValue(t);

                //DB에 저장된 값이 있으면 list에 저장
                if (user_travelList != null) {
                    Iterator<Travel> iterator = user_travelList.iterator();

                    while (iterator.hasNext()) {
                        Travel travel = (Travel) iterator.next();
                        //Log.d(TAG , "iterator test .. data is : " + travel.getTitle());
                        addTravelList(travel);
                    }
                }*/
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

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
