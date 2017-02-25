package com.yapp.lazitripper.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yapp.lazitripper.dto.common.CommonHeader;

/**
 * Created by ohdok on 2017-02-25.
 */
public class CommonResultDto<T> {
    @SerializedName("header")
    @Expose
    private CommonHeader header;
    @SerializedName("body")
    @Expose
    private T body;

    public CommonHeader getHeader() {
        return header;
    }

    public void setHeader(CommonHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
