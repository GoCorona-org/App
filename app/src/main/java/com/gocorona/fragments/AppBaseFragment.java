package com.gocorona.fragments;

import android.os.AsyncTask;
import android.widget.Toast;

import com.gocorona.MainActivity;
import com.gocorona.R;

import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.Logger;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.styleabletoast.StyleableToast;

public abstract class AppBaseFragment extends BaseFragment {

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        if(re!=null){
            if(re.getHttpStatusCode()==401){
                Preferences.deleteAllData();
            }
//            showToast(re.getMessage());
        }
        super.onBackgroundError(re, e, taskCode, params);
    }

    public void logout(){
        Preferences.deleteAllData();
        startNextActivity(MainActivity.class);
        getActivity().finish();
    }

    @Override
    public void showToast(int stringId) {
        this.showToast(getString(stringId));
    }

    @Override
    public void showToast(String message) {
        StyleableToast.makeText(getActivity(), message, Toast.LENGTH_SHORT, R.style.ErrorToast).show();
    }

    protected AsyncTask executeTask(int taskCode, Object... params) {

        if (Util.isConnectingToInternet(getActivity())) {
            AsyncManager task = new AsyncManager(taskCode, this);
            task.execute(params);
            return task;
        } else {
            Logger.info("Base Activity", "Not Connected to internet");
            StyleableToast.makeText(getActivity(), "Please Connect to Internet..!", Toast.LENGTH_SHORT, R.style.ErrorToast).show();
        }
        return null;
    }

}
