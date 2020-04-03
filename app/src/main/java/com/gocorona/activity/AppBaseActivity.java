package com.gocorona.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;

import com.gocorona.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.TaskFragment;
import simplifii.framework.utility.LocalHelperUtility;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.styleabletoast.StyleableToast;

public class AppBaseActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelperUtility.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Support Full screen view...
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //Support Vector Drawable...
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
     * This Method should only use for toggle the view which are dependent on user signIn or singOut
     * */


    public void addVectorDrawableOnTextView(AppCompatTextView textView,
                                            @DrawableRes int left, @DrawableRes int top,
                                            @DrawableRes int right, @DrawableRes int bottom) {
        textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void updateVisibilityOfViewsById(boolean shouldVisible, @IdRes int... ids) {
        for (@IdRes int id : ids) {
            if (findViewById(id) != null) {
                findViewById(id).setVisibility(shouldVisible ? View.VISIBLE : View.GONE);
            }
        }
    }


    private void restartApp() {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showToast(int stringId) {
        this.showToast(getString(stringId));
    }

    @Override
    public void showToast(String message) {
        StyleableToast.makeText(this, message, Toast.LENGTH_SHORT, R.style.ErrorToast).show();
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
        /*if (re !=null){
            if (re.getHttpStatusCode() == 400){
                StyleableToast.makeText(this, re != null ? re.getMessage() : getString(R.string.bkg_error), Toast.LENGTH_SHORT, R.style.ErrorToast).show();
            }
        }*/
        StyleableToast.makeText(this, re != null && !TextUtils.isEmpty(re.getMessage()) ? re.getMessage():getString(R.string.bkg_error), Toast.LENGTH_SHORT, R.style.ErrorToast).show();
    }

    public void logout(){
        Preferences.deleteAllData();
//        startNextActivity(MainActivity.class);
        finish();
    }

    public AsyncTask executeTask(int taskCode, Object... params) {
        if (Util.isConnectingToInternet(this)) {
            this.taskFragment.createAsyncTaskManagerObject(taskCode)
                    .executeOnExecutor(TaskFragment.AsyncManager.THREAD_POOL_EXECUTOR, params);
        } else {
            Log.i("Base Activity", "Not Connected to internet");
            StyleableToast.makeText(this, "Internet not connected...", Toast.LENGTH_SHORT, R.style.ErrorToast).show();
        }
        return null;
    }
}
