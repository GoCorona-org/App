package com.gocorona.model.dummy;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class OptionsData implements Serializable {
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("isSelected")
    @Expose
    private int isSelected;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
