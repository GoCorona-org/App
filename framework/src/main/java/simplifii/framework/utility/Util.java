package simplifii.framework.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.provider.UserDictionary;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import simplifii.framework.activity.BaseActivity;

public class Util {

    public static float randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static final String JAVA_DATE_PATTERN = "E MMM dd HH:mm:ss Z yyyy";
    public static final String BASE_DATE_FORMAT = "dd/MM/yyyy";
    public static final String BASE_DATE_FORMAT_TIME = "dd/MM/yyyy hh:mm a";
    public static final String INDIAN_STANDARD_TIMEZONE = "IST";
    public static final String UTC_STANDARD_TIMEZONE = "UTC";
    public static final String PREVIEW_DATE_FORMAT = "MMM d, yyyy, h:mm a";


    public static String getParseRangeQuery(String startDate, String endDate) {
        return String
                .format("where={'createdAt':{'$gte':{'__type':'DateFragment','iso':'%s'},'$lte':{'__type':'DateFragment','iso':'%s'}}}",
                        startDate, endDate);
    }

    public static Bitmap getBitmapFromUri(Context ctx, Uri imageUri) {
        try {
            return MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static byte[] getBytesFromBitmap(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static String setDate(int year, int month, int dayOfMonth) {
        String m, d;
        if (month < 10) {
            m = "0" + month;
        } else {
            m = String.valueOf(month);
        }
        if (dayOfMonth < 10) {
            d = "0" + dayOfMonth;
        } else {
            d = String.valueOf(dayOfMonth);
        }
        return year + "-" + m + "-" + d;
    }

    public static void addToLocalDictionary(Context context, String fullWord, String hint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // On JellyBean & above, you can provide a shortcut and an explicit Locale
            UserDictionary.Words.addWord(context, fullWord, 100, hint, Locale.getDefault());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            UserDictionary.Words.addWord(context, fullWord, 100, UserDictionary.Words.LOCALE_TYPE_CURRENT);
        }
    }

    public static void getEmail(String mailTo, String title, String body, String path, Context context) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("application/image");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mailTo});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        if (!TextUtils.isEmpty(path)) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + new File(path)));
        }
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }


    public interface DialogListener {
        public void onOKPressed(DialogInterface dialog, int which);

        public void onCancelPressed(DialogInterface dialog, int which);
    }

    public static int getQuantityFromEditText(EditText etQty) {
        int qty = 0;
        try {
            qty = Integer.parseInt(etQty.getText().toString());

        } catch (Exception e) {

        }
        return qty;
    }

    public static AlertDialog createAlertDialog(Context context,
                                                String message, String title, boolean isCancelable, String okText,
                                                String cancelText, final DialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setCancelable(isCancelable);
        builder.setPositiveButton(okText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        listener.onOKPressed(dialog, which);
                    }
                });
        builder.setNegativeButton(cancelText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        listener.onCancelPressed(dialog, which);
                    }
                });

        return builder.create();
    }

    public static boolean isConnectingToInternet(Context ctx) {

        boolean NetConnected = false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                Logger.info("tag", "couldn't get connectivity manager");
                NetConnected = false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            NetConnected = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Logger.error("Connectivity Exception",
                    "Exception AT isInternetConnection");
            NetConnected = false;
        }
        return NetConnected;

    }

    public static String getStringFromInputStream(InputStream is) {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader buReader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 50000);

            String line;

            while ((line = buReader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response.toString();

    }

    public static void startItemActivity(Context ctx, Class activityClass) {
        Intent i = new Intent(ctx, activityClass);
        ctx.startActivity(i);
    }

    public static String getStringFromHTMLContent(String s) {
        String str = s.replaceAll("<br />", "<br /><br />").replaceAll(
                "&nbsp;", "<br /><br />");
        Log.e("String After", str);
        return str;
    }

    public static String convertDateFormat(Date date,
                                           String reqDateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(reqDateFormat);
        return sdf.format(date);
    }

    public static String convertDateFormat(String currentDate,
                                           String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                JAVA_DATE_PATTERN);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormat(String currentDate,
                                           String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            TimeZone istTimeZone = TimeZone.getTimeZone(UTC_STANDARD_TIMEZONE);
//            TimeZone istTimeZone = TimeZone.getTimeZone(INDIAN_STANDARD_TIMEZONE);
            currentDateFormat.setTimeZone(istTimeZone);
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentDate;
    }

    public static String convertDateFormatWOTimeZone(String currentDate,
                                                     String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
//            TimeZone istTimeZone = TimeZone.getTimeZone(INDIAN_STANDARD_TIMEZONE);
//            currentDateFormat.setTimeZone(istTimeZone);
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentDate;
    }

    public static Object getColumnObject(Cursor c, String columnName) {
        int colIndex = c.getColumnIndex(columnName);
        switch (c.getType(colIndex)) {

            case Cursor.FIELD_TYPE_BLOB:
                return c.getBlob(colIndex);
            case Cursor.FIELD_TYPE_STRING:
                return c.getString(colIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return c.getFloat(colIndex);
            case Cursor.FIELD_TYPE_INTEGER:
                return c.getInt(colIndex);
            case Cursor.FIELD_TYPE_NULL:
                return null;
        }
        return null;

    }

    public static String getCombinedString(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static String DBL_FMT = "%.2f";

    public static Date convertStringToDate(String dateString, String dateFormat)
            throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.parse(dateString);
    }

    public static Date convertDateFormat(Date date, String reqDateFormat,
                                         String currentDateFormat) {
        String formattedDateString = convertDateFormat(date.toString(),
                currentDateFormat, reqDateFormat);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            return format.parse(formattedDateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static SpannableString getSppnnableString(String wholeText,
                                                     String spannedText, int colorId) {
        SpannableString spanString = new SpannableString(wholeText);
        try {
            int index = wholeText.indexOf(spannedText);
            if (index == -1) {
                return spanString;
            }
            spanString.setSpan(new ForegroundColorSpan(colorId), index, index
                    + spannedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        return spanString;
    }

    public static void setSppnnableString(TextView view, String wholeText,
                                          int colorId, String... spannedText) {
        SpannableString spanString = new SpannableString(wholeText);
        try {
            for (String s : spannedText) {
                int index = wholeText.indexOf(s);
                if (index != -1) {
                    spanString.setSpan(new ForegroundColorSpan(colorId), index, index
                            + s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
            }

        } catch (Exception e) {

        }
        view.setText(spanString);
    }


    public static String getAndroidId(Context ctx) {
        return Secure.getString(ctx.getContentResolver(),
                Secure.ANDROID_ID);
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public static void setBackground(View view, String color) {
        if (TextUtils.isEmpty(color)) {
            setBackground(view, Color.RED);
        } else {
            setBackground(view, Color.parseColor(color));
        }
    }

    public static void setBackground(View view, int color) {
//        Log.d(TAG, "H:" + view.getHeight() + ", W:" + view.getWidth());
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
//        view.setBackground(shape);
    }

    public static String getFirstCharacter(String title) {
        if (TextUtils.isEmpty(title)) return "";
        return title.trim().charAt(0) + "";
    }

    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SocialEvening");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");

        return mediaFile;
    }

    public static void performCrop(Activity ctx, Uri picUri, int reqCode) {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/png");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate <span id="IL_AD11" class="IL_AD">output</span> X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // <span id="IL_AD5" class="IL_AD">retrieve data</span> on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            ctx.startActivityForResult(cropIntent, reqCode);
        }
        // respond to users whose devices do <span id="IL_AD4" class="IL_AD">not support</span> the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(ctx, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

//            try {
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return null;
//            }

            bundle.putString("idFacebook", id);
            String firstName = "", lastName = "";
            if (object.has("first_name")) {
                Preferences.saveData("first_name", object.getString("first_name"));
                bundle.putString("name", object.getString("first_name"));
                firstName = object.getString("first_name");
            }
            if (object.has("last_name")) {
                bundle.putString("last_name", object.getString("last_name"));
                Preferences.saveData("last_name", object.getString("last_name"));
                lastName = object.getString("last_name");
            }

            Preferences.saveData("name", firstName + " " + lastName);

            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
                Preferences.saveData("email", object.getString("email"));
            }
            if (object.has("gender")) {
                bundle.putString("gender", object.getString("gender"));
                String gender = object.getString("gender");
                if ("male".equalsIgnoreCase(gender)) {
                    Preferences.saveData("gender", "M");
                } else if ("female".equalsIgnoreCase("gender")) {
                    Preferences.saveData("gender", "F");
                }
            }
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (Exception e) {

        }
        return null;
    }


    public static void setSpannableColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i < 0) {
            return;
        }
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        view.setText(str);
    }


    public static String getAppendedString(String s1, String sep, String s2) {
        if (TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2)) {
            return "";
        } else if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)) {
            return s1 + sep + s2;
        } else if (TextUtils.isEmpty(s1)) {
            return s2;
        } else {
            return s1;
        }
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    public static File getFile(Bitmap bMap) throws Exception {
        bMap = resizeBitMap(bMap,512);
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/JugoApp";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, System.currentTimeMillis() + ".png");
        FileOutputStream fOut = new FileOutputStream(file);
        bMap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        return file;
    }
    public static Bitmap resizeBitMap(Bitmap image,int size) {
        int maxSize = size;
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static void setReminderIntoCalender(Activity activity, long timeInMilies, String eventTitle, String location){

        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("calendar_id", 1);
        intent.putExtra("eventLocation", location);
        intent.putExtra("beginTime", timeInMilies);
        intent.putExtra("allDay", false);
        intent.putExtra("endTime", timeInMilies+10*60*1000);
        intent.putExtra("title", eventTitle);
        intent.putExtra("hasAlarm", 1);
        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        activity.startActivity(intent);

        /*Uri EVENTS_URI = Uri.parse(getCalendarUriBase(activity) + "events");
        ContentResolver cr = activity.getContentResolver();*/

// event insert
        /*ContentValues values = new ContentValues();
        values.put("calendar_id", 1);
        values.put("title", eventTitle);
        values.put("allDay", 0);
        values.put("eventLocation", location);
        values.put("dtstart", timeInMilies); // event starts at 11 minutes from now
        values.put("dtend", timeInMilies+10*60*1000); // ends 60 minutes from now
        values.put("hasAlarm", 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        Uri event = cr.insert(EVENTS_URI, values);

// reminder insert
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(activity) + "reminders");
        values = new ContentValues();
        values.put( "event_id", Long.parseLong(event.getLastPathSegment()));
        values.put( "method", 1 );
        values.put( "minutes", 10 );
        cr.insert( REMINDERS_URI, values );*/

    }

    private static String getCalendarUriBase(Activity act) {

        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = act.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = act.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

    public static Bitmap getOriginalBitmapFromPath(String absolutePath){
        Bitmap myBitmap = BitmapFactory.decodeFile(absolutePath);

        try {
            ExifInterface exif = new ExifInterface(absolutePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
        }
        catch (Exception e) {

        }
        return myBitmap;
    }

    public static Typeface findTypeface(Context context, String typeface) {
        AssetManager assets = context.getApplicationContext().getAssets();
        return Typeface.createFromAsset(assets, ("fonts" + File.separator)
                + typeface);
    }

    public static String getDeviceInfo(Context activity) {
        String details = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("OS.VERSION", "" + Build.VERSION.RELEASE);
            jsonObject.put("OS.VERSION", "" + Build.VERSION.RELEASE);
            jsonObject.put("VERSION.SDK.NUMBER", "" + Build.VERSION.SDK_INT);
            jsonObject.put("BRAND", "" + Build.BRAND);
            jsonObject.put("MANUFACTURER", "" + Build.MANUFACTURER);
            jsonObject.put("MODEL", "" + Build.MODEL);
            jsonObject.put("PRODUCT", "" + Build.PRODUCT);
            jsonObject.put("SERIAL", "" + Build.SERIAL);
            jsonObject.put("TIME", "" + Build.TIME);
            jsonObject.put("isAndroidEmulator", "" + isAndroidEmulator(activity));
            jsonObject.put("isMobileNetwork", "" + isMobileNetwork(activity));
            jsonObject.put("NETWORK TYPE", "" + getNetworkClass(activity));
            jsonObject.put("RAM SIZE", "" + getTotalRamSize(activity));
            jsonObject.put("SCREEN SIZE", "" + getScreenSize(activity));
            jsonObject.put("AVAILABLE SPACE", "" + getAvailableSpace(activity));

            Log.d("Device Details", jsonObject.toString());
            details = jsonObject.toString();
        } catch (Exception e) {
            Log.d("Device Details", details);
        }
        return details;
    }

    private static boolean isAndroidEmulator(Context activity) {
        boolean isEmulator = false;
        try {
            String product = Build.PRODUCT;
            if (product != null) {
                isEmulator = product.equals("sdk") || product.contains("_sdk") || product.contains("sdk_") || !isMobileNetwork(activity);
            }
        } catch (Exception e) {
        }
        return isEmulator;
    }

    private static boolean isMobileNetwork(Context baseActivity) {
        try {
            TelephonyManager tm = (TelephonyManager) baseActivity.getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = tm != null ? tm.getNetworkOperatorName() : null;
            return !"Android".equals(networkOperator + "");
        } catch (Exception e) {
            return true;
        }
    }

    private static String getNetworkClass(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;
            if (cm != null) {
                info = cm.getActiveNetworkInfo();
            }
            if (info == null || !info.isConnected())
                return "-"; //not connected
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return "WIFI";
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int networkType = info.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return "2G";
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "3G";
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "4G";
                    default:
                        return "?";
                }
            }
        } catch (Exception e) {
        }
        return "?";
    }

    private static long getTotalRamSize(Context baseActivity) {
        long totalMemory = 0;
        try {
            ActivityManager actManager = (ActivityManager) baseActivity.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            if (actManager != null) {
                actManager.getMemoryInfo(memInfo);
            }
            totalMemory = memInfo.totalMem;
        } catch (Exception e) {
        }
        return totalMemory;
    }

    private static String getAvailableSpace(Context baseActivity) {
        String availableSpace = "";
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            availableSpace = Formatter.formatFileSize(baseActivity, availableBlocks * blockSize);
        } catch (Exception e) {
        }
        return availableSpace;
    }

    private static String getScreenSize(Context baseActivity) {
        String size = "";
        int height, width;
        try {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((BaseActivity) baseActivity).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            height = displaymetrics.heightPixels;
            width = displaymetrics.widthPixels;
            size = height + " x " + width;
        } catch (Exception e) {
        }
        return size;
    }

    public static int getVisiblePercent(View subView, View parentView) {
        if (isViewVisible(subView, parentView)) {
            Rect r = new Rect();
            subView.getGlobalVisibleRect(r);
            double sVisible = r.width() * r.height();
            double sTotal = subView.getWidth() * subView.getHeight();
            return (int) (100 * sVisible / sTotal);
        } else {
            return -1;
        }

    }

    private static boolean isViewVisible(View subView, View parentView) {
        if (subView != null && parentView != null) {
            Rect scrollBounds = new Rect();
            parentView.getHitRect(scrollBounds);
            if (subView.getLocalVisibleRect(scrollBounds)) {
                return true;
            }
        }

        return false;
    }

    public static String getYouTubeLinkId(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("v=")){

                String[] array =  url.split("v=", 0);
                if(array != null && array.length>1){
                    array = array[1].split("&");
                    if(array != null && array.length>0){
                        return array[0];
                    }
                }
            }
        }

        return url;
    }


}

