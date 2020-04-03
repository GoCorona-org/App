package com.gocorona.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.gocorona.MainActivity;
import com.gocorona.R;

import simplifii.framework.activity.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolBar("Sign Up");
        initViews();
        setOnClickListener(R.id.btn_submit);
    }

    private void initViews() {
        setTermsAndConditionClick();
    }

    private void setTermsAndConditionClick() {
        TextView mTermConditionTV = findViewById(R.id.tv_terms_condition);
        String text = getResources().getString(R.string.terms_conditions);
        String str = getResources().getString(R.string.reg_lbl_agree_with_tnc);
        SpannableString ss = new SpannableString(str);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(RegisterActivity.this, TermsConditionsActivity.class);
                showToast("click worked");
                startActivityForResult(intent, 109);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, str.indexOf(text), str.indexOf(text) + (text.length()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTermConditionTV.setText(ss);
        mTermConditionTV.setMovementMethod(LinkMovementMethod.getInstance());
        mTermConditionTV.setHighlightColor(Color.TRANSPARENT);

        mTermConditionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, TermsConditionsActivity.class);
                startActivityForResult(intent, 109);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_submit:
                startNextActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
