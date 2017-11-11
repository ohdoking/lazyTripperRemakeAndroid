package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.TravelInfo;

import org.w3c.dom.Text;


public class PlaceInfoDayViewHolder extends RecyclerView.ViewHolder{

    private TextView textIndex,textTitle;

    public PlaceInfoDayViewHolder(Context context, View view) {
        super(view);
        textIndex = (TextView)view.findViewById(R.id.text_index_item);
        textTitle = (TextView)view.findViewById(R.id.text_title_item);
    }

    protected void bind(final TravelInfo item){

    }
}
