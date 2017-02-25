package com.yapp.lazitripper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.dto.RegionCode;
import com.yapp.lazitripper.dto.RegionResultDto;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCityActivity extends AppCompatActivity {

    private SharedPreferenceStore<PickDate> sharedPreferenceStore;

    Spinner countryDropDown;
    Spinner cityDropDown;
    String[] country = {
            "korea",
            "armerica",
    };

    ArrayList<String> cities = new ArrayList<String>();

    public RegionCode regionCodeDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;

    List<RegionCode> regionCodeList;

    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        sharedPreferenceStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);

        PickDate pickDate = sharedPreferenceStore.getPreferences(ConstantStore.DATEKEY, PickDate.class);
        Log.i("ohdoking",pickDate.getStartDate() + " / " + pickDate.getPeriod());

        // Get reference of SpinnerView from layout/main_activity.xml
        countryDropDown =(Spinner)findViewById(R.id.country_spinner);
        cityDropDown =(Spinner)findViewById(R.id.city_spinner);
        cityDropDown.setEnabled(false);
        cityDropDown.setClickable(false);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,country);


        countryDropDown.setAdapter(adapter);

        countryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                int sid=countryDropDown.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You have selected City : " + country[sid],
                        Toast.LENGTH_SHORT).show();

                cityDropDown.setEnabled(true);
                cityDropDown.setClickable(true);
                getCityData(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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
        Call<CommonResponse<RegionResultDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper");

        callRelionInfo.enqueue(new Callback<CommonResponse<RegionResultDto>>() {
            @Override
            public void onResponse(Call<CommonResponse<RegionResultDto>> call, Response<CommonResponse<RegionResultDto>> response) {
                regionCodeList = response.body().getResponse().getBody().getItems().getItems();
                int index = regionCodeList.size();
                ArrayList<String> list = new ArrayList<String>();
                for ( int i = 0 ; i < index ; i++){
                    list.add(regionCodeList.get(i).getName());
                }
                adapter2.addAll(list);
                adapter2.notifyDataSetChanged();

//                renderSecondSpinner(cities);
            }

            @Override
            public void onFailure(Call<CommonResponse<RegionResultDto>> call, Throwable t) {
                Log.i("ohdoking",t.getMessage());
            }
        });
    }

    void renderSecondSpinner(){
        adapter2= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,cities);

        cityDropDown.setAdapter(adapter2);

        cityDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                int sid=cityDropDown.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You have selected City : " + cities.get(sid),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
