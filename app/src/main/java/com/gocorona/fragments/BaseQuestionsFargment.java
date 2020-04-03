package com.gocorona.fragments;

import android.view.View;

import com.gocorona.model.dummy.QuestionData;

public abstract class BaseQuestionsFargment extends AppBaseFragment {

    public abstract boolean isValid();

    public abstract void showInvalidFieldError();

    public abstract void apply(QuestionData registrationModel);

    public abstract void setClickListenerCallback(View.OnClickListener listenerCallback);
}
