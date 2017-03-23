package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

//상세보기 페이지
public class MyProfileActivity extends BaseAppCompatActivity {
    private String TAG = "MyProfileActivity";
    //여행지 선택 화면과 동일하게 지명, 주소, 전화번호, 평균별점, 국기
    //해당 장소가 가지고 있는 키워드(관광 정보 서비스 분류 코드) 데이터 뿌려줌
    // 사용자가 남긴 모든 별점 및 리뷰를 뿌려줌(리뷰 남긴 순)
    // 마이페이지의 완성된 루트를 통해 상세보기에 들어왔을 때 별점 밀 리뷰 작성 기능
    // 본인이 작성한 별점 및 리뷰 수정 삭제.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Log.e(TAG,"onCreate");
        setHeader();
        Intent intent = getIntent();
        PlaceInfoDto placeinfo = (PlaceInfoDto) intent.getSerializableExtra("placeInfo");

        //Log.e(TAG,"name = " + placeinfo.getTitle());
    }
}
