package com.yapp.lazitripper.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapp.lazitripper.R;

/**
 * Created by clock on 2017-02-26.
 */
public class PlaceInfoItem extends LinearLayout {

    TextView name,lat,lng;

    public PlaceInfoItem(Context context) {
        super(context);

        init(context);
    }

    public PlaceInfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.place_info_item,this,true);

        name = (TextView) findViewById(R.id.text1);
        lat = (TextView) findViewById(R.id.text2);
        lng = (TextView) findViewById(R.id.text3);

    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setLat(String lat){
        this.lat.setText(lat);
    }

    public void setLng(String lng){
        this.lng.setText(lng);
    }

}
