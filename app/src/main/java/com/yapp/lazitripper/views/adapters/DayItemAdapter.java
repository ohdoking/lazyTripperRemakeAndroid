package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.TravelInfo;
import com.yapp.lazitripper.dto.TravelRouteDto;

import java.util.ArrayList;
import java.util.List;

public class DayItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TravelInfo> itemList = new ArrayList<>();
    private int viewType;

    public static final int SIMPLE = 0, DETAIL = 1;

    public DayItemAdapter(Context context, List<TravelInfo> itemList, int viewType) {

        this.context = context;
        this.itemList = itemList;
        this.viewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel_route, parent, false);
        viewHolder = new DayItemViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DayItemViewHolder) holder).bind(itemList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}
