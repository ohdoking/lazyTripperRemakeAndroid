package com.yapp.lazitripper.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PlaceCount;
import com.yapp.lazitripper.views.ChoosePlaceActivity;

import java.util.ArrayList;

/**
 * Created by ohdoking on 2017. 3. 19..
 *
 * 장소를 몇군대갈지 선택하는 다이얼로그이다
 */

public class SetPlaceCountDialog extends Dialog {

    PlaceCount placeCount;

    WheelView landmarkCountWheelView;
    WheelView restaurantCountWheelView;
    WheelView accommodationCountWheelView;

    Button chooseBtn;

    ArrayList<String> landmarkList = new ArrayList<String>();
    ArrayList<String> restarantList = new ArrayList<String>();
    ArrayList<String> accommodationList = new ArrayList<String>();

    Context context;

    Integer cityNum;

    public SetPlaceCountDialog(@NonNull Context context, Integer cityNum) {
        super(context);
        this.context = context;
        this.cityNum = cityNum;
        setContentView(R.layout.set_place_count_dialog);
        setTitle("선택해주세요 ..?");
        //default 랜드마크 4, 음식점 3, 숙소 1
        placeCount = new PlaceCount(4,3,1);

        init();
    }

    /*
    * 초기화를 한다
    * */
    void init(){
        for(int i = 1 ; i < 30 ; i++){
            landmarkList.add(i+"");
            restarantList.add(i+"");
            accommodationList.add(i+"");
        }

        landmarkCountWheelView = (WheelView) findViewById(R.id.landmark_count_wheel_view);
        landmarkCountWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
        landmarkCountWheelView.setSkin(WheelView.Skin.Holo);
        landmarkCountWheelView.setWheelData(landmarkList);
        landmarkCountWheelView.setSelection(placeCount.getLandMark());
        landmarkCountWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                placeCount.setLandMark(position);
            }
        });

        restaurantCountWheelView = (WheelView) findViewById(R.id.restaruant_count_wheel_view);
        restaurantCountWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
        restaurantCountWheelView.setSkin(WheelView.Skin.Holo);
        restaurantCountWheelView.setWheelData(restarantList);
        restaurantCountWheelView.setSelection(placeCount.getRestaurant());
        restaurantCountWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                placeCount.setRestaurant(position);
            }
        });

        accommodationCountWheelView = (WheelView) findViewById(R.id.accommodation_count_wheel_view);
        accommodationCountWheelView.setWheelAdapter(new ArrayWheelAdapter(context));
        accommodationCountWheelView.setSkin(WheelView.Skin.Holo);
        accommodationCountWheelView.setWheelData(accommodationList);
        accommodationCountWheelView.setSelection(placeCount.getAccommodation());
        accommodationCountWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                placeCount.setAccommodation(position);
            }
        });

        chooseBtn = (Button) findViewById(R.id.choose_btn);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChoosePlaceActivity.class);
                i.putExtra(ConstantIntent.CITYCODE, cityNum);
                i.putExtra(ConstantIntent.PLACECOUNT, placeCount);
                context.startActivity(i);
            }
        });
    }
}
