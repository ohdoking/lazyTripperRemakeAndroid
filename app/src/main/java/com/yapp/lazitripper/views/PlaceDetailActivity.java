package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.CommonInfoDto;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.Travel;
import com.yapp.lazitripper.network.LaziTripperKoreanTourClient;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//상세보기 페이지
public class PlaceDetailActivity extends BaseAppCompatActivity {
    private String TAG = "PlaceDetailActivity";
    private Travel landMark;
    private CommonInfoDto commonInfoDto;
    public LaziTripperKoreanTourClient laziTripperKoreanTourClient;
    public LaziTripperKoreanTourService laziTripperKoreanTourService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        landMark = new Travel();
        //NXE 예방

        Intent intent = getIntent();
        PlaceInfoDto handledata = (PlaceInfoDto) intent.getSerializableExtra("placeInfo");
        getPlaceData(handledata);

        landMark.setTel(handledata.getTel());
        landMark.setAddress(handledata.getAddr1());
        landMark.setTitle(handledata.getTitle());

        ImageView background = (ImageView)findViewById(R.id.backgroundImage);
        ImageView close = (ImageView)findViewById(R.id.iv_place_detail_close);
        TextView title = (TextView)findViewById(R.id.name);
        TextView tvPlaceDetailDays = (TextView)findViewById(R.id.tv_place_detail_day);
        TextView tvPlaceDetailCategory = (TextView)findViewById(R.id.tv_place_detail_category);
        TextView tvPlaceDetaillocation = (TextView)findViewById(R.id.tv_place_detail_location);
        TextView tvPlaceDetailContents = (TextView)findViewById(R.id.tv_place_detail_contents);
        TextView addr = (TextView) findViewById(R.id.addr);
        TextView tel = (TextView) findViewById(R.id.tel);
        Log.e(TAG,handledata.getFirstimage()+"");
        Glide.with(this).load(handledata.getFirstimage()).centerCrop().into(background);
        title.setText(handledata.getTitle());
        addr.setText(handledata.getAddr1());
        tel.setText(handledata.getTel());
        tvPlaceDetailDays.setText("DAY 1");
        tvPlaceDetaillocation.setText((new ChoosePlaceActivity().makeTitleName(3,handledata.getAreacode())).split(" ")[1]);
        tvPlaceDetailCategory.setText(handledata.getCat3());
//        tvPlaceDetailContents.setText(commonInfoDto.getOverview());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    void getPlaceData(PlaceInfoDto mPlaceInfo){

        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();

        Call<CommonInfoDto> callCommonnInfo;
        callCommonnInfo = laziTripperKoreanTourService.getPlaceInfoByDetailCommon("AND","LaziTripper",mPlaceInfo.getContentid(),mPlaceInfo.getContenttypeid(),"Y","Y","Y","Y","Y","Y","Y");

        callCommonnInfo.enqueue(new Callback<CommonInfoDto>() {
            @Override
            public void onResponse(Call<CommonInfoDto> call, Response<CommonInfoDto> response) {
                //TODO 이거 해결해야해..
                
            }

            @Override
            public void onFailure(Call<CommonInfoDto> call, Throwable t) {

            }
        });
    }
}
