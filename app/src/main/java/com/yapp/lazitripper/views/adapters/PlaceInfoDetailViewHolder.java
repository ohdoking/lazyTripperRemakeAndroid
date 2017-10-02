package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.TravelInfo;

public class PlaceInfoDetailViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    private ImageView imgPlace;
    private TextView textIndex, textTitle, textAddress;

    public PlaceInfoDetailViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        imgPlace = (ImageView) view.findViewById(R.id.img_place_item);
        textIndex = (TextView)view.findViewById(R.id.text_index_item);
        textTitle = (TextView)view.findViewById(R.id.text_title_item);
        textAddress = (TextView)view.findViewById(R.id.text_address_item);
    }

    protected void bind(final TravelInfo item){

    }
}
