package simplifii.framework.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundTopCornerImageView extends AppCompatImageView {

    private float radius = 14.0f;
    private Path path;
    private RectF rect;

    public RoundTopCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundTopCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundTopCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getWidth());
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}