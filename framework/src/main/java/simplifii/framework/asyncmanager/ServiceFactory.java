package simplifii.framework.asyncmanager;

import android.content.Context;

import simplifii.framework.utility.AppConstants;


public class ServiceFactory {

    public static Service getInstance(Context context, int taskCode) {
        Service service = null;
        switch (taskCode) {
            case AppConstants.TASK_CODES.UPLOAD_IMAGE:
            case AppConstants.TASK_CODES.USER_UPLOAD_FILE:
                service = new FileUploadService();
                break;
            default:
                //service = new HttpRestService();
                service = new OKHttpService();
                break;

        }
        return service;
    }

}
