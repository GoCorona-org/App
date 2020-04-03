package simplifii.framework.utility;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by nitin on 16/09/15.
 */
public class GetToken extends AsyncTask {

    Activity mActivity;
    String mScope;
    String mEmail;

    public GetToken(Activity activity, String name, String scope) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = name;
    }

    /**
     * Executes the asynchronous job. This runs when you call execute()
     * on the AsyncTask instance.
     */
    @Override
    protected Object doInBackground(Object... params) {
        try {
            String token = fetchToken();
            Logger.info("Token is :", "" + token);

            if (token != null) {
                // **Insert the good stuff here.**
                // Use the token to access the user's Google data.
                return token;
            }
        } catch (IOException e) {
            // The fetchToken() method handles Google-specific exceptions,
            // so this indicates something went wrong at a higher level.
            // TIP: Check for network connectivity before starting the AsyncTask.
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (null != o) {
//            ((LoginScreen) mActivity).onTokenReceived((String) o);
        } else {
//            ((LoginScreen) mActivity).onTokenReceived(null);
        }
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchToken() throws IOException {
//        try {
//            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
//        } catch (UserRecoverableAuthException userRecoverableException) {
//            // GooglePlayServices.apk is either old, disabled, or not present
//            // so we need to show the user some UI in the activity to recover.
//        } catch (GoogleAuthException fatalException) {
//            // Some other type of unrecoverable exception has occurred.
//            // Report and log the error as appropriate for your app.
//        }
        return null;
    }
}
