package com.gocorona.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gocorona.R;

import simplifii.framework.activity.BaseActivity;

public class TermsConditionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        initToolBar("Terms & Conditions");
    }
}
