package simplifii.framework.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatCheckBox;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;


public class CustomFontCheckBox extends AppCompatCheckBox {
    private static final String TAG = "CustomFont";

    public CustomFontCheckBox(Context context) {
        super(context);
    }

    public CustomFontCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public CustomFontEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
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
                asset = "fonts/"+ AppConstants.DEF_REGULAR_FONT;
            }
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Error to get typeface: " + e.getMessage());
            return false;
        }
        setTypeface(tf);
        return true;
    }


    public String getLowerCaseText(){
        return getText().toString().toLowerCase();
    }
}
