package com.yapp.lazitripper.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllTravelInfo implements Serializable {

    private String travelTitle;
    private int totalDay;
    private List<TravelInfo> allTraveInfo;

    public AllTravelInfo(){
        allTraveInfo = new ArrayList<TravelInfo>() ;
    }

    public String getTravelTitle() {
        return travelTitle;
    }

    public void setTravelTitle(String travelTitle) {
        this.travelTitle = travelTitle;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public List<TravelInfo> getAllTraveInfo() {
        return allTraveInfo;
    }

    public void setAllTraveInfo(List<TravelInfo> allTraveInfo) {
        this.allTraveInfo = allTraveInfo;
    }

    public void setTraveInfoItem(TravelInfo TraveInfo) {
        this.allTraveInfo.add(TraveInfo);
    }

    public String toString(){
        return allTraveInfo.get(0).getDay()
                + allTraveInfo.get(0).getPlaceInfoDtoList().get(0).getAddr1();
    }

}
