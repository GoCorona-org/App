package com.gocorona.model.response.register;

import com.gocorona.model.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
