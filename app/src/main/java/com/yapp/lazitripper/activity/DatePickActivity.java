package com.yapp.lazitripper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.timessquare.CalendarPickerView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PickDate;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;

import java.util.Calendar;
import java.util.Date;

public class DatePickActivity extends AppCompatActivity {

    private int FLAG = 0;
    private PickDate pickDate;
    private Button chooseCompleteBtn;
    private CalendarPickerView calendar;
    private SharedPreferenceStore<PickDate> sharedPreferenceStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_pick);

        chooseCompleteBtn = (Button) findViewById(R.id.chooseDateCompleteBtn);
        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

        sharedPreferenceStore = new SharedPreferenceStore<PickDate>(getApplicationContext(), ConstantStore.STORE);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        pickDate = new PickDate();

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Log.i("ohdoking","click");
                if (FLAG == 0){
                    pickDate.setStartDate(date);
                    FLAG = 1;
                }
                else{
                    pickDate.setFinishDate(date);
                    long diff = Math.abs(pickDate.getStartDate().getTime() - date.getTime());
                    long diffDays = (diff / (24 * 60 * 60 * 1000)) + 1;

                    pickDate.setPeriod(diffDays);
                    Log.i("ohdoking",diffDays+"");
                    FLAG = 0;
                }
            }

            @Override
            public void onDateUnselected(Date date) {
                Log.i("ohdoking","onDateUnselected");
                pickDate = new PickDate();
            }
        });

        chooseCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FLAG == 1){
                    Log.i("ohdoking",FLAG+"");
                    pickDate.setStartDate(new Date());
                    pickDate.setFinishDate(new Date());
                    pickDate.setPeriod(1l);
                }
                sharedPreferenceStore.savePreferences(ConstantStore.DATEKEY, pickDate);

                Intent i = new Intent(DatePickActivity.this, ChooseCityActivity.class);
                startActivity(i);
            }
        });

    }

}
