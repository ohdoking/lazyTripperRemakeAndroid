package com.yapp.lazitripper.dto;



/**
 * Created by donghyunkim on 2017. 3. 30..
 */
/**
 * Created by ESENS on 2017-03-12.
 * DB에 넣을 여행기록
 */

public class TravelRouteDto {
    private int contenttypeid;
    private int contentid;
    private String title;
    private String address;
    private String imageURL;
    private float rating_tot;

    public TravelRouteDto(int contenttypeid, int contentid, String title, String address, String imageURL, float rating_tot) {
        this.contenttypeid = contenttypeid;
        this.contentid = contentid;
        this.title = title;
        this.address = address;
        this.imageURL = imageURL;
        this.rating_tot = rating_tot;
    }

    public TravelRouteDto() {
    }

    public int getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(int contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public int getContentid() {
        return contentid;
    }

    public void setContentid(int contentid) {
        this.contentid = contentid;
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

    public float getRating_tot() {
        return rating_tot;
    }

    public void setRating_tot(float rating_tot) {
        this.rating_tot = rating_tot;
    }

    @Override
    public String toString() {
        return "TravelRoute{" +
                "contenttypeid=" + contenttypeid +
                ", contentid=" + contentid +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", rating_tot=" + rating_tot +
                '}';
    }
}
