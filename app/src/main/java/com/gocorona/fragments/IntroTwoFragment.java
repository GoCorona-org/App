package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.model.dummy.QuestionData;

public class IntroTwoFragment extends BaseQuestionsFargment {

    public static IntroTwoFragment newInstance() {
        IntroTwoFragment basicFragment = new IntroTwoFragment();
        return basicFragment;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void showInvalidFieldError() {

    }

    @Override
    public void apply(QuestionData registrationModel) {

    }

    @Override
    public void setClickListenerCallback(View.OnClickListener listenerCallback) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public int getViewID()  {
        return R.layout.layout_question;
    }
}
