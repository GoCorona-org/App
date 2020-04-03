package com.gocorona.model.response.login;

import com.gocorona.model.BaseApiResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
