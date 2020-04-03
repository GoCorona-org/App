package com.gocorona.fragments;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.gocorona.R;
import com.gocorona.listeners.ChangeFragmentListner;

import simplifii.framework.fragments.BaseDialogFragment;

public class CoronCheckDialog extends BaseDialogFragment {
    ChangeFragmentListner changeFragmentListner;

    // Empty constructor required for DialogFragment
    public CoronCheckDialog() {
    }

    public static CoronCheckDialog getInstance(ChangeFragmentListner changeFragmentListner) {
        CoronCheckDialog coronCheckDialog = new CoronCheckDialog();
        coronCheckDialog.changeFragmentListner = changeFragmentListner;
        return coronCheckDialog;
    }


    @Override
    protected int getViewID() {
        return R.layout.layout_corona_check;
    }

    @Override
    protected void initViews() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setOnClickListener(R.id.btn_checkup, R.id.btn_no);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                getDialog().dismiss();
                break;
            case R.id.btn_checkup:
                changeFragmentListner.addFragment(new QuestionsMainFargment(), true);
                getDialog().dismiss();
                break;
        }
    }
}
