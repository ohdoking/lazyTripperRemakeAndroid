package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

//여행 시작 -> 날짜 선택
public class DatePickActivity extends BaseAppCompatActivity {

    private EditText datePick;
    private InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);
        setHeader();

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
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(datePick.getWindowToken(), 0);
        //다음단계 버튼 -> 도시선택 액티비티
        Button btnMakeRoot = (Button) findViewById(R.id.next);
        btnMakeRoot.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        if(TextUtils.isEmpty(datePick.getText().toString())){
                            Toast.makeText(DatePickActivity.this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(Integer.parseInt(datePick.getText().toString()) == 0) {
                            Animation shake = AnimationUtils.loadAnimation(DatePickActivity.this, R.anim.shake);
                            datePick.startAnimation(shake);
                        }
                       else if(Integer.parseInt(datePick.getText().toString()) > 30) {
                            Animation shake = AnimationUtils.loadAnimation(DatePickActivity.this, R.anim.shake);
                            datePick.startAnimation(shake);
                            datePick.setText("30");
                        }
                        else if(Integer.parseInt(datePick.getText().toString()) <= 30 && Integer.parseInt(datePick.getText().toString()) > 0) {
                            Intent i = new Intent(DatePickActivity.this, CityPickActivity.class);
                            i.putExtra("date", datePick.getText().toString());
                            startActivity(i);
                            finish();
                            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.fade_in);
                        }
                    }
                }
        );



    }
}
