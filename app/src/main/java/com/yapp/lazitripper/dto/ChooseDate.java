package com.yapp.lazitripper.dto;

import java.util.Date;

/**
 * Created by ohdoking on 2017. 3. 21..
 */

public class ChooseDate {
    boolean isChoose;
    Date date;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
