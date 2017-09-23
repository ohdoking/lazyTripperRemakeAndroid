package com.yapp.lazitripper.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ohdoking on 2017. 9. 24..
 */

public class AllTravelInfo implements Serializable {

    private int totalDay;
    private List<TravelInfo> allTraveInfo;

    public AllTravelInfo(){
        allTraveInfo = new ArrayList<TravelInfo>() ;
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

}
