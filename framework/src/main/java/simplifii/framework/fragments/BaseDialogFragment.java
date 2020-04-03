package simplifii.framework.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import simplifii.framework.asyncmanager.Service;
import simplifii.framework.asyncmanager.ServiceFactory;
import simplifii.framework.exceptionhandler.ExceptionHandler;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.Logger;
import simplifii.framework.utility.Util;


public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener, TaskFragment.AsyncTaskListener {

    private View v;
    protected AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(getViewID(), null);
        initViews();
        return v;

    }

    public View findView(int id) {
        return v.findViewById(id);
    }

    protected void setOnClickListener(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setOnClickListener(this);
        }
    }

    protected abstract int getViewID();

    protected abstract void initViews();

    @Override
    public abstract void onClick(View view);

    protected AsyncTask executeTask(int taskCode, Object... params) {

        if (Util.isConnectingToInternet(getActivity())) {
            AsyncManager task = new AsyncManager(taskCode, this);
            task.execute(params);
            return task;
        } else {
            Logger.info("Base Activity", "Not Connected to internet");
//            Toast.makeText(getActivity(), "Please Connect to Internet..!", Toast.LENGTH_SHORT).show();
//			Util.createAlertDialog(getActivity(), "Please Connect to Internet",
//					"Not Connected to internet", false, "OK", "Cancel",
//					(Util.DialogListener) BaseActivity.internetDialogListener).show();
            onInternetException(taskCode);
        }
        return null;
    }

    protected void onInternetException(int taskCode) {

    }

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(String msg,int duration) {
        Toast.makeText(getActivity(), msg, duration).show();
    }

    protected void showToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode,
                                  Object... params) {
        hideProgressBar();
        if(re != null){
            int code = re.getHttpStatusCode();
            if(code == 401){
                showToast(re.getMessage());
            }
        }else {
            //showToast("Internal Server Error");
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        hideProgressBar();
    }

    @Override
    public void onPreExecute(int taskCode) {
        showProgressBar();
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

    public void showProgressBar(String message) {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.show();

        }
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
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

}
