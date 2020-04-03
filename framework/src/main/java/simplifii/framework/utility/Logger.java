package simplifii.framework.utility;

import android.util.Log;

public class Logger {
    // Enable the line below to start all logging
    private static final int logLevel = 0;
    // Enable the line below to stop all logging
    //private static final int logLevel = 7;

    public static void debug(String category, String message) {
        if (isDebugEnabled()) {
            Log.d(category, message);
        }
    }

    public static void info(String category, String message) {
        if (isInfoEnabled()) {
            Log.i(category, message);
        }
    }

    public static void warn(String category, String message) {
        if (isWarnEnabled()) {
            Log.w(category, message);
        }
    }

    public static void warn(String category, String message, Throwable throwable) {
        if (isWarnEnabled()) {
            Log.w(category, message, throwable);
        }
    }

    public static void warn(String category, StringBuffer message,
                            Throwable throwable) {
        if (isWarnEnabled()) {
            warn(category, message.toString(), throwable);
        }
    }

    public static void error(String category, String message) {
        if (isErrorEnabled()) {
            Log.e(category, message + "");
        }
    }

    public static void error(String category, String message,
                             Throwable throwable) {
        if (isErrorEnabled()) {
            Log.e(category, message, throwable);
        }
    }

    public static void error(String category, StringBuffer message,
                             Throwable throwable) {
        if (isErrorEnabled()) {
            error(category, message.toString(), throwable);
        }
    }

    public static boolean isDebugEnabled() {
        return logLevel <= Log.DEBUG; // 3
    }

    public static boolean isInfoEnabled() {
        return logLevel <= Log.INFO; // 4
    }

    public static boolean isWarnEnabled() {
        return logLevel <= Log.WARN; // 5
    }

    public static boolean isErrorEnabled() {
        return logLevel <= Log.ERROR; // 6
    }

}
