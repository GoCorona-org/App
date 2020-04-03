package simplifii.framework.widgets.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import simplifii.framework.utility.Util;

public class FontManager {
    private static FontManager manager;
    private Context context;
    private ArrayList<String> fontNames = new ArrayList<String>();
    private HashMap<String, Typeface> typeFaceStore = new HashMap<String, Typeface>();

    private FontManager(Context context) {
        this.context = context;
    }

    public static FontManager getInstance(Context context) {
        if (manager == null) {
            manager = new FontManager(context);
        }
        return manager;
    }

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context                    to work with assets
     * @param defaultFontNameToOverride  for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), "font/" + customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            Log.e("to=>> " + customFontFileNameInAssets, "from=>> " + defaultFontNameToOverride);
        }
    }

    //Returns the font typeface based on the string passed to it using the typefacestore hashmap.
    public Typeface getTypeface(String fontName) {
        int index = fontNames.indexOf(fontName);
        if (index == -1) {
            fontNames.add(fontName);
        } else {
            fontName = fontNames.get(index);
        }
        return typeFaceStore.get(fontName);

    }

    //Sets the typeface or font based on the view and name of typeface passed
    public void setTypeFace(View view, String fontName) {
        if (!(view instanceof TextView)) {
            return;
        }
        int index = fontNames.indexOf(fontName);
        if (index == -1) {
            fontNames.add(fontName);
        } else {
            fontName = fontNames.get(index);
        }
        Typeface typeface = typeFaceStore.get(fontName);
        if (typeface == null) {
            typeface = Util.findTypeface(context, fontName);
            typeFaceStore.put(fontName, typeface);
        }
        if (typeface != null) {
            ((TextView) view).setTypeface(typeface);
        } else {
            ((TextView) view).setTypeface(Typeface.DEFAULT);
        }
    }

    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "font.ttf"));
            }
        } catch (Exception e) {
        }
    }
}
