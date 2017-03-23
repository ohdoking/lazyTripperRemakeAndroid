package com.yapp.lazitripper.dto;

/**
 * Created by ESENS on 2017-03-12.
 * DB에 넣을 여행기록
 */

public class Travel {
    private int contenttypeid;
    private String title;
    private String address;
    private String imageURL;


    //댓글 기능 추가
    private String comment;
    //private String

    public Travel(){}

    public Travel(int contenttypeid,String title, String address,String imageURL){
        this.contenttypeid = contenttypeid;
        this.title = title;
        this.address = address;
        this.imageURL = imageURL;
    }

    public Travel(String address, int contenttypeid, String imageURL, String title){
        this.contenttypeid = contenttypeid;
        this.title = title;
        this.address = address;
        this.imageURL = imageURL;
    }

    public int getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(int contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


}
