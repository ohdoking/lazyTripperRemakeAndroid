package com.yapp.lazitripper.dto;

import java.util.Date;

/**
 * Created by ESENS on 2017-03-23.
 */

/*
*
* */
public class Review {

    private String userkey;
    private float rating;
    private String comment;
    private String username;

    public Review(){}

    public Review(String comment, float rating, String userkey, String username){
        this.userkey = userkey;
        this.rating = rating;
        this.comment = comment;
        this.username = username;
    }


    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
