package com.yapp.lazitripper.dto;

import com.yapp.lazitripper.util.TravelRoute;

import java.util.Date;
import java.util.List;

/**
 *  하루 단위의 여행 일정 (= TravelRoute) 을 가지고 있는
 *  여행일지 데이터
 */

public class TravelDiary {

    private List<TravelRoute> routeList;
    private Date date;

    public TravelDiary(){}

    public TravelDiary(List<TravelRoute> routeList){
        this.routeList = routeList;
    }

    public List<TravelRoute> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<TravelRoute> routeList) {
        this.routeList = routeList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
