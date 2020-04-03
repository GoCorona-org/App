package com.gocorona.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.utility.AppConstants;

public class EventFeedModel extends BaseAdapterModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getViewType() {
        return AppConstants.VIEW_CODES.EVENT_FEED;
    }
}
