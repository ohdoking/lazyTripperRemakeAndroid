package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp.lazitripper.dto.AllTravelInfo;

import java.util.ArrayList;
import java.util.List;

public class RecentTravelAdapter extends RecyclerView.Adapter<RecentTravelViewHolder>{

    private Context context;
    private int itemLayout;
    private RecentTravelViewHolder viewHolder;
    private List<AllTravelInfo> travelList = new ArrayList<>();

    public RecentTravelAdapter(Context context, List<AllTravelInfo> travelList, int itemLayout){
        this.context = context;
        this.travelList = travelList;
        this.itemLayout = itemLayout;
    }

    @Override
    public RecentTravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        viewHolder = new RecentTravelViewHolder(parent.getContext(), view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecentTravelViewHolder holder, int position) {
        holder.bind(travelList.get(position));
    }

    @Override
    public int getItemCount() {
        Log.e("adapter", "");
        return travelList.size();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }
}
