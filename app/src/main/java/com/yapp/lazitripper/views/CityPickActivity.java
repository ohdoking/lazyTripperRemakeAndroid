package com.yapp.lazitripper.views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.RegionCodeDto;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.views.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityPickActivity extends AppCompatActivity {

    private Spinner citySpinner;
    private Spinner dateSpinner;
    private Button nextBtn;

    private ArrayList<String> cities = new ArrayList<String>();
    private ArrayList<String> dates = new ArrayList<String>();

    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> dateAdapter;

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


        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        cityAdapter = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,cities);

        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
        dateAdapter = new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_item ,dates);

        nextBtn = (Button)findViewById(R.id.next);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        Log.d("date", "date is " + date);
        for(int i = 1; i <= Integer.valueOf(date); i++){
            dates.add(String.valueOf(i));
        }
        dateAdapter.notifyDataSetChanged();


        // Specify the layout to use when the list of choices appears
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        dateSpinner.setAdapter(dateAdapter);


        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();

        //@TODO 국가 정보를 받아서 지역을 뿌려준다.
        Call<CommonResponse<RegionCodeDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper");

        callRelionInfo.enqueue(new Callback<CommonResponse<RegionCodeDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<RegionCodeDto>> call, Response<CommonResponse<RegionCodeDto>> response) {
                regionCodeDtoList = response.body().getResponse().getBody().getItems().getItems();
                int index = regionCodeDtoList.size();
                cities.add("여행지를 선택하세요");
                for ( int i = 0 ; i < index ; i++){
                    cities.add(regionCodeDtoList.get(i).getName());
                }
                cityAdapter.notifyDataSetChanged();
                //loadingDialog.dismiss();

                //cityDropDown.setWheelData(list);
                //cityDropDown.deferNotifyDataSetChanged();
                isData = true;
            }

            @Override
            public void onFailure(Call<CommonResponse<RegionCodeDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });



        // Specify the layout to use when the list of choices appears
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        citySpinner.setAdapter(cityAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllTravelInfo allTravelInfo = setAllTravleInfo(Integer.valueOf(date));
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

    void renderSecondSpinner(){


        //cityDropDown.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        //cityDropDown.setSkin(WheelView.Skin.Holo); // common皮肤
        cities.add("무");
        //ityDropDown.setWheelData(cities);
//        cityDropDown.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//
//                if(isData){
//                    cityNum = regionCodeDtoList.get(position).getCode();
////                    Toast.makeText(getBaseContext(), "You have selected City : " + cityNum,
////                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }


    //TODO 삭제해야할 목객체
    public AllTravelInfo setAllTravleInfo(int day){

        AllTravelInfo allTravelInfo = new AllTravelInfo();

        allTravelInfo.setTotalDay(day);

        for(int i = 0; i < day; i++){
            TravelInfo travelInfo = new TravelInfo();
            travelInfo.setDay(i+1);
            travelInfo.setCityCode(getCityCode(citySpinner.getSelectedItem().toString()));
            allTravelInfo.setTraveInfoItem(travelInfo);
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
}
