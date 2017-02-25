package com.yapp.lazitripper.dto.common;

/**
 * Created by ohdok on 2017-02-25.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
        resultCode	결과코드	응답 결과코드
        resultMsg	결과메시지	응답 결과메시지
        numOfRows	한 페이지 결과 수	한 페이지 결과 수
        pageNo	페이지 번호	현재 페이지 번호
        totalCount	전체 결과 수	전체 결과 수
        code	코드	지역코드 또는 시군구코드
        name	코드명	지역명 또는 시군구명
        rnum	일련번호	일련번호
*/

public class CommonItems<T> {
    @SerializedName("items")
    @Expose
    private CommonItem<T> items;

    @SerializedName("numOfRows")
    @Expose
    private Integer numOfRows;

    @SerializedName("pageNo")
    @Expose
    private Integer pageNo;

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;

    public CommonItem<T> getItems() {
        return items;
    }

    public void setItems(CommonItem<T> items) {
        this.items = items;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
