package com.yapp.lazitripper.dto;

import java.io.Serializable;
import java.util.List;

/*

    resultCode 결과코드 응답 결과코드
	resultMsg 결과메시지 응답 결과메시지
	numOfRows 한 페이지 결과 수
	pageNo 현재 페이지 번호
	totalCount 전체 결과 수
	contentid 콘텐츠ID
	contenttypeid 콘텐츠타입ID 관광타입(관광지, 숙박 등) ID
	booktour 교과서 여행지 여부
	defaultYN=Y (기본정보 조회) 교과서 속 여행지 여부 (1=여행지, 0=해당없음)
	createdtime 등록일 콘텐츠 최초 등록일
	homepage 홈페이지 주소
	modifiedtime 콘텐츠 수정일
	tel 전화번호
	telname 전화번호명
	title 콘텐츠명(제목)
	firstimage 대표이미지(원본)
	firstImageYN=Y 대표이미지 조회
	원본 대표이미지(약 500*333 size) URL 응답
	firstimage2 대표이미지(썸네일)
	썸네일 대표이미지(약 150*100 size) URL 응답
	areacode 지역코드
	areacodeYN=Y (지역정보 조회)
	sigungucode 시군구코드
	cat1 대분류
	catcodeYN=Y (분류코드 조회) 대분류 코드
	cat2 중분류 코드
	cat3 소분류 코드
	addr1 주소
	addrinfoYN=Y (주소정보 조회) 주소(예, 서울 중구 다동)를 응답
	addr2 상세주소
	zipcode 우편번호
	mapinfoYN=Y (좌표정보 조회) GPS X좌표(WGS84 경도 좌표) 응답
	mapx GPS X좌표
	mapy GPS Y좌표(WGS84 위도 좌표) 응답
	mlevel Map Level 응답
	overview 개요
	overviewYN=Y 콘텐츠

* */

public class CommonInfoDto implements Serializable {

    private int resultCode;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private String resultMsg;
    private String contentid;
    private String contenttypeid;
    private int booktour;
    private String createdtime;
    private String homepage;
    private String modifiedtime;
    private String tel;
    private String telname;
    private String title;
    private String firstImage;
    private String firstImage2;
    private int areacode;
    private int sigungucode;
    private int cat1;
    private int cat2;
    private int cat3;
    private String adrr1;
    private String adrr2;
    private String zipcode;
    private String mapx;
    private String mapy;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public void setContenttypeid(String contenttypeid) {
        this.contenttypeid = contenttypeid;
    }

    public int getBooktour() {
        return booktour;
    }

    public void setBooktour(int booktour) {
        this.booktour = booktour;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelname() {
        return telname;
    }

    public void setTelname(String telname) {
        this.telname = telname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getFirstImage2() {
        return firstImage2;
    }

    public void setFirstImage2(String firstImage2) {
        this.firstImage2 = firstImage2;
    }

    public int getAreacode() {
        return areacode;
    }

    public void setAreacode(int areacode) {
        this.areacode = areacode;
    }

    public int getSigungucode() {
        return sigungucode;
    }

    public void setSigungucode(int sigungucode) {
        this.sigungucode = sigungucode;
    }

    public int getCat1() {
        return cat1;
    }

    public void setCat1(int cat1) {
        this.cat1 = cat1;
    }

    public int getCat2() {
        return cat2;
    }

    public void setCat2(int cat2) {
        this.cat2 = cat2;
    }

    public int getCat3() {
        return cat3;
    }

    public void setCat3(int cat3) {
        this.cat3 = cat3;
    }

    public String getAdrr1() {
        return adrr1;
    }

    public void setAdrr1(String adrr1) {
        this.adrr1 = adrr1;
    }

    public String getAdrr2() {
        return adrr2;
    }

    public void setAdrr2(String adrr2) {
        this.adrr2 = adrr2;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public CommonInfoDto(int resultCode, int numOfRows, int pageNo, int totalCount, String resultMsg, String contentid, String contenttypeid) {

        this.resultCode = resultCode;
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
        this.resultMsg = resultMsg;
        this.contentid = contentid;
        this.contenttypeid = contenttypeid;
    }

    private String overview;

}
