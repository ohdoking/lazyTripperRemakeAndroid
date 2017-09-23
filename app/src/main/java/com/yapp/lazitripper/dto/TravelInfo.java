package com.yapp.lazitripper.dto;

import com.yapp.lazitripper.dto.PlaceInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ohdoking on 2017. 9. 24..
 */

public class TravelInfo implements Serializable {
    private List<PlaceInfoDto> placeInfoDtoList;
    private int day;
    private int cityCode;

    public List<PlaceInfoDto> getPlaceInfoDtoList() {
        return placeInfoDtoList;
    }

    public void setPlaceInfoDtoList(List<PlaceInfoDto> placeInfoDtoList) {
        this.placeInfoDtoList = placeInfoDtoList;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}
