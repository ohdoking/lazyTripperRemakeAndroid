package com.yapp.lazitripper.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.RemainingDay;
import com.yapp.lazitripper.dto.Travel;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import me.gujun.android.taggroup.TagGroup;

/*
*
* 여행 추천 결과 화면
*
* */
public class TravelSummaryActivity extends BaseAppCompatActivity implements OnMapReadyCallback {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("lazitripper");
    private GoogleMap mMap;
    private String TAG = "TrevelSummaryActivity";
    private int i=0;
    private ArrayList<Travel> travelList = new ArrayList<Travel>();
    private String uuid;
    private boolean IsRemain;

    //리스트뷰
    ListView placeListView;
    PlaceInfoAdapter adapter;

    SharedPreferenceStore<PickDate> scheduleDateStore;
    PickDate scheduleDate;
    PickDate totalDate;
    String needWriteKey;
    String startdate;
    String key;
    LoadingDialog loadingDialog;
    int contentid;
    private SharedPreferenceStore sharedPreferenceStore1;
    PickDate alldate;

    private Boolean exit = false;
    Integer cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_summary);
        /* setHeaer */
        setHeader();

        ImageView rightImage = getRightImageView();
        String title;
        int typeid;
        String addr;
        String image;
        IsRemain = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        loadingDialog = new LoadingDialog(TravelSummaryActivity.this);
        loadingDialog.show();

        LinearLayout mapLayout = (LinearLayout)findViewById(R.id.mapLayout);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        View googleMap = (View)findViewById(R.id.map);

        googleMap.setOnTouchListener((new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("scroll","asdf");
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        }));
        //맵과 마찬가지로 리스트뷰가 문제가되네..이런...
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }

        });





        // 도시 코드를 가저옴
        cityCode = getIntent().getIntExtra(ConstantIntent.CITYCODE,1);

        // SP 에서 저장된 테그들의 정보를 가져옴.
        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        //UUID를 가져오기 위한 <String> SharedPreferenceStore
        sharedPreferenceStore1 = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);
        uuid = (String)sharedPreferenceStore1.getPreferences(ConstantStore.UUID, String.class);
        needWriteKey = (String)sharedPreferenceStore1.getPreferences(ConstantStore.KEY, String.class);

        alldate = (PickDate) sharedPreferenceStore1.getPreferences(ConstantStore.DATEKEY, PickDate.class);
        Log.d(TAG, "Allday -> startDay =" + alldate.getStartDate() + "All day -> finishDay" + alldate.getFinishDate());

        String remainString = (String)sharedPreferenceStore1.getPreferences(ConstantStore.REMAINFLAG, String.class);
        if(remainString != null && remainString.equals("true")) IsRemain = true;

        //도시선택한 여행 일정 (Travel 단위)
        scheduleDateStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);
        scheduleDate = scheduleDateStore.getPreferences(ConstantStore.SCHEDULE_DATE,PickDate.class);
        totalDate = scheduleDateStore.getPreferences(ConstantStore.DATEKEY,PickDate.class);
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        Log.d(TAG,"startDay : " + scheduleDate.getStartDate());
        Log.d(TAG,"FinishDay : " + scheduleDate.getFinishDate());

        //db에 저장될 해당 travel의 key값 설정
        key = myRef.child("user").child(uuid).child("Travel").push().getKey();
        //일정 작성한 날의 시작일.
        startdate = fmt.format(scheduleDate.getStartDate());
        // 선택 액티비티에서 선택한 장소에 대한 정보를 가져옴.
        Intent intent = getIntent();
        ArrayList<PlaceInfoDto> beforeSelectPlaceList =
                (ArrayList<PlaceInfoDto>)intent.getSerializableExtra(ConstantIntent.PLACELIST);

        if(needWriteKey != null && IsRemain) {
            key = needWriteKey;
            Log.d(TAG, "작성중 일정 작성완료!! date key is .." + needWriteKey);

        }

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
                travel.setContentid(beforeSelectPlaceList.get(i).getContentid());
                //Travel CONTENTID 추가
            travelList.add(travel);
                contentid  = travel.getContentid();

        }

        rightImage.setImageResource(android.R.drawable.ic_menu_save);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDB();
                Intent i = new Intent(TravelSummaryActivity.this, ChooseCityActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);
            }
        });

     ArrayList<String> dayRemaining = new ArrayList<String>();
        //전체 일정이 하루 이상일 때
        if(1< alldate.getPeriod()){
            //// TODO: 2017-03-29 여기서 저장(update)해야할 데이터는 전체일정 - 선택일정 이여야 함(여러날 가능)
            sharedPreferenceStore1.savePreferences(ConstantStore.KEY,key);
            sharedPreferenceStore1.savePreferences(ConstantStore.REMAINFLAG,"true");

            //마지막날까지 하루씩 증가하면서 다음날을 구함.
            for(int i=1; i<alldate.getPeriod(); i++) {
                //시작점은 scheduleDate의 finalDate
                //// TODO: 2017-03-25 만약 사용자가 2일 이상을 선택했고, 중간 날만 여행일정 작성 시에 이전 날짜는 저장 안됨..
                Date nextDay = alldate.getDate(scheduleDate, i);
                //여기서 nextDay가 사용자가 작성 안한 날
                Log.d(TAG, sdformat.format(nextDay));
                dayRemaining.add(sdformat.format(nextDay));
            }
        }

        RemainingDay remainingDay = new RemainingDay(key, dayRemaining);
        if(!dayRemaining.isEmpty()){
            myRef.child("user").child(uuid).child("needSelect").child(key).setValue(remainingDay);
        }

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
    private void saveDataToDB(){
        //// TODO: 저장버튼 누르기 전에 뒤로가면 dayRemaining만 저장되고 travel은 저장이 안되므로 해당 로직 구현해야함.
        String child = startdate+"@";
        child += Integer.toString(cityCode);
        myRef.child("user").child(uuid).child("Travel").child(key).child(child).setValue(travelList);
        // 지금은 한번에 하루만 추가 되므로 1을 빼면 됨
        alldate.setPeriod(alldate.getPeriod()-1);
//        sharedPreferenceStore1.savePreferences(ConstantStore.DATEKEY,alldate);

        if(IsRemain == true ) { // 이번에 저장한 데이터가 remaining day인 경우
            deleteDataToDB(startdate);
            //// TODO: 2017-03-29 shared 초기화-> 전체일정 작성 완료했으면 날짜들도 초기화해줘야함. period도 수정
            sharedPreferenceStore1.savePreferences(ConstantStore.REMAINFLAG,"false");
        }
    }
    ///남은 날 저장 완료 후 돌아가는 activity를 datePick에서 home으로..?
    private void deleteDataToDB(final String deleteDate){
        myRef.child("user").child(uuid).child("needSelect").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    RemainingDay day = child.getValue(RemainingDay.class);
                    ArrayList<String> array = new ArrayList<String>();
                    array = day.getDayRemaining();
                    ArrayList<String> arr_new = new ArrayList<String>();
                    String key = day.getKey();

                    Log.d(TAG,"day.get key = " + key);
                    Log.d(TAG,"array size : "+array.size());
                    RemainingDay newday = new RemainingDay();


                    if (array.size() > 1) {
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).equals(deleteDate)) {
                            }else arr_new.add(day.getDayRemaining().get(i));
                            }
                         }else{//array가 1일때
                        Log.d(TAG,"delete child to needSelect");
                        myRef.child("user").child(uuid).child("needSelect").child(key).removeValue();
                        arr_new = null;
                        if(arr_new != null) {
                            newday.setDayRemaining(arr_new);
                            newday.setKey(key);
                            Log.d(TAG,"update child to needSelect");
                            myRef.child("user").child(uuid).child("needSelect").child(key).setValue(newday);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                }
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

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        int index = adapter.getCount();
        LatLng[] latLngList = new LatLng[index];
        for(int i=0;i<adapter.getCount();i++){
            PlaceInfoDto temp = adapter.getItem(i);
            makeMarker(temp.getMapy(), temp.getMapx(), temp.getTitle(), null);
            latLngList[i] = new LatLng(temp.getMapy(), temp.getMapx());
        }

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(latLngList)
                .width(20)
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
        //TextView addr,tel;
        int position;


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

        public void assignNumber(){
            //items.
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

            view.setNumber(String.valueOf(position+1));

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
