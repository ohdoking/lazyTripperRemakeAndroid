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
import com.yapp.lazitripper.util.RoundedCornersTransformation;

public class RecentTravelViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    private TextView travelTitle, travelDate;
    private ImageView ivProfileItemBg;

    public RecentTravelViewHolder(Context context, View view){
        super(view);
        this.context = context;
        travelTitle = (TextView)view.findViewById(R.id.travel_title);
        travelDate = (TextView)view.findViewById(R.id.travel_date);
        ivProfileItemBg = (ImageView) view.findViewById(R.id.iv_item_route_background);

    }

    public void bind(final AllTravelInfo item){
        travelTitle.setText(item.getTravelTitle());
        travelTitle.setTextColor(context.getResources().getColor(R.color.white));
        travelDate.setText("");
        Glide.with(context)
                .load(item.getAllTraveInfo().get(0).getPlaceInfoDtoList().get(0).getFirstimage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CenterCrop(context),new RoundedCornersTransformation(context,60, 2))
                .into(ivProfileItemBg);
    }
}
