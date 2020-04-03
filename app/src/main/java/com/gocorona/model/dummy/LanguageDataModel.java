package com.gocorona.model.dummy;

import com.gocorona.model.BaseAdapterModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.utility.AppConstants;

public class LanguageDataModel extends BaseAdapterModel {
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("languageCode")
    @Expose
    private String languageCode;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public int getViewType() {
        return AppConstants.VIEW_CODES.LANGUAGE_DATA;
    }
}
