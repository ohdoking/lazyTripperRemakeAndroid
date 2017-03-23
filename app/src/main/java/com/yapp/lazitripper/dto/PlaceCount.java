package com.yapp.lazitripper.dto;

import java.io.Serializable;

/**
 * Created by ohdoking on 2017. 3. 19..
 *
 * 선택하고 싶은 장소 갯수 클래스
 */

public class PlaceCount implements Serializable {

    private Integer landMark;
    private Integer restaurant;
    private Integer accommodation;

    public PlaceCount(Integer landMark, Integer restaurant, Integer accommodation) {
        this.landMark = landMark;
        this.restaurant = restaurant;
        this.accommodation = accommodation;
    }

    public Integer getLandMark() {
        return landMark;
    }

    public void setLandMark(Integer landMark) {
        this.landMark = landMark;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Integer restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Integer accommodation) {
        this.accommodation = accommodation;
    }
}
