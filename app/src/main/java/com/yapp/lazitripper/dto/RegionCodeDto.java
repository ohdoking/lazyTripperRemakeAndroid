package com.yapp.lazitripper.dto;

/**
 * Created by ohdok on 2017-02-24.
 */

public class RegionCodeDto {
    /*resultCode	결과코드	응답 결과코드
    resultMsg	결과메시지	응답 결과메시지
    numOfRows	한 페이지 결과 수	한 페이지 결과 수
    pageNo	페이지 번호	현재 페이지 번호
    totalCount	전체 결과 수	전체 결과 수
    code	코드	지역코드 또는 시군구코드
    name	코드명	지역명 또는 시군구명
    rnum	일련번호	일련번호*/

    private Integer code;
    private String name;
    private Integer rnum;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRnum() {
        return rnum;
    }

    public void setRnum(Integer rnum) {
        this.rnum = rnum;
    }
}
