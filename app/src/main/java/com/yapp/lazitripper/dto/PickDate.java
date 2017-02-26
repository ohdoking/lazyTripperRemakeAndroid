package com.yapp.lazitripper.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ohdok on 2017-02-25.
 */

public class PickDate implements Serializable{
    private Date startDate;
    private Date finishDate;
    private Long period;


    public PickDate(){

    }

    public PickDate(Date startDate, Date finishDate, Long period) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.period = period;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }
}
