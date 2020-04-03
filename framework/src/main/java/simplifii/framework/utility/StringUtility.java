package simplifii.framework.utility;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

public class StringUtility {

    public static SpannableStringBuilder getSuperScriptSupportedString(String s, int superScriptStartIndex, int superScriptEndIndex) {
        if (TextUtils.isEmpty(s)) {
            return new SpannableStringBuilder("");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        try {
            builder.setSpan(new SuperscriptSpan(), superScriptStartIndex, superScriptEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new RelativeSizeSpan(0.55f), superScriptStartIndex, superScriptEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception ignored) {
        }

        return builder;
    }

    public static String getFirstCharUppercase(String s) {
        String newString = "";
        try {
            if (!TextUtils.isEmpty(s)) {
                newString = s.toLowerCase().substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            }
        } catch (Exception ignored) {

        }
        return newString;
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


}
