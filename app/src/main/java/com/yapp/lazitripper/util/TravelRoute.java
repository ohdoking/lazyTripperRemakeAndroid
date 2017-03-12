package com.yapp.lazitripper.util;

import com.yapp.lazitripper.dto.PlaceInfoDto;

import java.util.ArrayList;

/**
 * Created by ohdoking on 2017. 3. 12..
 *
 *
 */

public class TravelRoute {

    ArrayList<PlaceInfoDto> routeList;

    public TravelRoute(ArrayList<PlaceInfoDto> routeList){
        this.routeList = routeList;
    }


    /*
    * item 추가
    * */
    public void addItem(PlaceInfoDto placeInfo) {
        routeList.add(placeInfo);
    }


    /*
    * 위도 경도로 거리 계산
    *
    * @param lat1 지점 1 위도
    * @param lon1 지점 1 경도
    * @param lat2 지점 2 위도
    * @param lon2 지점 2 경도
    * @param unit 거리 표출단위
    *
    * */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }

    /*
    * 최단 경로의 랜드마크를 찾는다.
    * */
    public void findShortRoute(){

      

    }

    // This function converts decimal degrees to radians
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
