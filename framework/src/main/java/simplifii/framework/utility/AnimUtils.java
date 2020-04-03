package simplifii.framework.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AnimUtils {

    public static void fadeIn(int duration, long delay, Interpolator interpolator, View... views) {
        AnimatorSet animSet = new AnimatorSet();
        ArrayList<Animator> objectAnimators = new ArrayList<>();

        for (View view : views) {
            objectAnimators.add(ObjectAnimator.ofFloat(view, "alpha", 1f));
        }
        animSet.playTogether(objectAnimators);
        animSet.setInterpolator(interpolator);
        animSet.setStartDelay(delay);
        animSet.setDuration(duration);
        animSet.start();
    }

    public static void slideDownOpen(View layout) {
        layout.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = layout.getLayoutParams().width;
        layout.setLayoutParams(layoutParams);
    }

    public static void slideUpClose(final View layout) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(layout.getMeasuredHeight(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.height = val;
                layout.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                layout.setVisibility(View.GONE);
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    public static void showOrHideLayoutBySliding(final View layout) {
        switch (layout.getVisibility()) {
            case View.VISIBLE:
                AnimUtils.slideUpClose(layout);
                break;

            case View.INVISIBLE:
            case View.GONE:
                AnimUtils.slideDownOpen(layout);
                break;
        }
    }
}
