package com.yapp.lazitripper.views;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.ChooseDate;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.RegionCodeDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;
import com.yapp.lazitripper.views.component.weekcalendar.LazyWeekCalendar;
import com.yapp.lazitripper.views.component.weekcalendar.OnDateClickListener;
import com.yapp.lazitripper.views.component.weekcalendar2.HorizontalCalendar;
import com.yapp.lazitripper.views.component.weekcalendar2.HorizontalCalendarListener;
import com.yapp.lazitripper.views.dialog.LoadingDialog;
import com.yapp.lazitripper.views.dialog.SetPlaceCountDialog;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCityActivity extends BaseAppCompatActivity {

    private SharedPreferenceStore<PickDate> sharedPreferenceStore;

    WheelView countryDropDown;
    WheelView cityDropDown;
//    LazyWeekCalendar weekCalendar;
    HorizontalCalendar horizontalCalendar;
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

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        setHeader();

        loadingDialog = new LoadingDialog(ChooseCityActivity.this);
        loadingDialog.show();

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
//        weekCalendar = (LazyWeekCalendar) findViewById(R.id.weekCalendar);


        Integer period = 7;
        if(pickDate.getPeriod().intValue() < period){
            period = pickDate.getPeriod().intValue();
        }

        ArrayList<Date> chooseDates = new ArrayList<Date>();

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tommorow = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nextTommorow = calendar.getTime();


        chooseDates.add(tommorow);
        chooseDates.add(nextTommorow);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.weekCalendar)
                .setChooseDate(chooseDates)
                .startDate(pickDate.getStartDate())
                .endDate(pickDate.getFinishDate())
                .datesNumberOnScreen(period)   // Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")	  // WeekDay text format
                .dayNumberFormat("dd")    // Date format
                .monthFormat("MMM") 	  // Month format
                .showDayName(true)	  // Show or Hide dayName text
                .showMonthName(true)	  // Show or Hide month text
                .textColor(Color.LTGRAY, Color.WHITE)    // Text color for none selected Dates, Text color for selected Date.
                .selectedDateBackground(Color.TRANSPARENT)  // Background color of the selected date cell.
                .selectorColor(Color.RED)
                .build();


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,country);

        countryDropDown.setWheelAdapter(new ArrayWheelAdapter(this));
        countryDropDown.setSkin(WheelView.Skin.Holo);

        country.add("한국");
        countryDropDown.setWheelData(country);

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
                cityDropDown.setWheelClickable(true);
                getCityData(position);
            }
        });

        //다음으로 버튼
        selectPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(ChooseCityActivity.this, ChoosePlaceActivity.class);
//                i.putExtra(ConstantIntent.CITYCODE, cityNum);
//                startActivity(i);

                SetPlaceCountDialog setPlaceCountDialog = new SetPlaceCountDialog(ChooseCityActivity.this, cityNum);
                setPlaceCountDialog.show();
                setPlaceCountDialog.setCancelable(true);
            }
        });



       /* weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                Toast.makeText(ChooseCityActivity.this,
                        "You Selected " + dateTime.toString(), Toast.LENGTH_SHORT).show();
            }



        });*/
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Toast.makeText(ChooseCityActivity.this,
                        "You Selected " + date.toString(), Toast.LENGTH_SHORT).show();

                horizontalCalendar.setSelectedDateBackground(Color.GRAY);
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

                cityDropDown.setWheelData(list);
                cityDropDown.deferNotifyDataSetChanged();
                isData = true;
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

    public Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


}
