package com.gocorona.model.dummy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionsDataResponse {
    @SerializedName("data")
    @Expose
    private QuestionsData data;

    public QuestionsData getData() {
        return data;
    }

    public void setData(QuestionsData data) {
        this.data = data;
    }
}
