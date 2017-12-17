package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.dto.TravelRouteDto;
import com.yapp.lazitripper.util.RoundedCornersTransformation;

class DayItemViewHolder extends RecyclerView.ViewHolder {

    private TextView textDay, textName;
    private ImageView ivProfileItemBg;
    private Context context;

    DayItemViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        textDay = (TextView) view.findViewById(R.id.travel_day);
        textName = (TextView) view.findViewById(R.id.travel_name);
        ivProfileItemBg = (ImageView) view.findViewById(R.id.iv_item_route_background);
    }


    void bind(final TravelInfo item) {

        textDay.setText(item.getDay() + "");
        textName.setText(item.getCityName());
        Glide.with(context)
                .load(item.getPlaceInfoDtoList().get(0).getFirstimage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 60, 2))
                .into(ivProfileItemBg);
    }
}
