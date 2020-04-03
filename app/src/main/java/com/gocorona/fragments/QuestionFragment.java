package com.gocorona.fragments;

import android.view.View;

import com.gocorona.R;
import com.gocorona.model.dummy.QuestionData;

public class QuestionFragment extends BaseQuestionsFargment {
    private View.OnClickListener mListenerCallback;

    public static QuestionFragment newInstance() {
        QuestionFragment basicFragment = new QuestionFragment();
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
        mListenerCallback = listenerCallback;
    }

    @Override
    public void initViews() {

    }

    @Override
    public int getViewID() {
        return R.layout.layout_question;
    }
}
