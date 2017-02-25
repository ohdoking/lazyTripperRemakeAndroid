package com.yapp.lazitripper.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ohdok on 2017-02-25.
 */

public class CommonResponse<T> {
    @SerializedName("response")
    @Expose
    CommonResultDto<T> response;

    public CommonResultDto<T> getResponse() {
        return response;
    }

    public void setResponse(CommonResultDto<T> response) {
        this.response = response;
    }
}
