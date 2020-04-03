package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;


public class CustomFontACTV extends AppCompatAutoCompleteTextView {
    private static final String TAG = "CustomFont";

    public CustomFontACTV(Context context) {
        super(context);
    }

    public CustomFontACTV(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontACTV(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTxtView);
        String customFont = a
                .getString(R.styleable.CustomFontTxtView_customFont);

        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            if (asset == null || "".equals(asset)) {
                asset = AppConstants.DEF_REGULAR_FONT;
            }
            tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + asset);
        } catch (Exception e) {
            Log.e(TAG, "Error to get typeface: " + e.getMessage());
            return false;
        }
        setTypeface(tf);
        return true;
    }
}
