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
        holder.textTitle.setText(makeTitleName(list.get(section).getCityCode(), list.get(section).getDay()));
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

    //지역 코드를 지역 명으로 변환해준다
    private String makeTitleName(Integer day, Integer cityCode) {  //todo 이거정리하기

        String name = null;

        switch (cityCode) {
            case 1:
                name = "서울";
                break;
            case 2:
                name = "인천";
                break;
            case 3:
                name = "대전";
                break;
            case 4:
                name = "대구";
                break;
            case 5:
                name = "광주";
                break;
            case 6:
                name = "부산";
                break;
            case 7:
                name = "울산";
                break;
            case 8:
                name = "세종특별자치시";
                break;
            case 31:
                name = "경기도";
                break;
            case 32:
                name = "강원도";
                break;
            case 33:
                name = "충청북도";
                break;
            case 34:
                name = "충청남도";
                break;
            case 35:
                name = "경상북도";
                break;
            case 36:
                name = "경상남도";
                break;
            case 37:
                name = "전라북도";
                break;
            case 38:
                name = "전라남도";
                break;
            case 39:
                name = "제주도";
                break;
            default:
                new Exception("no place code");
                break;
        }
        return "DAY" + day + "    " + name;
    }
}
