package com.yapp.lazitripper.dto;

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

    public void setCityName(){}

    public String getCityName(){
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

        return name;
    }
}
