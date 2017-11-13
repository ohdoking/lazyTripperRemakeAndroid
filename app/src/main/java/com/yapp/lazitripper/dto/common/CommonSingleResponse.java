package com.yapp.lazitripper.dto.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ohdok on 2017-02-25.
 */

public class CommonSingleResponse<T> {
    @SerializedName("response")
    @Expose
    CommonSingleResult<T> response;

    public CommonSingleResult<T> getResponse() {
        return response;
    }

    public void setResponse(CommonSingleResult<T> response) {
        this.response = response;
    }
}
