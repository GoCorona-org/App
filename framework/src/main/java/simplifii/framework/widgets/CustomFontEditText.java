package simplifii.framework.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import simplifii.framework.R;
import simplifii.framework.utility.AppConstants;


public class CustomFontEditText extends AppCompatEditText {
    private static final String TAG = "CustomFont";

    public CustomFontEditText(Context context) {
        super(context);
    }

    public CustomFontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
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


    public String getLowerCaseText() {
        return getText().toString().toLowerCase();
    }
}
