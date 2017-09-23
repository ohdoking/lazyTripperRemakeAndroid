package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp.lazitripper.dto.TravelRouteDto;

import java.util.ArrayList;
import java.util.List;


public class DayItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<TravelRouteDto> itemList = new ArrayList<>();
    private int item_layout;

    public DayItemAdapter(Context context, List<TravelRouteDto> itemList, int item_layout){

        this.context = context;
        this.itemList = itemList;
        this.item_layout = item_layout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder;

        view = LayoutInflater.from(parent.getContext()).inflate(item_layout, parent, false);

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

}
