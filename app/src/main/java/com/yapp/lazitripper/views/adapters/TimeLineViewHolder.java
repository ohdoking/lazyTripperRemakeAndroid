package com.yapp.lazitripper.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vipul.hp_hp.timelineview.TimelineView;
import com.yapp.lazitripper.R;

/**
 * Created by clock on 2017-02-25.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    //public ImageView image;
    public TextView description;
    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView,int viewType){
        super(itemView);
        description = (TextView) itemView.findViewById(R.id.placeDescription);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
        mTimelineView.initLine(viewType);
    }

}
