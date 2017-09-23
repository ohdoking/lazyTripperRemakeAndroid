package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.TravelRouteDto;


public class DayItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textDay;
    private ImageView imgBack;
    private Context context;

    protected DayItemViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        textDay = (TextView)view.findViewById(R.id.text_item_route);
        imgBack = (ImageView)view.findViewById(R.id.img_item_route);
    }


    protected void bind(final TravelRouteDto dayItem) {

        textDay.setText(dayItem.getTitle());
        Glide.with(context).load(dayItem.getImageURL())
                .into(imgBack);

    }
}
