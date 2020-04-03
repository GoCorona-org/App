package simplifii.framework.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.DrawableRes;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import simplifii.framework.R;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.TaskFragment;
import simplifii.framework.listeners.OnFragmentCallBack;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.CustomFontTextView;


public class BaseActivity extends AppCompatActivity implements
        OnClickListener, TaskFragment.AsyncTaskListener, OnFragmentCallBack {

    protected ActionBar bar;
    protected View actionBarView;
    ProgressDialog dialog;
    protected boolean isCartIconNeeded = false;
    public TaskFragment taskFragment;
    protected Toolbar toolbar;

    protected final String TAG = getTag();

    public static boolean isInternetDialogVisible = false;

    public String getActionTitle() {
        return getResources().getString(R.string.app_name);
    }

    protected String getTag() {
        return this.getClass().getSimpleName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadBundle(bundle);
        }
        // initActionBar();
        taskFragment = new TaskFragment();
        getSupportFragmentManager().beginTransaction().add(taskFragment, "task").commit();
    }

    protected void loadBundle(Bundle bundle) {

    }

    protected void addFragmentReplace(int containerID, Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, 0, 0, R.anim.slide_out_bottom);
        getSupportFragmentManager().popBackStack();
        if (b) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerID, fragment).commit();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
    }

    protected void initWindow() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.white);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onActionItemClicked(View v) {
        Log.i(TAG, "onActionItemClicked");
        switch (v.getId()) {

            default:
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // do nothing
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setFullScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getWindow().setAttributes(attrs);
    }


    public void onBackIconClicked(View v) {
        super.onBackPressed();
    }

    protected String getTilText(int tilId) {
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(tilId);
        return textInputLayout.getEditText().getText().toString().trim();
    }

    protected void removeTilError(int tilId) {
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(tilId);
        textInputLayout.setError(null);
    }

    protected void setTilError(int tilId, String s) {
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(tilId);
        textInputLayout.setError(s);
    }

    public void showToast(String message) {
        Log.d(TAG, message+"");
        Toast.makeText(this, message+"", Toast.LENGTH_LONG).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_LONG).show();
    }




    protected void onInternetException() {
//        findViewById(R.id.frame_noInternet).setVisibility(View.VISIBLE);
    }

    public boolean isNetworkAvailable() {
        if (Util.isConnectingToInternet(this)) {
            return true;
        } else {
            Log.i("Base Activity", "Not Connected to internet");
            if (isInternetDialogVisible) {
                Util.createAlertDialog(this, "Please Connect to Internet",
                        "Not Connected to internet", false, "OK", "Cancel",
                        internetDialogListener).show();
                isInternetDialogVisible = true;
            }
            return false;
        }
    }

    public static Util.DialogListener internetDialogListener = new Util.DialogListener() {

        @Override
        public void onOKPressed(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            isInternetDialogVisible = false;
        }

        @Override
        public void onCancelPressed(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            isInternetDialogVisible = false;
        }
    };


    public void hideProgressBar() {
        Log.i(TAG + "Dialog", Thread.currentThread().getName());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Loading...");
            dialog.show();
            dialog.setCancelable(false);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    public void setText(String text, int textViewId) {
        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
    }

    protected String getTextView(int textViewId) {
        return ((TextView) findViewById(textViewId)).getText().toString().trim();
    }


    public void setText(String text, int textViewId, View v) {
        TextView view = (TextView) v.findViewById(textViewId);
        view.setText(text);
    }


    private int cartCount = 0;


    public void startNextActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
    }

    public void clearBackStackAndStartNextActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void startNextActivityForResult(Class<? extends Activity> activityClass, int REQUEST_CODE) {
        Intent i = new Intent(this, activityClass);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void startNextActivity(Bundle bundle,
                                  Class<? extends Activity> activityClass) {

        Intent i = new Intent(this, activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    public void startNextActivityForResult(Bundle bundle,
                                           Class<? extends Activity> activityClass, int REQ_CODE) {

        Intent i = new Intent(this, activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, REQ_CODE);
    }


    protected void setOnClickListener(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setOnClickListener(this);
        }
    }

    protected String getEditText(int editTextId) {
        return ((EditText) findViewById(editTextId)).getText().toString()
                .trim();
    }

    protected void setEditText(int editTextId, String text) {
        ((EditText) findViewById(editTextId)).setText(text);
    }

    @Override
    public void onPreExecute(int taskCode) {
        showProgressDialog();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        hideProgressBar();
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        hideProgressBar();
    }

    public void initToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setStatusBarColor(getResourceColor(R.color.color_primary_dark));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_title));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.color_title));
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView && ((TextView) view).getText().equals(title)) {
                ((TextView) view).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Campton_Medium.ttf"));
                break;
            }
        }
    }

    public void initToolBar(String title, boolean showBackBtn) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showBackBtn);
        getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setStatusBarColor(getResourceColor(R.color.color_primary_dark));
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_title));
        toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.color_title));
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView && ((TextView) view).getText().equals(title)) {
                ((TextView) view).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Campton_Medium.ttf"));
                break;
            }
        }
    }

    protected int getHomeIcon() {
        return R.drawable.ic_back_brown;
    }

    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    public void onRetryClicked(View view) {
        if (Util.isConnectingToInternet(this)) {
            findViewById(R.id.frame_noInternet).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomePressed();
                return true;
        }
        return false;
    }

    protected void onHomePressed() {
        onBackPressed();
    }

    protected void onServerError() {
//        FrameLayout errorLayout = (FrameLayout) findViewById(R.id.frame_noInternet);
//        if (errorLayout != null) {
//            errorLayout.setVisibility(View.VISIBLE);
//            ImageView errorImage = (ImageView) errorLayout.findViewById(R.id.iv_error);
//            TextView errorMsg = (TextView) errorLayout.findViewById(R.id.tv_errorMsg);
//            TextView errorInfo = (TextView) errorLayout.findViewById(R.id.tv_errorInfo);
//
//            errorImage.setImageResource(R.drawable.icon_server_error);
//            errorMsg.setText("SERVER ERROR");
//            errorInfo.setText("Oops! Something went wrong...");
//        }
    }

    protected void hideVisibility(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setVisibility(View.GONE);
        }
    }

    protected void showVisibility(int... viewIds) {
        for (int i : viewIds) {
            findViewById(i).setVisibility(View.VISIBLE);
        }
    }

    protected void showErrorDialog(String error) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        dialog.setContentView(R.layout.error_dialog);
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.tv_title);
        textView.setText(error);
        dialog.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    protected int getResourceColor(int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    protected void changeFontOfTextView(int id, String font) {
        ((CustomFontTextView) findViewById(id)).setCustomFont(this, font);
    }

    protected void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void setError(int editTextId, String error) {
        EditText editText = (EditText) findViewById(editTextId);
        editText.setError(error);
    }

    private ArrayList<Integer> editTextList;
    private ArrayList<String> validationTypeList;
    private ArrayList<String> errorMessegeList;
    private ArrayList<String> fieldNames;

    protected void initialiseValidation() {
        editTextList = new ArrayList();
        validationTypeList = new ArrayList();
        errorMessegeList = new ArrayList();
        fieldNames = new ArrayList();
    }

    protected void setValidation(int editTextId, String validationType, String errorMessege) {
        editTextList.add(editTextId);
        validationTypeList.add(validationType);
        errorMessegeList.add(errorMessege);
        fieldNames.add("");
    }

    protected void setEmptyValidation(int editTextId, String fieldName) {
        editTextList.add(editTextId);
        validationTypeList.add(AppConstants.VALIDATIONS.EMPTY);
        errorMessegeList.add("");
        fieldNames.add(fieldName);
    }

    protected void setEmailValidation(int editTextId) {
        setEmptyValidation(editTextId, "Email");
        setValidation(editTextId, AppConstants.VALIDATIONS.EMAIL, "");
    }

    protected void setPhoneNumberValidation(int editTextId, String fieldName) {
        setEmptyValidation(editTextId, fieldName);
        setValidation(editTextId, AppConstants.VALIDATIONS.MOBILE, "");
    }

    protected boolean isValidate() {
        for (int x = 0; x < editTextList.size(); x++) {
            int id = editTextList.get(x);
            String validationType = validationTypeList.get(x);
            String errorMessage = errorMessegeList.get(x);

            EditText editText = (EditText) findViewById(id);

            switch (validationType) {
                case AppConstants.VALIDATIONS.EMPTY:
                    String text = editText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        if ("".equals(errorMessage)) {
                            editText.setError(fieldNames.get(x) + " cannot be empty..!");
                        } else {
                            editText.setError(errorMessage);
                        }
                        return false;
                    }
                    break;
                case AppConstants.VALIDATIONS.EMAIL:
                    String email = editText.getText().toString();
                    if (!Util.isValidEmail(email)) {
                        if ("".equals(errorMessage))
                            editText.setError(getString(R.string.error_invalid_email));
                        else
                            editText.setError(errorMessage);
                        return false;
                    }
                    break;
                case AppConstants.VALIDATIONS.MOBILE:
                    String mobile = editText.getText().toString();
                    if (10 != mobile.length()) {
                        if ("".equals(errorMessage))
                            editText.setError(getString(R.string.error_invalid_mobile));
                        else
                            editText.setError(errorMessage);
                        return false;
                    }

                    break;
            }
        }
        return true;
    }

    protected void setImage(int imaheViewId, int resourceId) {
        ((ImageView) findViewById(imaheViewId)).setImageResource(resourceId);
    }

    protected void askPermission(PermissionListener permissionListener, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    new TedPermission(this)
                            .setPermissions(permission)
                            .setPermissionListener(permissionListener)
                            .check();
                }
            }
        }
    }

    public void hideKeyboard() {
        /*
         * getActivity().getWindow().setSoftInputMode(
         * WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         */

        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    public static String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    public void startNextActivityAndFinishCurrent(Class<? extends Activity> activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
        finish();
    }

    @Override
    public void onFragmentResult(int result, Bundle data) {

    }

    @Override
    public void onLabelChange() {

    }

    public void addVectorDrawableOnEditText(EditText editText,
                                            @DrawableRes int left, @DrawableRes int top,
                                            @DrawableRes int right, @DrawableRes int bottom) {
        editText.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}
