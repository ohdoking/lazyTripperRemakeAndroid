package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.util.ArrayList;

public class KeywordSelectActivity extends BaseAppCompatActivity implements View.OnClickListener {

    ImageButton[] text = new ImageButton[15];
    boolean[] flag = new boolean[15];
    ArrayList<String> stringArrayList;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader();
        setContentView(R.layout.activity_keyword_select);

        email = getIntent().getStringExtra(ConstantIntent.EMAIL);
        final SharedPreferenceStore sharedPreferenceStore = new SharedPreferenceStore(getApplicationContext(), ConstantStore.STORE);

        ImageView leftImage = getLeftImageView();
        leftImage.setVisibility(View.INVISIBLE);
        ImageView rightImage = getRightImageView();
        rightImage.setImageResource(R.drawable.next_icon);
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferenceStore.savePreferences(ConstantStore.TAGS, stringArrayList);
                Intent i = new Intent(KeywordSelectActivity.this, HomeActivity.class);
                i.putExtra(ConstantIntent.EMAIL,email);
                startActivity(i);
            }
        });


        stringArrayList = new ArrayList<>();

        text[0] = (ImageButton) findViewById(R.id.btn1);
        text[1] = (ImageButton) findViewById(R.id.btn2);
        text[2] = (ImageButton) findViewById(R.id.btn3);
        text[3] = (ImageButton) findViewById(R.id.btn4);
        text[4] = (ImageButton) findViewById(R.id.btn5);
        text[5] = (ImageButton) findViewById(R.id.btn6);
        text[6] = (ImageButton) findViewById(R.id.btn7);
        text[7] = (ImageButton) findViewById(R.id.btn8);
        text[8] = (ImageButton) findViewById(R.id.btn9);
        text[9] = (ImageButton) findViewById(R.id.btn10);
        text[10] = (ImageButton) findViewById(R.id.btn11);
        text[11] = (ImageButton) findViewById(R.id.btn12);
        text[12] = (ImageButton) findViewById(R.id.btn13);
        text[13] = (ImageButton) findViewById(R.id.btn14);
        text[14] = (ImageButton) findViewById(R.id.btn15);



        for(int i=0;i<text.length;i++) {
            flag[i] = true;
            text[i].setOnClickListener(this);
        }




    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn1:
                if(flag[0]) {
                    text[0].setImageDrawable(getResources().getDrawable(R.drawable.category01_c));
                    stringArrayList.add("뮤지컬");
                    flag[0] = !flag[0];
                } else {
                    text[0].setImageDrawable(getResources().getDrawable(R.drawable.category01));
                    stringArrayList.remove("뮤지컬");
                    flag[0] = !flag[0];
                }
                break;
            case R.id.btn2:
                if(flag[1]) {
                    text[1].setImageDrawable(getResources().getDrawable(R.drawable.category02_c));
                    stringArrayList.add("스포츠경기");
                    flag[1] = !flag[1];
                } else {
                    text[1].setImageDrawable(getResources().getDrawable(R.drawable.category02));
                    stringArrayList.remove("스포츠경기");
                    flag[1] = !flag[1];
                }
                break;
            case R.id.btn3:
                if(flag[2]) {
                    text[2].setImageDrawable(getResources().getDrawable(R.drawable.category03_c));
                    stringArrayList.add("한식");
                    flag[2] = !flag[2];
                } else {
                    text[2].setImageDrawable(getResources().getDrawable(R.drawable.category03));
                    stringArrayList.remove("한식");
                    flag[2] = !flag[2];
                }
                break;
            case R.id.btn4:
                if(flag[3]) {
                    text[3].setImageDrawable(getResources().getDrawable(R.drawable.category04_c));
                    stringArrayList.add("중식");
                    flag[3] = !flag[3];
                } else {
                    text[3].setImageDrawable(getResources().getDrawable(R.drawable.category04));
                    stringArrayList.remove("중식");
                    flag[3] = !flag[3];
                }
                break;
            case R.id.btn5:
                if(flag[4]) {
                    text[4].setImageDrawable(getResources().getDrawable(R.drawable.category05_c));
                    stringArrayList.add("자연생태관광지");
                    flag[4] = !flag[4];
                } else {
                    text[4].setImageDrawable(getResources().getDrawable(R.drawable.category05));
                    stringArrayList.remove("자연생태관광지");
                    flag[4] = !flag[4];
                }
                break;
            case R.id.btn6:
                if(flag[5]) {
                    text[5].setImageDrawable(getResources().getDrawable(R.drawable.category06_c));
                    stringArrayList.add("공원");
                    flag[5] = !flag[5];
                } else {
                    text[5].setImageDrawable(getResources().getDrawable(R.drawable.category06));
                    stringArrayList.remove("공원");
                    flag[5] = !flag[5];
                }
                break;
            case R.id.btn7:
                if(flag[6]) {
                    text[6].setImageDrawable(getResources().getDrawable(R.drawable.category07_c));
                    stringArrayList.add("연극");
                    flag[6] = !flag[6];
                } else {
                    text[6].setImageDrawable(getResources().getDrawable(R.drawable.category07));
                    stringArrayList.remove("연극");
                    flag[6] = !flag[6];
                }
                break;
            case R.id.btn8:
                if(flag[7]) {
                    text[7].setImageDrawable(getResources().getDrawable(R.drawable.category08_c));
                    stringArrayList.add("서양식");
                    flag[7] = !flag[7];
                } else {
                    text[7].setImageDrawable(getResources().getDrawable(R.drawable.category08));
                    stringArrayList.remove("서양식");
                    flag[7] = !flag[7];
                }
                break;
            case R.id.btn9:
                if(flag[8]) {
                    text[8].setImageDrawable(getResources().getDrawable(R.drawable.category09_c));
                    stringArrayList.add("이색체험");
                    flag[8] = !flag[8];
                } else {
                    text[8].setImageDrawable(getResources().getDrawable(R.drawable.category09));
                    stringArrayList.remove("이색체험");
                    flag[8] = !flag[8];
                }
                break;
            case R.id.btn10:
                if(flag[9]) {
                    text[9].setImageDrawable(getResources().getDrawable(R.drawable.category10_c));
                    stringArrayList.add("섬");
                    flag[9] = !flag[9];
                } else {
                    text[9].setImageDrawable(getResources().getDrawable(R.drawable.category10));
                    stringArrayList.remove("섬");
                    flag[9] = !flag[9];
                }
                break;
            case R.id.btn11:
                if(flag[10]) {
                    text[10].setImageDrawable(getResources().getDrawable(R.drawable.category11_c));
                    stringArrayList.add("박물관");
                    flag[10] = !flag[10];
                } else {
                    text[10].setImageDrawable(getResources().getDrawable(R.drawable.category11));
                    stringArrayList.remove("박물관");
                    flag[10] = !flag[10];
                }
                break;
            case R.id.btn12:
                if(flag[11]) {
                    text[11].setImageDrawable(getResources().getDrawable(R.drawable.category12_c));
                    stringArrayList.add("영화관");
                    flag[11] = !flag[11];
                } else {
                    text[11].setImageDrawable(getResources().getDrawable(R.drawable.category12));
                    stringArrayList.remove("영화관");
                    flag[11] = !flag[11];
                }
                break;
            case R.id.btn13:
                if(flag[12]) {
                    text[12].setImageDrawable(getResources().getDrawable(R.drawable.category13_c));
                    stringArrayList.add("일식");
                    flag[12] = !flag[12];
                } else {
                    text[12].setImageDrawable(getResources().getDrawable(R.drawable.category13));
                    stringArrayList.remove("일식");
                    flag[12] = !flag[12];
                }
                break;
            case R.id.btn14:
                if(flag[13]) {
                    text[13].setImageDrawable(getResources().getDrawable(R.drawable.category14_c));
                    stringArrayList.add("유적지,사적지");
                    flag[13] = !flag[13];
                } else {
                    text[13].setImageDrawable(getResources().getDrawable(R.drawable.category14));
                    stringArrayList.remove("유적지,사적지");
                    flag[13] = !flag[13];
                }
                break;
            case R.id.btn15:
                if(flag[14]) {
                    text[14].setImageDrawable(getResources().getDrawable(R.drawable.category15_c));
                    stringArrayList.add("산");
                    flag[14] = !flag[14];
                } else {
                    text[14].setImageDrawable(getResources().getDrawable(R.drawable.category15));
                    stringArrayList.remove("산");
                    flag[14] = !flag[14];
                }
            default:

        }

    }
}
