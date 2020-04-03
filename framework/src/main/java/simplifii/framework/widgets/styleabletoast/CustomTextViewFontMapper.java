package simplifii.framework.widgets.styleabletoast;

import android.text.TextUtils;

import simplifii.framework.utility.LocalHelperUtility;

public class CustomTextViewFontMapper {

    public static String getMappingFont(String font) {
        /*if (!TextUtils.isEmpty(font) && LocalHelperUtility.isArabic()) {
            switch (font) {
                case "GuessSans_Heavy.otf":
                case "GuessSans_Black.otf":
                case "GuessSans_Bold.otf":
                case "Campton_Bold.ttf":
                    return "TheSans_Bold.otf";
                case "Campton_Medium.ttf":
                case "Campton_ExtraLight.ttf":
                    return "TheSans_Plain.otf";
            }
        }*/
        //If local is default....
        return font;
    }

}
