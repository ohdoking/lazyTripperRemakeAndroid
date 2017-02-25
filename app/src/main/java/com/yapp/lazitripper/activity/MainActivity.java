package com.yapp.lazitripper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.common.CommonHeader;
import com.yapp.lazitripper.dto.common.CommonResponse;
import com.yapp.lazitripper.dto.RegionCode;
import com.yapp.lazitripper.dto.RegionResultDto;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yapp.lazitripper.R.id.mypageBtn;

public class MainActivity extends BaseAppCompatActivity {

    public RegionCode regionCodeDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
//        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();
//
//        Call<CommonResponse<RegionResultDto>> callRelionInfo = laziTripperKoreanTourService.getRelionInfo(100,1,"AND","LaziTripper",1);
//
//        callRelionInfo.enqueue(new Callback<CommonResponse<RegionResultDto>>() {
//            @Override
//            public void onResponse(Call<CommonResponse<RegionResultDto>> call, Response<CommonResponse<RegionResultDto>> response) {
//                RegionResultDto response2 = response.body().getResponse().getBody();
//                Log.i("ohdoking",response2.getItems().getItems().get(0).getName());
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse<RegionResultDto>> call, Throwable t) {
//                Log.i("ohdoking",t.getMessage());
//            }
//        });

        test();
    }

    public void test(){
        findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DatePickActivity.class);
                startActivity(i);
            }
        });

        findViewById(mypageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(i);
            }
        });
    }
}
