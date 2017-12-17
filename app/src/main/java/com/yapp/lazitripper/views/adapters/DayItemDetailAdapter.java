package com.yapp.lazitripper.views.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.PlaceInfoDto;
import com.yapp.lazitripper.dto.TravelInfo;

import java.util.ArrayList;
import java.util.List;

public class DayItemDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PlaceInfoDto> itemList = new ArrayList<>();
    private int viewType;

    public static final int SIMPLE = 0;

    public DayItemDetailAdapter(Context context, List<PlaceInfoDto> itemList, int viewType) {

        this.context = context;
        this.itemList = itemList;
        this.viewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_info, parent, false);
        RecyclerView.ViewHolder viewHolder = new DayItemDetailViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((DayItemDetailViewHolder) holder).bind(itemList.get(position), position);

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
