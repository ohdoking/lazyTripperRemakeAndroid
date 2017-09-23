package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.Calendar;
import java.util.Date;

//여행 시작 -> 날짜 선택
public class DatePickActivity extends BaseAppCompatActivity {

    private EditText datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
        setHeader();

        //뒤로가기, 액티비티 종료
        ImageView leftImage = getLeftImageView();
        leftImage.setImageResource(R.drawable.arrow);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //다음단계 버튼 -> 도시선택 액티비티
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.next_icon);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datePick != null){

                    Intent i = new Intent(DatePickActivity.this, ChooseCityActivity.class);
                    i.putExtra("name",datePick.getText().toString());
                    startActivity(i);
                    finish();
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);
                }
                else{
                    Toast.makeText(DatePickActivity.this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        datePick = (EditText) findViewById(R.id.datePick);
        datePick.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을때 처리
                    return true;
                }
                return false;
            }
        });

    }
}
