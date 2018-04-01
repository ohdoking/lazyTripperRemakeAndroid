package com.yapp.lazitripper.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.store.ConstantStore;
import com.yapp.lazitripper.store.SharedPreferenceStore;
import com.yapp.lazitripper.views.adapters.DayItemAdapter;
import com.yapp.lazitripper.views.adapters.RecyclerItemClickListener;
import com.yapp.lazitripper.views.bases.BaseAppCompatActivity;

import me.gujun.android.taggroup.TagGroup;

public class MyTravelActivity extends BaseAppCompatActivity {

    private RecyclerView recyclerTravel;
    private AllTravelInfo travelList;

    public static final String DELIVER_ITEM = "allTravelInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel);
        setHeader();

        travelList = (AllTravelInfo) getIntent().getSerializableExtra(DELIVER_ITEM);
        recyclerTravel = (RecyclerView) findViewById(R.id.recycler_travel_list);
        recyclerTravel.addOnItemTouchListener(new RecyclerItemClickListener(MyTravelActivity.this, recyclerTravel, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), TravelDayActivity.class);
                intent.putExtra(TravelDayActivity.DELIVER_ITEM, travelList.getAllTraveInfo().get(position));
                startActivity(intent);
            }
        }));

        recentTravelListSetting();
        setHeaderContent();
        setTagList();

    }

    private void setTagList(){
        SharedPreferenceStore<String[]> sharedPreferenceStore = new SharedPreferenceStore<String[]>(getApplicationContext(), ConstantStore.STORE);
        String[] tagList = sharedPreferenceStore.getPreferences(ConstantStore.TAGS, String[].class);

        //태그 그룹에 # 추가
        int index = 0;
        if (tagList.length != 0)
            for (String tag : tagList) {
                tagList[index] = "#" + tag;
                index++;
            }
        else {
            tagList = new String[1];
            tagList[0] = "#태그를 선택해주세요";
        }
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(tagList);
    }

    private void setHeaderContent() {
        TextView textDayDigit = (TextView) findViewById(R.id.text_header_day_digit);
        TextView textDayList = (TextView) findViewById(R.id.text_header_day_list);

        String dateDigit = "DAY ";
        String dateList = "";

        for (int i = 0; i < travelList.getTotalDay(); i++) {
            if (i == 0) {
                dateDigit += (i + 1);
                dateList += travelList.getAllTraveInfo().get(i).getCityName();
            } else if (i == travelList.getAllTraveInfo().size() - 1) {
                dateDigit += "/" + (i + 1);
                dateList += "/" + travelList.getAllTraveInfo().get(i).getCityName();
            }
        }

        textDayDigit.setText(dateDigit);
        textDayList.setText(dateList);

    }

    private void recentTravelListSetting() {

        DayItemAdapter adapter = new DayItemAdapter(getApplicationContext(), travelList.getAllTraveInfo(), DayItemAdapter.SIMPLE);
        recyclerTravel.setAdapter(adapter);
        recyclerTravel.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
