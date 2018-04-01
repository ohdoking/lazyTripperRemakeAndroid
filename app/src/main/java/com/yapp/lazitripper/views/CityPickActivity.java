package com.yapp.lazitripper.views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.RegionCodeDto;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityPickActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private Spinner citySpinner1;
    private Spinner citySpinner2;
    private Spinner citySpinner3;
    private Spinner citySpinner4;
    private Spinner citySpinner5;
    private Spinner citySpinner6;
    private Spinner citySpinner7;
    private Spinner citySpinner8;
    private Spinner citySpinner9;
    private Spinner citySpinner10;


    private Spinner dateSpinner1;
    private Spinner dateSpinner2;
    private Spinner dateSpinner3;
    private Spinner dateSpinner4;
    private Spinner dateSpinner5;
    private Spinner dateSpinner6;
    private Spinner dateSpinner7;
    private Spinner dateSpinner8;
    private Spinner dateSpinner9;
    private Spinner dateSpinner10;


    private LinearLayout cityPickLayout1;
    private LinearLayout cityPickLayout2;
    private LinearLayout cityPickLayout3;
    private LinearLayout cityPickLayout4;
    private LinearLayout cityPickLayout5;
    private LinearLayout cityPickLayout6;
    private LinearLayout cityPickLayout7;
    private LinearLayout cityPickLayout8;
    private LinearLayout cityPickLayout9;
    private LinearLayout cityPickLayout10;

    private boolean isDateTouch1 = false;
    private boolean isDateTouch2 = false;
    private boolean isDateTouch3 = false;
    private boolean isDateTouch4 = false;

    private boolean isCityTouch1 = false;
    private boolean isCityTouch2 = false;
    private boolean isCityTouch3 = false;
    private boolean isCityTouch4 = false;

    private int cityNum = 1;


    private Button nextBtn;

    private ArrayList<String> cities = new ArrayList<String>();

    private ArrayList<String> dates1 = new ArrayList<String>();
    private ArrayList<String> dates2 = new ArrayList<String>();
    private ArrayList<String> dates3 = new ArrayList<String>();
    private ArrayList<String> dates4 = new ArrayList<String>();
    private ArrayList<String> dates5 = new ArrayList<String>();



    private ArrayAdapter<CharSequence> cityAdapter;

    private ArrayAdapter<String> dateAdapter1;
    private ArrayAdapter<String> dateAdapter2;
    private ArrayAdapter<String> dateAdapter3;
    private ArrayAdapter<String> dateAdapter4;
    private ArrayAdapter<String> dateAdapter5;



    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;


    List<RegionCodeDto> regionCodeDtoList;

    LoadingDialog loadingDialog;
    boolean isData = false;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pick);

        getSupportActionBar().setElevation(0);

        cityPickLayout1 = (LinearLayout) findViewById(R.id.city_pick_layout_1);
        cityPickLayout2 = (LinearLayout) findViewById(R.id.city_pick_layout_2);
        cityPickLayout3 = (LinearLayout) findViewById(R.id.city_pick_layout_3);
        cityPickLayout4 = (LinearLayout) findViewById(R.id.city_pick_layout_4);
        cityPickLayout5 = (LinearLayout) findViewById(R.id.city_pick_layout_5);
        cityPickLayout6 = (LinearLayout) findViewById(R.id.city_pick_layout_6);
        cityPickLayout7 = (LinearLayout) findViewById(R.id.city_pick_layout_7);
        cityPickLayout8 = (LinearLayout) findViewById(R.id.city_pick_layout_8);
        cityPickLayout9 = (LinearLayout) findViewById(R.id.city_pick_layout_9);
        cityPickLayout10 = (LinearLayout) findViewById(R.id.city_pick_layout_10);



        citySpinner1 = (Spinner) findViewById(R.id.city_spinner_1);
        citySpinner2 = (Spinner) findViewById(R.id.city_spinner_2);
        citySpinner3 = (Spinner) findViewById(R.id.city_spinner_3);
        citySpinner4 = (Spinner) findViewById(R.id.city_spinner_4);
        citySpinner5 = (Spinner) findViewById(R.id.city_spinner_5);
        citySpinner6 = (Spinner) findViewById(R.id.city_spinner_6);
        citySpinner7 = (Spinner) findViewById(R.id.city_spinner_7);
        citySpinner8 = (Spinner) findViewById(R.id.city_spinner_8);
        citySpinner9 = (Spinner) findViewById(R.id.city_spinner_9);
        citySpinner10 = (Spinner) findViewById(R.id.city_spinner_10);

        dateSpinner1 = (Spinner) findViewById(R.id.date_spinner_1);
        dateSpinner2 = (Spinner) findViewById(R.id.date_spinner_2);
        dateSpinner3 = (Spinner) findViewById(R.id.date_spinner_3);
        dateSpinner4 = (Spinner) findViewById(R.id.date_spinner_4);
        dateSpinner5 = (Spinner) findViewById(R.id.date_spinner_5);
        dateSpinner6 = (Spinner) findViewById(R.id.date_spinner_6);
        dateSpinner7 = (Spinner) findViewById(R.id.date_spinner_7);
        dateSpinner8 = (Spinner) findViewById(R.id.date_spinner_8);
        dateSpinner9 = (Spinner) findViewById(R.id.date_spinner_9);
        dateSpinner10 = (Spinner) findViewById(R.id.date_spinner_10);


        dateAdapter1 = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates1);
        dateAdapter2 = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates2);
        dateAdapter3 = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates3);
        dateAdapter4 = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates4);
        dateAdapter5 = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates5);

        nextBtn = (Button)findViewById(R.id.next);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
//        dates1 = (ArrayList<String>) makeDateSpinner(0,Integer.valueOf(date)).clone();

        dates1.addAll(makeDateSpinner(0,Integer.valueOf(date)));
        dateAdapter1.notifyDataSetChanged();
        dateSpinner1.invalidate();

        Log.d("cityPickActivity", "make date : " + dateAdapter1.getCount() + " " + dates1.size());


        // Specify the layout to use when the list of choices appears
        dateAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        dateSpinner1.setAdapter(dateAdapter1);

        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();

        //@TODO 국가 정보를 받아서 지역을 뿌려준다.
//        Call<CommonResponse<RegionCodeDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper");
//
//        callRelionInfo.enqueue(new Callback<CommonResponse<RegionCodeDto>>() {
//            @Override
//            public void onResponse(Call<CommonResponse<RegionCodeDto>> call, Response<CommonResponse<RegionCodeDto>> response) {
//                regionCodeDtoList = response.body().getResponse().getBody().getItems().getItems();
//                int index = regionCodeDtoList.size();
//                cities.add("여행지를 선택하세요");
//                for ( int i = 0 ; i < index ; i++){
//                    cities.add(regionCodeDtoList.get(i).getName());
//                }
//                cityAdapter.notifyDataSetChanged();
//                //loadingDialog.dismiss();
//
//                //cityDropDown.setWheelData(list);
//                //cityDropDown.deferNotifyDataSetChanged();
//                isData = true;
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse<RegionCodeDto>> call, Throwable t) {
//                Log.i(" ",t.getMessage());
//            }
//        });

        cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        citySpinner1.setAdapter(cityAdapter);



        citySpinner1.setOnItemSelectedListener(this);
        citySpinner2.setOnItemSelectedListener(this);
        citySpinner2.setSelection(0, false);


        dateSpinner1.setOnItemSelectedListener(this);
        dateSpinner2.setOnItemSelectedListener(this);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllTravelInfo allTravelInfo = setAllTravleInfo(Integer.parseInt(date));
                Intent i = new Intent(CityPickActivity.this, ChoosePlaceActivity.class);
                i.putExtra(ConstantIntent.AllTRAVELINFO, allTravelInfo);
                i.putExtra(ConstantIntent.CURRENTDAY,1);
                startActivity(i);
                finish();
            }
        });




    }



    void getCityData(){
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();

        //@TODO 국가 정보를 받아서 지역을 뿌려준다.
        Call<CommonResponse<RegionCodeDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper");

        callRelionInfo.enqueue(new Callback<CommonResponse<RegionCodeDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<RegionCodeDto>> call, Response<CommonResponse<RegionCodeDto>> response) {
                regionCodeDtoList = response.body().getResponse().getBody().getItems().getItems();
                int index = regionCodeDtoList.size();
                ArrayList<String> list = new ArrayList<String>();
                for ( int i = 0 ; i < index ; i++){
                    list.add(regionCodeDtoList.get(i).getName());
                }

                loadingDialog.dismiss();

                //cityDropDown.setWheelData(list);
                //cityDropDown.deferNotifyDataSetChanged();
                isData = true;
            }

            @Override
            public void onFailure(Call<CommonResponse<RegionCodeDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });
    }




    //TODO 삭제해야할 목객체
    public AllTravelInfo setAllTravleInfo(int day){

        AllTravelInfo allTravelInfo = new AllTravelInfo();

        allTravelInfo.setTotalDay(day);

        int k = 1;

        for(int i = 0; i < cityNum; i++){
            TravelInfo travelInfo = new TravelInfo();

            int cityDate = 0;


            if(i == 0){
                for(int j = 0; j < Integer.parseInt(dateSpinner1.getSelectedItem().toString()); j++){
                    travelInfo.setDay(k);
                    travelInfo.setCityCode(getCityCode(citySpinner1.getSelectedItem().toString()));
                    Log.d("city pick", "city1 : " + citySpinner1.getSelectedItem().toString());

                }
            }
            else if(i == 1){
                for(int j = 0; j < Integer.parseInt(dateSpinner2.getSelectedItem().toString()); j++){
                    travelInfo.setDay(k);
                    travelInfo.setCityCode(getCityCode(citySpinner2.getSelectedItem().toString()));
                    Log.d("city pick", "city2 : " + citySpinner2.getSelectedItem().toString());

                }
            }
            else if(i == 2){
                for(int j = 0; j < Integer.parseInt(dateSpinner3.getSelectedItem().toString()); j++){
                    travelInfo.setDay(k);
                    travelInfo.setCityCode(getCityCode(citySpinner3.getSelectedItem().toString()));
                    Log.d("city pick", "city3 : " + citySpinner3.getSelectedItem().toString());

                }

            }
            else if(i == 3){
                for(int j = 0; j < Integer.parseInt(dateSpinner4.getSelectedItem().toString()); j++){
                    travelInfo.setDay(k);
                    travelInfo.setCityCode(getCityCode(citySpinner4.getSelectedItem().toString()));
                    Log.d("city pick", "city4 : " + citySpinner4.getSelectedItem().toString());

                }

            }
            else if(i == 4){
                for(int j = 0; j < Integer.parseInt(dateSpinner5.getSelectedItem().toString()); j++){
                    travelInfo.setDay(k);
                    travelInfo.setCityCode(getCityCode(citySpinner5.getSelectedItem().toString()));
                    Log.d("city pick", "city5 : " + citySpinner5.getSelectedItem().toString());

                }
            }

            allTravelInfo.setTraveInfoItem(travelInfo);

            k++;
        }
//        TravelInfo travelInfo = new TravelInfo();
//        travelInfo.setDay(1);
//        travelInfo.setCityCode(getCityCode(citySpinner.getSelectedItem().toString()));
//
//        TravelInfo travelInfo2 = new TravelInfo();
//        travelInfo2.setDay(2);
//        travelInfo2.setCityCode(getCityCode(citySpinner.getSelectedItem().toString()));
//
//        TravelInfo travelInfo3 = new TravelInfo();
//        travelInfo3.setDay(3);
//        travelInfo3.setCityCode(getCityCode(citySpinner.getSelectedItem().toString()));

//        allTravelInfo.setTraveInfoItem(travelInfo);
//        allTravelInfo.setTraveInfoItem(travelInfo2);
//        allTravelInfo.setTraveInfoItem(travelInfo3);

        return allTravelInfo;
    }

    public int getCityCode(String name){
        int cityCode = 0;

        switch (name) {
            case "서울":
                cityCode = 1;
                break;
            case "인천":
                cityCode = 2;
                break;
            case "대전":
                cityCode = 3;
                break;
            case "대구":
                cityCode = 4;
                break;
            case "광주":
                cityCode = 5;
                break;
            case "부산":
                cityCode = 6;
                break;
            case "울산":
                cityCode = 7;
                break;
            case "세종특별자치시":
                cityCode = 8;
                break;
            case "경기도":
                cityCode = 31;
                break;
            case "강원도":
                cityCode = 32;
                break;
            case "충청북도":
                cityCode = 33;
                break;
            case "충청남도":
                cityCode = 34;
                break;
            case "경상북도":
                cityCode = 35;
                break;
            case "경상남도":
                cityCode = 36;
                break;
            case "전라북도":
                cityCode = 37;
                break;
            case "전라남도":
                cityCode = 38;
                break;
            case "제주도":
                cityCode = 39;
                break;
            default:
                new Exception("no place code");
                break;
        }

        return cityCode;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int date1, date2, date3, date4, date5;
        int totalDate;
        Log.d("position", "pos : " + position);

        Spinner spinner = (Spinner)parent;

        switch(spinner.getId()){
            case R.id.city_spinner_1 :
                Log.d("city1", "city");
                if(dateSpinner1.getSelectedItem() != null) {
                    date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());

                    if (parent.getSelectedItemPosition() != 0 && date1 < Integer.parseInt(date)) {

                        cityPickLayout2.setVisibility(View.VISIBLE);
                        citySpinner2.setAdapter(cityAdapter);

                        dates2.clear();
                        dates2.addAll(makeDateSpinner(date1, Integer.parseInt(date)));
                        dateAdapter2.notifyDataSetChanged();
                        dateSpinner2.setAdapter(dateAdapter2);
                        dateSpinner2.invalidate();

                        cityNum += 1;

                    } else {
                        if(parent.getChildAt(0) != null) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                        }
                    }
                }
                break;
            case R.id.city_spinner_2 :
                if(dateSpinner2.getSelectedItem() != null) {
                    date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                    date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                    totalDate = date1 + date2;

                    if (totalDate < Integer.parseInt(date) && parent.getSelectedItemPosition() != 0) {

                        cityPickLayout3.setVisibility(View.VISIBLE);
                        citySpinner3.setAdapter(cityAdapter);

                        dates3.clear();
                        dates3.addAll(makeDateSpinner(date2, Integer.parseInt(date) - date1));
                        dateAdapter3.notifyDataSetChanged();
                        dateSpinner3.setAdapter(dateAdapter3);
                        dateSpinner3.invalidate();


                        citySpinner3.setOnItemSelectedListener(this);
                        cityNum += 1;


                    } else if (totalDate == Integer.parseInt(date)) {
                        cityPickLayout3.setVisibility(View.GONE);
                        cityPickLayout4.setVisibility(View.GONE);
                        cityPickLayout5.setVisibility(View.GONE);
                        cityNum = 2;

                    } else {
                        if(parent.getChildAt(0) != null) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                        }
                    }

                }
                break;

            case R.id.city_spinner_3 :
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                date3 = Integer.parseInt(dateSpinner3.getSelectedItem().toString());
                totalDate = date1 + date2 + date3;

                if( totalDate < Integer.parseInt(date) && parent.getSelectedItemPosition() != 0) {

//                    citySpinner3.setEnabled(false);
//                    dateSpinner3.setEnabled(false);
                    cityPickLayout4.setVisibility(View.VISIBLE);
                    citySpinner4.setAdapter(cityAdapter);

                    dates4.clear();
                    dates4.addAll( makeDateSpinner(date3, Integer.parseInt(date) - totalDate + date3));
                    dateAdapter4.notifyDataSetChanged();
                    dateSpinner4.setAdapter(dateAdapter4);
                    dateSpinner4.invalidate();


                    citySpinner4.setOnItemSelectedListener(this);
                    cityNum += 1;


                }
                else if(totalDate == Integer.parseInt(date)){
                    cityPickLayout4.setVisibility(View.GONE);
                    cityPickLayout5.setVisibility(View.GONE);
                    cityNum = 3;

                }

                else{
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }
                break;

            case R.id.city_spinner_4 :
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                date3 = Integer.parseInt(dateSpinner3.getSelectedItem().toString());
                date4 = Integer.parseInt(dateSpinner4.getSelectedItem().toString());

                totalDate = date1 + date2 + date3 + date4;

                if( totalDate < Integer.parseInt(date) && parent.getSelectedItemPosition() != 0) {
//                    citySpinner4.setEnabled(false);
//                    dateSpinner4.setEnabled(false);
                    cityPickLayout5.setVisibility(View.VISIBLE);
                    citySpinner5.setAdapter(cityAdapter);

                    dates5.clear();
                    dates5.addAll( makeDateSpinner(date4, Integer.parseInt(date) - totalDate + date4));
                    dateAdapter5.notifyDataSetChanged();
                    dateSpinner5.setAdapter(dateAdapter5);
                    dateSpinner5.invalidate();


                    citySpinner5.setOnItemSelectedListener(this);
                    cityNum += 1;

                }
                else if(totalDate == Integer.parseInt(date)){
                    cityPickLayout5.setVisibility(View.GONE);
                    cityNum = 4;

                }
                else{
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }
                break;

            case R.id.city_spinner_5 :
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                date3 = Integer.parseInt(dateSpinner3.getSelectedItem().toString());
                date4 = Integer.parseInt(dateSpinner4.getSelectedItem().toString());
                date5 = Integer.parseInt(dateSpinner5.getSelectedItem().toString());

                totalDate = date1 + date2 + date3 + date4 + date5;

                if( totalDate < Integer.parseInt(date)) {
                    Toast.makeText(this, "최대 5곳의 여행지를 선택가능합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }
                break;

            case R.id.city_spinner_6 :
                break;
            case R.id.city_spinner_7 :
                break;
            case R.id.city_spinner_8 :
                break;
            case R.id.city_spinner_9 :
                break;

            case R.id.city_spinner_10 :
                break;
            case R.id.date_spinner_1:

                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                Log.d("date spinner", "date : " + date);


                if(date1 == Integer.parseInt(date)){

                    Log.d("spinner1", "date1 : " + date1);
                    cityPickLayout2.setVisibility(View.GONE);
                    cityPickLayout3.setVisibility(View.GONE);
                    cityPickLayout4.setVisibility(View.GONE);
                    cityPickLayout5.setVisibility(View.GONE);
                    cityNum = 1;

                }
                else if(date1 < Integer.parseInt(date) && citySpinner1.getSelectedItemPosition() != -1){
                    Log.d("spinner1", "citySpinner position : "+ citySpinner1.getSelectedItemPosition());

                    dates2.clear();
                    dates2.addAll(makeDateSpinner(date1, Integer.parseInt(date)));
                    dateAdapter2.notifyDataSetChanged();
                    dateSpinner2.setAdapter(dateAdapter2);
                    dateSpinner2.invalidate();

                    cityNum += 1;

                }else{
                    cityPickLayout2.setVisibility(View.GONE);


                }


                break;

            case R.id.date_spinner_2:
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                totalDate = date1 + date2;
                Log.d("CityPickActivity", "date spinner : "+ totalDate);

                if(totalDate == Integer.parseInt(date)){
                    Log.d("CityPickActivity", "date spinner : "+ totalDate);

                    cityPickLayout3.setVisibility(View.GONE);
                    cityNum = 2;

                }else if(parent.getSelectedItemPosition() != 0){
                    Log.d("CityPickActivity", "parent 2 : " + parent.getSelectedItemPosition());
                    cityPickLayout3.setVisibility(View.VISIBLE);
                    citySpinner3.setAdapter(cityAdapter);

                    dates3.clear();
                    dates3.addAll(makeDateSpinner(date2, Integer.parseInt(date) - date1));
                    dateAdapter3.notifyDataSetChanged();
                    dateSpinner3.setAdapter(dateAdapter3);
                    dateSpinner3.invalidate();


                    citySpinner3.setOnItemSelectedListener(this);
                    cityNum += 1;

                }

                break;
            case R.id.date_spinner_3:
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                date3 = Integer.parseInt(dateSpinner3.getSelectedItem().toString());
                totalDate = date1 + date2 + date3;
                Log.d("CityPickActivity", "parent 3 : " + parent.getSelectedItemPosition());

                if(totalDate == Integer.parseInt(date)){
                    Log.d("CityPickActivity", "total parent 3 : " + parent.getSelectedItemPosition());

                    cityPickLayout4.setVisibility(View.GONE);
                    cityNum = 3;

                }
                else if(parent.getSelectedItemPosition() != 0){
                    cityPickLayout4.setVisibility(View.VISIBLE);
                    citySpinner4.setAdapter(cityAdapter);

                    dates4.clear();
                    dates4.addAll( makeDateSpinner(date3, Integer.parseInt(date) - totalDate + date3));
                    dateAdapter4.notifyDataSetChanged();
                    dateSpinner4.setAdapter(dateAdapter4);
                    dateSpinner4.invalidate();


                    citySpinner4.setOnItemSelectedListener(this);
                    cityNum += 1;

                }
                break;
            case R.id.date_spinner_4:
                date1 = Integer.parseInt(dateSpinner1.getSelectedItem().toString());
                date2 = Integer.parseInt(dateSpinner2.getSelectedItem().toString());
                date3 = Integer.parseInt(dateSpinner3.getSelectedItem().toString());
                date4 = Integer.parseInt(dateSpinner4.getSelectedItem().toString());
                totalDate = date1 + date2 + date3 + date4;

                if(totalDate == Integer.parseInt(date)){
                    cityPickLayout5.setVisibility(View.GONE);
                    cityNum = 4;

                }
                else if(parent.getSelectedItemPosition() != 0){
                    cityPickLayout5.setVisibility(View.VISIBLE);
                    citySpinner5.setAdapter(cityAdapter);

                    dates5.clear();
                    dates5.addAll( makeDateSpinner(date4, Integer.parseInt(date) - totalDate + date4));
                    dateAdapter5.notifyDataSetChanged();
                    dateSpinner5.setAdapter(dateAdapter5);
                    dateSpinner5.invalidate();


                    citySpinner5.setOnItemSelectedListener(this);
                    cityNum += 1;

                }
                break;
            case R.id.date_spinner_5:

                break;





        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner spinner = (Spinner)parent;

//        switch(spinner.getId()){
//            case R.id.city_spinner_1 :
//
//                citySpinner2.setOnItemSelectedListener(this);
//                break;
//        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.date_spinner_1:
                isDateTouch1 = true;
                break;
            case R.id.date_spinner_2:
                isDateTouch2 = true;
                break;
            case R.id.date_spinner_3:
                isDateTouch3 = true;
                break;
            case R.id.date_spinner_4:
                isDateTouch4 = true;
                break;

        }
        return false;
    }


    public ArrayList<String> makeDateSpinner(int selectDate, int allDate){

        ArrayList<String> dates = new ArrayList<String>();

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates);

        allDate = allDate - selectDate;
        for(int i = 1; i <= allDate; i++){
            dates.add(String.valueOf(i));
            Log.d("CityPickActivity", "date : " + String.valueOf(i));
        }

        return dates;
    }
}
