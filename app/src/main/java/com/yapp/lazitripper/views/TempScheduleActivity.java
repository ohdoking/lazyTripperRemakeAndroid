package com.yapp.lazitripper.views;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.common.ConstantIntent;
import com.yapp.lazitripper.dto.PlaceCount;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.TimelineRow;
import com.yapp.lazitripper.views.adapters.TimelineViewAdapter;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TempScheduleActivity extends BaseAppCompatActivity {



    ImageView closeBtn;
    private List<PlaceInfoDto> placeInfoDtoList;

    //Create Timeline Rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;

    int imageSize = 130;
    int backgroundSize = 150;
    String point = " · ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_schedule);

        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        placeInfoDtoList = (List<PlaceInfoDto>) getIntent().getSerializableExtra(ConstantIntent.TEMPSCHEDULELIST);


        // Add Random Rows to the List
        for (int i = 0; i < 15; i++) {
            //add the new row to the list
            if(i == 14){
                timelineRowsList.add(createTimeLastLineRow(i,new PlaceInfoDto()));
            }
            else{
                timelineRowsList.add(createTimeLineRow(i,new PlaceInfoDto()));
            }
        }
        timelineRowsList.add(createEmptyTimelineRow(16));

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                true);


        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) findViewById(R.id.timeline_listView);
        myListView.setAdapter(myAdapter);


        //if you wish to handle list scrolling
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    //TODO 데이터가 더있다면
//                    for (int i = 0; i < 15; i++) {
//                        myAdapter.add(createRandomTimelineRow(i));
//                    }

                }
            }
        });

        closeBtn = (ImageView) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ohdoking-test","test");
                finish();
            }
        });

        myListView.setSelection(myListView.getAdapter().getCount()-1);

    }

    private TimelineRow createTimeLineRow(int index, PlaceInfoDto placeInfoDto){

        TimelineRow myRow = new TimelineRow(index);
        if(index%2==0){
            myRow.setTitle(point + "Paris");
        }
        else{
            myRow.setDescription("Paris" + point);
        }
        myRow.setImage("http://holotrip.co.kr/wp-content/uploads/2017/05/%EC%83%B9%EB%93%9C%EB%A7%88%EB%A5%B4%EC%8A%A4-%EA%B3%B5%EC%9B%90.jpg");
        myRow.setBellowLineColor(Color.GRAY);
        myRow.setBellowLineSize(4);
        myRow.setImageSize(imageSize);
        myRow.setTitleColor(Color.WHITE);

        return myRow;
    }

    private TimelineRow createTimeLastLineRow(int index, PlaceInfoDto placeInfoDto){

        TimelineRow myRow = new TimelineRow(index);
        if(index%2==0){
            myRow.setTitle(point + "Paris");
        }
        else{
            myRow.setDescription("Paris" + point);
        }
        myRow.setImage("http://holotrip.co.kr/wp-content/uploads/2017/05/%EC%83%B9%EB%93%9C%EB%A7%88%EB%A5%B4%EC%8A%A4-%EA%B3%B5%EC%9B%90.jpg");
        myRow.setBellowLineColor(Color.GRAY);
        myRow.setBellowLineSize(4);
        myRow.setImageSize(imageSize);
        myRow.setBackgroundColor(Color.YELLOW);
        myRow.setBackgroundSize(backgroundSize);
        myRow.setTitleColor(Color.YELLOW);
        myRow.setDescriptionColor(Color.YELLOW);

        return myRow;
    }

    private TimelineRow createEmptyTimelineRow(int index){
        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(index);
        myRow.setBackgroundColor(Color.CYAN);
        myRow.setBackgroundSize(20);
        return myRow;
    }

    //Method to create new Timeline Row
    private TimelineRow createRandomTimelineRow(int id) {

        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(id);

        //to set the row Date (optional)
        myRow.setDate(getRandomDate());
        //to set the row Title (optional)
        myRow.setTitle("Title " + id);
        //to set the row Description (optional)
        myRow.setDescription("Description " + id);
        //to set the row bitmap image (optional)
        //myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher + getRandomNumber(0, 10)));
        //to set row Below Line Color (optional)
        myRow.setBellowLineColor(getRandomColor());
        //to set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(getRandomNumber(2, 25));
        //to set row Image Size in dp (optional)
        myRow.setImageSize(getRandomNumber(25, 40));
        //to set background color of the row image (optional)
        myRow.setBackgroundColor(getRandomColor());
        //to set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(getRandomNumber(25, 40));
        //to set row Date text color (optional)
        myRow.setDateColor(getRandomColor());
        //to set row Title text color (optional)
        myRow.setTitleColor(getRandomColor());
        //to set row Description text color (optional)
        myRow.setDescriptionColor(getRandomColor());

        return myRow;
    }


    //Random Methods
    public int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        ;
        return color;
    }

    public int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * max);
    }


    public Date getRandomDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = new Date();
        try {
            startDate = sdf.parse("02/09/2015");
            long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            endDate = new Date(random);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }
}
