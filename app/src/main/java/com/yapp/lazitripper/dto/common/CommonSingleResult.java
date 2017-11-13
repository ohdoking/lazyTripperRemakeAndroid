package com.yapp.lazitripper.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ohdok on 2017-02-25.
 */
public class CommonSingleResult<T> {
    @SerializedName("header")
    @Expose
    private CommonHeader header;
    @SerializedName("body")
    @Expose
    private CommonSingleItems<T> body;

    public CommonHeader getHeader() {
        return header;
    }

    public void setHeader(CommonHeader header) {
        this.header = header;
    }

    public CommonSingleItems<T> getBody() {
        return body;
    }

    public void setBody(CommonSingleItems<T> body) {
        this.body = body;
    }
}
