package com.yapp.lazitripper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yapp.lazitripper.R;

import java.util.ArrayList;
import java.util.List;
//import android.support.v7.widget

public class TempActivity extends AppCompatActivity {

    Button minusBtn,plusBtn;
    TextView numOfDay;

    //SharedPreferences pref;

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

//        pref = getPreferences(MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String json = pref.getString("item","nonono");
//        ChosenPlaceItem item = gson.fromJson(json,ChosenPlaceItem.class);
//
//        TimeLineModel bringChoose = new TimeLineModel(item.getDescription());
//        Toast.makeText(getApplicationContext(),item.getName(),Toast.LENGTH_LONG).show();

        //mTimeLineAdapter.addItem(bringChoose);


        initView();
    }

    public void initView(){
        /*for(int i=0;i<20;i++){
            TimeLineModel model = new TimeLineModel();
            model.setDes("rnadom"+i);
            mdataList.add(model);
        }*/

        mTimeLineAdapter = new TimeLineAdapter(mdataList, TempActivity.this);
        mRecyclerView.setAdapter(mTimeLineAdapter);

        //mTimeLineAdapter.addItem(new TimeLineModel("asdfasf"));


    }
/*
    private void addItem(String des){
        TimeLineModel model = new TimeLineModel();
        model.setDes(des);
        mdataList.add(model);

    }*/

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
/*
    public void onDesClicked(View v){
        Intent intent = new Intent(getApplicationContext(),test.class);
        TimeLineModel temp = mTimeLineAdapter.getItemViewType();
        intent.putExtra("test",);
        startActivity(intent);

    }*/




}
