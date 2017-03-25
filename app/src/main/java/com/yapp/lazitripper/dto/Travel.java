package com.yapp.lazitripper.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by ESENS on 2017-03-12.
 * DB에 넣을 여행기록
 */

public class Travel {
    private int contenttypeid;
    private String title;
    private String address;
    private String imageURL;
    private Date  date;

    private String tel;
    private float rating_tot;
    private List<Review> reviewList;
    private Review review;


    //댓글 기능 추가
    private String comment;
    //private String

    public Travel(){}

    public Travel(int contenttypeid,String title, String address, String imageURL){
        this.contenttypeid = contenttypeid;
        this.title = title;
        this.address = address;
        this.imageURL = imageURL;
    }

    public Travel(String title, String address, String tel, float rating_tot, Review review){
        this.title = title;
        this.address = address;
        this.tel = tel;
        this.rating_tot = rating_tot;
        this.review = review;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getRating_tot() {
        return rating_tot;
    }

    public void setRating_tot(float rating_tot) {
        this.rating_tot = rating_tot;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }


}
