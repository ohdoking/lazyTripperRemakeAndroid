package com.yapp.lazitripper.activity;

import java.io.Serializable;

/**
 * Created by clock on 2017-02-25.
 */
public class TimeLineModel implements Serializable {

    //private Drawable image;
    private String des;

    public TimeLineModel(){

    }

    public TimeLineModel(String des){
        this.des = des;
    }

/*
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }*/

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
