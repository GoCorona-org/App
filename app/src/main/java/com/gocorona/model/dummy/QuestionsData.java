package com.gocorona.model.dummy;

import com.gocorona.model.DrawerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.LocalHelperUtility;

public class QuestionsData {
    @SerializedName("qstionList")
    @Expose
    private List<QuestionData> qstionList;

    public List<QuestionData> getQstionList() {
        return qstionList;
    }

    public void setQstionList(List<QuestionData> qstionList) {
        this.qstionList = qstionList;
    }






}
