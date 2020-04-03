package simplifii.framework.exceptionhandler;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Timestamp;

public class ExceptionHandler  implements UncaughtExceptionHandler{

	private static final int MAX_LENGTH_EXCEPTION_MESSAGE = 1000;

	private enum ENUM_EXCEPTIONS {
		TypeInvalidException, BlankInputException, InvalidRangeException, NullInputException
	};

	private final static StringBuilder NEW_LINE = new StringBuilder(
			System.getProperty("line.separator"));
	private static final String SEPARATOR_PIPE = "|";

	// classLevel and fullTrace variables are read from the file for the
	// stacktrace of different levels...READ them from file.
	private static final boolean CLASS_TRACE_LEVEL = false;
	private static final boolean DEFAULT_TRACE_LEVEL = false;
	private static final boolean FULL_TRACE_LEVEL = true;

	private static int numberOfStackTraces = 3;
	private Context context;
	private UncaughtExceptionHandler defaultExceptionHandler = Thread
			.getDefaultUncaughtExceptionHandler();

	public ExceptionHandler(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public static void handleException(Exception e, String className,
			Context callingPageRef) {

		String exceptionClass = e.getClass().getSimpleName();
		ENUM_EXCEPTIONS exclass = ENUM_EXCEPTIONS.valueOf(exceptionClass);
		System.out.println(exclass);
		System.out.println(getStackString(e, className));
		e.printStackTrace();

		switch (exclass) {

		case BlankInputException:
			Toast.makeText(callingPageRef, "BlankInputException",
					Toast.LENGTH_LONG).show();
			// handle(callingPageRef,"Title Comes Here");
			sendException(e, className, callingPageRef);
			break;
		case TypeInvalidException:
			Toast.makeText(callingPageRef, "TypeInvalidException",
					Toast.LENGTH_LONG).show();
			// handleWithActivityCall(callingPageRef, "TypeInValidException",
			// "New Activity Will Be Started", newacti);
			break;
		case NullInputException:
			Toast.makeText(callingPageRef, "NullInputException",
					Toast.LENGTH_LONG).show();
			// handleWithNegativeButton(callingPageRef, "NullInputException",
			// "Cancel Button");
			break;
		case InvalidRangeException:
			Toast.makeText(callingPageRef, "BlankInputException",
					Toast.LENGTH_LONG).show();
			// handleWithPositiveButton(callingPageRef, "InvalidRangeException",
			// "Okk Button");
			break;

		}

	}

	private static void sendException(Exception exception, String className,
			Context callingPageRef) {
		/*
		 * Intent serverLoggingIntent = new
		 * Intent(callingPageRef,ServerLoggingService.class);
		 * serverLoggingIntent.putExtra("StackTrace",getStackString(exception,
		 * className));
		 * 
		 * callingPageRef.startService(serverLoggingIntent);
		 */

	}

	@Override
	public void uncaughtException(Thread thread, Throwable e) {

		e.printStackTrace();
		// DBAdapter dbAdapter = new DBAdapter(this.context);
		// dbAdapter.putStackTrace(getStackString(e, null));
		System.exit(2);
		defaultExceptionHandler.uncaughtException(thread, e);

		// without system.exit(2) direct exit

		// System.exit(2);
		// defaultExceptionHandler.uncaughtException(thread, e);
		// direct to mnj without any error message

		// defaultExceptionHandler.uncaughtException(thread, e);
		// System.exit(2);
		// direct to mnj
	}

	private static void handle(Context callingPageRef, String title) {
		Builder.showAlert(callingPageRef, title);
	}

	private static void handleWithActivityCall(Context callingPageRef,
			String title, String message, Intent classIntent) {
		Builder.showAlertWithActivityCall(callingPageRef, title, message,
				classIntent);
	}

	private static void handleWithNegativeButton(Context callingPageRef,
			String title, String message) {
		Builder.showAlertWithNegativeButton(callingPageRef, title, message);
	}

	private static void handleWithPositiveButton(Context callingPageRef,
			String title, String message) {
		Builder.showAlertWithPositiveButton(callingPageRef, title, message);
	}

	public static String getStackString(Throwable e, String className) {

		boolean fullTrace = false, defaultLevel = false, classLevel = false;

		if (DEFAULT_TRACE_LEVEL)
			defaultLevel = true;
		if (FULL_TRACE_LEVEL)
			fullTrace = true;
		if (CLASS_TRACE_LEVEL)
			classLevel = true;

		StackTraceElement stackTrace[] = e.getStackTrace();
		StringBuilder trace = new StringBuilder();
		/*
		 * if (LoginUtil.isUserLoggedIn()) { // just to avoid nullPointer, it
		 * wont occur (try catch for safety) // -- gaurav try {
		 * trace.append(" UserId : " +
		 * LoginUtil.getLoggedInUser().getUniqueId()); } catch (Exception ex) {
		 * } }
		 */
		trace.append("TimeStamp:" + getTimeStamp());
		trace.append(" #ExceptionClass:" + e.getClass());
		trace.append(" #Cause:" + e.getCause());

		trace.append(" #Traces:");

		int loopCounter = stackTrace.length;

		if (classLevel && className != null) {

			className = className.replaceFirst("class ", "");
			// fullTrace = false;
			// defaultLevel = false;

		} else if (!fullTrace) {

			defaultLevel = true;
		}

		if (defaultLevel) {

			if (numberOfStackTraces < stackTrace.length)

				loopCounter = numberOfStackTraces;

			else {
				loopCounter = stackTrace.length;
			}
		}

		for (int i = 0; i < loopCounter; i++) {

			if (defaultLevel
					|| fullTrace
					|| (classLevel && stackTrace[i].getClassName().equals(
							className))) {

				if (i == loopCounter - 1)
					trace.append(stackTrace[i].toString());
				else {
					trace.append(stackTrace[i].toString() + SEPARATOR_PIPE);
				}
			}

		}

		String tempMessage = e.getMessage();
		if (tempMessage.length() > MAX_LENGTH_EXCEPTION_MESSAGE) {
			trace.append(tempMessage.substring(0, MAX_LENGTH_EXCEPTION_MESSAGE));
		} else {
			trace.append(" #Message:" + tempMessage);
		}

		trace.append(NEW_LINE);
		return trace.toString();

	}

	private static String getHandsetModel() {

		return android.os.Build.MODEL;
	}

	private static String getHandsetManufacturer() {
		return android.os.Build.MANUFACTURER;
	}

	private static String getHandsetAndroidVersion() {
		return android.os.Build.VERSION.RELEASE;

	}

	private static int getHandsetSDK() {

		return android.os.Build.VERSION.SDK_INT;
	}

	public static String getTimeStamp() {
		java.util.Date d = new java.util.Date();
		Timestamp time = new Timestamp(d.getTime());
		String t = time.toString().substring(0, time.toString().indexOf("."));
		System.out.println("Get TimeFragment Stamp from String " + t);
		return t;
	}

	public static StringBuilder getHandsetInfo() {
		StringBuilder trace = new StringBuilder("");
		trace.append(" #Handset Model:" + getHandsetModel() + " #Manufacturer:"
				+ getHandsetManufacturer() + " #Android Version:"
				+ getHandsetAndroidVersion());
		return trace;
	}
}
