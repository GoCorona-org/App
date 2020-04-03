package com.gocorona.model.dummy;

import com.gocorona.model.DrawerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.LocalHelperUtility;

public class DrawerData {
    @SerializedName("enData")
    @Expose
    private List<DrawerModel> enData;
    @SerializedName("paData")
    @Expose
    private List<DrawerModel> paData;

    public List<DrawerModel> getEnData() {
        return enData;
    }

    public void setEnData(List<DrawerModel> enData) {
        this.enData = enData;
    }

    public List<DrawerModel> getPaData() {
        return paData;
    }

    public void setPaData(List<DrawerModel> paData) {
        this.paData = paData;
    }

    public List<DrawerModel> getDrawerList(){
        switch (LocalHelperUtility.getLanguage()){
            case AppConstants.LANGUAGE.ENGLISH:
                return getEnData();
            case AppConstants.LANGUAGE.PUNJABI:
                return getPaData();
        }
        return getEnData();
    }


}
