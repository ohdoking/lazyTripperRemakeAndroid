package com.yapp.lazitripper.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.yapp.lazitripper.R;
import com.yapp.lazitripper.dto.AllTravelInfo;
import com.yapp.lazitripper.dto.TravelInfo;

import java.util.List;

import static android.R.id.list;

/**
 * @author Aidan Follestad (afollestad)
 */
@SuppressLint("DefaultLocale")
public class PlaceInfoAdapter extends SectionedRecyclerViewAdapter<PlaceInfoAdapter.MainVH> {

    private AllTravelInfo allTravelInfo;
    private List<TravelInfo> list;
    private Context context;

    public PlaceInfoAdapter(Context context, AllTravelInfo allTravelInfo) {
        this.context = context;
        this.allTravelInfo = allTravelInfo;
        list = allTravelInfo.getAllTraveInfo();
    }

    @Override
    public int getSectionCount() {
        return allTravelInfo.getTotalDay();
    }

    @Override
    public int getItemCount(int section) {
        return list.get(section).getPlaceInfoDtoList().size();
    }

    @Override
    public void onBindHeaderViewHolder(MainVH holder, int section, boolean expanded) {

        String headerTitle =  "DAY" + list.get(section).getDay() + "    " + list.get(section).getCityName();
        holder.textTitle.setText(headerTitle);

    }

    @Override
    public void onBindFooterViewHolder(MainVH holder, int section) {
    }

    @Override
    public void onBindViewHolder(MainVH holder, int section, int relativePosition, int absolutePosition) {
        Glide.with(context).load(list.get(section).getPlaceInfoDtoList().get(relativePosition).getFirstimage2()).centerCrop().into(holder.imgPlace);
        holder.textTitle.setText(list.get(section).getPlaceInfoDtoList().get(relativePosition).getTitle());
        holder.textAddress.setText(list.get(section).getPlaceInfoDtoList().get(relativePosition).getAddr1());
        holder.textIndex.setText((relativePosition+1)+"");
    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {

        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.item_place_header;
                break;
            case VIEW_TYPE_ITEM:
                layout = R.layout.item_place_info;
                break;
            case VIEW_TYPE_FOOTER:
                layout = R.layout.item_place_footer;
                break;
            default:
                layout = R.layout.item_place_info; // 대부분 여기로 들어옴
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MainVH(v, this);
    }

    static class MainVH extends SectionedViewHolder implements View.OnClickListener{

        final ImageView imgPlace;
        final TextView textTitle, textIndex, textAddress;
        final PlaceInfoAdapter adapter;

        MainVH(View itemView, PlaceInfoAdapter adapter) {
            super(itemView);
            this.imgPlace = (ImageView) itemView.findViewById(R.id.img_place_item);
            this.textTitle = (TextView) itemView.findViewById(R.id.text_title_item);
            this.textIndex = (TextView) itemView.findViewById(R.id.text_index_item);
            this.textAddress = (TextView) itemView.findViewById(R.id.text_address_item);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(isHeader())
                adapter.toggleSectionExpanded(getRelativePosition().section());
        }
    }


}
