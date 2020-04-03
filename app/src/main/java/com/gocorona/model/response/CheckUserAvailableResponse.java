package com.gocorona.model.response;

import com.gocorona.model.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckUserAvailableResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private boolean data;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
