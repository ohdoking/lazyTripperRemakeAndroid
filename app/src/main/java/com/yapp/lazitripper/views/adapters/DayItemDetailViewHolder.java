package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.TravelInfo;

public class DayItemDetailViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView imgPlace;
    private TextView textIndex, textTitle, textAddress;

    public DayItemDetailViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.imgPlace = (ImageView) itemView.findViewById(R.id.img_place_item);
        this.textTitle = (TextView) itemView.findViewById(R.id.text_title_item);
        this.textIndex = (TextView) itemView.findViewById(R.id.text_index_item);
        this.textAddress = (TextView) itemView.findViewById(R.id.text_address_item);
    }

    protected void bind(final PlaceInfoDto item, int index) {
        Glide.with(context).load(item.getFirstimage()).centerCrop().into(imgPlace);
        textTitle.setText(item.getTitle());
        textAddress.setText(item.getAddr1());
        textIndex.setText((index + 1) + "");
    }
}
