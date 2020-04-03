package simplifii.framework.utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.List;
import java.util.Set;


/**
 * Created by nitin on 24/08/15.
 */
public class Preferences {

    public static final String ENTER_DETAILS_SHOWCASE = "enterDetailsShowCase";
    private static final String PREF_NAME = "Pioneer_Prefs";
    public static final String LOGIN_KEY = "isUserLoggedIn";
    public static final String SAVE_USER_DEVICE_INFO = "saveUserDeviceInfo";
    public static final String LANGUAGE_SLECTED = "isLanguageSelected";
    public static final String CHECKBOX_SHOWCASE = "checkBocShowcase";
    public static final String FIRST_SHOWCASE = "firstShowcase";
    private static SharedPreferences xebiaSharedPrefs;
    private static SharedPreferences.Editor editor;
    private static Preferences sharedPreferenceUtil;

    public static void initSharedPreferences(Context context) {
        sharedPreferenceUtil = new Preferences();
        sharedPreferenceUtil.xebiaSharedPrefs = context.getSharedPreferences(
                PREF_NAME, Activity.MODE_PRIVATE);
        sharedPreferenceUtil.editor = sharedPreferenceUtil.xebiaSharedPrefs
                .edit();
    }

    public static Preferences getInstance() {

        return sharedPreferenceUtil;
    }

    private Preferences() {
        // TODO Auto-generated constructor stub
    }

    public static synchronized boolean saveData(String key, String value) {
        editor.putString(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, Set<String> value) {
        editor.putStringSet(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, boolean value) {
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, long value) {
        editor.putLong(key, value);
        return editor.commit();
    }


    public static synchronized boolean saveData(String key, float value) {
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static synchronized boolean saveData(String key, int value) {
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean isUserLoggerIn() {
        return getData(LOGIN_KEY, false);
    }
    public static boolean isLanguageSelected() {
        return getData(LANGUAGE_SLECTED, false);
    }
    public static boolean isShowEnterDetailsShowcase() {
        return getData(ENTER_DETAILS_SHOWCASE, false);
    }
    public static boolean isWhisListSelected() {
        return getData(CHECKBOX_SHOWCASE, false);
    }

	/*
     * public synchronized boolean saveData(String key, Set<String> value) {
	 * //editor.putStringSet(key, value); return editor.commit(); }
	 */

    public static synchronized boolean removeData(String key) {
        editor.remove(key);
        return editor.commit();
    }

    public static synchronized Boolean getData(String key, boolean defaultValue) {
        return xebiaSharedPrefs.getBoolean(key, defaultValue);
    }

    public static synchronized String getData(String key, String defaultValue) {
        return xebiaSharedPrefs.getString(key, defaultValue);
    }

    public static synchronized Set<String> getData(String key, Set<String> defaultValue) {
        return xebiaSharedPrefs.getStringSet(key, defaultValue);
    }

    public static synchronized float getData(String key, float defaultValue) {

        return xebiaSharedPrefs.getFloat(key, defaultValue);
    }

    public static synchronized int getData(String key, int defaultValue) {
        return xebiaSharedPrefs.getInt(key, defaultValue);
    }

    public static synchronized long getData(String key, long defaultValue) {
        return xebiaSharedPrefs.getLong(key, defaultValue);
    }

    /*
     * public synchronized Set<String> getData(String key, Set<String> defValue)
     * {
     *
     * // return naukriSharedPreferences.getStringSet(key, defValue); return
     * null; }
     */
    public static synchronized void deleteAllData() {

        sharedPreferenceUtil = null;
        editor.clear();
        editor.commit();
    }

    public static double getLat() {
        return Double.parseDouble(Preferences.getData(AppConstants.PARAMS.LAT, "0.0"));
    }

    public static double getLng() {
        return Double.parseDouble(Preferences.getData(AppConstants.PARAMS.LAT, "0.0"));
    }

    public static boolean isUserDeviceInfoSaveed() {
        return getData(SAVE_USER_DEVICE_INFO, false);
    }

    public static boolean isFirstTimeShowcaseOpened() {
        return getData(FIRST_SHOWCASE, false);
    }


//    public static String getAppLink() {
//        String link = getData(AppConstants.PREF_KEYS.APP_LINK, null);
//        if (TextUtils.isEmpty(link)) {
//            link = AppConstants.APP_LINK;
//        }
//        return link;
//    }


}
