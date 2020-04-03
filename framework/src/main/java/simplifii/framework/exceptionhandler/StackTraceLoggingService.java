package simplifii.framework.exceptionhandler;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import simplifii.framework.utility.Logger;


public class StackTraceLoggingService extends IntentService {

    public final static String TAG = "StackTraceLoggingService";
    public final static int NUMBER_OF_TRACES_TO_SEND = 5;
    public final static int MAX_STACK_ENTRIES_IN_DB = 5;

    public StackTraceLoggingService() {
        super(TAG);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate() {
        Logger.error(TAG, "on create");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Logger.error(TAG, "on start");
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.error(TAG, "on bind");
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.error(TAG, "on destroy");
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        /*try {
            Logger.error(TAG, "on handle intent");
			DBAdapter dbAdapter = new DBAdapter(getApplicationContext());

			Cursor c = dbAdapter.getStackTraces(NUMBER_OF_TRACES_TO_SEND);
			Logger.info("stack", "STACK TRACE cursor size" + c.getCount());
			StringBuilder traces = new StringBuilder("");

			if (c != null && c.getCount() > 0) {

				traces = XebiaExceptionHandler.getHandsetInfo();
				traces.append(System.getProperty("line.separator"));

				while (c.moveToNext()) {
					
					 traces.append(c.getString(c
					 .getColumnIndex(DBconstant.COLUMN_STACKTRACE_DATA)));
					 
					traces.append(System.getProperty("line.separator"));
				}

				c.close();

				Logger.info("log", "trace to send " + traces.toString());
				if (intent != null) {
					Service stackService = ServiceFactory.getInstance(this,
							CommonVars.TASK_CODES.SEND_STACKTRACE);
					try {
						handleServiceResponse(stackService.getData(traces
								.toString()));
					} catch (Exception e) {
						// nothing to do as next time when this service runs it
						// will
						// try to send logs that time

						e.printStackTrace();

					}

				}
			}
		} catch (Exception e) {

		}
*/
    }

    private void handleServiceResponse(Object response) {
        /*int responseCode = -1;
		if (response instanceof Integer) {
			responseCode = (Integer) response;

			if (responseCode == 200) {

				Logger.info("stack", "Flushing stack trace table");
				DBAdapter dbAdapter = new DBAdapter(getApplicationContext());
				dbAdapter.clearStackTraceTable();
			}
		}*/
    }

}
