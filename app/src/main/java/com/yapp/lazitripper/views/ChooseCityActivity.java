package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.RegionCodeDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCityActivity extends BaseAppCompatActivity {

    private SharedPreferenceStore<PickDate> sharedPreferenceStore;

    WheelView countryDropDown;
    WheelView cityDropDown;
    WeekCalendar weekCalendar;
    ImageView selectPlaceBtn;
    RingProgressBar mRingProgressBar;

    boolean isData = false;
    ArrayList<String> country = new ArrayList<String>();

    ArrayList<String> cities = new ArrayList<String>();

    public RegionCodeDto regionCodeDtoDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;

    List<RegionCodeDto> regionCodeDtoList;
    ArrayAdapter<String> adapter2;

    //city 의 id를 저장
    Integer cityNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        setHeader();

        getRightImageView().setVisibility(View.INVISIBLE);
        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.arrow);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





        sharedPreferenceStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);

        PickDate pickDate = sharedPreferenceStore.getPreferences(ConstantStore.DATEKEY, PickDate.class);
        Log.i("ohdoking",pickDate.getStartDate() + " / " + pickDate.getPeriod());

        // Get reference of SpinnerView from layout/main_activity.xml
        countryDropDown =(WheelView)findViewById(R.id.country_spinner);
        cityDropDown =(WheelView)findViewById(R.id.city_spinner);
        selectPlaceBtn = (ImageView) findViewById(R.id.selectPlaceBtn);
        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,country);

        countryDropDown.setWheelAdapter(new ArrayWheelAdapter(this));
        countryDropDown.setSkin(WheelView.Skin.Holo);

        country.add("한국");
        country.add("미국");
        countryDropDown.setWheelData(country);
            Log.d("tttttttt","test");

        mRingProgressBar = (RingProgressBar) findViewById(R.id.progress_bar);

        showProgressBar(2L, pickDate.getPeriod());
        // Set the progress bar's progress
        mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener()
        {

            @Override
            public void progressToComplete()
            {
                // Progress reaches the maximum callback default Max value is 100
                Toast.makeText(ChooseCityActivity.this, "complete", Toast.LENGTH_SHORT).show();
            }
        });
//        countryDropDown.setAdapter(adapter);
//
        countryDropDown.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                int sid=countryDropDown.getSelectedItemPosition();
                Log.d("tttttttt","test2");
                cityDropDown.setWheelClickable(true);
                getCityData(position);
            }
        });

        //다음으로 버튼
        selectPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChooseCityActivity.this, ChoosePlaceActivity.class);
                i.putExtra(ConstantIntent.CITYCODE, cityNum);
                startActivity(i);
            }
        });



        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(ChooseCityActivity.this,
                        "You Selected " + dateTime.toString(), Toast.LENGTH_SHORT).show();
            }



        });
        renderSecondSpinner();
    }

    /*
        도시 데이터를 불러온다.
    */
    void getCityData(Integer areaCode){
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();
        //@TODO 국가 정보를 받아서 지역을 뿌려준다.
        Call<CommonResponse<RegionCodeDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper");
        Log.d("tttttttt","test3");

        callRelionInfo.enqueue(new Callback<CommonResponse<RegionCodeDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<RegionCodeDto>> call, Response<CommonResponse<RegionCodeDto>> response) {
                regionCodeDtoList = response.body().getResponse().getBody().getItems().getItems();
                int index = regionCodeDtoList.size();
                ArrayList<String> list = new ArrayList<String>();
                for ( int i = 0 ; i < index ; i++){
                    list.add(regionCodeDtoList.get(i).getName());
                }

                Log.d("ttttttttt",list.size() + "test4");
//                renderSecondSpinner();
                cityDropDown.setWheelData(list);
//                cityDropDown.setWheelSize(placeListView.size());
//                cityDropDown.setLoop(true);
//                cityDropDown.setClickable(true);
//                cityDropDown.resetDataFromTop(placeListView);
                cityDropDown.deferNotifyDataSetChanged();
//                cityDropDown.onRemoteAdapterDisconnected();
//                cityDropDown.notifyAll();
                isData = true;
//                adapter2.addAll(placeListView);
//                adapter2.notifyDataSetChanged();


//                renderSecondSpinner(cities);
            }

            @Override
            public void onFailure(Call<CommonResponse<RegionCodeDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });
    }

    void renderSecondSpinner(){
        adapter2= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,cities);

        cityDropDown.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        cityDropDown.setSkin(WheelView.Skin.Holo); // common皮肤
        cities.add("무");
        cityDropDown.setWheelData(cities);
        cityDropDown.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

                if(isData){
                    cityNum = regionCodeDtoList.get(position).getCode();
                    Toast.makeText(getBaseContext(), "You have selected City : " + cityNum,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void showProgressBar(Long pre, Long total){//현재일정, 총 일수
        Long l = pre*100/total;
        mRingProgressBar.setProgress(l.intValue());
        
    }


}