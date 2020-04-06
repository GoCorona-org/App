package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.model.dummy.QuestionProgressData;

public class StartSlideMainFragment  extends BaseQuestionsFargment {
    private View.OnClickListener mListenerCallback;
    private boolean isBtnClicked;

    public static StartSlideMainFragment newInstance(boolean hideNextBtn) {
        StartSlideMainFragment baseQuestionsFargment = new StartSlideMainFragment();
        baseQuestionsFargment.isBtnClicked = hideNextBtn;
        return baseQuestionsFargment;
    }

    @Override
    public void initViews() {
        setOnClickListener(R.id.startquestionaireclicked, R.id.interestcalculator);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        isBtnClicked = true;
        switch (v.getId()) {
            case R.id.startquestionaireclicked:
                mListenerCallback.onClick(v);
                break;
            case R.id.interestcalculator:
                mListenerCallback.onClick(v);
                break;
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_show_slide_main;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void showInvalidFieldError() {

    }

    @Override
    public void apply(QuestionProgressData questionProgressData) {
        questionProgressData.setProgress(100);
        questionProgressData.setTitle("Start");
        questionProgressData.setHideNextBtn(true);
    }

    @Override
    public void setClickListenerCallback(View.OnClickListener listenerCallback) {
        mListenerCallback = listenerCallback;
    }
}
