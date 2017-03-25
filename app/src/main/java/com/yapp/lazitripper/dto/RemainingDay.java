package com.yapp.lazitripper.dto;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ESENS on 2017-03-25.
 */
public class RemainingDay {
    private String key;
    private ArrayList<String> dayRemaining;

    public RemainingDay(){}

    public RemainingDay(String key , ArrayList<String> dayRemaining){
        this.key = key;
        this.dayRemaining = dayRemaining;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getDayRemaining() {
        return dayRemaining;
    }

    public void setDayRemaining(ArrayList<String> dayRemaining) {
        this.dayRemaining = dayRemaining;
    }



}
