package com.gocorona.model.dummy;

import com.gocorona.model.BaseAdapterModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentIssueModel extends BaseAdapterModel {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("imageUrlList")
    @Expose
    private List<String> imageListUrl;

    @SerializedName("youtubeVideoUrl")
    @Expose
    private String youtubeVideoUrl;

    @SerializedName("youtubeVideoId")
    @Expose
    private String youtubeVideoId;

    @SerializedName("shareCount")
    @Expose
    private String shareCount;
//
//    @SerializedName("type")
//    @Expose
//    private FeedType feedType;


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

    public List<String> getImageListUrl() {
        return imageListUrl;
    }

    public void setImageListUrl(List<String> imageListUrl) {
        this.imageListUrl = imageListUrl;
    }

    public String getYoutubeVideoUrl() {
        return youtubeVideoUrl;
    }

    public void setYoutubeVideoUrl(String youtubeVideoUrl) {
        this.youtubeVideoUrl = youtubeVideoUrl;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

//    public FeedType getFeedType() {
//        return feedType;
//    }
//
//    public void setFeedType(FeedType feedType) {
//        this.feedType = feedType;
//    }

    @Override
    public int getViewType() {
        return 0;
    }
}
