package com.yapp.lazitripper.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp.lazitripper.R;

/**
 * Created by clock on 2017-02-26.
 */
public class PlaceInfoItem extends LinearLayout {

    TextView title, location, tel;
    ImageView thumbnail;
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
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        title = (TextView) findViewById(R.id.place_title);
        location = (TextView) findViewById(R.id.place_location);
        tel = (TextView) findViewById(R.id.place_tel);

    }

    public void setImage(String url){
        Glide.with(getContext()).load(url).into(this.thumbnail);
    }
    public void setTitle(String name){
        this.title.setText(name);
    }

    public void setLocatioin(String lat){
        this.location.setText(lat);
    }

    public void setel(String lng){
        this.tel.setText(lng);
    }

}
