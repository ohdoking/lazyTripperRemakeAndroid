package com.yapp.lazitripper.activity;

import android.graphics.drawable.Drawable;

/**
 * Created by clock on 2017-02-25.
 */
public class ChosenPlaceItem {

    Drawable image;
    String name;
    String description;

    public ChosenPlaceItem(){
        image = null;
        name = null;
        description = null;
    }

    public ChosenPlaceItem(Drawable image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
