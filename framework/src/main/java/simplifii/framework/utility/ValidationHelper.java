package simplifii.framework.utility;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.widget.Spinner;

import java.io.File;
import java.util.regex.Pattern;

public class ValidationHelper {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final int MIN_LENGTH_PASSWORD = 4;

    public static boolean isEmailValid(String email) {
        if (email != null && !email.isEmpty()) { //check empty for fast execution....
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            return pattern.matcher(email).matches();
        }
        return false;
    }

    public static boolean isEmailNotValid(String email) {
        return !isEmailValid(email);
    }

    public static boolean isValidMobile(String mobileNo) {
        if (mobileNo.length()>=9){
            return true;
        }
        return false;

    }

    public static boolean isValidPincode(String pinCOde) {
        if (pinCOde.length()>5){
            return true;
        }
        return false;

    }

    public static boolean isPasswordValid(String password) {
        return isNotEmpty(password.trim()) && password.length() >= MIN_LENGTH_PASSWORD;
    }

    public static boolean isPasswordNotValid(String password) {
        return !isPasswordValid(password);
    }

    public static boolean isEmpty(String field) {
        return TextUtils.isEmpty(field);
    }

    public static boolean isNotEmpty(String field) {
        return !TextUtils.isEmpty(field);
    }

    public static boolean isEquals(String fieldA, String fieldB, boolean ignoreCase) {
        return (fieldA != null && fieldB != null) && (ignoreCase ? fieldA.equalsIgnoreCase(fieldB) : fieldA.equals(fieldB));
    }

    public static boolean isNotEquals(String fieldA, String fieldB, boolean ignoreCase) {
        return !isEquals(fieldA, fieldB, ignoreCase);
    }

    public static boolean isFileSizeValid(Context context, Uri uri) {
        Uri fileUri = uri;
        Cursor cursor = context.getContentResolver().query(fileUri,
                null, null, null, null);
        cursor.moveToFirst();
        long fileSizeInBytes = cursor.getLong(cursor.getColumnIndex(OpenableColumns.SIZE));
        cursor.close();
//        File file = new File(uri.getPath());

//        if (file != null && file.exists()) {
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;

            return fileSizeInMB < 2;
//        }
//        return false;
    }

    public static boolean isFileSizeValid(File file) {
        if (file != null && file.exists()) {
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;

            return fileSizeInMB < 2;
        }
        return false;
    }

    public static boolean isFileSizeNotValid(File file) {
        return !isFileSizeValid(file);
    }

    public static boolean isItemNotSelected(Spinner spinner) {
        return spinner.getSelectedItemPosition() >= 1;
    }
}
