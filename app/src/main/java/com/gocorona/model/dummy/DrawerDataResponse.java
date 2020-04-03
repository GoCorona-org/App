package com.gocorona.model.dummy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrawerDataResponse {
    @SerializedName("data")
    @Expose
    private DrawerData data;

    public DrawerData getData() {
        return data;
    }

    public void setData(DrawerData data) {
        this.data = data;
    }
}
