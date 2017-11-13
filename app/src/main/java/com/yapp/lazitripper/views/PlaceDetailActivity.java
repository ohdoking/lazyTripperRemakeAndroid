package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.CommonInfoDto;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.Travel;
import com.yapp.lazitripper.dto.common.CommonSingleResponse;
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
    private ImageView background;
    private ImageView close;
    private TextView title;
    private TextView tvPlaceDetailDays;
    private TextView tvPlaceDetailCategory;
    private TextView tvPlaceDetaillocation;
    private TextView tvPlaceDetailContents;
    private TextView addr;
    private TextView tel;
    private LinearLayout llPlaceDetail;
    private DatabaseReference myRef;
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
        llPlaceDetail = (LinearLayout) findViewById(R.id.layout_tel);
        background = (ImageView) findViewById(R.id.backgroundImage);

        close = (ImageView) findViewById(R.id.iv_place_detail_close);

        title = (TextView) findViewById(R.id.name);

        tvPlaceDetailDays = (TextView) findViewById(R.id.tv_place_detail_day);

        tvPlaceDetailCategory = (TextView) findViewById(R.id.tv_place_detail_category);

        tvPlaceDetaillocation = (TextView) findViewById(R.id.tv_place_detail_location);

        tvPlaceDetailContents = (TextView) findViewById(R.id.tv_place_detail_contents);

        addr = (TextView) findViewById(R.id.addr);

        tel = (TextView) findViewById(R.id.tel);
        myRef = FirebaseDatabase.getInstance().getReference("lazitripper");
        myRef.child("info").child("serviceCode").child(handledata.getCat3()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data =dataSnapshot.getValue(String.class);
                tvPlaceDetailCategory.setText(data);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Glide.with(this).load(handledata.getFirstimage()).centerCrop().into(background);
        title.setText(handledata.getTitle());
        addr.setText(handledata.getAddr1());
        landMark.setTel(handledata.getTel());
        landMark.setAddress(handledata.getAddr1());
        landMark.setTitle(handledata.getTitle());
        if(handledata.getTel() == null)
            llPlaceDetail.setVisibility(View.VISIBLE);
        tel.setText(handledata.getTel());
        tvPlaceDetailDays.setText("DAY "+intent.getIntExtra("day",0));
        tvPlaceDetaillocation.setText((new ChoosePlaceActivity().makeTitleName(3, handledata.getAreacode())).split(" ")[1]);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    void getPlaceData(PlaceInfoDto mPlaceInfo) {
        laziTripperKoreanTourClient = new LaziTripperKoreanTourClient(getApplicationContext());
        laziTripperKoreanTourService = laziTripperKoreanTourClient.getLiziTripperService();

        Call<CommonSingleResponse<CommonInfoDto>> callCommonnInfo;
        callCommonnInfo = laziTripperKoreanTourService.getPlaceInfoByDetailCommon("AND", "LaziTripper", mPlaceInfo.getContentid(), mPlaceInfo.getContenttypeid(), "Y", "Y", "Y", "Y", "Y", "Y", "Y");

        callCommonnInfo.enqueue(new Callback<CommonSingleResponse<CommonInfoDto>>() {
            @Override
            public void onResponse(Call<CommonSingleResponse<CommonInfoDto>> call, Response<CommonSingleResponse<CommonInfoDto>> response) {
                commonInfoDto = response.body().getResponse().getBody().getItems().getItem();
                tvPlaceDetailContents.setText(commonInfoDto.getOverview().replaceAll("&nbsp;", "").replaceAll("<br>", "").replaceAll("<br/>", ""));
            }

            @Override
            public void onFailure(Call<CommonSingleResponse<CommonInfoDto>> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }
}
