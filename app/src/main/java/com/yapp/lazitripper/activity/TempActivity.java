package com.yapp.lazitripper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.util.TimeLineAdapter;
import com.yapp.lazitripper.dto.TimeLineModel;

import java.util.ArrayList;
import java.util.List;
//import android.support.v7.widget

public class TempActivity extends BaseAppCompatActivity {

    Button minusBtn,plusBtn;
    TextView numOfDay;

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mdataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        minusBtn = (Button)findViewById(R.id.minusBtn);
        plusBtn = (Button)findViewById(R.id.plusBtn);
        numOfDay = (TextView)findViewById(R.id.numofday);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        initView();
    }

    public void initView(){

        mTimeLineAdapter = new TimeLineAdapter(mdataList, TempActivity.this);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    public void onMinusClicked(View v){
        String n = numOfDay.getText().toString();
        int d = Integer.parseInt(n);
        if(d > 0){
            numOfDay.setText(String.valueOf(d-1));
            mTimeLineAdapter.removeItem();
        }
    }

    public void onPlusClicked(View v){
        String n = numOfDay.getText().toString();
        int d = Integer.parseInt(n);
        if(d < 30){
            numOfDay.setText(String.valueOf(d+1));
            mTimeLineAdapter.addItem(new TimeLineModel(""));
        }
    }



}
