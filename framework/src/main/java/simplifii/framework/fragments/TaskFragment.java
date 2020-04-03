package simplifii.framework.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;



import simplifii.framework.asyncmanager.Service;
import simplifii.framework.asyncmanager.ServiceFactory;
import simplifii.framework.exceptionhandler.ExceptionHandler;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.Logger;

import org.json.JSONException;

public class TaskFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the task's
     * progress and results back to the Activity.
     */

    private Context ctx;

    public interface AsyncTaskListener {

        public void onPreExecute(int taskCode);

        public void onPostExecute(Object response, int taskCode,
                                  Object... params);

        public void onBackgroundError(RestException re, Exception e,
                                      int taskCode, Object... params);

    }


    private AsyncTaskListener asyncTaskListener;

    /**
     * Hold a reference to the parent Activity so we can report the task's
     * current progress and results. The Android framework will pass us a
     * reference to the newly created Activity after each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        Logger.info("Check AsyncTask Listener", asyncTaskListener + "");
        super.onAttach(activity);
        asyncTaskListener = (AsyncTaskListener) activity;
        ctx = activity;
        Logger.info("Check AsyncTask Listener", asyncTaskListener + "");
    }

    /**
     * This method will only be called once when the retained Fragment is first
     * created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
//        setRetainInstance(true);
        asyncTaskListener = (AsyncTaskListener) getActivity();
        // Create and execute the background task.

    }

	/*
     * public static TaskFragment getInstance(){
	 * 
	 * }
	 */

    /**
     * Set the callback to null so we don't accidentally leak the Activity
     * instance.
     */
    /*
	 * @Override public void onDetach() { super.onDetach(); asyncTaskListener =
	 * null; }
	 */
    public AsyncManager createAsyncTaskManagerObject(int taskCode) {
        return new AsyncManager(taskCode);
    }

    public AsyncManager createAsyncTaskManagerObject(int taskCode, boolean isFromFragment) {
        return new AsyncManager(taskCode);
    }

    /**
     * A dummy task that performs some (dumb) background work and proxies
     * progress updates and results back to the Activity.
     * <p/>
     * Note that we need to check if the callbacks are null in each method in
     * case they are invoked after the Activity's and Fragment's onDestroy()
     * method have been called.
     */

    public class AsyncManager extends AsyncTask<Object, Object, Object> {

        public static final String TAG = "XebiaAsyncManage";

        private int taskCode;
        private Object[] params;
        private Exception e;
        private long startTime;
        AsyncTaskListener ref;

        public AsyncManager(int taskCode) {

            this.taskCode = taskCode;

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
            Service service = ServiceFactory.getInstance(ctx, taskCode);
            Logger.info(TAG, "DoinBackGround");
            try {
                this.params = params;
                response = service.getData(params);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof JSONException) {
                    String exceptionMessage = "APIMANAGEREXCEPTION : ";

                    exceptionMessage += ExceptionHandler.getStackString(e,
                            asyncTaskListener.getClass().getName());
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