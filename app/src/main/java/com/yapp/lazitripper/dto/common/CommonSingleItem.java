package com.yapp.lazitripper.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ohdok on 2017-02-25.
 */

public class CommonSingleItem<T> {
    @SerializedName("item")
    @Expose
    private T item;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
