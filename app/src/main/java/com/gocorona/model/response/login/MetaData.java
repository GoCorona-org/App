package com.gocorona.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaData implements Serializable {
    @SerializedName("constituency")
    @Expose
    private String constituency;

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }
}
