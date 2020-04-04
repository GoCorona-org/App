package com.gocorona.model.dummy;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

public class QuestionData implements Serializable {
    @SerializedName("qstion")
    @Expose
    private String qstion;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("isQuestion")
    @Expose
    private boolean isQuestion;
    @SerializedName("options")
    @Expose
    private List<OptionsData> options = null;

    public String getQstion() {
        return qstion;
    }

    public void setQstion(String qstion) {
        this.qstion = qstion;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public List<OptionsData> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsData> options) {
        this.options = options;
    }
//
//    public static QuestionData getInstance() {
//        String sessionJson = Preferences.getData(AppConstants.PREF_KEYS.QUESTIONS_DATA, "");
//        if (!TextUtils.isEmpty(sessionJson)) {
//            return new Gson().fromJson(sessionJson, QuestionData.class);
//        }
//        return null;
//    }
}
