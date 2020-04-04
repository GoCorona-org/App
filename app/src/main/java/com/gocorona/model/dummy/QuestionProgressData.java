package com.gocorona.model.dummy;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class QuestionProgressData implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("progress")
    @Expose
    private int progress;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
