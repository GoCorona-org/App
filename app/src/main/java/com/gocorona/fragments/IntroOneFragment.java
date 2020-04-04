package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.model.dummy.QuestionData;
import com.gocorona.model.dummy.QuestionProgressData;

import simplifii.framework.widgets.CustomFontTextView;

public class IntroOneFragment extends BaseQuestionsFargment {
    private View.OnClickListener mListenerCallback;

    public static IntroOneFragment newInstance() {
        IntroOneFragment basicFragment = new IntroOneFragment();
        return basicFragment;
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
        questionProgressData.setProgress(50);
        questionProgressData.setTitle("Introduction");
    }

    @Override
    public void setClickListenerCallback(View.OnClickListener listenerCallback) {
        mListenerCallback = listenerCallback;
    }

    @Override
    public void initViews() {

    }

    @Override
    public int getViewID() {
        return R.layout.layout_intor_one;
    }
}
