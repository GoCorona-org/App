package com.gocorona.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConctituencyResponce extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private List<ConctituencyResponceData> data = null;

    public List<ConctituencyResponceData> getData() {
        return data;
    }

    public void setData(List<ConctituencyResponceData> data) {
        this.data = data;
    }
}
