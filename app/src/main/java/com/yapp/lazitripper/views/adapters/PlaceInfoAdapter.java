package com.yapp.lazitripper.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.TravelInfo;
import java.util.List;

public class PlaceInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TravelInfo> itemList;
    private int totalDay;
    private int item_layout;

    public PlaceInfoAdapter(Context context, AllTravelInfo allTravelInfo) {

        this.context = context;
        totalDay = allTravelInfo.getTotalDay();
        itemList = allTravelInfo.getAllTraveInfo();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        RecyclerView.ViewHolder viewHolder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_info, parent, false);

        viewHolder = new PlaceInfoDetailViewHolder(context, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //((PlaceInfoDetailViewHolder)holder).bind(itemList.get(0).getPlaceInfoDtoList().get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}