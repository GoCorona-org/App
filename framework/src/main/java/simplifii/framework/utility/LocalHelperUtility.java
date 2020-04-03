package simplifii.framework.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocalHelperUtility {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String language = getPersistedData(Locale.getDefault().getLanguage());
        return setLocale(context, language);

    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String language = getPersistedData(defaultLanguage);
        return setLocale(context, language);
    }

    public static String getLanguage() {
        return getPersistedData(Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }
        return updateResourcesLegacy(context, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    private static void persist(String language) {
        Preferences.saveData(SELECTED_LANGUAGE, language);
    }

    public static String getPersistedData(String defaultLanguage) {
        return Preferences.getData(SELECTED_LANGUAGE, defaultLanguage);
    }


}
