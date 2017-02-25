package com.yapp.lazitripper.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vipul.hp_hp.timelineview.TimelineView;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.TimeLineModel;

import java.util.List;

/**
 * Created by clock on 2017-02-25.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;

    public TimeLineAdapter(List<TimeLineModel> mFeedList, Context mContext) {
        this.mFeedList = mFeedList;
        this.mContext = mContext;
    }


    public void addItem(TimeLineModel item){
        mFeedList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(){
        mFeedList.remove(mFeedList.size()-1);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view;

        view = View.inflate(parent.getContext(), R.layout.place_item, null);

        return new TimeLineViewHolder(view, viewType);
    }

    public void onBindViewHolder(TimeLineViewHolder holder,final int position) {
        TimeLineModel timeLineModel = mFeedList.get(position);

        holder.description.setText(timeLineModel.getDes());
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "Recycle Click" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null ? mFeedList.size() : 0);
    }
}
