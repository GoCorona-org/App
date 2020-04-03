package simplifii.framework.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import simplifii.framework.R;

public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {

    public CustomSwipeRefreshLayout(Context context) {
        super(context);
        setColorSchemeResources(R.color.color_primary,
                R.color.color_primary);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.color_primary,
                R.color.color_primary);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //Fix for support lib bug, happening when onDestroy() is
            return true;
        }
    }

}
