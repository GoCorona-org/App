package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.model.dummy.QuestionData;
import com.gocorona.model.dummy.QuestionProgressData;

public class IntroTwoFragment extends BaseQuestionsFargment {

    public static IntroTwoFragment newInstance() {
        IntroTwoFragment basicFragment = new IntroTwoFragment();
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
        questionProgressData.setProgress(100);
        questionProgressData.setTitle("Introduction");

    }

    @Override
    public void setClickListenerCallback(View.OnClickListener listenerCallback) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public int getViewID()  {
        return R.layout.layout_intor_two;
    }
}
