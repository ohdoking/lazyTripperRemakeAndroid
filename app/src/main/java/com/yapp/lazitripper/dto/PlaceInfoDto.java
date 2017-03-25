package com.yapp.lazitripper.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ohdok on 2017-02-25.
 */


/*

    addr1	주소	주소(예, 서울 중구 다동)를 응답
    addr2	상세주소	상세주소
    areacode	지역코드	지역코드
    booktour	교과서 여행지 여부	교과서 속 여행지 여부 (1=여행지, 0=해당없음)
    cat1	대분류	대분류 코드
    cat2	중분류	중분류 코드
    cat3	소분류	소분류 코드
    contentid	콘텐츠ID	콘텐츠ID
    contenttypeid	콘텐츠타입ID	관광타입(관광지, 숙박 등) ID
    createdtime	등록일	콘텐츠 최초 등록일
    firstimage	대표이미지(원본)	원본 대표이미지(약 500*333 size) URL 응답
    firstimage2	대표이미지(썸네일)	썸네일 대표이미지(약 150*100 size) URL 응답
    mapx	GPS X좌표	GPS X좌표(WGS84 경도 좌표) 응답
    mapy	GPS Y좌표	GPS Y좌표(WGS84 위도 좌표) 응답
    mlevel	Map Level	Map Level 응답
    modifiedtime	수정일	콘텐츠 수정일
    readcount	조회수	콘텐츠 조회수(korean.visitkorea.or.kr 웹사이트 기준)
    sigungucode	시군구코드	시군구코드
    tel	전화번호	전화번호
    title	제목	콘텐츠 제목
    review 리뷰(유저키,별점,코멘트)
    rating_avg 평균별점

* */
public class PlaceInfoDto implements Serializable {

    public PlaceInfoDto(){

    }

    public PlaceInfoDto(String title, String addr1, String tel, ArrayList<Review> review, double rating_tot){
        this.title = title;
        this.addr1 = addr1;
        this.tel = tel;
        this.review = review;
        this.rating_tot = rating_tot;
    }

    private ArrayList<Review> review;
    private double rating_tot;

    private String addr1;
    private String addr2;
    private Integer areacode;
    private String cat1;
    private String cat2;
    private String cat3;
    private Integer contentid;
    private Integer contenttypeid;
    private Long createdtime;
    private String firstimage;
    private String firstimage2;
    private Float mapx;
    private Float mapy;
    private Integer mlevel;
    private Long modifiedtime;
    private Integer readcount;
    private Integer sigungucode;
    private String tel;
    private String title;
    private String zipcode;

    public ArrayList<Review> getReview() {
        return review;
    }

    public void setReview(ArrayList<Review> review) {
        this.review = review;
    }

    public double getRating_tot() {
        return rating_tot;
    }

    public void setRating_tot(double rating_avg) {
        this.rating_tot = rating_avg;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public Integer getAreacode() {
        return areacode;
    }

    public void setAreacode(Integer areacode) {
        this.areacode = areacode;
    }

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public void setCat3(String cat3) {
        this.cat3 = cat3;
    }

    public Integer getContentid() {
        return contentid;
    }

    public void setContentid(Integer contentid) {
        this.contentid = contentid;
    }

    public Integer getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(Integer contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public Long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Long createdtime) {
        this.createdtime = createdtime;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {
        this.firstimage = firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public void setFirstimage2(String firstimage2) {
        this.firstimage2 = firstimage2;
    }

    public Float getMapx() {
        return mapx;
    }

    public void setMapx(Float mapx) {
        this.mapx = mapx;
    }

    public Float getMapy() {
        return mapy;
    }

    public void setMapy(Float mapy) {
        this.mapy = mapy;
    }

    public Integer getMlevel() {
        return mlevel;
    }

    public void setMlevel(Integer mlevel) {
        this.mlevel = mlevel;
    }

    public Long getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(Long modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Integer getReadcount() {
        return readcount;
    }

    public void setReadcount(Integer readcount) {
        this.readcount = readcount;
    }

    public Integer getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(Integer sigungucode) {
        this.sigungucode = sigungucode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
