package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import simplifii.framework.widgets.manager.FontManager;

/**
 * * 5/11/15.
 */
public class IconView extends AppCompatTextView {
    private String typeFace;
    private Context context;
    private ColorStateList textColors;

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setCTypeFace("fonts/icomoon.ttf");
        textColors = getTextColors();
    }

    public String getCTypeFace() {
        return typeFace;
    }

    public void setCTypeFace(String tf) {
        typeFace = tf;
        if (tf != null) {
            FontManager.getInstance(context).setTypeFace(this, tf);
        }
    }
}
