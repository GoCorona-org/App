package simplifii.framework.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import simplifii.framework.R;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.Service;
import simplifii.framework.asyncmanager.ServiceFactory;
import simplifii.framework.exceptionhandler.ExceptionHandler;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.listeners.OnFragmentCallBack;
import simplifii.framework.receivers.AppBrodcastReciver;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Logger;
import simplifii.framework.utility.Util;
import simplifii.framework.utility.ViewPagerScroller;

public abstract class BaseFragment extends Fragment implements
        View.OnClickListener,
        TaskFragment.AsyncTaskListener, AppBrodcastReciver.SetTabListener, OnFragmentCallBack {

    protected View v;
    int layoutId;
    protected AppCompatActivity activity;
    boolean retainFlag = false;
    public static String LAST_UPDATED = "Last Updated: ";
    public static String LAST_UPDATED_ANALYTICS = "Last Updated Analytics: ";
    protected AlertDialog dialog;
    protected String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;
    protected AppBrodcastReciver rec;

    public String getActionTitle() {
        return null;
    }


    public void refreshData() {
    }

    protected void setError(int editTextId, String error) {
        EditText editText = (EditText) findView(editTextId);
        editText.setError(error);
    }

    protected void setOnClickListener(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setOnClickListener(this);
        }
    }

    protected void copyToClipBoard(final TextView tv){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(tv.getText());
                Toast.makeText(getActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void addFragment(int containerID, Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //getChildFragmentManager().popBackStack();
        if (b) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(containerID, fragment).commitAllowingStateLoss();
    }

    protected void setEditText(int layoutId, String text) {
        ((EditText) findView(layoutId)).setText(text);
    }

    protected void hideVisibility(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setVisibility(View.GONE);
        }
    }

    protected void showVisibility(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setVisibility(View.VISIBLE);
        }
    }

    protected void setOnClickListener(View v, int... viewIds) {
        for (int id : viewIds) {
            v.findViewById(id).setOnClickListener(this);
        }
    }

    public void openSortDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
//        this.setRetainInstance(true);
        retainFlag = true;
        Log.e("onCreate", "savedInstanceState:" + savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        v = inflater.inflate(getViewID(), null);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("Retain Flag", retainFlag + "");
        initViews();
    }

    @Override
    public void onFragmentResult(int result, Bundle data) {

    }

    @Override
    public void onLabelChange() {
//        initViews();
    }

    public void startNextActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(getActivity(), activityClass);
        startActivity(i);
    }

    public void startNextActivityForResult(Class<? extends Activity> activityClass, int req_code) {
        Intent i = new Intent(getActivity(), activityClass);
        startActivityForResult(i, req_code);
    }

    public void startNextActivity(Bundle bundle,
                                  Class<? extends Activity> activityClass) {

        Intent i = new Intent(getActivity(), activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    public void startNextActivityForResult(Bundle bundle,
                                           Class<? extends Activity> activityClass, int REQ_CODE) {

        Intent i = new Intent(getActivity(), activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, REQ_CODE);
    }

    protected void disableView(int... viewId) {
        for (int id : viewId) {
            findView(id).setEnabled(false);
        }
    }

    protected String getTextView(int textViewId) {
        return ((TextView) findView(textViewId)).getText().toString().trim();
    }

    public void scrollToBottom(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
                // scrollView.scrollTo(0, scrollView.getBottom());
            }
        });
    }

    protected void onInternetException() {
        FrameLayout noInternetLayout = (FrameLayout) findView(R.id.frame_noInternet);
        if (noInternetLayout != null) {
            noInternetLayout.setVisibility(View.VISIBLE);
            findView(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClicked(v);
                }
            });
        }

    }

    protected void onServerError() {
        FrameLayout errorLayout = (FrameLayout) findView(R.id.frame_noInternet);
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
            ImageView errorImage = (ImageView) errorLayout.findViewById(R.id.iv_error);
            TextView errorMsg = (TextView) errorLayout.findViewById(R.id.tv_errorMsg);
            TextView errorInfo = (TextView) errorLayout.findViewById(R.id.tv_errorInfo);

//            errorImage.setImageResource(R.drawable.icon_server_error);
            errorMsg.setText(R.string.server_error);
            errorInfo.setText(R.string.msg_server_error);
            findView(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClicked(v);
                }
            });
        }
    }

    public void onRetryClicked(View view) {
        if (Util.isConnectingToInternet(getActivity())) {
            findView(R.id.frame_noInternet).setVisibility(View.GONE);
        }
    }

    public void scrollToTop(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
                // scrollView.scrollTo(0, scrollView.getBottom());
            }
        });
    }

    protected void initActionBar(String text) {
        ((BaseActivity) getActivity()).initToolBar(text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    public void registerClickListeners(int... viewIds) {
        for (int id : viewIds) {
            v.findViewById(id).setOnClickListener(this);
        }
    }

    public void registerClickListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public View findView(int id) {
        return v.findViewById(id);
    }

    public abstract void initViews();

    public abstract int getViewID();

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    protected void setText(String text, int textViewID) {
        TextView textView = (TextView) findView(textViewID);
        textView.setText(text);
    }

    protected void setText(int textViewID, String text, View row) {
        TextView textView = (TextView) row.findViewById(textViewID);
        textView.setText(text);
    }

    public void hideKeyboard() {
        /*
         * getActivity().getWindow().setSoftInputMode(
		 * WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		 */

        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    @Override
    public void onReceive(Intent intent) {

    }

    public class AsyncManager extends AsyncTask<Object, Object, Object> {

        public static final String TAG = "XebiaAsyncManage";

        private int taskCode;
        private Object[] params;
        private Exception e;
        private long startTime;
        TaskFragment.AsyncTaskListener asyncTaskListener;

        public AsyncManager(int taskCode, TaskFragment.AsyncTaskListener ref) {
            this.taskCode = taskCode;
            asyncTaskListener = ref;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
            Logger.info(TAG, "On Preexecute AsyncTask");
            if (asyncTaskListener != null) {
                asyncTaskListener.onPreExecute(this.taskCode);
            }
        }

        @Override
        protected Object doInBackground(Object... params) {
            Object response = null;
            Service service = ServiceFactory.getInstance(getActivity(),
                    taskCode);
            Logger.info(TAG, "DoinBackGround");
            try {
                this.params = params;
                response = service.getData(params);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof JSONException) {
                    String exceptionMessage = "APIMANAGEREXCEPTION : ";

                    exceptionMessage += ExceptionHandler.getStackString(e,
                            asyncTaskListener.getClass()
                                    .getName());
                    /*
                     * ServiceFactory.getDBInstance(context).putStackTrace(
					 * exceptionMessage);
					 */
                }
                this.e = e;
            }
            return response;
        }

        @Override
        protected void onPostExecute(Object result) {

            if (getActivity() != null) {

                Logger.error(
                        "servicebenchmark",
                        asyncTaskListener.getClass().getName()
                                + " , time taken in ms: "
                                + (System.currentTimeMillis() - startTime));

                if (e != null) {

                    if (e instanceof RestException) {
                        asyncTaskListener.onBackgroundError((RestException) e,
                                null, this.taskCode, this.params);
                    } else {
                        asyncTaskListener.onBackgroundError(null, e,
                                this.taskCode, this.params);
                    }
                } else {
                    asyncTaskListener.onPostExecute(result, this.taskCode,
                            this.params);
                }
                super.onPostExecute(result);
            }
        }

        public int getCurrentTaskCode() {
            return this.taskCode;
        }

    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode,
                                  Object... params) {
        hideProgressBar();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        hideProgressBar();
    }

    @Override
    public void onPreExecute(int taskCode) {
        showProgressBar();
        switch (taskCode){
            case AppConstants.TASK_CODES.CATEGORY_VENDOR:
                hideProgressBar();
                break;
        }
    }

    public void showProgressBar() {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    protected String getEditText(int editTextId) {
        return ((EditText) findView(editTextId)).getText().toString().trim();
    }

    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showToast(int stringId) {
        showToast(getString(stringId));
    }

    protected void showToast(String msg, int duration) {
        Toast.makeText(getActivity(), msg, duration).show();
    }

    protected void showErrorDialog(String error) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
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

    public void initToolBar(String title) {
        toolbar = (Toolbar) findView(R.id.toolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    public void initActivityToolBar(String title) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    public void initActivityContainerToolbar(String title){
//        ((android.support.v7.widget.Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle(title);
    }

    protected int getHomeIcon() {
        return R.drawable.ic_back_brown;
    }

    public void registerReceiver(String... actions) {
        if (rec == null) {
            IntentFilter filter = new IntentFilter();
            for (String s : actions){
                filter.addAction(s);
            }
            /*rec.removeListener(this);
            unregisterReceiver(rec);*/
            rec=new AppBrodcastReciver(this);
            getActivity().registerReceiver(rec, filter);

        }
    }

    public void unregisterReceiver(AppBrodcastReciver rec) {
        if (this.rec != null) {
            this.rec.removeListener(this);
            getActivity().unregisterReceiver(this.rec);
        }
    }



    @Override
    public void onDestroy() {
        unregisterReceiver(rec);
        super.onDestroy();
    }

    protected int getResourceColor(int colorId) {
        return ContextCompat.getColor(getActivity(), colorId);
    }

    protected void hideKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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

            EditText editText = (EditText) findView(id);

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

    protected void setFocasable(boolean b, int... ids) {
        for (int id : ids) {
            View view = findView(id);
            view.setFocusable(b);
            view.requestFocus(View.FOCUS_RIGHT);
            if (b) {
                view.setOnClickListener(null);
            } else {
                view.setOnClickListener(this);
            }

        }
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

    public void addVectorDrawableOnTextView(TextView textView,
                                            @DrawableRes int left, @DrawableRes int top,
                                            @DrawableRes int right, @DrawableRes int bottom) {
        textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void addVectorDrawableOnEditText(EditText editText,
                                            @DrawableRes int left, @DrawableRes int top,
                                            @DrawableRes int right, @DrawableRes int bottom) {
        editText.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void updateVisibilityOfViewsById(boolean visible, @IdRes int... ids) {
        for (@IdRes int id : ids) {
            if (findView(id) != null) {
                findView(id).setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    protected void setupViewPagerScrolling(ViewPager viewPager) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(viewPager.getContext());
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
//            Log.e(TAG, "error of change scroller ", e);
        }
    }


}
